package com.lessonlink.repository.item;

import com.lessonlink.domain.db.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

}