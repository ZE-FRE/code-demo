package cn.zefre.state;

import lombok.AllArgsConstructor;

/**
 * @author pujian
 * @date 2021/4/12 15:08
 */
@AllArgsConstructor
public class HasQuarterState implements State {

    private GumballMachine machine;

    @Override
    public void turnCrank() {
        System.out.println("转动了曲柄");
        machine.setCurrentState(machine.soldState);
    }

    @Override
    public void backCoin() {
        System.out.println("退回硬币");
        machine.setCurrentState(machine.noQuarterState);
    }
}
