package com.example.taobaounion.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int id, int page) {
        return "discovery/" + id + "/" + page;
    }

    public static String getCoverPath(String uri, int size) {
        return "https:" + uri + "_" + size + "x" + size + ".jpg";
    }

    public static String getCoverPath(String uri) {
        if (uri.startsWith("https:"))
            return uri;
        return "https:" + uri;
    }

    public static String getTicketUrl(String url) {
        if (url.startsWith("http") || url.startsWith("https"))
            return url;
        else
            return "https:" + url;
    }

    public static String getDiscountsUrl(int currentPage) {
        return "onSell/" + currentPage;
    }

    public static String getCodeUrl(long random) {
        return Constants.SUNOFBEACHES_BASE_URL + "uc/ut/captcha?code=" + random;
    }
}
