package Ascenso.sytem.sale.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.sale.dto.ReceiptResponseDto;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.entity.SaleItem;
import Ascenso.sytem.sale.entity.SalePayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiptMapper {

    public ReceiptResponseDto toResponse(Sale sale) {
        if (sale == null) return null;
        
        List<ReceiptResponseDto.ReceiptItem> items = sale.getItems().stream()
                .map(this::mapItem)
                .collect(Collectors.toList());
        
        List<ReceiptResponseDto.ReceiptPayment> payments = sale.getPayments().stream()
                .map(this::mapPayment)
                .collect(Collectors.toList());
        
        return ReceiptResponseDto.builder()
                .id(sale.getId())
                .saleNumber(sale.getSaleNumber())
                .cashierName(sale.getCashier() != null ? sale.getCashier().getFullName() : null)
                .customerName(sale.getCustomer() != null ? sale.getCustomer().getFullName() : null)
                .total(sale.getTotal())
                .items(items)
                .payments(payments)
                .createdAt(sale.getCreatedAt())
                .build();
    }
    
    private ReceiptResponseDto.ReceiptItem mapItem(SaleItem item) {
        return ReceiptResponseDto.ReceiptItem.builder()
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .lineTotal(item.getLineTotal())
                .build();
    }
    
    private ReceiptResponseDto.ReceiptPayment mapPayment(SalePayment payment) {
        return ReceiptResponseDto.ReceiptPayment.builder()
                .paymentMethod(payment.getPaymentMethod().name())
                .amount(payment.getAmount())
                .build();
    }
}
