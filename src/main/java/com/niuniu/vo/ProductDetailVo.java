package com.niuniu.vo;

import com.niuniu.model.Product;

/**
 * @Authror DFQ
 * @Date 2019-10-16 15:37
 */
public class ProductDetailVo extends Product {
    private Integer parentCategoryId;

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}