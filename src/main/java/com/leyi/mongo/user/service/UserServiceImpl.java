package com.leyi.mongo.user.service;

import com.leyi.mongo.user.dao.UserRepository;
import com.leyi.mongo.user.domain.User;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户操作实现类
 * @author: Li ZeKai
 * @time: 2021/4/3 19:37
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseGet(() -> User.builder().build());
    }

    @Override
    public boolean incrField(String id, Integer incrNum) {
        // 构造查询对象
        Query query = Query.query(Criteria.where("_id").is(id));
        // 构造更新对象
        Update update = new Update();
        // age+1
        update.inc("age", incrNum);
        // 执行update
        final UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        return updateResult.wasAcknowledged() && (updateResult.getModifiedCount() == 1);
    }

    @Override
    public List<User> findAllSimple(User condition) {
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll() // 满足所有条件 AND
//                .matchingAny() // 满足任一条件 OR
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.caseSensitive())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.regex())
                .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.caseSensitive())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.caseSensitive());
        Example<User> example = Example.of(condition, matcher);

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "age"));

        return userRepository.findAll(example, Sort.by(orders));
    }

    @Override
    public List<User> findAllComplex(User condition) {
        // 构造一个查询对象
        Query query = new Query();
        // 设置参数
        if (StringUtils.hasLength(condition.getId())) {
            query.addCriteria(Criteria.where("id").is(condition.getId()));
        }
        if (StringUtils.hasLength(condition.getName())) {
            query.addCriteria(Criteria.where("name").regex(condition.getName()));
        }
        if (!ObjectUtils.isEmpty(condition.getAge())) {
            query.addCriteria(Criteria.where("age").is(condition.getAge()));
        }
        if (!ObjectUtils.isEmpty(condition.getSex())) {
            query.addCriteria(Criteria.where("sex").is(condition.getSex()));
        }

        // 时间范围查询
        if (!ObjectUtils.isEmpty(condition.getStartTime()) && !ObjectUtils.isEmpty(condition.getEndTime())) {
            query.addCriteria(Criteria.where("createTime").gte(condition.getStartTime()).lte(condition.getEndTime()));
        }

        // 构造排序对象
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "age"));
        // 设置排序对象
        query.with(Sort.by(orders));

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Page<User> findAllSimple(User condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll() // 满足所有条件 AND
//                .matchingAny() // 满足任一条件 OR
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.caseSensitive())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.regex())
                .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.caseSensitive())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.caseSensitive());
        Example<User> example = Example.of(condition, matcher);

        return userRepository.findAll(example, pageable);
    }

    @Override
    public Page<User> findAllComplex(User condition, Pageable pageable) {
        // 构造一个查询对象
        Query query = new Query();
        // 设置参数
        if (StringUtils.hasLength(condition.getId())) {
            query.addCriteria(Criteria.where("id").is(condition.getId()));
        }
        if (StringUtils.hasLength(condition.getName())) {
            query.addCriteria(Criteria.where("name").regex(condition.getName()));
        }
        if (!ObjectUtils.isEmpty(condition.getAge())) {
            query.addCriteria(Criteria.where("age").is(condition.getAge()));
        }
        if (!ObjectUtils.isEmpty(condition.getSex())) {
            query.addCriteria(Criteria.where("sex").is(condition.getSex()));
        }

        // 时间范围查询
        if (!ObjectUtils.isEmpty(condition.getStartTime()) && !ObjectUtils.isEmpty(condition.getEndTime())) {
            query.addCriteria(Criteria.where("createTime").gte(condition.getStartTime()).lte(condition.getEndTime()));
        }

        // 设置分页对象
        query.with(pageable);

        // 构造排序对象
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "age");
        // 设置排序对象
        query.with(Sort.by(order));

        final List<User> users = mongoTemplate.find(query, User.class);
        final long count = mongoTemplate.count(query, User.class);
        // 构造分页
        return new PageImpl<>(users, PageRequest.of((int) (count / pageable.getPageSize()), pageable.getPageSize()), count);
    }
}
