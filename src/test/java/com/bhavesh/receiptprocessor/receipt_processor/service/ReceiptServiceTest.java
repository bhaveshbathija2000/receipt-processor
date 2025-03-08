package com.bhavesh.receiptprocessor.receipt_processor.service;

import com.bhavesh.receiptprocessor.receipt_processor.dto.ItemDTO;
import com.bhavesh.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @InjectMocks
    private ReceiptService receiptService;

    private ReceiptDTO sampleReceipt;

    @BeforeEach
    void setUp() {
        sampleReceipt = new ReceiptDTO();
        sampleReceipt.setRetailer("Target");
        sampleReceipt.setPurchaseDate("2022-01-01");
        sampleReceipt.setPurchaseTime("15:30");
        sampleReceipt.setTotal("10.00");

        ItemDTO item1 = new ItemDTO();
        item1.setShortDescription("Milk");
        item1.setPrice("2.50");

        ItemDTO item2 = new ItemDTO();
        item2.setShortDescription("Bread");
        item2.setPrice("3.00");

        sampleReceipt.setItems(List.of(item1, item2));
    }

    @Test
    void testProcessReceipt_GeneratesValidId() {
        String receiptId = receiptService.processReceipt(sampleReceipt);
        assertNotNull(receiptId);
        assertFalse(receiptId.isEmpty());
    }

    @Test
    void testCalculatePoints_CorrectPointsCalculation() {
        int points = receiptService.getPoints(receiptService.processReceipt(sampleReceipt));
        assertTrue(points > 0);
    }

    @Test
    void testGetPoints_ReturnsNullForInvalidId() {
        Integer points = receiptService.getPoints("invalid-id");
        assertNull(points);
    }

    @Test
    void testCalculatePoints_ValidEdgeCases() {
        ReceiptDTO edgeCaseReceipt = new ReceiptDTO();
        edgeCaseReceipt.setRetailer("M&M Corner Market");
        edgeCaseReceipt.setPurchaseDate("2022-03-20");
        edgeCaseReceipt.setPurchaseTime("14:33");
        edgeCaseReceipt.setTotal("9.00");

        ItemDTO item = new ItemDTO();
        item.setShortDescription("Gatorade");
        item.setPrice("2.25");
        edgeCaseReceipt.setItems(List.of(item, item, item, item));

        int points = receiptService.getPoints(receiptService.processReceipt(edgeCaseReceipt));
        assertEquals(109, points);
    }
}
