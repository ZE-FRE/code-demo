package cn.zefre.decorator;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pujian
 * @date 2021/3/22 11:13
 */
@Data
@NoArgsConstructor
public abstract class Beverage {

    public Beverage(String description) {
        this.description = description;
    }

    protected String description;

    public abstract double cost();
}
