package cn.zefre.observable;

/**
 * @author pujian
 * @date 2021/3/19 15:12
 */
public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void notifyObservers(Object args);
}
