package com.ezreal.autobi.mapper;

import com.ezreal.autobi.common.PageParams;
import com.ezreal.autobi.domain.chart.model.resp.ChartInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@SpringBootTest
@Slf4j
public class ChartMapperTest {

    @Resource
    private ChartMapper chartMapper;

    @Test
    public void testList() {
        Long userId = 1L;

        PageParams pageParams = new PageParams();
        pageParams.setPageNum(0);
        pageParams.setPageSize(10);
        pageParams.setKeyword("用户");

        List<ChartInfoResp> chartInfoResps = chartMapper.selectChartInfoList(userId, pageParams);

        log.info("图表信息列表为：{}", chartInfoResps);
    }

    @Test
    public void createTable() {
//        boolean genChartTable = chartMapper.createGenChartTable("gen_table_" + 1);
//
//        log.info("genChartTable: {}" , genChartTable);
    }
}