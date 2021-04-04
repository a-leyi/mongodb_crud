package com.leyi.mongo.user.service;

import com.leyi.mongo.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 数据操作接口（mongodb）
 * @author: Li ZeKai
 * @time: 2021/4/3 19:36
 */

public interface UserService {

    /**
     * 保存
     *
     * @param user 数据对象
     * @return 数据对象
     */
    User save(User user);

    /**
     * 根据ID删除
     *
     * @param id ID
     */
    void deleteById(String id);

    /**
     * 更新
     *
     * @param user 数据对象
     * @return 数据对象
     */
    User update(User user);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 数据对象
     */
    User findById(String id);

    /**
     * 字段自增
     *
     * @param id      ID
     * @param incrNum 增长值
     * @return 写入被确认 && 修改的文档数量 == 1
     */
    boolean incrField(String id, Integer incrNum);

    /**
     * 简单条件 查询所有
     *
     * @param condition condition
     * @return 数据对象集合
     */
    List<User> findAllSimple(User condition);

    /**
     * 复杂条件 查询所有
     *
     * @param condition condition
     * @return 数据对象集合
     */
    List<User> findAllComplex(User condition);

    /**
     * 简单条件 分页查询
     *
     * @param condition condition
     * @param condition pageable
     * @return 数据对象分页集合
     */
    Page<User> findAllSimple(User condition, Pageable pageable);

    /**
     * 复杂条件 分页查询
     *
     * @param condition condition
     * @param condition pageable
     * @return 数据对象分页集合
     */
    Page<User> findAllComplex(User condition, Pageable pageable);
}
