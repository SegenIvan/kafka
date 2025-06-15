package com.aston.controller;

import com.aston.kafka.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    private final String TEST_EMAIL = "test@example.com";

    @Test
    void sendAccountCreatedNotification() throws Exception {
        doNothing().when(notificationService).sendAccountCreatedEmail(TEST_EMAIL);

        mockMvc.perform(post("/notifications/created")
                        .param("email", TEST_EMAIL))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).sendAccountCreatedEmail(TEST_EMAIL);
        verifyNoMoreInteractions(notificationService);
    }

    @Test
    void sendAccountDeletedNotification() throws Exception {
        doNothing().when(notificationService).sendAccountDeletedEmail(TEST_EMAIL);

        mockMvc.perform(post("/notifications/deleted")
                        .param("email", TEST_EMAIL))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).sendAccountDeletedEmail(TEST_EMAIL);
    }


    @Test
    void sendNotification_ShouldReturnBadRequest_WhenEmailParamMissing() throws Exception {
        mockMvc.perform(post("/notifications/created"))
                .andExpect(status().isBadRequest());
    }
}
