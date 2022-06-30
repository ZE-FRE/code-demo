package cn.zefre.spring.mybatisplus.crud.controller;

import cn.zefre.base.web.annotation.WrapResponse;
import cn.zefre.spring.common.constant.HttpMethodConstant;
import cn.zefre.spring.common.dto.PageDto;
import cn.zefre.spring.mybatisplus.crud.ExpressionUtil;
import cn.zefre.spring.mybatisplus.crud.dto.*;
import cn.zefre.spring.mybatisplus.crud.service.CrudService;
import cn.zefre.spring.mybatisplus.crud.sql.DeleteSqlBuilder;
import cn.zefre.spring.mybatisplus.crud.sql.InsertSqlBuilder;
import cn.zefre.spring.mybatisplus.crud.sql.SelectSqlBuilder;
import cn.zefre.spring.mybatisplus.crud.sql.UpdateSqlBuilder;
import cn.zefre.spring.mybatisplus.crud.where.Where;
import cn.zefre.spring.mybatisplus.crud.where.WhereBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用crud操作接口
 *
 * @author pujian
 * @date 2022/6/21 11:14
 */
@WrapResponse
@Validated
@RestController
@RequestMapping("/crud")
@Api(value = "CrudRest",tags = "通用crud操作接口")
public class CrudRest {

    @Resource
    private CrudService crudService;
    

    /**
     * 新增
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:39
     * @return int
     */
    @PostMapping(value = "insert")
    @ApiOperation(value = "新增", httpMethod = HttpMethodConstant.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int insert(@Valid @RequestBody InsertDto dto) {
        String tableName = dto.getTableName();
        List<Map<String, Object>> values = dto.getValues();
        // fillPrimaryKey(tableName, values);
        List<String> fields = new ArrayList<>(values.get(0).keySet());
        String sql = new InsertSqlBuilder(tableName, fields, values.size()).build();
        return crudService.insert(sql, values);
    }

    /**
     * 修改
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:39
     * @return int
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "修改", httpMethod = HttpMethodConstant.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int update(@Valid @RequestBody UpdateDto dto) {
        String tableName = dto.getTableName();
        Map<String, Object> setMap = dto.getSetMap();
        List<String> fields = new ArrayList<>(setMap.keySet());
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new UpdateSqlBuilder(tableName, fields, where).build();
        return crudService.update(sql, setMap, ExpressionUtil.getWhereMap(where));
    }

    /**
     * 删除
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:39
     * @return int
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除", httpMethod = HttpMethodConstant.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int delete(@Valid @RequestBody DeleteDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new DeleteSqlBuilder(tableName, where).build();
        return crudService.delete(sql, ExpressionUtil.getWhereMap(where));
    }

    /**
     * 详情，查询单条
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:40
     * @return Map<String, Object>
     */
    @PostMapping(value = "detail")
    @ApiOperation(value = "详情，查询单条", httpMethod = HttpMethodConstant.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> detail(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectOne(sql, whereMap);
    }

    /**
     * 查询列表
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:40
     * @return List<Map<String, Object>>
     */
    @PostMapping(value = "list")
    @ApiOperation(value = "查询列表", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> list(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectList(sql, whereMap);
    }

    /**
     * 分页查询
     *
     * @param dto dto
     * @author pujian
     * @date 2022/6/24 14:40
     * @return PageResDto
     */
    @PostMapping(value = "listPage")
    @ApiOperation(value = "分页查询", httpMethod = HttpMethodConstant.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageDto listPage(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectPage(sql, dto, whereMap);
    }

}
