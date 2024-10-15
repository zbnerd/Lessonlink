package com.lessonlink.service;

import com.lessonlink.domain.item.Book;
import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.item.Item;
import com.lessonlink.dto.ItemDto;
import com.lessonlink.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List <Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }



    @Transactional
    public void updateBook(Long id, ItemDto itemDto) {
        Book foundItem = (Book) itemRepository.findOne(id);
        foundItem.setBookInfo(itemDto);
    }

    @Transactional
    public void updateCourse(Long id, ItemDto itemDto) {
        Course foundItem = (Course) itemRepository.findOne(id);
        foundItem.setCourseInfo(itemDto);
    }
}