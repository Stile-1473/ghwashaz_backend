package Ascenso.sytem.sync.dto;

import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.ProductResponseDto;
import Ascenso.sytem.system.dto.NotificationResponseDto;
import Ascenso.sytem.system.dto.SettingResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DownloadSyncResponseDto {

    private List<ProductResponseDto> products;

    private List<CategoryResponseDto> categories;

    private List<InventoryResponseDto> inventory;

    private List<NotificationResponseDto> notifications;

    private List<SettingResponseDto> settings;

    private LocalDateTime serverTime;
}
