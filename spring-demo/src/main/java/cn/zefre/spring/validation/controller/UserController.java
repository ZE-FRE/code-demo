package cn.zefre.spring.validation.controller;

import cn.zefre.spring.common.Response;
import cn.zefre.spring.validation.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author pujian
 * @date 2021/10/19 15:37
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @GetMapping(value = "/{id}")
    public Response getById(@PathVariable String id) {
        UserDto user = new UserDto(id, "张三", 12, null, null);
        return Response.ok(user);
    }

    @PostMapping(value = "updateNameById")
    public Response updateNameById(@NotBlank(message = "id不能为空") String id,
                                   @NotBlank(message = "用户名不能为空") String name) {
        log.info("id:{},name:{}", id, name);
        return Response.ok();
    }

    @PostMapping(value = "updateByForm")
    public Response updateByForm(UserDto userDto) {
        log.info("userDto:{}", userDto);
        return Response.ok();
    }

    @PostMapping(value = "updateByBody", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateByBody(@Valid @RequestBody UserDto userDto) {
        log.info("userDto:{}", userDto);
        return Response.ok();
    }

}
