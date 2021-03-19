package cn.zefre.observable;

/**
 * @author pujian
 * @date 2021/3/19 15:45
 */
public class CurrentConditionObserver implements Observer {

    public CurrentConditionObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object args) {
        if(null == args) {
            System.out.println("CurrentConditionObserver has been notified");
        }
        if(args instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) args;
            long temperature = weatherData.getTemperature();
            long humidity = weatherData.getHumidity();
            System.out.println("CurrentConditionObserver: temperature=" + temperature + ";humidity=" + humidity);
        }
    }
}
