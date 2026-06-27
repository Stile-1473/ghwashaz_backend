package Ascenso.sytem.supplier.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class PurchaseNumberGenerator {

    public String generate(){

        return "PUR-" +

                LocalDate.now().format(
                        DateTimeFormatter.BASIC_ISO_DATE
                )

                + "-"

                + UUID.randomUUID()
                .toString()
                .substring(0,6)
                .toUpperCase();

    }

}