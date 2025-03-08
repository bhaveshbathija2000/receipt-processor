package com.bhavesh.receiptprocessor.receipt_processor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDTO {

    @NotBlank(message = "Retailer name cannot be empty")
    private String retailer;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format")
    private String purchaseDate;

    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Time must be in HH:mm format")
    private String purchaseTime;

    @NotEmpty(message = "Items cannot be empty")
    private List<ItemDTO> items;

    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in decimal format (e.g., 12.34)")
    private String total;
}
