package Ascenso.sytem.inventory.service.impl;


import Ascenso.sytem.inventory.dto.LowStockAlertDto;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.repository.InventoryRepository;
import Ascenso.sytem.inventory.service.LowStockAlertServiceContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LowStockAlertServiceImpl implements LowStockAlertServiceContract {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<LowStockAlertDto> getLowStockAlerts() {

        List<Inventory> lowStock = inventoryRepository.findAll().stream()
                .filter(inv -> inv.getQuantityAvailable() <= inv.getProduct().getMinimumStockLevel())
                .collect(Collectors.toList());
        
        log.info("Found {} products with low stock", lowStock.size());
        
        return lowStock.stream()
                .map(inv -> LowStockAlertDto.builder()
                        .productId(inv.getProduct().getId())
                        .productName(inv.getProduct().getName())
                        .sku(inv.getProduct().getSku())
                        .currentStock(inv.getQuantityAvailable())
                        .minimumStock(inv.getProduct().getMinimumStockLevel())
                        .shortage(inv.getProduct().getMinimumStockLevel() - inv.getQuantityAvailable())
                        .build())
                .collect(Collectors.toList());
    }

}
