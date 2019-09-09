package com.cloud.cloudconsumer.clientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//@FeignClient(name = "microservice-user", fallbackFactory = FeignClientFallbackFactory.class)
@FeignClient(name = "microservice-user")
public interface UserFeignClient  {
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam("email") String email, @RequestParam("password") String password);
    @PostMapping("/register")
    public void register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password);
    @GetMapping("/usercenter/{id}")
    public String usercenter(@PathVariable("id") int id);
    @PostMapping("/users/update/{id}")
    public String updateUserById(@PathVariable("id") int id,@RequestParam("username") String username,@RequestParam("email") String email);
}

/**
 * UserFeignClient的fallbackFactory类，该类需实现FallbackFactory接口，并覆写create方法
 * 打开Hystrix熔断功能时才能生效
 */
//@Component
//class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);
//
//    @Override
//    public UserFeignClient create(Throwable cause) {
//        return new UserFeignClient() {
//            @Override
//            public User allUser() {
//                FeignClientFallbackFactory.LOGGER.info("fallback; reason was:", cause);
//                User user = new User();
//                user.setId(1);
//                user.setUsername("默认用户");
//                return user;
//            }
//        };
//    }
//}