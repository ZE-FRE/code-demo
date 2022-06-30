package cn.zefre.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pujian
 * @date 2021/10/19 16:39
 */
@SpringBootApplication(scanBasePackages = {"cn.zefre.base", "cn.zefre.spring"})
@MapperScan("cn.zefre.spring.**.mapper")
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class);
    }
}
