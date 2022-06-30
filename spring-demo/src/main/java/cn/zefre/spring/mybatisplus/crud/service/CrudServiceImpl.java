package cn.zefre.spring.mybatisplus.crud.service;

import cn.zefre.spring.common.util.PageUtil;
import cn.zefre.spring.common.dto.PageDto;
import cn.zefre.spring.mybatisplus.crud.dto.SelectDto;
import cn.zefre.spring.mybatisplus.crud.mapper.GenericMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 15:04
 */
@Service
public class CrudServiceImpl implements CrudService {

    @Resource
    private GenericMapper genericMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(String sql, List<? extends Map<String, Object>> values) {
        return genericMapper.insert(sql, values);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(String sql, Map<String, Object> setMap, Map<String, Object> whereMap) {
        return genericMapper.update(sql, setMap, whereMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(String sql, Map<String, Object> whereMap) {
        return genericMapper.delete(sql, whereMap);
    }

    @Override
    public Map<String, Object> selectOne(String sql, Map<String, Object> whereMap) {
        return genericMapper.selectOne(sql, whereMap);
    }

    @Override
    public List<Map<String, Object>> selectList(String sql, Map<String, Object> whereMap) {
        return genericMapper.selectList(sql, whereMap);
    }

    @Override
    public PageDto selectPage(String sql, SelectDto dto, Map<String, Object> whereMap) {
        IPage<Map<String, Object>> page = PageUtil.getPage(dto);
        page = genericMapper.selectPage(sql, page, whereMap);
        return PageUtil.toPageDto(page);
    }

}
