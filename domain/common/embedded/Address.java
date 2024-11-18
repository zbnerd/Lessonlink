package com.lessonlink.domain.common.embedded;

import com.lessonlink.dto.builder.AddressDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Address {


    private String metropolitanCityProvince; // 특별시, 광역시, 도
    private String cityDistrict; // 시, 군, 구
    private String village; // 동, 읍, 면
    private String roadName; // 도로명
    private int roadNumber; // 도로명 숫자
    private String zipCode; // 우편번호

    public Address() {
    }


    public Address(AddressDto addressDto) {
        this.metropolitanCityProvince = addressDto.getMetropolitanCityProvince();
        this.cityDistrict = addressDto.getCityDistrict();
        this.village = addressDto.getVillage();
        this.roadName = addressDto.getRoadName();
        this.roadNumber = addressDto.getRoadNumber();
        this.zipCode = addressDto.getZipCode();
    }
}
