package cn.sibat.cas.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author: xiaojun
 * @version: 2018/11/22
 */
@RestController
public class HelloController {

    @RequestMapping("index")
    public String showIndex(HttpServletRequest request, HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        request.getSession().setAttribute("user", principal.getName());
        return "Hello" + principal.getName();
    }

}
