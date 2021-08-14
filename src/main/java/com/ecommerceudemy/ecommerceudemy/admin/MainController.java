package com.ecommerceudemy.ecommerceudemy.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class MainController {

    @GetMapping("/adminpage")
    public String PageAdmin(){
        return "admin/mainadminpage";
    }

    @GetMapping("/testPage")
    public String pageTest(){
        return "admin/test";
    }

}
