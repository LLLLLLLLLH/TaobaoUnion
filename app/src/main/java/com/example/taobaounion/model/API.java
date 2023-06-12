package com.example.taobaounion.model;

import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.CheckTokenResult;
import com.example.taobaounion.model.bean.DiscountsContent;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.LoginInfo;
import com.example.taobaounion.model.bean.RegainInfo;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.model.bean.ResponseData;
import com.example.taobaounion.model.bean.SearchRecommend;
import com.example.taobaounion.model.bean.SearchResult;
import com.example.taobaounion.model.bean.TicketParams;
import com.example.taobaounion.model.bean.TicketResult;
import com.example.taobaounion.model.bean.UserAchievement;
import com.example.taobaounion.model.bean.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API {

    @GET("discovery/categories")
    Call<Categories> GET_CATEGORIES();

    @GET
    Call<HomePagerContent> GET_PAGER_CONTENT(@Url String url);

    @POST("tpwd")
    Call<TicketResult> GET_TICKET(@Body TicketParams ticketParams);

   /* @GET("recommend/categories")
    Call<SelectPageCategory> GET_SELECT_PAGE_CATEGORY();*/

    @GET("discovery/categories")
    Call<Categories> GET_SELECT_PAGE_CATEGORY();


    /*@GET("/recommend/categoryId")
    Call<SelectedContent> GET_SELECT_CONTENT(@Query("categoryId") int categoryId);*/

    @GET
    Call<HomePagerContent> GET_SELECT_CONTENT(@Url String url);

    //特惠
    @GET
    Call<DiscountsContent> GET_DISCOUNTS_CONTENT(@Url String url);

    //推荐词搜索
    @GET("search/recommend")
    Call<SearchRecommend> GET_SEARCH_RECOMMEND();


    //搜索
    @GET("search")
    Call<SearchResult> GET_SEARCH_RESULT(@Query("page") int page, @Query("keyword") String keyword);


    //登录
    @POST("uc/user/login/{captcha}")
    Call<ResponseData<String>> POST_LOGIN(@Path("captcha") String captcha, @Body LoginInfo loginInfo);

    //获取验证码
    @GET
    Call<ResponseBody> GET_CAPTCHA(@Url String url);

    //检查token
    @GET("uc/user/checkToken")
    Call<CheckTokenResult> CHECK_TOKEN();

    //获取头像
    @GET("uc/user/avatar/{phoneNum}")
    Call<ResponseData<String>> GET_AVATAR(@Path("phoneNum") String phone);

    //获取用户信息
    @GET("uc/user-info/{userId}")
    Call<UserInfo> GET_USER_INFO(@Path("userId") String userId);


    //退出登录
    @GET("uc/user/logout")
    Call<ResponseData<String>> GET_LOGOUT();

    //获取成就
    @GET("ast/achievement")
    Call<UserAchievement> GET_ACHIEVEMENT();

    //获取手机验证码
    @POST("uc/ut/join/send-sms")
    Call<ResponseBody> POST_JOIN_SEND_SMS(@Body RegisterInfo.Send info);

    @GET("uc/ut/check-sms-code/{phoneNumber}/{smsCode}")
    Call<ResponseBody> GET_CHECK_SMS_CODE(@Query("phoneNum") String phone,@Query("smsCode") String smsCode);

    @POST("uc/user/register/{smsCode}")
    Call<ResponseBody> POST_REGISTER(@Path("smsCode") String smsCode,@Body RegisterInfo.Register info);

    @POST("uc/ut/forget/send-sms")
    Call<ResponseBody> POST_FORGET_SEND_SMS(@Body RegisterInfo.Send info);


    @PUT("/uc/user/modify-pwd")
    Call<ResponseBody> PUT_MODIFY_PWD(@Body RegainInfo.modify info);

    @PUT("/uc/user/forget/{smsCode}")
    Call<ResponseBody> PUT_GET_PASSWORD_BY_SMS(@Query("smsCode") String smsCode,@Body RegainInfo.forget info);

}
