package com.lessonlink;

import com.lessonlink.adapter.repository.*;
import com.lessonlink.aop.annotation.LogExecutionTime;
import com.lessonlink.application.service.*;
import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.delivery.enums.DeliveryStatus;
import com.lessonlink.domain.item.*;
import com.lessonlink.domain.item.embedded.Duration;
import com.lessonlink.domain.item.embedded.Period;
import com.lessonlink.domain.item.embedded.TimeRange;
import com.lessonlink.domain.item.enums.BookFormat;
import com.lessonlink.domain.item.enums.BookLanguage;
import com.lessonlink.domain.item.enums.CourseLevel;
import com.lessonlink.domain.item.enums.CourseType;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.member.enums.Role;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.post.PostCategory;
import com.lessonlink.domain.post.Tag;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.AddressDto;
import com.lessonlink.dto.ItemDto;
import com.lessonlink.dto.MemberDto;
import com.lessonlink.dto.PostDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.basicDbInit1();
        initService.basicDbInit2();
        initService.hundredDbInit();
        initService.reservationTestData();
        initService.attendanceTestData();
        initService.postTestData();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final MemberRepositoryBulk memberRepositoryBulk;
        private final ReservationService reservationService;
        private final PasswordEncoder passwordEncoder;
        private final ReservationRepository reservationRepository;
        private final AttendanceService attendanceService;
        private final MemberService memberService;
        private final PostService postService;
        private final OrderService orderService;
        private final OrderRepository orderRepository;
        private final ItemRepository itemRepository;


        public void basicDbInit1() {
            Member student = createMember(
                    new MemberDto.Builder()
                            .memberId("test1")
                            .password(passwordEncoder.encode("test1"))
                            .name("testName")
                            .birthDate(LocalDate.of(2000, 10, 14))
                            .phoneNumber("010-2222-0000")
                            .email("test@gmail.com")
                            .role(Role.STUDENT)
                            .build()
            );
            student.setAddress(
                    createAddress(
                            new AddressDto.Builder()
                                    .metropolitanCityProvince("서울특별시")
                                    .cityDistrict("강남구")
                                    .village("삼성동")
                                    .roadName("테헤란로")
                                    .roadNumber(427)
                                    .zipCode("06168")
                                    .build()
                    )
            );
            em.persist(student);

            Member teacher = createMember(
                    new MemberDto.Builder()
                            .memberId("teacher1")
                            .password(passwordEncoder.encode("teachPass1"))
                            .name("teacherName")
                            .birthDate(LocalDate.of(1985, 5, 20))
                            .phoneNumber("010-3333-1111")
                            .email("teacher@example.com")
                            .role(Role.TEACHER)
                            .build()
            );

            teacher.setAddress(
                    createAddress(
                            new AddressDto.Builder()
                                    .metropolitanCityProvince("경기도")
                                    .cityDistrict("성남시 분당구")
                                    .village("정자동")
                                    .roadName("분당내곡로")
                                    .roadNumber(151)
                                    .zipCode("13529")
                                    .build()
                    )
            );

            em.persist(teacher);

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
                            .teacherId(teacher.getId())
                            .description("자바 프로그래밍의 기초를 배울 수 있는 강의로, 변수, 제어문, 객체 지향 개념을 다룹니다.")
                            .period(new Period(LocalDate.of(2024, 11, 1), LocalDate.of(2025, 1, 31)))
                            .timeRange(new TimeRange(LocalTime.of(10, 0), LocalTime.of(12, 0)))
                            .duration(new Duration(2, 0))
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
            Order order1 = Order.createOrder(student, createDelivery(student), orderItem1, orderItem2);
            em.persist(order1);
        }

        public void basicDbInit2() {
            Member member = createMember(
                    new MemberDto.Builder()
                            .memberId("test2")
                            .password(passwordEncoder.encode("test2"))
                            .name("testName2")
                            .birthDate(LocalDate.of(2001, 10, 14))
                            .phoneNumber("010-2222-0001")
                            .email("test2@gmail.com")
                            .role(Role.TEACHER)
                            .build()
            );

            member.setAddress(
                    createAddress(
                            new AddressDto.Builder()
                                    .metropolitanCityProvince("부산광역시")
                                    .cityDistrict("해운대구")
                                    .village("우동")
                                    .roadName("센텀중앙로")
                                    .roadNumber(90)
                                    .zipCode("48058")
                                    .build()
                    )
            );

            Member teacher = createMember(
                    new MemberDto.Builder()
                            .memberId("teacher3")
                            .password(passwordEncoder.encode("teachPass3"))
                            .name("teacherThree")
                            .birthDate(LocalDate.of(1982, 3, 10))
                            .phoneNumber("010-5555-3333")
                            .email("teacher3@example.com")
                            .role(Role.TEACHER)
                            .build()
            );

            teacher.setAddress(
                    createAddress(
                            new AddressDto.Builder()
                                    .metropolitanCityProvince("대구광역시")
                                    .cityDistrict("수성구")
                                    .village("범어동")
                                    .roadName("동대구로")
                                    .roadNumber(123)
                                    .zipCode("42180")
                                    .build()
                    )
            );

            em.persist(member);

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
                            .teacherId(teacher.getId())
                            .description("스프링 프레임워크의 고급 기능을 깊이 있게 다루는 강의로, AOP, 트랜잭션 관리, 스프링 시큐리티 등을 포함합니다.")
                            .period(new Period(LocalDate.of(2024, 12, 1), LocalDate.of(2025, 2, 28)))
                            .timeRange(new TimeRange(LocalTime.of(14, 0), LocalTime.of(17, 0)))
                            .duration(new Duration(3, 0))
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

        @LogExecutionTime
        public void hundredDbInit() {

            List<Member> members = new ArrayList<>();
            List<Member> teachers = new ArrayList<>();
            List<Book> books = new ArrayList<>();
            List<Course> courses = new ArrayList<>();

            for (int i = 0; i < 50; i++) {
                Member member1 = createMember(
                        new MemberDto.Builder()
                                .memberId("teststudent" + i)
                                .password(passwordEncoder.encode("teststudent" + i))
                                .name("ImStudent" + i)
                                .birthDate(LocalDate.of(1995 + (i/10), 10, 14))
                                .phoneNumber("010-2222-" + (1000+i))
                                .email("tester"+i+"@gmail.com")
                                .role(Role.STUDENT)
                                .build()
                );

                member1.setAddress(
                        createAddress(
                                new AddressDto.Builder()
                                        .metropolitanCityProvince("부산광역시")
                                        .cityDistrict("해운대구")
                                        .village("우동")
                                        .roadName("센텀중앙로")
                                        .roadNumber(90+i)
                                        .zipCode("48058")
                                        .build()
                        )
                );

//                em.persist(member1);
                members.add(member1);

                Member member2 = createMember(
                        new MemberDto.Builder()
                                .memberId("testteacher" + i)
                                .password(passwordEncoder.encode("testteacher" + i))
                                .name("ImTeacher" + i)
                                .birthDate(LocalDate.of(1984 + (i/10), 2, 14))
                                .phoneNumber("010-2212-" + (1000+i))
                                .email("testteacher"+i+"@gmail.com")
                                .role(Role.TEACHER)
                                .build()
                );

                member2.setAddress(
                        createAddress(
                                new AddressDto.Builder()
                                        .metropolitanCityProvince("서울특별시")
                                        .cityDistrict("노원구")
                                        .village("공릉동")
                                        .roadName("동일로193길")
                                        .roadNumber(i)
                                        .zipCode("11155")
                                        .build()
                        )
                );
//                em.persist(member2);
                members.add(member2);
                teachers.add(member2);

            }

            memberRepositoryBulk.bulkInsertMembers(members);


            for (int i = 0; i < 10; i++) {
                Book book = createBook(
                        new ItemDto.BookBuilder()
                                .author("Author " + i)
                                .isbn("978-3-16-148410-" + i)
                                .publisher("Publisher " + i)
                                .publishedDate(LocalDate.of(2023, 1, i % 30 + 1))
                                .pageCount(200 + i * 10) // 200부터 페이지 수 설정
                                .format(BookFormat.HARDCOVER)
                                .language(BookLanguage.ENGLISH)
                                .summary("Summary for Book " + i)
                                .name("Book Name " + i)
                                .price(20000 + i * 1000) // 20000부터 가격 설정
                                .stockQuantity(100 + i)
                                .build()
                );

                books.add(book);
//                em.persist(book);


                Course course = createCourse(
                        new ItemDto.CourseBuilder()
                                .teacherId(teachers.get(i).getId())
                                .description("Description for Course " + i)
                                .period(new Period(LocalDate.of(2024, i % 12 + 1, 1),
                                        LocalDate.of(2024, i % 12 + 1, 28)))
                                .timeRange(new TimeRange(LocalTime.of(10, 0), LocalTime.of(12, 0)))
                                .duration(new Duration(2, 0)) // 2시간짜리 강의
                                .level(CourseLevel.BEGINNER)
                                .courseType(CourseType.ONLINE)
                                .materialUrl("https://example.com/course-" + i)
                                .name("Course Name " + i)
                                .price(150000 + i * 5000) // 150000부터 가격 설정
                                .stockQuantity(500 + i)
                                .build()
                );

                courses.add(course);
//                em.persist(course);
            }
            itemRepository.saveAll(books);
            itemRepository.saveAll(courses);

            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < members.size(); i++) {
                OrderItem orderItem1 = OrderItem.createOrderItem(books.get(i % 10), books.get(i % 10).getPrice(), (i % 10) + 1);
                OrderItem orderItem2 = OrderItem.createOrderItem(courses.get(i % 10), courses.get(i % 10).getPrice(), (i % 10) + 1);

                Order order = Order.createOrder(members.get(i), createDelivery(members.get(i)), orderItem1, orderItem2);
                orders.add(order);
//                em.persist(order);
            }

            orderRepository.saveAll(orders);

        }

        public void reservationTestData() {
            List<Member> allMember = memberRepository.findAll(); // 한 강의에 모두 예약할 멤버들
            Member teacher = createMember(
                    new MemberDto.Builder()
                            .memberId("famous_teacher")
                            .password(passwordEncoder.encode("teachPass3"))
                            .name("teacherThree")
                            .birthDate(LocalDate.of(1982, 3, 10))
                            .phoneNumber("010-1821-8733")
                            .email("famous_teacher@example.com")
                            .role(Role.TEACHER)
                            .build()
            );

            teacher.setAddress(
                    createAddress(
                            new AddressDto.Builder()
                                    .metropolitanCityProvince("대구광역시")
                                    .cityDistrict("수성구")
                                    .village("범어동")
                                    .roadName("동대구로")
                                    .roadNumber(123)
                                    .zipCode("42180")
                                    .build()
                    )
            );

            em.persist(teacher);

            Course course = createCourse(
                    new ItemDto.CourseBuilder()
                            .teacherId(teacher.getId())
                            .description("스프링 프레임워크의 고급 기능을 깊이 있게 다루는 강의로, AOP, 트랜잭션 관리, 스프링 시큐리티 등을 포함합니다.")
                            .period(new Period(LocalDate.of(2024, 12, 1), LocalDate.of(2025, 2, 28)))
                            .timeRange(new TimeRange(LocalTime.of(14, 0), LocalTime.of(17, 0)))
                            .duration(new Duration(3, 0))
                            .level(CourseLevel.ADVANCED)
                            .courseType(CourseType.OFFLINE)
                            .materialUrl("https://example.com/advanced-spring-course")
                            .name("Advanced Spring Framework")
                            .price(250000)
                            .stockQuantity(500)
                            .build()
            );

            em.persist(course);

            for (Member member : allMember) {
                OrderItem orderItem1 = OrderItem.createOrderItem(course, course.getPrice(), 1);
                Order order = Order.createOrder(member, createDelivery(member), orderItem1);
                em.persist(order);

                reservationService.makeReservation(order.getId());
            }

        }

        public void attendanceTestData() {
            List<Reservation> reservations = reservationRepository.findByCourseId(25L, PageRequest.of(0, 1000));

            for (Reservation reservation : reservations) {
                attendanceService.checkIn(reservation.getId());
            }
        }

        public void postTestData() {

            Member member = memberService.findOneByMemberId("test1");

            PostCategory postCategory1 = createPostCategory("자유게시판");
            em.persist(postCategory1);

            Tag tag = new Tag();
            em.persist(tag);

            PostDto postDto = createPostDto("test게시글제목", "test게시글내용", false);

            postService.postByMemberIdSecretKey(member.getId(), postCategory1.getId(), postDto, tag.getId());
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

        private Address createAddress(AddressDto addressDto){
            Address address = new Address(addressDto);
            return address;
        }

        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setDeliveryInfo(member.getAddress(), DeliveryStatus.READY);
            return delivery;
        }

        private PostDto createPostDto(String postTitle, String postContents, boolean isNotices) {
            return new PostDto.Builder()
                    .title(postTitle)
                    .contents(postContents)
                    .isNotice(isNotices)
                    .build();
        }

        private PostCategory createPostCategory(String postCategoryName) {
            return new PostCategory(postCategoryName);
        }
    }
}
