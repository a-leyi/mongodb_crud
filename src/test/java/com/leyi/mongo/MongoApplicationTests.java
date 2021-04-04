package com.leyi.mongo;

import cn.hutool.core.date.DateUtil;
import com.leyi.mongo.user.domain.User;
import com.leyi.mongo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
class MongoApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void save() {
        final User user = User.builder()
                .name("乐一")
                .sex(1)
                .createTime(DateUtil.parseDateTime("2021-04-05 00:00:00"))
                .updateTime(DateUtil.parseDateTime("2021-04-05 00:00:00"))
                .build();

        for (int i = 21; i <= 40; i++) {
            user.setId(String.valueOf(i));
            user.setAge(i);
            final User target = userService.save(user);
            System.out.println(target);
        }
    }

    @Test
    void findById() {
        User user = userService.findById("0");
        log.info("{}", user);
    }

    @Test
    void deleteById() {
        userService.deleteById("2");
    }

    @Test
    void update() {
        final User user = userService.update(User.builder().id("2").name("李泽锴update").age(23).sex(1).createTime(new Date()).updateTime(new Date()).build());
        log.info("{}", user);
    }

    @Test
    void incrAge() {
        final boolean result = userService.incrField(String.valueOf(0), 2);
        log.info("修改结果: {}", result);
    }

    @Test
    void findAllSimple() {
        final User condition = User.builder()
                .name("乐")
                .build();

        final List<User> users = userService.findAllSimple(condition);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void findAllComplex() {
        User condition = User.builder()
                .name("乐")
                .startTime(DateUtil.parseDateTime("2021-04-04 00:00:00"))
                .endTime(DateUtil.parseDateTime("2021-04-04 23:59:59"))
                .build();

        final List<User> users = userService.findAllComplex(condition);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void findPageAllSimple() {
        int limit = 10;
        int page = 1;
        Pageable pageable = PageRequest.of(page - 1, limit);

        User condition = User.builder()
                .name("乐")
                .build();
        final Page<User> userPage = userService.findAllSimple(condition, pageable);

        log.info("getTotalElements: {}, getTotalPages: {}", userPage.getTotalElements(), userPage.getTotalPages());
        for (User user : userPage.getContent()) {
            System.out.println(user);
        }
    }

    @Test
    void findPageAllComplex() {
        int limit = 10;
        int page = 1;
        Pageable pageable = PageRequest.of(page - 1, limit);

        User condition = User.builder()
                .name("乐")
                .startTime(DateUtil.parseDateTime("2021-04-04 00:00:00"))
                .endTime(DateUtil.parseDateTime("2021-04-04 23:59:59"))
                .build();

        final Page<User> userPage = userService.findAllComplex(condition, pageable);

        log.info("getTotalElements: {}, getTotalPages: {}", userPage.getTotalElements(), userPage.getTotalPages());
        for (User user : userPage.getContent()) {
            System.out.println(user);
        }
    }
}
