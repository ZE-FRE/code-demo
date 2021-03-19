package cn.zefre.observable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pujian
 * @date 2021/3/19 15:43
 */
@Data
public class WeatherData {

    private Observable observable = new ObservableImpl();

    private long temperature;

    private long humidity;

    private long pressure;

    public void measurementChanged(long temperature, long humidity, long pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        observable.notifyObservers(this);
    }

}
