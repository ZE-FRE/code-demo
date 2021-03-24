package cn.zefre.factory.virtual.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pujian
 * @date 2021/3/24 10:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Engine {
    protected String name;
}
