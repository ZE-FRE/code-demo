package cn.zefre.factory.virtual;

import cn.zefre.factory.virtual.component.BenzEngine;
import cn.zefre.factory.virtual.component.BenzTyre;
import cn.zefre.factory.virtual.component.Engine;
import cn.zefre.factory.virtual.component.Tyre;

/**
 * @author pujian
 * @date 2021/3/24 11:18
 */
public class BenzFactory implements Factory {
    @Override
    public Engine produceEngine() {
        return new BenzEngine();
    }

    @Override
    public Tyre produceTyre() {
        return new BenzTyre();
    }
}
