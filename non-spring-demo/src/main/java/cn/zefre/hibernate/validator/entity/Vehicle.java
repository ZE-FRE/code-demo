package cn.zefre.hibernate.validator.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/15 15:50
 */
public class Vehicle {

    /**
     * 牌照
     */
    @NotNull
    private String licensePlate;

    @Valid
    private Person driver;

    public Vehicle(String licensePlate, Person driver) {
        this.licensePlate = licensePlate;
        this.driver = driver;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }
}
