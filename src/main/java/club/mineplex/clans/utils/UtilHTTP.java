package club.mineplex.clans.utils;

import club.mineplex.clans.utils.object.ConnectionBuilder;
import com.sun.net.ssl.HttpsURLConnection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class UtilHTTP {

    public static String agent = "Chrome";
    public static String accept = "*/*";

    public static String mineplexScrape(String url) throws IOException {

        ConnectionBuilder conn = new ConnectionBuilder(url).method("GET");

        conn.header("Accept", UtilHTTP.accept);
        conn.header("Origin", "https://mineplex.com/");
        conn.header("User-Agent", UtilHTTP.agent);

        conn.send();

        if (conn.getResponseCode() == 301) {
            Map<String, List<String>> headers = conn.getFinalConnection().getHeaderFields();
            if (headers.containsKey("Location")) return mineplexScrape(headers.get("Location").get(0));
        }

        return conn.getResponseString();
    }

    public static int getURLCode(HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (IOException | NullPointerException e) {
            return 400;
        }
    }

    public static String getURLResponse(HttpURLConnection connection) {
        if (connection == null) return "Error grabbing response!";

        try {
            if (connection.getResponseCode() > 199 && connection.getResponseCode() < 400) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String finalRes = "";
                String line;

                while ((line = reader.readLine()) != null) {
                    finalRes += line;
                }

                reader.close();

                return finalRes;

            } else {

                BufferedReader reader;
                try {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                } catch (NullPointerException x) {
                    return "Error grabbing response";
                }

                StringBuilder finalRes = new StringBuilder();
                String line;

                try {
                    while ((line = reader.readLine()) != null) finalRes.append(line);
                } catch (IOException e1) {
                    return "Error grabbing response";
                }

                try {
                    reader.close();
                } catch (IOException e1) {
                    return "Error grabbing response";
                }

                return finalRes.toString();

            }
        } catch (IOException e) {
            return "Error grabbing response";
        }
    }

    public static HttpURLConnection ssl(String url, Proxy proxy) throws IOException {

        HttpURLConnection connection;
        CookieHandler.setDefault(new CookieManager());
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        if (proxy != null) {
            connection = (HttpURLConnection) new URL(url).openConnection(proxy);
        } else {
            connection = (HttpURLConnection) new URL(url).openConnection();
        }

        connection.setInstanceFollowRedirects(false);
        return connection;
    }

}
