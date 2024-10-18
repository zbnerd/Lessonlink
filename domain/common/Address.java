package com.lessonlink.domain.common;

import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.member.Member;
import com.lessonlink.dto.AddressDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Address {

    @Id @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id_secret_key")
    private Member member;

    private String metropolitanCityProvince; // 특별시, 광역시, 도
    private String cityDistrict; // 시, 군, 구
    private String village; // 동, 읍, 면
    private String roadName; // 도로명
    private int roadNumber; // 도로명 숫자
    private String zipCode; // 우편번호

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    public void setAddressInfo(AddressDto addressDto) {
        this.member = addressDto.getMember();
        this.metropolitanCityProvince = addressDto.getMetropolitanCityProvince();
        this.cityDistrict = addressDto.getCityDistrict();
        this.village = addressDto.getVillage();
        this.roadName = addressDto.getRoadName();
        this.roadNumber = addressDto.getRoadNumber();
        this.zipCode = addressDto.getZipCode();
        this.delivery = addressDto.getDelivery();
    }
}
