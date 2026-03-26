package com.zzh.coursehub.controller;


import com.zzh.coursehub.annotation.RequireTeacher;
import com.zzh.coursehub.entity.dto.ChangePwdDTO;
import com.zzh.coursehub.entity.dto.NewUserDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.UserLoginDTO;
import com.zzh.coursehub.entity.vo.Result;
import com.zzh.coursehub.service.IUserService;
import com.zzh.coursehub.utils.UserHolder;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    //登录
    @PostMapping("/auth/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session){
        return userService.login(userLoginDTO,session);
    }

    //登出
    @PostMapping("/auth/logout")
    public Result logout(HttpSession session){
        session.invalidate();
        return Result.success("再见");
    }

    //查看个人信息
    @GetMapping("/users/profile")
    private Result getById(){
        Long userId = UserHolder.getUser();
        return userService.getById(userId);
    }

    //教师获取用户列表
    @GetMapping("/users")
    @RequireTeacher
    public Result getAll(@RequestBody PageDTO pageDTO){
        return userService.getAll(pageDTO);
    }

    //教师创建用户
    @PostMapping("/users")
    @RequireTeacher
    public Result addUser(@Valid @RequestBody NewUserDTO newUserDTO){
        return userService.addUser(newUserDTO);
    }

    //修改密码
    @PostMapping("/auth/pwd")
    public Result changePwd(@Valid @RequestBody ChangePwdDTO changePwdDTO){
        return userService.changePwd(changePwdDTO.getOldPwd(),changePwdDTO.getNewPwd());
    }
}
