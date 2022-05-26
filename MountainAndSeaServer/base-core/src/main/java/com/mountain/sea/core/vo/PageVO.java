package com.mountain.sea.core.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 10:01
 */
public class PageVO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("起始页数")
    private int pageNumber;
    @ApiModelProperty("分页条数")
    private int pageSize;
    @ApiModelProperty("排序方式")
    private String orderBy = "";

    public PageVO() {
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
