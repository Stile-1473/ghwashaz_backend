package Ascenso.sytem.supplier.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ReceivePurchaseRequestDto {

    @Valid
    @NotEmpty(message = "At least one item must be received")
    private List<ReceivedItemDto> items;


    private String notes;


    @Builder
    @Getter
    @Setter
    public static class ReceivedItemDto {

        @NotNull(message = "Item ID is required")
        private UUID itemId;

        @NotNull(message = "Received quantity is required")
        @Min(value = 0, message = "Received quantity cannot be negative")
        private Integer receivedQuantity;
    }
}
