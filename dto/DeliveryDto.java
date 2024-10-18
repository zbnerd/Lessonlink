package com.lessonlink.dto;

import com.lessonlink.domain.common.Address;
import com.lessonlink.domain.delivery.DeliveryStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class DeliveryDto {
    private Address address;
    private DeliveryStatus status;

    // `Builder` 클래스에서 전달된 값을 통해 `DeliveryDto` 객체를 생성하는 생성자
    private DeliveryDto(Builder builder) {
        this.address = builder.address;
        this.status = builder.status;
    }

    /**
     * 배송 정보를 설정하는 빌더 클래스.
     * 빌더 패턴을 통해 각 필드를 설정하고, `build()` 메서드를 통해
     * `DeliveryDto` 객체를 생성합니다.
     */
    public static class Builder {
        private Address address;
        private DeliveryStatus status;

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder status(DeliveryStatus status) {
            this.status = status;
            return this;
        }

        public DeliveryDto build() {
            return new DeliveryDto(this);
        }
    }
}
