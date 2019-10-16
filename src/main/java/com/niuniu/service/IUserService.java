package com.niuniu.service;

import com.niuniu.common.ServerResponse;
import com.niuniu.model.User;

/**
 * @Authror DFQ
 * @Date 2019-10-09 14:13
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse<String> selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username,String question ,String  answer);
    ServerResponse<String>forgetResetPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
    ServerResponse<User> updateInfo(User user);
    ServerResponse<User> getInformation(Integer id);
    ServerResponse checkAdmin(User user);
}
