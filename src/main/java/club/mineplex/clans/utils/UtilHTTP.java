package club.mineplex.clans.utils;

import club.mineplex.clans.utils.object.ConnectionBuilder;
import com.sun.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UtilHTTP {
    private UtilHTTP() {
    }

    private static final String ACCEPT = "*/*";
    private static final String AGENT = "Chrome";

    public static String mineplexScrape(final String url) throws IOException {

        final ConnectionBuilder conn = new ConnectionBuilder(url).method("GET");

        conn.header("Accept", UtilHTTP.ACCEPT);
        conn.header("Origin", "https://mineplex.com/");
        conn.header("User-Agent", UtilHTTP.AGENT);

        conn.send();

        if (conn.getResponseCode() == 301) {
            final Map<String, List<String>> headers = conn.getFinalConnection().getHeaderFields();
            if (headers.containsKey("Location")) {
                return mineplexScrape(headers.get("Location").get(0));
            }
        }

        return conn.getResponseString();
    }

    public static int getURLCode(final HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (final IOException | NullPointerException e) {
            return 400;
        }
    }

    public static Optional<String> getURLResponse(final HttpURLConnection connection) {
        if (connection == null) {
            return Optional.empty();
        }

        try {
            final String response;
            if (connection.getResponseCode() > 199 && connection.getResponseCode() < 400) {
                response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

            } else {
                response = IOUtils.toString(connection.getErrorStream(), StandardCharsets.UTF_8);
            }
            return Optional.of(response);
        } catch (final IOException e) {
            return Optional.empty();
        }
    }

    public static HttpURLConnection ssl(final String url, final Proxy proxy) throws IOException {

        final HttpURLConnection connection;
        CookieHandler.setDefault(new CookieManager());
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
            }
        }};
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (final KeyManagementException e) {
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
