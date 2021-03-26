package cn.zefre.builder;

import lombok.Data;
import lombok.ToString;

/**
 * @author pujian
 * @date 2021/3/25 10:00
 */
@Data
@ToString
public class Computer {

    private String cpu;

    private String gpu;

    private String memory;

    private String disk;


}
