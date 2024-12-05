package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.eenum.PageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentBuyPagesRequestDto {
    private Long numOfPages;
    private PageType pageType;
}
