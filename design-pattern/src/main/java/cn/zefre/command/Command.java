package cn.zefre.command;

/**
 * @author pujian
 * @date 2021/3/27 14:39
 */
public interface Command {

    void execute();

    void undo();
}
