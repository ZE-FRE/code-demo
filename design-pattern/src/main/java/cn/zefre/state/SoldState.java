package cn.zefre.state;

import lombok.AllArgsConstructor;

/**
 * @author pujian
 * @date 2021/4/12 15:29
 */
@AllArgsConstructor
public class SoldState implements State {

    private GumballMachine machine;

    @Override
    public void dispense() {
        machine.releaseGumball();
        if(machine.getCount() > 0) {
            machine.setCurrentState(machine.noQuarterState);
        } else {
            machine.setCurrentState(machine.soldOutState);
        }
    }
}
