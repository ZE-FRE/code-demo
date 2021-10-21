package cn.zefre.spring.validation.entity;

import lombok.Data;

/**
 * @author pujian
 * @date 2021/10/19 15:36
 */
@Data
public class User {

    private String id;

    private String username;

    private int age;

    private String phone;

    private String email;
}
