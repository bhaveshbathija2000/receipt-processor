package com.bhavesh.receiptprocessor.receipt_processor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    @NotBlank(message = "Item description cannot be empty")
    private String shortDescription;

    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in decimal format (e.g., 5.99)")
    private String price;
}
