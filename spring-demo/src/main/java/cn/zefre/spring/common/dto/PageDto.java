package cn.zefre.spring.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author pujian
 * @date 2022/6/29 17:18
 */
@Data
public class PageDto {

    @ApiModelProperty(value = "分页编号")
    private Integer pageNo;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "分页数据")
    private List<?> rows;

    @ApiModelProperty(value = "总数据")
    private int total;

}
