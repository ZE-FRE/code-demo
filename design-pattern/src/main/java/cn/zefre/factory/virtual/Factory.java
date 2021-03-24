package cn.zefre.factory.virtual;

import cn.zefre.factory.virtual.component.Engine;
import cn.zefre.factory.virtual.component.Tyre;

/**
 * @author pujian
 * @date 2021/3/24 10:54
 */
public interface Factory {

    Engine produceEngine();

    Tyre produceTyre();
}
