package cn.zefre.spring.mybatisplus.typehandler;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 处理String类型枚举的TypeHandler
 *
 * mybatis默认提供的用于处理枚举类型的TypeHandler有两个，分别是：
 * 1、默认枚举类型处理器{@link org.apache.ibatis.type.EnumTypeHandler}，如果一个枚举类型未指定TypeHandler，则默认为它，它映射的是枚举的{@link Enum#name()}
 * 2、映射枚举下标索引的{@link org.apache.ibatis.type.EnumOrdinalTypeHandler}，即映射的是{@link Enum#ordinal()}
 *
 * 但是，有时候有这样的需求：实体中枚举类型的字段在数据库却希望存类似1、2、3、4等等这种数据，所以需要自定义TypeHandler来实现
 * 此时，如果一个枚举实现了{@link GenericEnumType}接口，则它的TypeHandler就是{@link StringEnumTypeHandler}，
 * 而不再是默认的{@link org.apache.ibatis.type.EnumTypeHandler}
 *
 * @author pujian
 * @param <E> 枚举类型
 * @date 2022/7/5 14:09
 */
@Component
@MappedTypes(GenericEnumType.class)
public class StringEnumTypeHandler<E extends Enum<E>, P> extends BaseTypeHandler<E> {

    private static final ObjectTypeHandler OBJECT_TYPE_HANDLER = new ObjectTypeHandler();

    private TypeHandlerRegistry typeHandlerRegistry;

    /**
     * 枚举的Class，{@link GenericEnumType}及其子类
     */
    private Class<E> enumType;

    private Class<P> actualParamType;

    @Autowired
    public StringEnumTypeHandler(SqlSessionFactory SqlSessionFactory) {
        TypeHandlerRegistry typeHandlerRegistry = SqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.register(this);
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    /**
     * 构造方法，由mybatis在扫描注册TypeHandler时反射调用
     *
     * @param enumType javaType
     * @author pujian
     * @date 2022/7/5 14:42
     */
    public StringEnumTypeHandler(Class<E> enumType) {
        if (!GenericEnumType.class.isAssignableFrom(enumType)) {
            throw new IllegalArgumentException("映射String类型的枚举请实现" + GenericEnumType.class.getName() + "接口");
        }
        this.enumType = enumType;
        actualParamType = getGenericType();
    }

    private Class<P> getGenericType() {
        Type[] genericInterfaces = enumType.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type paramType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            }
        }
        Type genericInterface = genericInterfaces[0];
        Type paramType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        return (Class<P>) paramType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E e, JdbcType jdbcType) throws SQLException {
        GenericEnumType<P> genericEnumType = (GenericEnumType<P>) e;
        P paramValue = genericEnumType.getValue();
        TypeHandler<P> typeHandler = resolveTypeHandler(actualParamType, jdbcType);
        typeHandler.setParameter(ps, i, paramValue, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return toEnum(value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return toEnum(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return toEnum(value);
    }

    private TypeHandler<P> resolveTypeHandler(Class<P> paramClass, JdbcType jdbcType) {
        TypeHandler<P> handler = typeHandlerRegistry.getTypeHandler(paramClass, jdbcType);
//        if (handler == null || handler instanceof UnknownTypeHandler) {
//            handler = OBJECT_TYPE_HANDLER;
//        }
        return handler;
    }

    /**
     * 得到枚举值
     *
     * @param value 枚举内部字段的值
     * @author pujian
     * @date 2022/7/5 16:28
     * @return E
     */
    private E toEnum(String value) {
        if (value == null) {
            return null;
        }
        // 取得该枚举类中定义的所有实例
        E[] enums = enumType.getEnumConstants();
        for (E enumeration : enums) {
            GenericEnumType genericEnumType = (GenericEnumType) enumeration;
            if (value.equals(genericEnumType.getValue())) {
                return enumeration;
            }
        }
        return null;
    }

}
