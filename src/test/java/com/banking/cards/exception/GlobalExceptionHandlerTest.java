package com.banking.cards.exception;

import com.banking.cards.common.TestData;
import com.banking.cards.controller.CardsController;
import com.banking.cards.dto.CardDto;
import com.banking.cards.service.ICardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardsController.class)
class GlobalExceptionHandlerTest {

    @MockitoBean
    private ICardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void resourceAlreadyExistsException() throws Exception {
        doThrow(new ResourceAlreadyExistsException("Resource already exists")).when(cardService).createCard(anyString());
        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.validCreateRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Resource already exists"));
    }

    @Test
    void methodArgumentNotValidException() throws Exception {
         mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.invalidCreateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mobileNumber").value("Mobile number must have 10 digits"));

    }
}