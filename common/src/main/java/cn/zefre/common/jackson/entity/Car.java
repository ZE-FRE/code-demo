package cn.zefre.common.jackson.entity;

import lombok.ToString;

/**
 * @author pujian
 * @date 2022/3/22 14:41
 */
@ToString(callSuper = true)
public class Car extends Vehicle {

    private String name;

    public static int tyre = 4;

    public Car() {
        setName("john");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("super vehicle name is: " + super.getName());
        System.out.println("car name is: " + this.name);
    }

    public String getName() {
        return this.name;
    }

}
