package com.ezreal.autobi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezreal.autobi.mapper.ChartMapper;
import com.ezreal.autobi.model.entity.Chart;
import com.ezreal.autobi.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author Ezreal
* @description 针对表【chart(图表表)】的数据库操作Service实现
* @createDate 2023-06-17 12:08:59
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




