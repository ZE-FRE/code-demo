package cn.zefre.spring.mybatisplus.crud.service;

import cn.zefre.spring.common.dto.PageDto;
import cn.zefre.spring.mybatisplus.crud.dto.SelectDto;

import java.util.List;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 15:02
 */
public interface CrudService {

    /**
     * 插入操作
     *
     * @param sql insert语句
     * @param values 待插入数据
     * @author pujian
     * @date 2022/6/24 15:05
     * @return int 成功插入数
     */
    int insert(String sql, List<? extends Map<String, Object>> values);

    /**
     * 修改操作
     *
     * @param sql update语句
     * @param setMap set语句参数值
     * @param whereMap where语句参数值
     * @author pujian
     * @date 2022/6/24 15:05
     * @return int 成功修改数
     */
    int update(String sql, Map<String, Object> setMap, Map<String, Object> whereMap);

    /**
     * 删除操作
     *
     * @param sql delete语句
     * @param whereMap where语句参数值
     * @author pujian
     * @date 2022/6/24 15:05
     * @return int 成功删除数
     */
    int delete(String sql, Map<String, Object> whereMap);

    /**
     * 单条查询
     *
     * @param sql 单条查询sql
     * @param whereMap where语句参数值
     * @author pujian
     * @date 2022/6/24 15:15
     * @return Map<String, Object>
     */
    Map<String, Object> selectOne(String sql, Map<String, Object> whereMap);

    /**
     * 列表查询
     *
     * @param sql 列表查询sql
     * @param whereMap where语句参数值
     * @author pujian
     * @date 2022/6/24 15:15
     * @return java.util.List
     */
    List<Map<String, Object>> selectList(String sql, Map<String, Object> whereMap);

    /**
     * 分页查询
     *
     * @param sql 分页查询sql
     * @param dto dto
     * @param whereMap where语句参数值
     * @author pujian
     * @date 2022/6/24 15:15
     * @return Page
     */
    PageDto selectPage(String sql, SelectDto dto, Map<String, Object> whereMap);

}
