package cn.zefre.adapter;

/**
 * @author pujian
 * @date 2021/3/24 17:01
 */
public class RobotAdapter implements Robot {

    private Cat cat;

    public RobotAdapter(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void speak() {
        this.cat.meow();
    }

    @Override
    public void move() {
        this.cat.run();
    }
}
