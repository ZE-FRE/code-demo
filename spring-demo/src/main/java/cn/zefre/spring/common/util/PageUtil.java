package cn.zefre.spring.common.util;

import cn.zefre.spring.common.dto.BaseDto;
import cn.zefre.spring.common.dto.PageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author pujian
 * @date 2022/6/29 17:41
 */
public class PageUtil {

    public static <T> Page<T> getPage(BaseDto dto) {
        int pageNo = null == dto.getPageNo() ? 1 : dto.getPageNo();
        int pageSize = null == dto.getPageSize() ? 10 : dto.getPageSize();
        return new Page<>(pageNo, pageSize);
    }

    public static PageDto toPageDto(IPage<?> page) {
        PageDto dto = new PageDto();
        dto.setPageNo((int) page.getCurrent());
        dto.setPageSize((int) page.getSize());
        dto.setRows(page.getRecords());
        dto.setTotal((int) page.getTotal());
        return dto;
    }

}
