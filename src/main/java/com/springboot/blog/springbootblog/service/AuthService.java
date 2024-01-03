package com.springboot.blog.springbootblog.service;

import com.springboot.blog.springbootblog.payload.LoginDto;
import com.springboot.blog.springbootblog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
