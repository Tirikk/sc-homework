package com.dontirikk.addressservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerIntegrationTests {
    public static final String EXPECTED_ADDRESS = "Budapest, Komor Marcell u. 1, 1095";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddress() throws Exception {
        mockMvc.perform(get("/api/v1/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.address").value(EXPECTED_ADDRESS));
    }

    @Test
    void shouldFailWithoutAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/v1/address"))
                .andExpect(status().isUnauthorized());
    }
}
