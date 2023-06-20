package com.ezreal.autobi.domain.chart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Ezreal
* @description 针对表【chart(图表表)】的数据库操作Service
* @createDate 2023-06-17 12:08:59
*/
public interface ChartService extends IService<Chart> {

    BaseResponse<ChartResp> getChartData(MultipartFile multipartFile, ChartReq chartReq);
}
