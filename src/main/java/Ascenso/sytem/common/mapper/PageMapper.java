package Ascenso.sytem.common.mapper;

import Ascenso.sytem.common.response.PageResponse;
import org.springframework.data.domain.Page;

public final class PageMapper {

    public static <T> PageResponse<T> from(Page<T> page){
        return PageResponse.<T>builder()

                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
