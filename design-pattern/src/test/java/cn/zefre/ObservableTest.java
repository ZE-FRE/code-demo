package cn.zefre;

import cn.zefre.observable.CurrentConditionObserver;
import cn.zefre.observable.ForecastObserver;
import cn.zefre.observable.Observer;
import cn.zefre.observable.WeatherData;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/19 15:48
 */
public class ObservableTest {

    @Test
    public void testObservable() {
        WeatherData weatherData = new WeatherData();
        Observer observer1 = new CurrentConditionObserver(weatherData.getObservable());
        Observer observer2 = new ForecastObserver(weatherData.getObservable());

        weatherData.measurementChanged(22, 67, 120);
        weatherData.measurementChanged(18, 45, 110);
        weatherData.measurementChanged(28, 56, 125);

    }
}
