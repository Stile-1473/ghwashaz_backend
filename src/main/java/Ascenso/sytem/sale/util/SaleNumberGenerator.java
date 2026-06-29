package Ascenso.sytem.sale.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Component
public class SaleNumberGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;
    
    public String generate() {
        return "SAL-" 
                + LocalDate.now().format(DATE_FORMAT) 
                + "-" 
                + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
}
