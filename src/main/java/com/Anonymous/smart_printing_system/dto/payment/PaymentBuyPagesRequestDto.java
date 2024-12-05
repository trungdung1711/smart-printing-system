package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.eenum.PageType;
import lombok.Value;

@Value
public class PaymentBuyPagesRequestDto {
    private Long numOfPages;
    private PageType pageType;
}
