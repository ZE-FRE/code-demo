package cn.zefre.command;

/**
 * 开关，对应命令模式的Invoker
 * Invoker发出命令
 * @author pujian
 * @date 2021/3/27 14:43
 */
public class LightSwitch {

    /**
     * Invoker持有Command的引用
     */
    private Command command;

    public LightSwitch(Command command) {
        this.command = command;
    }

    public void lightOn() {
        this.command.execute();
    }

    public void lightOff() {
        this.command.undo();
    }
}
