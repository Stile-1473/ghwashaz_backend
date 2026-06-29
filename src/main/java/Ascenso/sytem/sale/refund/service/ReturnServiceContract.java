package Ascenso.sytem.sale.refund.service;

import Ascenso.sytem.sale.refund.dto.CreateReturnRequestDto;
import Ascenso.sytem.sale.refund.dto.ReturnResponseDto;

import java.util.UUID;

public interface ReturnServiceContract {

    ReturnResponseDto createReturn(CreateReturnRequestDto dto);

    ReturnResponseDto getReturn(UUID id);

}
