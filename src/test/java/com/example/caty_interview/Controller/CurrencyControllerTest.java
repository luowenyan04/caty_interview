package com.example.caty_interview.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrency() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/caty/{name}", "USD");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("返回結果: " + body);
    }

    @Test
    void createCurrency() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/caty")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\": \"TWD\",\n" +
                        "\"nameZh\": \"新台幣\",\n" +
                        "\"rate\": \"123.456\",\n" +
                        "\"updatedTime\": null\n" +
                        "}");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void updateCurrency() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/caty/{name}", "USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"nameZh\": \"美金\",\n" +
                        "\"rate\": \"123\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("返回結果: " + body);
    }

    @Test
    void deleteCurrency() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/caty/{name}", "GBP");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void callCoinDesk() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/caty/getCoinDesk");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("返回結果: " + body);
    }

    @Test
    void updateCoinDesk() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/caty/updateCoinDesk");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("返回結果: " + body);
    }


}