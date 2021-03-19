package cn.zefre.observable;

/**
 * @author pujian
 * @date 2021/3/19 15:55
 */
public class ForecastObserver implements Observer {
    public ForecastObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object args) {
        if(null == args) {
            System.out.println("ForecastObserver has been notified");
        }
        if(args instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) args;
            long pressure = weatherData.getPressure();
            System.out.println("ForecastObserver: pressure=" + pressure);
        }
    }
}
