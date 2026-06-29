package Ascenso.sytem.inventory.service;

import Ascenso.sytem.inventory.dto.LowStockAlertDto;


import java.util.List;

public interface LowStockAlertServiceContract {
    List<LowStockAlertDto> getLowStockAlerts();
}
