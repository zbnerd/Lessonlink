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
import java.util.Optional;

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
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("해당 아이템이 존재하지 않습니다. ID: " + itemId));
    }

    @Transactional
    public void updateBook(Long id, ItemDto itemDto) {
        Optional<Item> foundItem = itemRepository.findById(id);
        if (foundItem.isPresent()) {
            Book book = (Book) foundItem.get();
            book.setBookInfo(itemDto);
        }
    }

    @Transactional
    public void updateCourse(Long id, ItemDto itemDto) {
        Optional<Item> foundItem = itemRepository.findById(id);
        if (foundItem.isPresent()) {
            Course course = (Course) foundItem.get();
            course.setCourseInfo(itemDto);
        }
    }
}