package com.example.notification.feign;

import com.example.notification.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserResponse getUser(@PathVariable String userId); // Fetch user data (sync)
}
