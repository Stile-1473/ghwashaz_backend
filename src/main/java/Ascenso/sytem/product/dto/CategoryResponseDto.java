package Ascenso.sytem.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryResponseDto {

        private UUID id;

        private String name;

        private String description;

        private Boolean active;

        private LocalDateTime createdAt;
    }
