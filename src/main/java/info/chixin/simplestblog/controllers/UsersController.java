package info.chixin.simplestblog.controllers;

import info.chixin.simplestblog.models.User;
import info.chixin.simplestblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/users")
@Validated
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public HashMap<String, Object> register(
            @RequestParam String name,
            @RequestParam @Email String email,
            @RequestParam @Size(min = 8, max = 64) String password) {
        HashMap<String, Object> response = new LinkedHashMap<>();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            response.put("code", 1001);
            response.put("message", "email has already been taken");
            return response;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        String passwordDigest = DigestUtils.md5DigestAsHex(password.getBytes());
        newUser.setPasswordDigest(passwordDigest);
        userRepository.save(newUser);

        response.put("code", 200);
        response.put("message", "success");
        response.put("user", newUser);
        return response;
    }
}
