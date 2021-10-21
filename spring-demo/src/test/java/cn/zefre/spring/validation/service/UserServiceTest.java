package cn.zefre.spring.validation.service;

import cn.zefre.spring.validation.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author pujian
 * @date 2021/10/19 15:38
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private Validator validator;

    @Resource
    private Validator validator2;

    @Test
    public void testValidate() {

        System.out.println(validator.getClass());
        System.out.println(validator2.getClass());

        UserDto userDto = new UserDto();
        Set<ConstraintViolation<UserDto>> violations = validator2.validate(userDto);
        for (ConstraintViolation<UserDto> violation : violations) {
            log.info(violation.getMessage());
        }

    }

    @Test
    public void testUpdateNameById() {
        userService.updateNameById("", null);
    }

    @Test
    public void testGetById() {
        UserDto userDto = userService.getById("1");
    }

    @Test
    public void testInsert() {
        userService.insert(new UserDto());
    }
}
