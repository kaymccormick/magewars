package us.heptet.magewars.rest.client;

/* Created by kay on 7/8/2014. */
/**
 *
 */
import java.net.URI;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class HttpComponentsClientHttpRequestFactoryBasicAuth
        extends HttpComponentsClientHttpRequestFactory {
    private static Logger logger = LoggerFactory.getLogger(HttpComponentsClientHttpRequestFactoryBasicAuth.class);

    HttpHost host;
    private final String username;
    private final String password;

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host, String username, String password) {
        super();
        this.host = host;
        this.username = username;
        this.password = password;
    }

    protected HttpClientContext createHttpContext(HttpMethod httpMethod, URI uri) {
        return createHttpContext();
    }
    private HttpClientContext createHttpContext() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);

        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);

        credentialsProvider.setCredentials(
                new AuthScope(host.getHostName(), host.getPort(), AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(getUsername(), getPassword()));

        return context;
    }

    public String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }
}
