package de.teamproject16.pbft.Network.Database;

import de.luckydonald.utils.ObjectWithLogger;
import de.luckydonald.utils.dockerus.Dockerus;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.luckydonald.utils.dockerus.IDoNotWantThisException;

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

    public static Dockerus dockerus = null;

    public static void send(String json) {
        try {
            URL url = new URL(getApiHost() + "/dump/"); // TODO: env
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

    public static String getApiHost() {
        try {
            return DockerusAuto.getInstance().getApiHost();
        } catch (IDoNotWantThisException e) {
            e.printStackTrace();
        }
        return null;
    }
}