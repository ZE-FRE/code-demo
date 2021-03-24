package cn.zefre.factory.virtual.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轮胎
 * @author pujian
 * @date 2021/3/24 10:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Tyre {
    protected String name;
}
