package info.chixin.simplestblog.controllers.admin;

import info.chixin.simplestblog.models.User;
import info.chixin.simplestblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/users")
@Validated
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Map<String, Object> index(@RequestParam @Min(0) Integer page,
                                     @RequestParam @Max(50) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "success");
        response.put("number", users.getNumber());
        response.put("totalPages", users.getTotalPages());
        response.put("users", users.getContent());
        return response;
    }
}
