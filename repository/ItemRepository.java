package com.lessonlink.repository;

import com.lessonlink.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

}