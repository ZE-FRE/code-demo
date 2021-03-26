package cn.zefre.template;

/**
 * @author pujian
 * @date 2021/3/25 14:37
 */
public class BrotherCookNoodles extends CookNoodles {
    @Override
    public void prepareIngredient() {
        System.out.println("the brother准备了猪油、蒜、辣椒等调料");
    }

    @Override
    public void prepareNoodles() {
        System.out.println("the brother准备了水面");
    }
}
