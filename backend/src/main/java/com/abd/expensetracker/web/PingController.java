package com.abd.expensetracker.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class PingController {
 
    @GetMapping({"/", "/ping", "/api/ping"})
    public Map<String,String> ping() {
        return Map.of("status","ok");
    }
}




