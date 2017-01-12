package us.heptet.magewars.rest.client;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.heptet.magewars.rest.domain.Game;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.service.game.GameService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 6/16/2014. */
/**
 * GamesClient - a client for the gameserver
 */
public class GamesClient implements GameService, InitializingBean {
    private RestTemplate restTemplate;
    private String username;
    private String password;
    private String baseUrl = "http://localhost:5050/magewars-gameserver";

    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        ResponseEntity<List<Game>> responseEntity =
                restTemplate.exchange(baseUrl + "/aggregators/games", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Game>>() {});
        List<GameDetails> detailsList = new ArrayList<>();
        for(Game game:responseEntity.getBody())
        {
            detailsList.add(game.toGameDetails());
        }
        return new AllGamesEvent(detailsList);
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        URI uri;
        try {
            uri = new URI(baseUrl);
        } catch(URISyntaxException ex)
        {
            throw new IllegalArgumentException(ex);
        }
        String host = uri.getHost();
        int port = uri.getPort();

        HttpHost httpHost = new HttpHost(host, port);
        HttpComponentsClientHttpRequestFactoryBasicAuth requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost, username, password);
        restTemplate = new RestTemplate(requestFactory);

    }
}
