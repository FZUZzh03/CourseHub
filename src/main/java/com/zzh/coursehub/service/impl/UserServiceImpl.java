package com.zzh.coursehub.service.impl;

import com.zzh.coursehub.entity.dto.NewUserDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.UserLoginDTO;
import com.zzh.coursehub.entity.pojo.User;
import com.zzh.coursehub.entity.vo.*;
import com.zzh.coursehub.mapper.UserMapper;
import com.zzh.coursehub.service.IUserService;
import com.zzh.coursehub.utils.PwdUtil;
import com.zzh.coursehub.utils.UserHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //创建用户
    @Transactional
    @Override
    public Result addUser(NewUserDTO newUserDTO) {
        //查询用户是否存在
        User user = userMapper.getByUsername(newUserDTO.getUsername());
        if(user!=null){
            return Result.fail(400,"该用户已存在");
        }
        user = new User();
        BeanUtils.copyProperties(newUserDTO,user);
        //设置默认密码
        String defaultPassword = PwdUtil.encode("123456");
        user.setPassword(defaultPassword);
        userMapper.add(user);
        return Result.success("创建成功");
    }

    @Override
    public Result changePwd(String oldPwd, String newPwd) {
        Long userId = UserHolder.getUser();
        String pwd = userMapper.getPwd(userId);
        if(!pwd.equals(PwdUtil.encode(oldPwd))){
            return Result.fail(400,"原始密码错误");
        }
        if(pwd.equals(PwdUtil.encode(newPwd))){
            return Result.fail(400,"新密码不可和原始密码相同");
        }
        userMapper.updatePwd(PwdUtil.encode(newPwd),userId);
        return Result.success("修改成功");
    }

    //登录
    @Override
    public Result login(UserLoginDTO userLoginDTO, HttpSession session){
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        UserLoginVO user = userMapper.login(username);
        if(user == null){
            return Result.fail(403,"非本校人员");
        }
        if(!user.getPassword().equals(PwdUtil.encode(password))){
            return Result.fail(400,"密码错误");
        }
        session.setAttribute("userId",user.getId());
        session.setAttribute("role",user.getRole());
        session.setMaxInactiveInterval(10800);
        return Result.success("登录成功");
    }

    //查看个人信息
    @Override
    public Result getById(Long id) {
        UserVO user = userMapper.getById(id);
        return Result.success(user);
    }

    //教师获取用户列表
    @Override
    public Result getAll(PageDTO pageDTO) {
        int pageNum = pageDTO.getPageNum() == null || pageDTO.getPageNum() < 1 ? 1 : pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize() == null || pageDTO.getPageSize() < 1 ? 10 : pageDTO.getPageSize();
        int offset = (pageNum - 1) * pageSize;
        pageDTO.setOffset(offset);
        List<UserInfoVO> list = userMapper.getAll(pageDTO);
        Long total = userMapper.countAll();
        PageResult<UserInfoVO> result = PageResult.build(
                list,
                total,
                pageDTO.getPageSize(),
                pageDTO.getPageNum()
        );
        return Result.success(result);
    }
}
