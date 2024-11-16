package com.lessonlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.lessonlink", "com.lessonlink.config"})
public class LessonlinkApplication {
	public static void main(String[] args) {
		SpringApplication.run(LessonlinkApplication.class, args);
	}
}
