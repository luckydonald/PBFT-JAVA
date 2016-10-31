package de.teamproject16.pbft.Network.Database;

import de.luckydonald.utils.ObjectWithLogger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * For throwing the sending json to the database.
 *
 * @author luckydonald
 * @since 31.10.2016
 **/
public class Dumper extends ObjectWithLogger {
    public static void send(String json) {
        try {
            URL url = new URL("http://api/dump/"); // TODO: env
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(json);
            out.close();
            httpCon.getInputStream();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}