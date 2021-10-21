package cn.zefre.hibernate.validator.group;

import cn.zefre.hibernate.validator.entity.group.America;
import cn.zefre.hibernate.validator.entity.group.Child;
import cn.zefre.hibernate.validator.entity.group.Domestic;
import cn.zefre.hibernate.validator.entity.group.Phone;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.groups.Default;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Set;

/**
 * @author pujian
 * @date 2021/10/18 15:10
 */
public class SimpleGroupTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDefaultGroup() {
        Phone phone = new Phone(null, false);
        Child child = new Child(null, 16, phone);

        Set<ConstraintViolation<Child>> violations = validator.validate(child);
        Assert.assertEquals(2, violations.size());
        for (ConstraintViolation<Child> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testDomesticGroup() {
        Phone phone = new Phone(null, false);
        Child child = new Child(null, 16, phone);

        Set<ConstraintViolation<Child>> violations = validator.validate(child, Domestic.class);
        Assert.assertEquals("手机必须附带充电器", violations.iterator().next().getMessage());
    }

    @Test
    public void testDomesticAndDefaultGroup() {
        Phone phone = new Phone(null, false);
        Child child = new Child(null, 16, phone);

        Set<ConstraintViolation<Child>> violations = validator.validate(child, Default.class, Domestic.class);
        Assert.assertEquals(3, violations.size());
        for (ConstraintViolation<Child> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testAllGroup() {
        Phone phone = new Phone(null, false);
        Child child = new Child(null, 16, phone);

        Set<ConstraintViolation<Child>> violations = validator.validate(child, Default.class, Domestic.class, America.class);
        Assert.assertEquals(4, violations.size());
        for (ConstraintViolation<Child> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
