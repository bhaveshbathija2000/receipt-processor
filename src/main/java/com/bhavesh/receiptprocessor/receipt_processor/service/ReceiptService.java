package com.bhavesh.receiptprocessor.receipt_processor.service;

import com.bhavesh.receiptprocessor.receipt_processor.dto.ItemDTO;
import com.bhavesh.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import com.bhavesh.receiptprocessor.receipt_processor.model.Item;
import com.bhavesh.receiptprocessor.receipt_processor.model.Receipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);
    private final Map<String, Receipt> receiptStore = new HashMap<>();

    public String processReceipt(ReceiptDTO receiptDTO) {
        logger.info("Processing receipt for retailer: {}", receiptDTO.getRetailer());

        // Convert DTO to Model
        Receipt receipt = mapToReceipt(receiptDTO);
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, receipt);

        logger.info("Receipt processed successfully with ID: {}", id);
        return id;
    }

    public Integer getPoints(String id) {
        logger.info("Fetching points for receipt ID: {}", id);
        Receipt receipt = receiptStore.get(id);

        if (receipt == null) {
            logger.warn("Receipt ID {} not found", id);
            return null;
        }

        int points = calculatePoints(receipt);
        logger.info("Points calculated for receipt ID {}: {}", id, points);
        return points;
    }

    private Receipt mapToReceipt(ReceiptDTO dto) {
        Receipt receipt = new Receipt();
        receipt.setRetailer(dto.getRetailer());
        receipt.setPurchaseDate(dto.getPurchaseDate());
        receipt.setPurchaseTime(dto.getPurchaseTime());
        receipt.setTotal(dto.getTotal());

        List<Item> items = dto.getItems().stream().map(this::mapToItem).collect(Collectors.toList());
        receipt.setItems(items);

        return receipt;
    }

    private Item mapToItem(ItemDTO dto) {
        Item item = new Item();
        item.setShortDescription(dto.getShortDescription());
        item.setPrice(dto.getPrice());
        return item;
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in retailer name
        int retailerPoints = receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
        points += retailerPoints;
        logger.debug("Retailer name points: {}", retailerPoints);

        // Rule 2: 50 points if total is a round number
        if (receipt.getTotal().matches("\\d+\\.00")) {
            points += 50;
            logger.debug("Added 50 points for round dollar total: {}", receipt.getTotal());
        }

        // Rule 3: 25 points if total is a multiple of 0.25
        double totalValue = Double.parseDouble(receipt.getTotal());
        if (totalValue % 0.25 == 0) {
            points += 25;
            logger.debug("Added 25 points for total being a multiple of 0.25: {}", totalValue);
        }

        // Rule 4: 5 points for every two items
        int pairItemPoints = (receipt.getItems().size() / 2) * 5;
        points += pairItemPoints;
        logger.debug("Item pair points: {}", pairItemPoints);

        // Rule 5: Check item descriptions
        for (Item item : receipt.getItems()) {
            int descLength = item.getShortDescription().trim().length();
            if (descLength % 3 == 0) {
                int itemPoints = (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
                points += itemPoints;
                logger.debug("Added {} points for item '{}'", itemPoints, item.getShortDescription());
            }
        }

        // Rule 6: 6 points if purchase date is odd
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 == 1) {
            points += 6;
            logger.debug("Added 6 points for odd purchase date: {}", receipt.getPurchaseDate());
        }

        // Rule 7: 10 points if purchase time is between 2:00 PM and 4:00 PM
        int hour = Integer.parseInt(receipt.getPurchaseTime().split(":")[0]);
        if (hour >= 14 && hour < 16) {
            points += 10;
            logger.debug("Added 10 points for purchase time between 2:00 PM and 4:00 PM: {}", receipt.getPurchaseTime());
        }

        logger.info("Total points calculated: {}", points);
        return points;
    }
}
