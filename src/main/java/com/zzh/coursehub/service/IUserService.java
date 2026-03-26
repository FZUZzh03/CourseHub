package com.zzh.coursehub.service;

import com.zzh.coursehub.entity.dto.NewUserDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.UserLoginDTO;
import com.zzh.coursehub.entity.vo.PageResult;
import com.zzh.coursehub.entity.vo.Result;
import com.zzh.coursehub.entity.vo.UserInfoVO;
import jakarta.servlet.http.HttpSession;


public interface IUserService {
    public Result login(UserLoginDTO userLoginDTO, HttpSession session);

    public Result getById(Long id);

    public Result getAll(PageDTO pageDTO);

    public Result addUser(NewUserDTO newUserDTO);

    public Result changePwd(String oldPwd, String newPwd);
}
