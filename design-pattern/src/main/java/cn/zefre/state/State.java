package cn.zefre.state;

/**
 * @author pujian
 * @date 2021/4/12 15:05
 */
public interface State {

    default void insertCoin() {
        System.out.println("can't insert coin!");
    }

    default void turnCrank() {
        System.out.println("can't turn crank!");
    }

    default void backCoin() {
        System.out.println("can't back coin!");
    }

    default void dispense() {
        System.out.println("can't dispense gumball!");
    }
}
