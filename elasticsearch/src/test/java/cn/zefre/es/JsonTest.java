package cn.zefre.es;

import cn.zefre.es.entity.Movie;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author pujian
 * @date 2020/12/3 10:45
 */
public class JsonTest {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testFastJson() throws ParseException {
        Movie movie = new Movie(1, "泰坦尼克号", sdf.parse("1999-06-12"), "2小时57分钟34秒",
                "史诗级爱情灾难巨作");
        // Object to json string
        String movieString = JSON.toJSONString(movie);
        System.out.println(movieString);

        // json string to Object
        Movie movie1 = JSON.parseObject(movieString, Movie.class);
        System.out.println(movie1);
    }
}
