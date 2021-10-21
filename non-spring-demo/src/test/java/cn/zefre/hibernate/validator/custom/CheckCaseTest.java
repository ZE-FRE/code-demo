package cn.zefre.hibernate.validator.custom;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Set;

/**
 * 自定义注解测试
 *
 * @author pujian
 * @date 2021/10/19 11:14
 */
public class CheckCaseTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUpper() {
        Computer hpComputer = new Computer("hp");
        Set<ConstraintViolation<Computer>> violations = validator.validate(hpComputer);
        Assert.assertEquals("厂商必须大写", violations.iterator().next().getMessage());
    }
}
