package cn.zefre.common.jackson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 莲花
 *
 * @author pujian
 * @date 2022/3/22 15:19
 */
@Data
@AllArgsConstructor
public class Lotus {

    private String name;

    private double weight;

    private Empty empty;

    public Lotus() {

    }

    public Lotus(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

}
