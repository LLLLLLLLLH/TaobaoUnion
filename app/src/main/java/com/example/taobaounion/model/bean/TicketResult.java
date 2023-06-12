package com.example.taobaounion.model.bean;

import com.google.gson.Gson;

import java.io.Serializable;

public class TicketResult  {

    /**
     * success : true
     * code : 10000
     * message : 淘宝口令构建成功!
     * data : {"tbk_tpwd_create_response":{"data":{"model":"￥xhQoYC66sMX￥"},"request_id":"64jzpdn6m026"}}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBeanX data;

    public static TicketResult objectFromData(String str) {

        return new Gson().fromJson(str, TicketResult.class);
    }

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Serializable {
        /**
         * tbk_tpwd_create_response : {"data":{"model":"￥xhQoYC66sMX￥"},"request_id":"64jzpdn6m026"}
         */

        private TbkTpwdCreateResponseBean tbk_tpwd_create_response;

        public static DataBeanX objectFromData(String str) {

            return new Gson().fromJson(str, DataBeanX.class);
        }

        public TbkTpwdCreateResponseBean getTbk_tpwd_create_response() {
            return tbk_tpwd_create_response;
        }

        public void setTbk_tpwd_create_response(TbkTpwdCreateResponseBean tbk_tpwd_create_response) {
            this.tbk_tpwd_create_response = tbk_tpwd_create_response;
        }

        public static class TbkTpwdCreateResponseBean implements Serializable {
            /**
             * data : {"model":"￥xhQoYC66sMX￥"}
             * request_id : 64jzpdn6m026
             */

            private DataBean data;
            private String request_id;

            public static TbkTpwdCreateResponseBean objectFromData(String str) {

                return new Gson().fromJson(str, TbkTpwdCreateResponseBean.class);
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public static class DataBean implements Serializable {
                /**
                 * model : ￥xhQoYC66sMX￥
                 */

                private String model;

                public static DataBean objectFromData(String str) {

                    return new Gson().fromJson(str, DataBean.class);
                }

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                @Override
                public String toString() {
                    return "DataBean{" +
                            "model='" + model + '\'' +
                            '}';
                }
            }
        }
    }
}
