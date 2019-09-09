package com.cloud.microserviceuser.controller;

import com.cloud.microserviceuser.annotions.RequiredPermission;
import com.cloud.microserviceuser.controller.service.UserService;
import com.cloud.microserviceuser.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Value("${server.port}")
    private String port;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public Map<String, Object> login(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User res= userService.query(user);
        Map<String, Object > tmp = new HashMap<>();
        if(res != null){
            tmp.put("userId",res.getId());
            tmp.put("username",res.getUsername());
            tmp.put("user",res);
        }
        return tmp;
    }

    @GetMapping("/logout/{id}")
    public String logout(@PathVariable("id") int id,
                         HttpSession session){
        if(userService.findUserById(id) == null){
            return "redirect:/";
        }
        session.invalidate(); //失效
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public void register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password){

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setLevel(1);
        user.setPermission(1);
        // 数据校验,用户名要大于8位，密码在8-16位，包含数字和字母，邮箱格式要正确
//        Pattern p = Pattern.compile("");
//        Matcher m = p.matches();
        userService.addUser(user);
    }
    @RequiredPermission
    @GetMapping("/usercenter/{id}")
    public String usercenter(@PathVariable("id") int id){
        User user = userService.findUserById(id);
        if(user != null){
            return "usercenter";
        }
        return "redirect:/";
    }
    @PostMapping("/users/update/{id}")
    public String updateUserById(@PathVariable("id") int id,String username,String email){
        User user = userService.findUserById(id);
        if(user == null){
            return "redirect:/";
        }
        if(username != null){
            user.setUsername(username);
        }
        if(email != null){
            user.setEmail(email);
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/usercenter/"+id;
    }
    @PostMapping("/changepwd/{id}")
    public String changePassword(@PathVariable("id") int id, HttpServletRequest request, Model model){
        User user = userService.findUserById(id);
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");

        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userService.updateUser(user);
            return "redirect:/usercenter/"+id;
        }
        else{
            //返回错误
            model.addAttribute("change_password_error", "密码修改失败");
            return "redirect:/usercenter/"+id;
        }
    }
}
