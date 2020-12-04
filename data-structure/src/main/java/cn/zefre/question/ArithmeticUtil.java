package cn.zefre.question;

import cn.zefre.stack.impl.LinkedStack;

/**
 * 四则运算
 * @Author pujian
 * @Date 2020/5/19
 * @Description
 */
public class ArithmeticUtil {

    /**
     * 对输入的表达式计算结果
     * @Author pujian
     * @Date 2020/5/19 11:32
     * @Param express
     * @return
     */
    public static Double caculate(String express){
        checkExpress(express);
        return operate(toSuffixExpress(express));
    }

    private static void checkExpress(String express){
        if(null == express || "".equals(express)){
            throw new RuntimeException("表达式不能为空！");
        }
        // 运算符
        char[] op = {'+','-','*','/','(',')'};
    }

    /**
     *中缀表达式转后缀表达式(逆波兰表达式)
     * @Author pujian
     * @Date 2020/5/19 11:25
     * @Param express 中缀表达式
     * @return
     */
    private static String toSuffixExpress(String express){
        // 存放结果的栈
        LinkedStack<Character> sExpress = new LinkedStack<>();
        // 存放运算符号的栈
        LinkedStack<Character> sSymbol = new LinkedStack<>();

        return null;
    }

    /**
     *计算结果
     * @Author pujian
     * @Date 2020/5/19 11:30
     * @Param suffixExpress
     * @return
     */
    private static Double operate(String suffixExpress){
        return null;
    }
}
