package com.ezreal.autobi.domain.chart.model.req;

import com.ezreal.autobi.common.PageParams;
import lombok.Data;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */

@Data
public class ChartInfoReq {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分页参数
     */
    private PageParams pageParams;
}
