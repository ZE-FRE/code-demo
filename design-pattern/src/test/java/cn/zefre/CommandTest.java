package cn.zefre;

import cn.zefre.command.Light;
import cn.zefre.command.LightCommand;
import cn.zefre.command.LightSwitch;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/27 14:45
 */
public class CommandTest {

    @Test
    public void testLightOn() {
        LightSwitch lightSwitch = new LightSwitch(new LightCommand(new Light()));
        lightSwitch.lightOn();

        // 撤销操作
        lightSwitch.lightOff();
    }
}
