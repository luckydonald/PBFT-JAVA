package de.teamproject16.pbft.Network;

import de.teamproject16.pbft.Messages.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Test with netcat in terminal:
 * $ nc localhost 4458
 * ANSWER 19
 * {"hello": "world"}
 */


/**
 * Class to which will receive Messages.
 * It will call {@link Receiver#addMessage(String) this.addMessage(String messageContent)} to process incoming messages.
 **/
public class Receiver extends Thread {
    private static String ANSWER_SYNTAX = "ANSWER ";
    private Logger logger = null;

    public Receiver() {
        this.setName("Receiver");
    }

    public void receiver() throws IOException {
        this.receiver(this.newServerSocket());
    }
    public boolean do_quit = false;

    public void receiver(ServerSocket server) {
        while (!do_quit) {  // For each connection do  // TODO: self.do_quit or similar
            Socket socket;
            try {
                socket = server.accept();  // throws IOException
            } catch (IOException e) {
                this.getLogger().finest("Could not accept server client: " + e.getLocalizedMessage());
                continue;
            }
            try {
                String received = this.receiveFromSocket(socket);
                this.addMessage(received);
                // now the client would close the sockets. We do, too, in the finally statement.
            } catch (CloseConnectionPlease e) {
                this.getLogger().finest("Requested to close connection prematurely: " + e.getLocalizedMessage());
            } catch (IOException e){
                this.getLogger().warning("IOException");
                e.printStackTrace();
            } finally {
                // in case it should close prematurely (by throwing CloseConnectionPlease)
                // in case of normal operation after a received message.
                try {
                    socket.close();  // throws IOException
                } catch (IOException e) {
                    this.getLogger().warning("Ignored failed socket closing.");
                }
            }
        }
    }

    String receiveFromSocket(Socket socket) throws IOException, CloseConnectionPlease {
        BufferedInputStream input = new BufferedInputStream(socket.getInputStream()); // throws IOException

        int completed = -ANSWER_SYNTAX.length();
        /**
         -7 = ^ANSWER 123\n
         -6 = A^NSWER 123\n
         -1 = ANSWER^ 123\n
         0 = ANSWER ^123\n  => ready to read the number
         **/

        ByteBuffer buff = null;
        // buff.put(ANSWER_SYNTAX.getBytes()); // TODO: Unit test :D
        long length_of_answer = -1;
        buff = ByteBuffer.allocate(520);  // ANSWER <int>\n
        while (length_of_answer == -1) {  // Length detection: "ANSWER 123\n"
            int char_ = input.read();  // throws IOException
            if (char_ == -1) {
                // Client disconnected prematurely: Close connection; connect to next incoming client.
                throw new CloseConnectionPlease("Client disconnected.");
            }
            if (completed < 0) {
                // must be inside ANSWER_SYNTAX
                if ((char) char_ != ANSWER_SYNTAX.charAt(ANSWER_SYNTAX.length() + completed)) {
                    // if the received character not as expected of the ANSWER_SYNTAX header.
                    //Wrong ANSWER_SYNTAX => Close (abort) connection; connect to next incoming client.
                    throw new CloseConnectionPlease("Syntax error in ANSWER header.");
                }
                completed++;
            } else {
                if ((char) char_ != '\n') {  // not end yet. // line breaks in json strings should be "\\" and "n".
                    // put it into our number buffer.
                    buff.put((byte) char_);
                } else { // end of ANSWER_SYNTAX+number+\n, after number
                    // linebreak: we have the ending.
                    // http://stackoverflow.com/a/22717246
                    byte[] str_bytes = new byte[buff.position()];
                    buff.rewind();
                    buff.get(str_bytes);
                    // http://stackoverflow.com/a/17355227
                    String str = new String(str_bytes, Charset.forName("UTF-8"));
                    this.getLogger().finest("\"ANSWER <length>\\n\" header read: " + buff + "> " + str);
                    length_of_answer = Integer.parseInt(str);
                    break;  // (while should end anyway)
                }
            }
        }
        // prepare content reading
        this.getLogger().finer("Waiting to receive " + length_of_answer + " bytes.");
        buff = ByteBuffer.allocate((int) length_of_answer);
        int bytes_read = 0;
        while (bytes_read < length_of_answer) {
            int char_ = input.read();  // throws IOException
            getLogger().finest("READ: "+ Character.toString((char)char_) + ", " + bytes_read + "<" + length_of_answer);
            if (char_ == -1) {
                // Client disconnected prematurely: Close connection; connect to next incoming client.
                throw new CloseConnectionPlease("Client disconnected.");
            }
            if (bytes_read+1 >= length_of_answer) {
                if (bytes_read+1 > length_of_answer) {
                    // moved inside other if to have less to checks on normal execution -> performance
                    throw new CloseConnectionPlease("Read to much.");
                }
                if (char_ == '\n') {
                    // last char should be '\n'
                    getLogger().finer("Skipping ending linebreak.");
                } else {
                    getLogger().warning("Message did not end with '\\n', ignoring!");
                    throw new CloseConnectionPlease("Not ending with '\\n'");
                }
            }
            bytes_read++;
            buff.put((byte) char_);
        }
        String result = new String(buff.array(), Charset.forName("UTF-8"));
        this.getLogger().fine("Received: " + result);
        return result;
        // Now the client would close the sockets.
        // We do, too, in the finally statement, of the method `receiver()` calling this.
    }

    public Logger getLogger() {
        if (logger == null) {
            logger =  Logger.getLogger(this.getClass().getCanonicalName());
        }
        logger.setLevel(Level.ALL);
        return logger;
    }


    ServerSocket newServerSocket() throws IOException {
        ServerSocket s = new ServerSocket(4458);  // throws IOException
        s.setReuseAddress(true);
        return s;
    }

    @Override
    public void run() {
        try {
            this.receiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String json) {
        synchronized (this) {
            try {
                System.out.println("RECEIVED SOMETHING!!!!!" + json);
                JSONObject data = new JSONObject(json);
                MessageQueue.messageQueue(Message.messageConvert(data));
            } catch (JSONException e) {
                System.out.println("Convert the String to JSONObject failed.");
                e.printStackTrace();
            }
            this.notifyAll();  // In case someone is waiting for new messages.
        }
    }

}
