package com.shyc;

/**
 * @author shiyc
 * @date 2026/7/25 19:00
 */
public class UserService {
    public String getUserGender_Male(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            return "未知";
        }
        int sex = Integer.parseInt(idCard.substring(16, 17)) % 2;
        return sex == 0 ? "女" : "男";
    }
}
