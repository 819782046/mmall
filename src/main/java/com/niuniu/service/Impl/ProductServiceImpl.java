package com.niuniu.service.Impl;

import com.niuniu.common.ResponseCode;
import com.niuniu.common.ServerResponse;
import com.niuniu.dao.CategoryMapper;
import com.niuniu.dao.ProductMapper;
import com.niuniu.model.Category;
import com.niuniu.model.Product;
import com.niuniu.service.IProductService;
import com.niuniu.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Authror DFQ
 * @Date 2019-10-15 14:40
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 保存Product
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveProduct(Product product) {
        if (product!=null){
            if (StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray=product.getSubImages().split(",");
                if (subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId()==null){
               int rowCount= productMapper.updateByPrimaryKey(product);
               if (rowCount>0){
                   return ServerResponse.createBySuccess("更新产品成功");
               }
               return ServerResponse.createByErrorMessage("更新产品失败");
            }else {
               int rowCount= productMapper.insert(product);
               if (rowCount>0){
                   return ServerResponse.createBySuccess("添加产品成功");
               }
               return ServerResponse.createByErrorMessage("添加失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse<Product> updateStatus(Integer productId, Integer status) {
        if (productId==null|| status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product =productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createByErrorMessage("要上架的商品不存在");
        }
        product.setStatus(status);
        int rowCount=productMapper.updateByPrimaryKeySelective(product);
        if (rowCount>0){
            return ServerResponse.createBySuccess(product);
        }
        return ServerResponse.createBySuccessMessage("更新失败");

    }

    @Override
    public ServerResponse<ProductDetailVo> getDetail(Integer productId) {
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createByErrorMessage("目标不存在");
        }
        ProductDetailVo productDetailVo=new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setCreateTime(product.getCreateTime());
        productDetailVo.setUpdateTime(product.getUpdateTime());
        productDetailVo.setDetail(product.getDetail());
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category==null){
            productDetailVo.setParentCategoryId(0);
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        return ServerResponse.createBySuccess(productDetailVo);
    }
//    private ProductDetailVo assembleProductDetailVo(Product product){
//        ProductDetailVo productDetailVo=new ProductDetailVo();
//        productDetailVo.setId(product.getId());
//        productDetailVo.setSubtitle(product.getSubtitle());
//        productDetailVo.setPrice(product.getPrice());
//        productDetailVo.setName(product.getName());
//        productDetailVo.setCreateTime(product.getCreateTime());
//        productDetailVo.setUpdateTime(product.getUpdateTime());
//        productDetailVo.setDetail(product.getDetail());
//        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
//        if (category==null){
//            productDetailVo.setParentCategoryId(0);
//        }else {
//            productDetailVo.setParentCategoryId(category.getParentId());
//            return productDetailVo;
//        }
//        return productDetailVo;
//    }
}
