package cn.zefre.factory.virtual;

import cn.zefre.factory.virtual.component.BMWEngine;
import cn.zefre.factory.virtual.component.BMWTyre;
import cn.zefre.factory.virtual.component.Engine;
import cn.zefre.factory.virtual.component.Tyre;

/**
 * @author pujian
 * @date 2021/3/24 11:18
 */
public class BMWFactory implements Factory {
    @Override
    public Engine produceEngine() {
        return new BMWEngine();
    }

    @Override
    public Tyre produceTyre() {
        return new BMWTyre();
    }
}
