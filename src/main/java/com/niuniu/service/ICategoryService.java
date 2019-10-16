package com.niuniu.service;

import com.niuniu.common.ServerResponse;
import com.niuniu.model.Category;

import java.util.List;

/**
 * @Authror DFQ
 * @Date 2019-10-14 15:45
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName,Integer parentId);

    ServerResponse updateCategoryName(String categoryNameNew, Integer id);
    ServerResponse<List<Category>> getChildrenCategory(Integer categoryId);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
