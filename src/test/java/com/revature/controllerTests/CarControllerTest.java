package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.controllers.CarController;
import com.revature.rideforce.user.services.CarService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
////@ContextConfiguration(classes=UserApplication.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@WebMvcTest(controllers = CarController.class)
//@EnableWebMvc
//@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;


    @Test
    public void loggedOutUserCannotAccessSecureEndpoint() throws Exception {
        mockMvc.perform(get("/cars")).andExpect(status().isForbidden());
    }

    @Test
    public void fourIsFour() {
        Assertions.assertThat(4).isEqualTo(4);
    }

}