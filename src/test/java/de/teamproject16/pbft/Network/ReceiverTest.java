package de.teamproject16.pbft.Network;

import de.luckydonald.utils.mockups.SocketMockup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import de.luckydonald.utils.mockups.ServerSocketMockup;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * @author luckydonald
 * @since 26.10.2016
 **/
public class ReceiverTest {
    Receiver r = null;
    ServerSocketMockup s = null;
    InputStream is = null;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReceiver() throws Exception {
        String expected = "{'node':1,'type':1,'value':5.3,'sequence_no':3}\n".replace("'", "\"");
        String send = "ANSWER 48\n" + expected;

        SocketMockup s = new SocketMockup();
        s.in = new ByteArrayInputStream(send.getBytes());

        Receiver r = new Receiver();
        String result = r.receiveFromSocket(s);

        assertEquals("Socket Read", expected, result);
    }

    @Test
    public void testAddMessage() throws Exception {

    }
}