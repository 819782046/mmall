package com.niuniu.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.niuniu.common.ServerResponse;
import com.niuniu.dao.CategoryMapper;
import com.niuniu.model.Category;
import com.niuniu.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Authror DFQ
 * @Date 2019-10-14 15:46
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);// 默认可用
        int resultCount = categoryMapper.checkCategoryName(categoryName);
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("名字已存在");
        } else {
            int rowCount = categoryMapper.insert(category);
            category.setId(category.getId());//注入ID
            if (rowCount > 0) {
                return ServerResponse.createBySuccess("创建成功", category);
            }
        }
        return ServerResponse.createByErrorMessage("创建失败");
    }

    @Override
    public ServerResponse updateCategoryName(String categoryNameNew, Integer id) {
//        if (id == null || StringUtils.isBlank(categoryNameNew)) {
//            return ServerResponse.createByErrorMessage("传入参数错误");
//        }
        Category category =categoryMapper.selectByPrimaryKey(id);
        if (category==null){
            return ServerResponse.createByErrorMessage("目标不存在");
        }
        category.setName(categoryNameNew);
        Date date=new Date();
        category.setUpdateTime(date);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新成功", category);
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }
// @Override
//    public ServerResponse updateCategoryName(String categoryNameNew, Integer id) {
//        if (id == null || StringUtils.isBlank(categoryNameNew)) {
//            return ServerResponse.createByErrorMessage("传入参数错误");
//        }
//        Category category = new Category();
//        category.setId(id);
//        category.setName(categoryNameNew);
//        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
//        if (rowCount > 0) {
//            return ServerResponse.createBySuccess("更新成功", category);
//        }
//        return ServerResponse.createByErrorMessage("更新失败");
//    }

    @Override
    public ServerResponse<List<Category>> getChildrenCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的id及孩子节点的ID
     * @param categoryId
     * @return
     */

    @Override
    public ServerResponse selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList= Lists.newArrayList();
        if (categoryId!=null){
            for (Category categoryItem:categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return  ServerResponse.createBySuccess(categoryIdList);
    }


    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
