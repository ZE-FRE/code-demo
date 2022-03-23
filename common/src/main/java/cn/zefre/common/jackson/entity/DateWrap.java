package cn.zefre.common.jackson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author pujian
 * @date 2022/3/23 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateWrap {

    @JsonProperty("now")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy_MM_dd HH:mm:ss")
    private Date date;
}
