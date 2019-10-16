package com.niuniu.controller.backend;

import com.niuniu.common.Const;
import com.niuniu.common.ServerResponse;
import com.niuniu.model.User;
import com.niuniu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Authror DFQ
 * @Date 2019-10-14 15:08
 */
@RestController
@RequestMapping("/admin/user")
public class AdminController {
    @Autowired
    private IUserService iUserService;

    @PostMapping("/login")
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员无权登录");
            }
        }
        return response;
    }

}
