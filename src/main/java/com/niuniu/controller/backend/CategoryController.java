package com.niuniu.controller.backend;

import com.niuniu.common.Const;
import com.niuniu.common.ResponseCode;
import com.niuniu.common.ServerResponse;
import com.niuniu.model.User;
import com.niuniu.service.ICategoryService;
import com.niuniu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Authror DFQ
 * @Date 2019-10-14 15:27
 */
@RestController
@RequestMapping("/back/category")
public class CategoryController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @PostMapping("/add_category")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parenId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

    @PostMapping("/update_categoryName")
    public ServerResponse updateCategoryName(HttpSession session, int id, String categoryNameNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryNameNew, id);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

    @GetMapping("/get_children_category")
    public ServerResponse getChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.getChildrenCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

    @GetMapping("/get__deep_children_category")
    public ServerResponse getDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED__LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            // return iCategoryService.getChildrenCategory(categoryId);
            return  iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无权操作");
        }
    }

}
