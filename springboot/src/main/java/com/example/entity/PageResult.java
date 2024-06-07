package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应实体
 *
 * @author br
 * @since 2024-05-31 17:26:54
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据集合
    */
    private List<T> content;

    /**
     * 总条数
    */
    private long totalElements;

}