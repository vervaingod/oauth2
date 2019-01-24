package com.example.controller;

import com.example.bean.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:
 * Function:
 * date: 2019年01月14日
 *
 * @author 许嘉阳
 */
@Controller
public class UserController {
    @RequestMapping("/api/profile")
    public ResponseEntity<UserProfile> profile() {
        UserProfile profile = new UserProfile();
        profile.setName("xjy");
        profile.setEmail("742749059@qq.com");
        return ResponseEntity.ok(profile);
    }
}
