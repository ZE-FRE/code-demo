package cn.zefre.es.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author pujian
 * @date 2020/12/3 10:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie {

    @JSONField(serialize = false)
    private Integer id;

    private String name;

    @JSONField(format = "yyyy-MM-dd")
    /** 上映日期 */
    private Date releaseDate;

    /** 电影时长 */
    private String duration;

    /** 电影简介 */
    private String introduce;
}
