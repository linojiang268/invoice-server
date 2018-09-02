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
}
