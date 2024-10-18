package com.lessonlink.repository;

import com.lessonlink.domain.common.Address;
import com.lessonlink.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressRepository {

    private final EntityManager em;

    public void save(Address address) {
        em.persist(address);
    }

    public Address findOne(Long id) {
        return em.find(Address.class, id);
    }

    public List<Address> findAll() {
        return em.createQuery("select a from Address a", Address.class).getResultList();
    }

    public List<Address> findAddressesByMember(Member member) {
        return em.createQuery(
                        "SELECT a FROM Address a WHERE a.member = :member", Address.class)
                .setParameter("member", member)
                .getResultList();
    }
}
