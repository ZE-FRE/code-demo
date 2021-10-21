package cn.zefre.hibernate.validator.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/15 15:50
 */
public class Person {

    @NotNull
    private String name;

    @NotNull
    @Valid
    private Vehicle vehicle;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
