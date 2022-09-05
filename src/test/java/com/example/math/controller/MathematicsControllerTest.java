package com.example.math.controller;

import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneRequest;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MathematicsControllerTest {

    @LocalServerPort
    int port;

    String baseUrl="http://localhost";

    RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void init() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port));
    }

    @Test
    void givenEquation_whenGetEquationQuadraticNoneSolution_shouldReturnSolution() {
        GetEquationQuadraticNoneRequest request = new GetEquationQuadraticNoneRequest();
        request.setEquation("x-3=0");

        GetEquationQuadraticNoneResponse response = restTemplate.postForObject(baseUrl + "/mathematics/equation/quadratic/none", request, GetEquationQuadraticNoneResponse.class);

        Assertions.assertEquals("3", response.getSolution().getTermByVariable().get("x"));
    }

}
