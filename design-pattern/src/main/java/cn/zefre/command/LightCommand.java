package cn.zefre.command;

/**
 * @author pujian
 * @date 2021/3/27 14:40
 */
public class LightCommand implements Command {

    private Light light;

    public LightCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        this.light.on();
    }

    @Override
    public void undo() {
        this.light.off();
    }
}
