package cn.zefre.spring.validation.service.impl;

import cn.zefre.spring.validation.dto.UserDto;
import cn.zefre.spring.validation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pujian
 * @date 2021/10/19 17:06
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String updateNameById(String id, String newName) {
        log.info("id:{},newName:{}", id, newName);
        return "张三";
    }

    @Override
    public UserDto getById(String id) {
        return new UserDto();
    }

    @Override
    public void insert(UserDto userDto) {
        log.info("userDto:{}", userDto);
    }
}
