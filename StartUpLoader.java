public class StartUpLoader {

    public static void main(String[] args) throws Exception {
        // Load the keystore and truststore using the bootstrap class loader
        String keystorePath = "/path/to/keystore";
        String keystorePassword = "keystore-password";
        String truststorePath = "/path/to/truststore";
        String truststorePassword = "truststore-password";

        URL keystoreUrl = new URL("file:" + keystorePath);
        URL truststoreUrl = new URL("file:" + truststorePath);

        InputStream keystoreStream = keystoreUrl.openStream();
        InputStream truststoreStream = truststoreUrl.openStream();

        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(keystoreStream, keystorePassword.toCharArray());

        KeyStore truststore = KeyStore.getInstance("JKS");
        truststore.load(truststoreStream, truststorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, keystorePassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();

        // Use the socketFactory to create a TLS socket connection
        Socket socket = socketFactory.createSocket("hostname", port);

        // Use the socket to send and receive data over the TLS connection
        // ...

        // Close the socket when done
        socket.close();
    }
}
