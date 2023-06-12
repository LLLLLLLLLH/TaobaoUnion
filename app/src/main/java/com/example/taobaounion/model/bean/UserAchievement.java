package com.example.taobaounion.model.bean;

import java.io.Serializable;

public class UserAchievement implements Serializable {

    /**
     * success : true
     * code : 10000
     * message : 获取成就成功.
     * data : {"id":"1628303352338243585","userId":"1628303351709097986","articleDxView":0,"shareDxView":0,"articleTotal":0,"shareTotal":0,"wendaTotal":0,"asCount":0,"resolveCount":0,"fansDx":0,"fansCount":0,"thumbUpDx":0,"thumbUpTotal":0,"followCount":1,"momentCount":1,"favorites":0,"sobDx":4,"sob":54,"createTime":"2023-02-22 15:59","updateTime":"2023-02-22 15:59","atotalView":0,"stotalView":0}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1628303352338243585
         * userId : 1628303351709097986
         * articleDxView : 0
         * shareDxView : 0
         * articleTotal : 0
         * shareTotal : 0
         * wendaTotal : 0
         * asCount : 0
         * resolveCount : 0
         * fansDx : 0
         * fansCount : 0
         * thumbUpDx : 0
         * thumbUpTotal : 0
         * followCount : 1
         * momentCount : 1
         * favorites : 0
         * sobDx : 4
         * sob : 54
         * createTime : 2023-02-22 15:59
         * updateTime : 2023-02-22 15:59
         * atotalView : 0
         * stotalView : 0
         */

        private String id;
        private String userId;
        private int articleDxView;
        private int shareDxView;
        private int articleTotal;
        private int shareTotal;
        private int wendaTotal;
        private int asCount;
        private int resolveCount;
        private int fansDx;
        private int fansCount;
        private int thumbUpDx;
        private int thumbUpTotal;
        private int followCount;
        private int momentCount;
        private int favorites;
        private int sobDx;
        private int sob;
        private String createTime;
        private String updateTime;
        private int atotalView;
        private int stotalView;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getArticleDxView() {
            return articleDxView;
        }

        public void setArticleDxView(int articleDxView) {
            this.articleDxView = articleDxView;
        }

        public int getShareDxView() {
            return shareDxView;
        }

        public void setShareDxView(int shareDxView) {
            this.shareDxView = shareDxView;
        }

        public int getArticleTotal() {
            return articleTotal;
        }

        public void setArticleTotal(int articleTotal) {
            this.articleTotal = articleTotal;
        }

        public int getShareTotal() {
            return shareTotal;
        }

        public void setShareTotal(int shareTotal) {
            this.shareTotal = shareTotal;
        }

        public int getWendaTotal() {
            return wendaTotal;
        }

        public void setWendaTotal(int wendaTotal) {
            this.wendaTotal = wendaTotal;
        }

        public int getAsCount() {
            return asCount;
        }

        public void setAsCount(int asCount) {
            this.asCount = asCount;
        }

        public int getResolveCount() {
            return resolveCount;
        }

        public void setResolveCount(int resolveCount) {
            this.resolveCount = resolveCount;
        }

        public int getFansDx() {
            return fansDx;
        }

        public void setFansDx(int fansDx) {
            this.fansDx = fansDx;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getThumbUpDx() {
            return thumbUpDx;
        }

        public void setThumbUpDx(int thumbUpDx) {
            this.thumbUpDx = thumbUpDx;
        }

        public int getThumbUpTotal() {
            return thumbUpTotal;
        }

        public void setThumbUpTotal(int thumbUpTotal) {
            this.thumbUpTotal = thumbUpTotal;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public int getMomentCount() {
            return momentCount;
        }

        public void setMomentCount(int momentCount) {
            this.momentCount = momentCount;
        }

        public int getFavorites() {
            return favorites;
        }

        public void setFavorites(int favorites) {
            this.favorites = favorites;
        }

        public int getSobDx() {
            return sobDx;
        }

        public void setSobDx(int sobDx) {
            this.sobDx = sobDx;
        }

        public int getSob() {
            return sob;
        }

        public void setSob(int sob) {
            this.sob = sob;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getAtotalView() {
            return atotalView;
        }

        public void setAtotalView(int atotalView) {
            this.atotalView = atotalView;
        }

        public int getStotalView() {
            return stotalView;
        }

        public void setStotalView(int stotalView) {
            this.stotalView = stotalView;
        }
    }
}
