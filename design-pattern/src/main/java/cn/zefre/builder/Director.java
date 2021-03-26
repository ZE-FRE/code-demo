package cn.zefre.builder;

/**
 * @author pujian
 * @date 2021/3/25 10:08
 */
public class Director {

    private ComputerBuilder builder;

    public Director(ComputerBuilder builder) {
        this.builder = builder;
    }

    public Computer build() {
        this.builder.buildCpu();
        this.builder.buildGpu();
        this.builder.buildMemory();
        this.builder.buildDisk();
        return this.builder.build();
    }
}
