package com.ezreal.autobi.mapper;

import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ezreal
* @description 针对表【chart(图表表)】的数据库操作Mapper
* @createDate 2023-06-17 12:08:59
* @Entity com.ezreal.autobi.model/entity.Chart
*/
@Mapper
public interface ChartMapper extends BaseMapper<Chart> {

}




