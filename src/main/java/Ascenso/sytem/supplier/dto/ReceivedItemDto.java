package Ascenso.sytem.supplier.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReceivedItemDto {

    private UUID productId;

    private Integer quantityReceived;
}
