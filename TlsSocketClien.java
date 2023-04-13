import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
To use a TLS certificate to connect to a socket server, you need to perform the following steps:

    Obtain the TLS certificate and install it in your application's keystore. This typically involves obtaining the certificate from a certificate authority, converting it to a format that can be used by your application (such as PKCS12 or JKS), and then importing it into your keystore. You can use the keytool command-line tool to manage keystores in Java.

    Configure your socket client to use the TLS protocol and the appropriate keystore. This involves setting the javax.net.ssl.keyStore and javax.net.ssl.keyStorePassword system properties to the path and password of your keystore, and setting the javax.net.ssl.trustStore and javax.net.ssl.trustStorePassword system properties to the path and password of your truststore (if the server's TLS certificate is not already trusted by your system).
*/
public class TlsSocketClient {
    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.keyStore", "/path/to/keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "keystore-password");
        System.setProperty("javax.net.ssl.trustStore", "/path/to/truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "truststore-password");

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket("server.hostname.com", 443);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Send a request to the server
        writer.println("GET / HTTP/1.1");
        writer.println("Host: server.hostname.com");
        writer.println();
        writer.flush();

        // Read the response from the server
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        socket.close();
    }
}
