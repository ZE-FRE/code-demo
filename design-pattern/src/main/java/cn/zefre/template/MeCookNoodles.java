package cn.zefre.template;

/**
 * @author pujian
 * @date 2021/3/25 14:36
 */
public class MeCookNoodles extends CookNoodles {
    @Override
    public void prepareIngredient() {
        System.out.println("我准备了葱、姜、蒜、辣椒、香油、藤椒油等调料");
    }

    @Override
    public void prepareNoodles() {
        System.out.println("我准备了中江细面");
    }

    /**
     * 覆盖钩子函数
     * @author pujian
     * @date 2021/3/25 14:39
     * @return
     */
    @Override
    public boolean isRinse() {
        return true;
    }

    /**
     * 覆盖钩子函数
     * @author pujian
     * @date 2021/3/25 14:40
     * @return
     */
    @Override
    public void hook() {
        System.out.println("我吃完了面条");
    }
}
