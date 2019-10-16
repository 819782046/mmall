package com.niuniu.controller.backend;

import com.niuniu.common.Const;
import com.niuniu.common.ResponseCode;
import com.niuniu.common.ServerResponse;
import com.niuniu.model.Product;
import com.niuniu.model.User;
import com.niuniu.service.IProductService;
import com.niuniu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Authror DFQ
 * @Date 2019-10-15 14:27
 */
@RestController
@RequestMapping("/back/product")
public class ProductController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    @PostMapping("/save_product")
    public ServerResponse saveProduct(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登入");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.saveProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

    @PostMapping("/update_status")
    public ServerResponse updateStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登入");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.updateStatus(productId,status);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

    @PostMapping("/detail")
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登入");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return  iProductService.getDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }
}
