package Ascenso.sytem.product.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSkuGenerator {

    private static final String DEFAULT_SEQUENCE_KEY = "product_sku";

    private final SkuSequenceRepository skuSequenceRepository;

    public String generate(String productName) {
        String prefix = productName
                .replaceAll("[^A-Za-z]", "")
                .toUpperCase();

        if (prefix.length() > 3) {
            prefix = prefix.substring(0, 3);
        }

        long next = skuSequenceRepository.nextValue(DEFAULT_SEQUENCE_KEY);
        return prefix + "-" + String.format("%05d", next);
    }
}

