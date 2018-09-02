package com.icar.invoice.controller.user;

import com.icar.invoice.controller.Controller;
import com.icar.invoice.po.user.User;
import com.icar.invoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController extends Controller {
    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public Object listUsers() {
        List<User> rstUsers = this.userService.listAll();
        return this.json(rstUsers.size(), 1, rstUsers);
    }

    // Todo:
    // 选月份上传发票  pdf识别
    // 选日期 选人 发票号码 获取发票列表（总数、总金额）
}
