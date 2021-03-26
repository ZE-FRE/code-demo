package cn.zefre.builder;

/**
 * @author pujian
 * @date 2021/3/25 10:05
 */
public interface ComputerBuilder {

    void buildCpu();

    void buildGpu();

    void buildMemory();

    void buildDisk();

    Computer build();

}
