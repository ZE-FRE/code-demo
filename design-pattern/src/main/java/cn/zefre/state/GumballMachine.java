package cn.zefre.state;

import lombok.Data;

/**
 * @author pujian
 * @date 2021/4/12 15:03
 */
@Data
public class GumballMachine {

    State noQuarterState;

    State hasQuarterState;

    State soldState;

    State soldOutState;

    private State currentState = new SoldOutState(this);

    private int count;

    public GumballMachine() {}

    public GumballMachine(int initialCount) {
        this.count = initialCount;
        this.noQuarterState = new NoQuarterState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.soldState = new SoldState(this);
        this.soldOutState = new SoldOutState(this);
        this.currentState = this.noQuarterState;
    }


    public void insertCoin() {
        this.currentState.insertCoin();
    }

    public void turnCrank() {
        this.currentState.turnCrank();
        this.currentState.dispense();
    }

    public void backCoin() {
        this.currentState.backCoin();
    }

    public void releaseGumball() {
        System.out.println("发放一颗口香糖");
        this.count = this.count - 1;
    }
}
