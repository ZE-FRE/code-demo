package cn.zefre.state;

import lombok.AllArgsConstructor;

/**
 * @author pujian
 * @date 2021/4/12 15:29
 */
@AllArgsConstructor
public class SoldOutState implements State {

    private GumballMachine machine;
}
