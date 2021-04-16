package cn.zefre.proxy.cglib;

/**
 * @author pujian
 * @date 2021/4/16 15:33
 */
public class UserDao {

    public void selectOne(String name) {
        System.out.println(name + " was queried");
    }

    public void update(String name) {
        System.out.println(name + " was modified");
    }
}
