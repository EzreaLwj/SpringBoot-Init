package com.ezreal.autobi.common;

import lombok.Data;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Data
public class PageParams {

    private Integer pageNum;

    private Integer pageSize;

    private String keyword;

}
