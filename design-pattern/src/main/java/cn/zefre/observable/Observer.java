package cn.zefre.observable;

/**
 * @author pujian
 * @date 2021/3/19 15:13
 */
public interface Observer {

    void update(Observable observable, Object args);
}
