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

    @GetMapping("/user/administrators/list")
    public String userAdministratorsList() {
        return "/user/administrators/list";
    }

    @GetMapping("user/administrators/adminform")
    public String userAdministratorsAdminForm() {
        return "/user/administrators/adminform";
    }

    @GetMapping("set/user/password")
    public String setUserPassword() {
        return "/set/user/password";
    }

    @GetMapping("/invoice/list")
    public String invoiceList() {
        return "/invoice/list";
    }

    @GetMapping("/invoice/manager_list")
    public String invoiceManagerList() {
        return "/invoice/manager_list";
    }
}
