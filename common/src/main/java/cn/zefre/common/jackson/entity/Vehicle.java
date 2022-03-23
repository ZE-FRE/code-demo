package cn.zefre.common.jackson.entity;

import lombok.ToString;

/**
 * @author pujian
 * @date 2022/3/22 13:32
 */
@ToString
public class Vehicle {

    private String name;

    private double weight = 1.5;

    public Vehicle() {
        setName("alice");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("vehicle name is: " + this.name);
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
