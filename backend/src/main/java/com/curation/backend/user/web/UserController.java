package com.curation.backend.user.web;

import com.curation.backend.user.domain.Role;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

}
