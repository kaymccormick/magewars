package us.heptet.magewars.gameserver.rest.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.service.game.GameService;

import java.util.Collections;

import static org.testng.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class GameQueriesControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    GameQueriesController controller;

    @Mock
    CombinedGameService combinedGameService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetAllGames() throws Exception {
        when(combinedGameService.requestAllGames(any(RequestAllGamesEvent.class))).thenReturn(
                new AllGamesEvent(Collections.singletonList(new GameDetails(1, "hi", "kay", 2, 2))));

        mockMvc.perform(get("/aggregators/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetFalse() throws Exception {
        mockMvc.perform(get("/aggregators/games/false").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}