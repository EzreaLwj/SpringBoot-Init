package com.ezreal.autobi.domain.chart.model.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Data
public class ChartInfoResp {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 状态
     */
    private String status;

    /**
     * 图表类型
     */
    private String charType;

    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成图表结果
     */
    private String genResult;

}
