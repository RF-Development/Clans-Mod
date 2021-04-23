package club.mineplex.clans.utils.object;

import club.mineplex.clans.utils.UtilHTTP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.SocketException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ConnectionBuilder {

    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    private String endpoint;
    private String data;
    private String method;

    private Proxy proxy;

    private final Map<String, String> headers;
    private HttpURLConnection finalConnection;
    public long delay = 0, sentTime = 0;

    public ConnectionBuilder(String endpoint) {

        this.data = "";
        this.method = "GET";
        this.endpoint = endpoint;
        this.headers = new HashMap<>();

    }

    public void proxy(Proxy proxy) {

        this.proxy = proxy;

    }

    public void endpoint(String endpoint) {

        this.endpoint = endpoint;

    }

    public void header(String key, String value) {

        this.headers.put(key, value);

    }

    public ConnectionBuilder method(String method) {

        this.method = method;
        return this;

    }

    public void data(String data) {

        this.data = data;

    }

    public void send() {

        sentTime = Instant.now().toEpochMilli();

        try {

            HttpURLConnection connection = ssl();

            connection.setRequestProperty("User-Agent", USER_AGENT);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if (method.equalsIgnoreCase("POST")) {

                connection.setConnectTimeout(10_000);
                connection.setReadTimeout(10_000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestMethod("POST");
                connection.connect();

                DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeBytes(data);
                output.flush();
                output.close();

                finalConnection = connection;

            } else if (method.equalsIgnoreCase("GET")) {

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10_000);
                connection.setDoOutput(true);

                connection.getResponseCode();
                connection.connect();

                finalConnection = connection;
            } else if (method.equalsIgnoreCase("PUT")) {

                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);

                connection.connect();

                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(data);
                osw.flush();
                osw.close();

                finalConnection = connection;
            }


        } catch (SocketException e) {
            System.out.println();
            System.out.println("[SocketException] " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.delay = Instant.now().toEpochMilli() - sentTime;
    }

    private HttpURLConnection ssl() throws IOException {
        return UtilHTTP.ssl(this.endpoint, proxy);
    }

    public String getResponseString() {
        return UtilHTTP.getURLResponse(finalConnection);
    }

    public String getHeader(String name) {

        return finalConnection.getHeaderField(name);

    }

    public int getResponseCode() {
        return UtilHTTP.getURLCode(finalConnection);
    }

    public HttpURLConnection getFinalConnection() {
        return finalConnection;
    }

    public Map<String, String> getSentHeaders() {
        return headers;
    }

    private String formatGetURL(String url, String data) {

        String newURL = url;

        if (!newURL.endsWith("?")) {
            newURL = newURL + "?";
        }

        newURL = newURL + data;

        return newURL;

    }

}
