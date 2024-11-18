package com.lessonlink.domain.item;

import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.dto.builder.ItemDto;
import com.lessonlink.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<Category>();

    protected void setItemInfo(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
    }

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}