package com.niuniu.service;

import com.niuniu.common.ServerResponse;
import com.niuniu.model.Product;
import com.niuniu.vo.ProductDetailVo;

/**
 * @Authror DFQ
 * @Date 2019-10-15 14:39
 */
public interface IProductService {
    ServerResponse saveProduct(Product product);

    ServerResponse<Product> updateStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> getDetail(Integer productId);
}
