package com.leyi.mongo.user.domain.vo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @description:
 * @author: Li ZeKai
 * @time: 2021/4/3 19:17
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "age")
    private Integer age;

    @Field(name = "sex")
    private Integer sex;

    @Field(name = "createTime")
    private Date createTime;

    @Field(name = "updateTime")
    private Date updateTime;
}
