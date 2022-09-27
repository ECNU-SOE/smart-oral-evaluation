package net.ecnu.controller;

//import org.example.util.JsonData;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @GetMapping("test")
    public JsonData test() {
        return JsonData.buildError("hello world");
    }
}
