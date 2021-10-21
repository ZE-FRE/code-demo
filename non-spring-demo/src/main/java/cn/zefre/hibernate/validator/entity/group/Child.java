package cn.zefre.hibernate.validator.entity.group;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pujian
 * @date 2021/10/18 15:02
 */
@Data
@AllArgsConstructor
public class Child {

    @NotBlank(message = "name不能为null")
    private String name;

    @Min(value = 18, message = "美国未成年人禁止玩儿手机", groups = America.class)
    private int age;

    @NotNull
    @Valid
    private Phone phone;

}
