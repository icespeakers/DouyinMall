package org.example.userservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum code {
    SUCCESS(0, "success"),
    EMPTY_PASSWORD(1, "密码不能为空"),
    DIFFERENT_PASSWORD(2, "两次输入密码不一致"),
    WRONG_INFORMATION(3, "用户名或密码错误");

    private int code;
    private String message;
}
