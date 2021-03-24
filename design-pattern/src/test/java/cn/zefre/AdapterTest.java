package cn.zefre;

import cn.zefre.adapter.Cat;
import cn.zefre.adapter.Robot;
import cn.zefre.adapter.RobotAdapter;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/24 17:03
 */
public class AdapterTest {

    @Test
    public void testAdapter() {
        Robot robot = new RobotAdapter(new Cat());
        robot.speak();
        robot.move();
    }
}
