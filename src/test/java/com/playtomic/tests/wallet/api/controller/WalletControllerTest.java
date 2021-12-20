package com.playtomic.tests.wallet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.api.request.TopUpRequest;
import com.playtomic.tests.wallet.handler.ControllerExceptionHandler;
import com.playtomic.tests.wallet.service.impl.WalletServiceImpl;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@SpringBootTest(classes = {WalletController.class, JacksonAutoConfiguration.class, ControllerExceptionHandler.class},
                properties = "api.servlet.context-path=/")
@AutoConfigureMockMvc
class WalletControllerTest {

    private TopUpRequest request;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WalletServiceImpl walletService;

    @BeforeEach
    void init() {
        request = new TopUpRequest("6939f50b-a80e-44d9-859c-99e1813c1c73", "4242 4242 4242 4242", new BigDecimal(20));
    }

    @Test
    void topUpWallet_successTest() throws Exception {
        var paymentUuid = "213-321";
        Mockito.when(walletService.topUpByCard(request)).thenReturn(paymentUuid);

        mvc.perform(MockMvcRequestBuilders.post("/top-up")
                                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                                          .content(new ObjectMapper().writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(jsonPath("$.walletUuid").value(request.getWalletUuid()))
           .andExpect(jsonPath("$.amount").value(request.getAmount().toString()))
           .andExpect(jsonPath("$.paymentUuid").value(paymentUuid));
    }

    @Test
    void getByClientRequest_NotFoundTest() throws Exception {
        Mockito.when(walletService.topUpByCard(request)).thenThrow(WalletNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.post("/top-up")
                                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                                          .content(new ObjectMapper().writeValueAsString(request)))
           .andExpect(status().isNotFound());
    }

    @Test
    void getByClientRequest_BadRequestTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/top-up")
                                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                                          .content("{}"))
           .andExpect(status().isBadRequest())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(jsonPath("$.message").isNotEmpty());
    }
}