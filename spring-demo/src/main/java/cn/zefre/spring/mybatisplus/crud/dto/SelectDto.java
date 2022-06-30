package cn.zefre.spring.mybatisplus.crud.dto;

import cn.zefre.spring.common.dto.BaseDto;
import cn.zefre.spring.mybatisplus.crud.Condition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author pujian
 * @date 2022/6/24 14:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询Dto")
public class SelectDto extends BaseDto {

    /**
     * 表名
     */
    @NotEmpty(message = "表名不能为空")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    /**
     * 查询字段列表
     */
    @NotEmpty(message = "查询字段列表不能为空")
    @ApiModelProperty(value = "查询字段列表", required = true)
    private List<String> fields;

    /**
     * 筛选条件
     */
    @ApiModelProperty(value = "筛选条件")
    private Condition condition;

}
