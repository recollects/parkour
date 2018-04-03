package com.alipay.parkour.common.dal;

import java.io.Serializable;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/4 17:04
 */
public class BaseQuery implements Serializable{

    private Integer currentPage=1;
    private Integer pageSize=20;

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
