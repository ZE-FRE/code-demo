package cn.zefre.observable;

import java.util.*;

/**
 * @author pujian
 * @date 2021/3/19 15:20
 */
public class ObservableImpl implements Observable {

    private Set<Observer> observers = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object args) {
        observers.forEach(observer -> observer.update(this, args));
    }
}
