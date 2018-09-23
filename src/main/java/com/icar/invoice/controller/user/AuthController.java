package com.icar.invoice.controller.user;

import com.icar.invoice.controller.Controller;
import com.icar.invoice.po.user.User;
import com.icar.invoice.request.adapter.WebMvcConf;
import com.icar.invoice.response.entity.ResponseEntity;
import com.icar.invoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class AuthController extends Controller {
    @Autowired
    private UserService userService;

    @RequestMapping("/data")
    public ResponseEntity data() {
        return this.json(new Object() {
            public String id = UUID.randomUUID().toString();
        });
    }

    @PostMapping("/api/user/login")
    public Object login(HttpSession httpSession, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User rstUser = userService.login(user);
        if (null == rstUser) {
            return this.jsonException(40004, "用户名或密码错误");
        }

        httpSession.setAttribute(WebMvcConf.SESSION_KEY, rstUser.getId());
        return this.json();
    }

    @PostMapping("/api/user/logout")
    public Object userLogout(HttpSession httpSession) {
        httpSession.removeAttribute(WebMvcConf.SESSION_KEY);
        return this.json();
    }

    @PostMapping("/api/user/password/change")
    public Object changePassword(HttpSession httpSession, String oldPassword, String password, String repassword) {
        User user = userService.getUser((int) httpSession.getAttribute(WebMvcConf.SESSION_KEY));

        if (!user.getPassword().equals(oldPassword)) {
            return this.jsonException(10000, "旧密码错误");
        }

        if (!password.equals(repassword)) {
            return this.jsonException(10000, "两次密码输入不一致");
        }

        userService.changePassword(user, password);

        return this.json();
    }
}
