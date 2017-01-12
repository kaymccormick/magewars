package us.heptet.magewars.gameserver.rest.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.gameservice.core.events.UserExistDetails;
import us.heptet.magewars.gameservice.core.service.UserService;

import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {
    private static Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    MockMvc mockMvc;

    @InjectMocks
    UserController controller;

    @Mock
    UserService userService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(groups = {"broken"})
    public void testCheckUsername() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/rest/user/name/kay").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        UserExistDetails userExistDetails = (UserExistDetails)mvcResult.getModelAndView().getModelMap().get("userInfo");

        logger.info("{}", userExistDetails.isUserExisting());
    }

    @Test(groups = {"broken"})
    public void testCheckUsername2() throws Exception {
        mockMvc.perform(get("/rest/user/noname").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}