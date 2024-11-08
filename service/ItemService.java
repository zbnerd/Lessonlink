package com.lessonlink.service;

import com.lessonlink.domain.item.Book;
import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.item.Item;
import com.lessonlink.dto.ItemDto;
import com.lessonlink.exception.IllegalInstanceTypeException;
import com.lessonlink.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("해당 아이템이 존재하지 않습니다. ID: " + itemId));
    }

    @Transactional
    public void updateBook(Long itemId, ItemDto itemDto) {
        Item foundItem = findOne(itemId);
        if (foundItem instanceof Book book) {
            book.setBookInfo(itemDto);
        } else {
            throw new IllegalInstanceTypeException("해당 메서드는 Book 타입만 사용 가능합니다.");
        }

    }

    @Transactional
    public void updateCourse(Long itemId, ItemDto itemDto) {
        Item foundItem = findOne(itemId);
        if (foundItem instanceof Course course) {
            course.setCourseInfo(itemDto);
        } else {
            throw new IllegalInstanceTypeException("해당 메서드는 Course 타입만 사용 가능합니다.");
        }

    }
}