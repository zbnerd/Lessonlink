package com.lessonlink.service;

import com.lessonlink.domain.common.Address;
import com.lessonlink.domain.member.Member;
import com.lessonlink.repository.AddressRepository;
import com.lessonlink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveAddress(Member member, Address address) {
        member.addAddress(address);
        addressRepository.save(address);

        return address.getId();
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findAddressByMember(Member member) {
        return addressRepository.findAddressesByMember(member);
    }
}
