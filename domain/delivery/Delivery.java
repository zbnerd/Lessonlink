package com.lessonlink.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.delivery.enums.DeliveryStatus;
import com.lessonlink.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setDeliveryInfo(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}
