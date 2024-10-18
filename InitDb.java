package com.lessonlink;

import com.lessonlink.domain.common.Address;
import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.delivery.DeliveryStatus;
import com.lessonlink.domain.item.*;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.member.Role;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.dto.AddressDto;
import com.lessonlink.dto.ItemDto;
import com.lessonlink.dto.MemberDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember(
                    new MemberDto.Builder()
                            .memberId("test1")
                            .password("test1")
                            .name("testName")
                            .birthDate(LocalDate.of(2000, 10, 14))
                            .phoneNumber("010-2222-0000")
                            .email("test@gmail.com")
                            .role(Role.STUDENT)
                            .build()
            );
            em.persist(member);

            Address address = createAddress(
                    member, new AddressDto.Builder()
                            .member(member)
                            .metropolitanCityProvince("서울특별시")
                            .cityDistrict("강남구")
                            .village("삼성동")
                            .roadName("테헤란로")
                            .roadNumber(427)
                            .zipCode("06168")
                            .build()
            );
            em.persist(address);

            Book book = createBook(
                    new ItemDto.BookBuilder()
                            .author("Joshua Bloch")
                            .isbn("9780134685991")
                            .publisher("Addison-Wesley")
                            .publishedDate(LocalDate.of(2018, 1, 11))
                            .pageCount(416)
                            .format(BookFormat.HARDCOVER)
                            .language(BookLanguage.ENGLISH)
                            .summary("A comprehensive guide to programming in Java, covering best practices and patterns.")
                            .name("Effective Java")
                            .price(45000)
                            .stockQuantity(100)
                            .build()
            );
            em.persist(book);

            Course course = createCourse(
                    new ItemDto.CourseBuilder()
                            .teacher("김자바")
                            .description("자바 프로그래밍의 기초를 배울 수 있는 강의로, 변수, 제어문, 객체 지향 개념을 다룹니다.")
                            .startDate(LocalDate.of(2024, 11, 1))
                            .endDate(LocalDate.of(2025, 1, 31))
                            .startTime(LocalTime.of(10, 0))
                            .endTime(LocalTime.of(12, 0))
                            .durationHour(2)
                            .durationMinute(0)
                            .level(CourseLevel.BEGINNER)
                            .courseType(CourseType.ONLINE)
                            .materialUrl("https://example.com/java-course")
                            .name("Java Programming for Beginners")
                            .price(150000)
                            .stockQuantity(30)
                            .build()
            );
            em.persist(course);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, book.getPrice(), 10);
            OrderItem orderItem2 = OrderItem.createOrderItem(course, course.getPrice(), 5);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember(
                    new MemberDto.Builder()
                            .memberId("test2")
                            .password("test2")
                            .name("testName2")
                            .birthDate(LocalDate.of(2001, 10, 14))
                            .phoneNumber("010-2222-0001")
                            .email("test2@gmail.com")
                            .role(Role.TEACHER)
                            .build()
            );
            em.persist(member);

            Address address = createAddress(
                    member, new AddressDto.Builder()
                            .member(member)
                            .metropolitanCityProvince("부산광역시")
                            .cityDistrict("해운대구")
                            .village("우동")
                            .roadName("센텀중앙로")
                            .roadNumber(90)
                            .zipCode("48058")
                            .build()
            );
            em.persist(address);

            Book book = createBook(
                    new ItemDto.BookBuilder()
                            .author("이펙티브 자바 저자 한국어 번역")
                            .isbn("9788966262281")
                            .publisher("한빛미디어")
                            .publishedDate(LocalDate.of(2018, 12, 1))
                            .pageCount(416)
                            .format(BookFormat.EBOOK)
                            .language(BookLanguage.KOREAN)
                            .summary("이펙티브 자바 3판의 한국어 번역서로, 자바 프로그래밍의 모범 사례와 효율적인 코딩 방법을 다루고 있습니다.")
                            .name("이펙티브 자바 3판 (한국어 번역)")
                            .price(30000)
                            .stockQuantity(150)
                            .build()
            );
            em.persist(book);

            Course course = createCourse(
                    new ItemDto.CourseBuilder()
                            .teacher("박스프링")
                            .description("스프링 프레임워크의 고급 기능을 깊이 있게 다루는 강의로, AOP, 트랜잭션 관리, 스프링 시큐리티 등을 포함합니다.")
                            .startDate(LocalDate.of(2024, 12, 1))
                            .endDate(LocalDate.of(2025, 2, 28))
                            .startTime(LocalTime.of(14, 0))
                            .endTime(LocalTime.of(17, 0))
                            .durationHour(3)
                            .durationMinute(0)
                            .level(CourseLevel.ADVANCED)
                            .courseType(CourseType.OFFLINE)
                            .materialUrl("https://example.com/advanced-spring-course")
                            .name("Advanced Spring Framework")
                            .price(250000)
                            .stockQuantity(20)
                            .build()
            );
            em.persist(course);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, book.getPrice(), 10);
            OrderItem orderItem2 = OrderItem.createOrderItem(course, course.getPrice(), 5);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(MemberDto memberDto){
            Member member = new Member();
            member.setMemberInfo(memberDto);
            return member;
        }

        private Book createBook(ItemDto itemDto){
            Book book = new Book();
            book.setBookInfo(itemDto);
            return book;
        }

        private Course createCourse(ItemDto itemDto){
            Course course = new Course();
            course.setCourseInfo(itemDto);
            return course;
        }

        private Address createAddress(Member member, AddressDto addressDto){
            Address address = new Address();
            address.setAddressInfo(addressDto);
            member.addAddress(address);
            return address;
        }

        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setDeliveryInfo(member.getAddresses().getFirst(), DeliveryStatus.READY);
            return delivery;
        }
    }
}
