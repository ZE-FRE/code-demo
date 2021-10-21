package cn.zefre.spring.common;

/**
 * http响应返回对象
 *
 * @author pujian
 * @date 2021/10/20 10:29
 */
public class Response {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;

    private Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getDescription();
    }

    /**
     * 响应成功
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response ok() {
        return respond(ResponseCode.OK);
    }

    /**
     * 响应成功，自定义message
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response ok(String message) {
        Response response = ok();
        response.setMessage(message);
        return response;
    }

    /**
     * 响应成功，自定义data
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response ok(Object data) {
        Response response = ok();
        response.setData(data);
        return response;
    }

    /**
     * 响应成功，自定义message以及data
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response ok(String message, Object data) {
        Response response = ok(message);
        response.setData(data);
        return response;
    }

    /**
     * 响应错误
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response error() {
        return respond(ResponseCode.ERROR);
    }

    /**
     * 响应错误，自定义message
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response error(String message) {
        Response response = error();
        response.setMessage(message);
        return response;
    }

    /**
     * 响应未认证
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response unauthorized() {
        return respond(ResponseCode.UNAUTHORIZED);
    }

    /**
     * 响应参数验证错误
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response validationFailed(String message) {
        return respond(ResponseCode.VALIDATION_EXCEPTION, message);
    }

    /**
     * 生成响应对象
     *
     * @author pujian
     * @date 2021/10/20 11:00
     */
    public static Response respond(ResponseCode responseCode) {
        return new Response(responseCode);
    }

    public static Response respond(ResponseCode responseCode, String message) {
        Response response = new Response(responseCode);
        response.setMessage(message);
        return response;
    }


    // getters and setters

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }

}
