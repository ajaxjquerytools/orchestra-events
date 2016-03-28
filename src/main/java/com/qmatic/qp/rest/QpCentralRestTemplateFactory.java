package com.qmatic.qp.rest;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by orchestra on 22.03.2016.
 */
public class QpCentralRestTemplateFactory {

    public static RestTemplate createRestTemplate(String username, String password, String host, int port) {
        return new RestTemplate(createSecureTransport(username, password, host, port));
    }

    private static ClientHttpRequestFactory createSecureTransport(String username, String password, String host, int port ) {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(host, port), credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
