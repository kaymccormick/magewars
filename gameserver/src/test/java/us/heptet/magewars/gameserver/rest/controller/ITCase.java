package us.heptet.magewars.gameserver.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//import us.heptet.magewars.game.AsyncClient;
//import us.heptet.magewars.game.events.GameEvent;
//import us.heptet.magewars.game.events.RPCEvent;
import us.heptet.magewars.gameservice.core.events.Users.UserDetails;
import us.heptet.magewars.rest.domain.Game;

import java.net.URI;
import java.util.List;

// rename class
public class ITCase {
    String projectName = "magewars";

    String moduleName = "magewars-gameserver";
    String moduleVersion = "0.20-SNAPSHOT";

    String propertyProjectPart = projectName;
    String propertyModulePart = "gameserver";
    String propertyPrefix = propertyProjectPart + "." + propertyModulePart + ".";
    String hostProperty = propertyPrefix + "host";
    String portProperty = propertyPrefix + "port";
    String contextPathProperty = propertyPrefix + "contextPath";
    String gameserverUrlProperty = propertyPrefix + "gameserverUrl";

    private String contextPath = "/" + moduleName + "-" + moduleVersion;
    private String host = "localhost";
    private int port = 5050;
    private String gameserverUrl;

    private Logger logger = LoggerFactory.getLogger(ITCase.class);

    @BeforeMethod
    public void setUp() throws Exception {
        host = System.getProperty(hostProperty, host);
        port = Integer.parseInt(System.getProperty(portProperty, String.valueOf(port)));
        contextPath = System.getProperty(contextPathProperty, contextPath);

        gameserverUrl = "http://" + host + (port == 80 ? "" : (":" + String.valueOf(port))) + contextPath;
        gameserverUrl = System.getProperty(gameserverUrlProperty, gameserverUrl);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetAllGames() throws Exception {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ParameterizedTypeReference<List<Game>> typeReference = new ParameterizedTypeReference<List<Game>>() {
        };
        ResponseEntity<List<Game>> r = restTemplate.exchange(gameserverUrl + "/aggregators/games",
                HttpMethod.GET, null, typeReference);
        for(Game game:r.getBody())
        {
            assert game != null;
            assert game.getCreatedByUsername() != null;
            assert !game.getCreatedByUsername().isEmpty();
        }
        logger.info("{}", r.getBody());
    }

    /*
    Right now, we aren't sure what kind of errors we produce.
     */
    @Test
    public void testCreateGameUnknownUser() throws Exception {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        Game game = new Game();
        game.setGameName("my game");
        game.setCreatedByUsername("me");
        HttpEntity<Game> requestEntity = new HttpEntity<Game>(game);

        URI uri = restTemplate.postForLocation(gameserverUrl + "/aggregators/games", game);
        logger.warn("uri is {}", uri);
    }

    @Test
    public void testCreateUser() throws Exception {
        String userName = "kay1234";
        createUser(userName);
    }

    private UserDetails createUser(String userName) {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<UserDetails> response = restTemplate.postForEntity(gameserverUrl + "/rest/user/name/" + userName, null, UserDetails.class);
        assert response.getStatusCode() == HttpStatus.CREATED;
        logger.info("{}", response.getBody());
        return response.getBody();
    }

/*    @Test
    public void testJoinGame() throws Exception {

        String url = gameserverUrl + "/atm";
        logger.info("{}", url);
        AsyncClient asyncClient = new AsyncClient(gameserverUrl + "/atm");
        asyncClient.connect();

        asyncClient.setOnMessageHandler(new AsyncClient.OnMessage() {
            @Override
            public void handle(RPCEvent rpcEvent) {
                logger.info("{}", rpcEvent);
            }
        });

        java.util.Random random = new java.util.Random();
        int i = random.nextInt(65536);
        String userName =  "test" + i;
        UserDetails user = createUser(userName);

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        Game game = new Game();
        game.setGameName("my game");
        game.setCreatedByUsername(user.getUsername());
        game.setMaxPlayers(2);
        game.setMinPlayers(2);

        URI uri = restTemplate.postForLocation(gameserverUrl + "/aggregators/games", game);
        int gameId = Integer.parseInt(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
        logger.warn("uri is {}", uri);

        asyncClient.fire(new RPCEvent(new GameEvent(GameEvent.VIEW_TABLE, gameId, null, null, null)));

        String url1 = gameserverUrl + "/aggregators/games/" + gameId + "/join/" + userName;
        logger.info("{}", url1);
        ResponseEntity<Boolean> booleanResponseEntity = restTemplate.postForEntity(url1, null, Boolean.class);
        assert booleanResponseEntity.getStatusCode() != HttpStatus.ACCEPTED;

        Thread.sleep(1000);

        //for(;;);
    }*/
}