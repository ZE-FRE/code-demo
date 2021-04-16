package cn.zefre.state;

import lombok.AllArgsConstructor;

/**
 * @author pujian
 * @date 2021/4/12 15:18
 */
@AllArgsConstructor
public class NoQuarterState implements State {

    private GumballMachine machine;

    @Override
    public void insertCoin() {
        System.out.println("放入硬币！");
        machine.setCurrentState(machine.hasQuarterState);
    }


}
