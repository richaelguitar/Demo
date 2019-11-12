package com.app.demo.entity;

public class Result {

    /**
     * code : 200
     * message : 请求成功
     * data : {"userId":"666666","roomId":"r1234567891"}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public static class DataBean {
        public DataBean(String userId, String roomId) {
            this.userId = userId;
            this.roomId = roomId;
        }

        /**
         * userId : 666666
         * roomId : r1234567891
         */



        private String userId;
        private String roomId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }
}
