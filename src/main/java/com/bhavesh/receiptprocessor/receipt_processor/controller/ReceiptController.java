package com.bhavesh.receiptprocessor.receipt_processor.controller;


import com.bhavesh.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import com.bhavesh.receiptprocessor.receipt_processor.model.Receipt;
import com.bhavesh.receiptprocessor.receipt_processor.service.ReceiptService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
@Validated
//@Slf4j
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody ReceiptDTO receiptDTO) {
        logger.info("Received request to process receipt: {}", receiptDTO);
        String id = receiptService.processReceipt(receiptDTO);
        logger.info("Receipt processed successfully, generated ID: {}", id);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        logger.info("Received request to fetch points for receipt ID: {}", id);
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            logger.warn("No receipt found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Returning points for receipt ID {}: {}", id, points);
        return ResponseEntity.ok(Map.of("points", points));
    }
}
