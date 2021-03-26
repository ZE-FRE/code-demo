package cn.zefre.template;

/**
 * @author pujian
 * @date 2021/3/25 14:08
 */
public abstract class CookNoodles {

    /**
     * 模板方法，固定了煮面的算法流程
     * @author pujian
     * @date 2021/3/25 14:35
     * @return
     */
    public void cook() {
        boilWater();
        prepareIngredient();
        prepareNoodles();
        boil();
        if (isRinse()) {
            rinseNoodles();
        }
        hook();
    }

    public void boilWater() {
        System.out.println("烧开水...");
    }

    /**
     * 准备调料
     * @author pujian
     * @date 2021/3/25 14:30
     * @return
     */
    public abstract void prepareIngredient();

    public abstract void prepareNoodles();

    public void boil() {
        System.out.println("放入面条，煮熟");
    }

    /**
     * 用于逻辑判断的钩子函数
     * @author pujian
     * @date 2021/3/25 14:33
     * @return
     */
    public boolean isRinse() {
        return false;
    }

    /**
     * 用凉水过一遍
     * @author pujian
     * @date 2021/3/25 14:30
     * @return
     */
    public void rinseNoodles() {
        System.out.println("用凉水过凉面条");
    }

    /**
     * 其他空实现钩子函数
     * @author pujian
     * @date 2021/3/25 14:34
     * @return
     */
    public void hook() {}
}
