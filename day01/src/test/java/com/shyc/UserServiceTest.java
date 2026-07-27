package com.shyc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author shiyc
 * @date 2026/7/26 19:44
 */
@DisplayName("用户服务测试类")
public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    public void setUp() {
     userService = new UserService();
        
    }


    @DisplayName("测试-获取性别-男性")
    @Test
    public void testGetUserGender_Male() {
        // 第 17 位是 3（奇数）→ 男

        String idCard = "110101199001011234";
        String gender = userService.getUserGender_Male(idCard);
        Assertions.assertEquals("男", gender, "证号第17位奇数,应为男性");
    }

    @DisplayName("测试-获取性别-女性")
    @Test
    public void testGetUserGender_Female() {
        // 第 17 位是 2（偶数）→ 女

        String idCard = "110101199001011222";
        String gender = userService.getUserGender_Male(idCard);
        Assertions.assertEquals("女", gender, "证号第17位偶数,应为女性");
    }

    @DisplayName("测试-获取性别-身份证为null")
    @Test
    public void testGetUserGender_Null() {
        String gender = userService.getUserGender_Male(null);
        Assertions.assertEquals("未知", gender, "null输入应返回未知");
    }

    @DisplayName("测试-获取性别-身份证长度不为18")
    @Test
    public void testGetUserGender_InvalidLength() {
        String idCard = "12345";
        String gender = userService.getUserGender_Male(idCard);
        Assertions.assertEquals("未知", gender, "非18位身份证应返回未知");
    }
}
