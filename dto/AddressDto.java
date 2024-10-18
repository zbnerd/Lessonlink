package com.lessonlink.dto;

import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.member.Member;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class AddressDto {
    private Member member;
    private String metropolitanCityProvince;
    private String cityDistrict;
    private String village;
    private String roadName;
    private int roadNumber;
    private String zipCode;
    private Delivery delivery;

    // `Builder` 클래스에서 전달된 값을 통해 `AddressDto` 객체를 생성하는 생성자
    private AddressDto(Builder builder) {
        this.member = builder.member;
        this.metropolitanCityProvince = builder.metropolitanCityProvince;
        this.cityDistrict = builder.cityDistrict;
        this.village = builder.village;
        this.roadName = builder.roadName;
        this.roadNumber = builder.roadNumber;
        this.zipCode = builder.zipCode;
        this.delivery = builder.delivery;
    }

    /**
     * 주소 정보를 설정하는 빌더 클래스.
     * 빌더 패턴을 통해 각 필드를 설정하고, `build()` 메서드를 통해
     * `AddressDto` 객체를 생성합니다.
     */
    public static class Builder {
        private Member member;
        private String metropolitanCityProvince;
        private String cityDistrict;
        private String village;
        private String roadName;
        private int roadNumber;
        private String zipCode;
        private Delivery delivery;


        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder metropolitanCityProvince(String metropolitanCityProvince) {
            this.metropolitanCityProvince = metropolitanCityProvince;
            return this;
        }

        public Builder cityDistrict(String cityDistrict) {
            this.cityDistrict = cityDistrict;
            return this;
        }

        public Builder village(String village) {
            this.village = village;
            return this;
        }

        public Builder roadName(String roadName) {
            this.roadName = roadName;
            return this;
        }

        public Builder roadNumber(int roadNumber) {
            this.roadNumber = roadNumber;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder delivery(Delivery delivery) {
            this.delivery = delivery;
            return this;
        }

        public AddressDto build() {
            return new AddressDto(this);
        }
    }
}