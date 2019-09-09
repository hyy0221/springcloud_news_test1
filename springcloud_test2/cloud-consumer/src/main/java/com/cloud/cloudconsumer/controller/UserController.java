package com.cloud.cloudconsumer.controller;

import com.cloud.cloudconsumer.clientInterface.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@SessionAttributes({"userId","username","user"})
@Controller
public class UserController {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model
    ){
        Map<String, Object> res = userFeignClient.login(email, password);
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "redirect:/";
    }
    @GetMapping("/logout/{id}")
    public String logout(@PathVariable("id") int id,
                         HttpSession session,
                         Model model){
        session.invalidate(); //失效
        model.addAttribute("userId",null);
        model.addAttribute("username",null);
        model.addAttribute("user",null);
        return "redirect:/";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password){
        userFeignClient.register(username,email,password);
        return "redirect:/";
    }
    @GetMapping("/usercenter/{id}")
    public String usercenter(@PathVariable("id") int id){
        String res = userFeignClient.usercenter(id);
        return res;
    }
    @PostMapping("/users/update/{id}")
    public String updateUserById(@PathVariable("id") int id,String username,String email){
        String res = userFeignClient.updateUserById(id,username,email);
        return res;
    }
}

