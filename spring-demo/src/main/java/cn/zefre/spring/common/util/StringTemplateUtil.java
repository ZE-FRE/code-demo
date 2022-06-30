package cn.zefre.spring.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ParserContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/28 14:14
 */
@UtilityClass
public class StringTemplateUtil {

    private SpelExpressionParser parser = new SpelExpressionParser();

    private ParserContext parserContext = new CustomParserContext();

    private PropertyAccessor propertyAccessor = new MapAccessor();

    static final class CustomParserContext implements ParserContext {
        @Override
        public boolean isTemplate() { return true; }
        @Override
        public String getExpressionPrefix() { return "$("; }
        @Override
        public String getExpressionSuffix() { return ")"; }
    }


    public static String replace(String template, Map<String, ?> map) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(map);
        evaluationContext.addPropertyAccessor(propertyAccessor);
        return parser.parseExpression(template, parserContext).getValue(evaluationContext, String.class);
    }

}
