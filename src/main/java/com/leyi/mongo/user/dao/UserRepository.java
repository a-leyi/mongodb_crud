package com.leyi.mongo.user.dao;

import com.leyi.mongo.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @description: 用户持久化操作
 * @author: Li ZeKai
 * @time: 2021/4/3 19:34
 */

public interface UserRepository extends MongoRepository<User, String> {
}
