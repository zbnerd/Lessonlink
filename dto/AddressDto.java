package com.lessonlink.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class AddressDto {
    private String metropolitanCityProvince;
    private String cityDistrict;
    private String village;
    private String roadName;
    private int roadNumber;
    private String zipCode;

}