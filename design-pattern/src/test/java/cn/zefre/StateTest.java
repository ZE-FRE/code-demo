package cn.zefre;

import cn.zefre.state.GumballMachine;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/4/12 15:58
 */
public class StateTest {

    @Test
    public void testGumballMachine() {
        GumballMachine machine = new GumballMachine(2);

        machine.insertCoin();
        machine.turnCrank();

        machine.turnCrank();

        machine.insertCoin();
        machine.backCoin();
    }
}
