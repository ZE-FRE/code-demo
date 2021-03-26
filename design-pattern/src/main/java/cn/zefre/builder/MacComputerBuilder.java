package cn.zefre.builder;

/**
 * @author pujian
 * @date 2021/3/25 10:13
 */
public class MacComputerBuilder implements ComputerBuilder {

    private Computer computer;

    public MacComputerBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void buildCpu() {
        computer.setCpu("mac cpu");
    }

    @Override
    public void buildGpu() {
        computer.setGpu("mac gpu");
    }

    @Override
    public void buildMemory() {
        computer.setMemory("mac memory");
    }

    @Override
    public void buildDisk() {
        computer.setDisk("mac disk");
    }

    @Override
    public Computer build() {
        return this.computer;
    }
}
