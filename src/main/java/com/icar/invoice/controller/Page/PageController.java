package com.icar.invoice.controller.Page;

import com.icar.invoice.request.adapter.WebMvcConf;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/user/login")
    public String userLogin() {
        return  "/user/login";
    }

    @GetMapping("/user/logout")
    public String userLogout(HttpSession httpSession) {
        httpSession.removeAttribute(WebMvcConf.SESSION_KEY);
        return "redirect:/login";
    }

    @GetMapping("/home/console")
    public String homeConsole() {
        return  "/home/console";
    }

    @GetMapping("/user/administrators/list")
    public String userAdministratorsList() {
        return "/user/administrators/list";
    }

    @GetMapping("user/administrators/adminform")
    public String userAdministratorsAdminForm() {
        return "/user/administrators/adminform";
    }
}
