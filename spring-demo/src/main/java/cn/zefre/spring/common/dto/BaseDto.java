package cn.zefre.spring.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BaseDto", description = "分页请求父类")
public abstract class BaseDto {

	private static final long serialVersionUID = -6154294972982694604L;

	@ApiModelProperty(value = "分页编号")
	private Integer pageNo=1;

	@ApiModelProperty(value = "分页大小")
	private Integer pageSize=20;


}
