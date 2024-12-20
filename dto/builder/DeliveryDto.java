package com.lessonlink.dto.builder;

import com.lessonlink.domain.db.common.embedded.Address;
import com.lessonlink.domain.db.delivery.enums.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class DeliveryDto {
    private Address address;
    private DeliveryStatus status;

}
