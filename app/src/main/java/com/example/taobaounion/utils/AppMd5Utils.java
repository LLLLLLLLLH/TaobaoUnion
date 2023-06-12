package com.example.taobaounion.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppMd5Utils {
    public static String getMd5(String input) {
        try {
            // 使用 MessageDigest 类获取 MD5 实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入的字符串转换为字节数组
            byte[] inputBytes = input.getBytes();
            // 计算 MD5 值并返回
            byte[] mdBytes = md.digest(inputBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : mdBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
