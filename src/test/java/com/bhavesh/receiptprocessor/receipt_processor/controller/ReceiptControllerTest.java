package com.bhavesh.receiptprocessor.receipt_processor.controller;

import com.bhavesh.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import com.bhavesh.receiptprocessor.receipt_processor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
    }

    @Test
    void testProcessReceipt_Success() throws Exception {
        String mockId = "12345";
        when(receiptService.processReceipt(any(ReceiptDTO.class))).thenReturn(mockId);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "retailer": "Walmart",
                        "purchaseDate": "2022-01-01",
                        "purchaseTime": "15:30",
                        "total": "10.00",
                        "items": [
                            {"shortDescription": "Milk", "price": "2.50"}
                        ]
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockId));
    }

    @Test
    void testGetPoints_Success() throws Exception {
        when(receiptService.getPoints("12345")).thenReturn(50);

        mockMvc.perform(get("/receipts/12345/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(50));
    }

    @Test
    void testGetPoints_NotFound() throws Exception {
        when(receiptService.getPoints("99999")).thenReturn(null);

        mockMvc.perform(get("/receipts/99999/points"))
                .andExpect(status().isNotFound());
    }
}
