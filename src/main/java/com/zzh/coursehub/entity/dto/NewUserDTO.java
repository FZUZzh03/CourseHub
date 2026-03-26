package com.zzh.coursehub.entity.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewUserDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "角色不能为空")
    private String role;
    @NotNull(message = "年龄不能为空")
    @Min(value = 1,message = "年龄必须大于0")
    private Integer age;
}
