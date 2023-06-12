package com.example.taobaounion.view;

public interface ILoginCallBack {
    default void onSuccess(boolean isSuccess) {

    }

    default void showAvatar(String avatar) {
            // 默认实现为空
        }

}
