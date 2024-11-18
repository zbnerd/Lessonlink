package com.lessonlink.domain.db.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.db.common.BaseTimeEntity;
import com.lessonlink.domain.db.common.embedded.Address;
import com.lessonlink.domain.db.order.Order;
import com.lessonlink.domain.db.delivery.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Delivery extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Setter
    private DeliveryStatus status;

    public void setDeliveryInfo(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }

    /** 배송중으로 전환 **/
    public void setComp() {
        this.setStatus(DeliveryStatus.COMP);
    }
}
