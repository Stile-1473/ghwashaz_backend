package Ascenso.sytem.sale.refund.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.sale.dto.SaleResponseDto;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.entity.SaleItem;
import Ascenso.sytem.sale.entity.SalePayment;
import Ascenso.sytem.sale.mapper.SaleMapper;
import Ascenso.sytem.sale.refund.dto.CreateReturnRequestDto;
import Ascenso.sytem.sale.refund.dto.ReturnResponseDto;
import Ascenso.sytem.sale.refund.entity.SaleReturn;
import Ascenso.sytem.sale.refund.repository.ReturnRepository;
import Ascenso.sytem.sale.refund.service.ReturnServiceContract;
import Ascenso.sytem.sale.refund.validator.ReturnValidator;
import Ascenso.sytem.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Ascenso.sytem.common.exception.ResourceNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReturnServiceImpl implements ReturnServiceContract {

    private final ReturnRepository returnRepository;
    private final ReturnValidator returnValidator;
    private final AuditServiceContract auditService;
    private final SaleMapper saleMapper;
    private final InventoryServiceContract inventoryService;

    @Override
    public ReturnResponseDto createReturn(CreateReturnRequestDto dto) {
        Sale originalSale = returnValidator.getValidatedSale(dto.getOriginalSaleId());
        returnValidator.validateSaleCanBeReturned(originalSale);
        returnValidator.validateReturnAmount(originalSale, dto.getRefundAmount());
        
        SaleReturn saleReturn = SaleReturn.builder()
                .originalSale(originalSale)
                .processedBy(SecurityUtils.getCurrentUser().getUser())
                .returnType(dto.getReturnType())
                .refundMethod(dto.getRefundMethod())
                .refundAmount(dto.getRefundAmount())
                .reason(dto.getReason())
                .referenceNumber("RET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();
        
        SaleReturn saved = returnRepository.save(saleReturn);
        
        // Restore inventory for returned items
        for (SaleItem item : originalSale.getItems()) {
            inventoryService.receiveStock(item.getProduct(), item.getQuantity());
        }
        
        // Update original sale status
        originalSale.setStatus(SaleStatus.REFUNDED);
        
        try {
            auditService.log(AuditModule.SALE, AuditActionType.CREATE, saved.getId(), 
                    "Return processed for sale: " + originalSale.getSaleNumber());
        } catch (Exception e) {
            log.warn("Audit log failed for return: {}", e.getMessage());
        }
        
        log.info("Return created: {} for amount {}", saved.getReferenceNumber(), saved.getRefundAmount());
        
        return mapToResponse(saved, originalSale);
    }

    @Override
    @Transactional(readOnly = true)
    public ReturnResponseDto getReturn(UUID id) {
        SaleReturn saleReturn = returnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return not found"));
        return mapToResponse(saleReturn, saleReturn.getOriginalSale());
    }

    private ReturnResponseDto mapToResponse(SaleReturn saleReturn, Sale originalSale) {
        return ReturnResponseDto.builder()
                .id(saleReturn.getId())
                .originalSaleNumber(originalSale.getSaleNumber())
                .processedBy(saleReturn.getProcessedBy().getFullName())
                .returnType(saleReturn.getReturnType())
                .refundMethod(saleReturn.getRefundMethod())
                .refundAmount(saleReturn.getRefundAmount())
                .reason(saleReturn.getReason())
                .referenceNumber(saleReturn.getReferenceNumber())
                .createdAt(saleReturn.getCreatedAt())
                .build();
    }

}
