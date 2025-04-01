package com.example.ocrservice.config;


import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;


@Configuration
public class SslConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Value("${ssl.cert}")
    private String certBase64;

    @Value("${ssl.key}")
    private String keyBase64;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        try {
            Path certPath = Files.createTempFile("cert", ".pem");
            Path keyPath = Files.createTempFile("key", ".pem");

            Files.write(certPath, Base64.getDecoder().decode(certBase64));
            Files.write(keyPath, Base64.getDecoder().decode(keyBase64));

            factory.addAdditionalTomcatConnectors(createSslConnector(certPath, keyPath));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to configure SSL", e);
        }
    }

    private Connector createSslConnector(Path certPath, Path keyPath) {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(3000);
        connector.setSecure(true);
        connector.setScheme("https");

        Http11NioProtocol sslProtocol = (Http11NioProtocol) connector.getProtocolHandler();
        sslProtocol.setSSLEnabled(true);
        sslProtocol.setSSLCertificateFile(certPath.toAbsolutePath().toString());
        sslProtocol.setSSLPrivateKeyFile(keyPath.toAbsolutePath().toString());
        sslProtocol.setSSLProtocol("TLS");

        return connector;
    }
}
