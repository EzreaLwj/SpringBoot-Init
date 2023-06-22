package com.ezreal.autobi.mapper;

import com.ezreal.autobi.common.PageParams;
import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezreal.autobi.domain.chart.model.resp.ChartInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ezreal
 * @description 针对表【chart(图表表)】的数据库操作Mapper
 * @createDate 2023-06-17 12:08:59
 * @Entity com.ezreal.autobi.model.entity.Chart
 */
@Mapper
public interface ChartMapper extends BaseMapper<Chart> {

    /**
     * 查找列表资料
     * @param userId
     * @param pageParams
     * @return
     */
    List<ChartInfoResp> selectChartInfoList(@Param("userId") Long userId, @Param("pageParams") PageParams pageParams);

    boolean createGenChartTable(@Param("tableName") String tableName, @Param("columnName") List<String> columnName);

    int insertGenChartValue(@Param("tableName") String tableName, @Param("genChartValue") List<List<String>> genChartValue);
}




