package com.app.demo.entity;

public class Result {


    /**
     * code : 200
     * message : 成功
     * data : {"id":26,"type":2,"producer":7,"consumer":8,"observer":0,"executor":0,"status":1,"message":"","remark":"","created_at":"2019-11-13 16:29:40","updated_at":"2019-11-13 16:29:40","deleted_at":null,"room_id":"room001"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public static class DataBean {
        public DataBean(int consumer) {
            this.consumer = consumer;
        }

        /**
         * id : 26
         * type : 2
         * producer : 7
         * consumer : 8
         * observer : 0
         * executor : 0
         * status : 1
         * message :
         * remark :
         * created_at : 2019-11-13 16:29:40
         * updated_at : 2019-11-13 16:29:40
         * deleted_at : null
         * room_id : room001
         */



        private int id;
        private int event_id;
        private int type;
        private int producer;
        private int consumer;
        private int observer;
        private int executor;
        private int status;
        private String message;
        private String remark;
        private String created_at;
        private String updated_at;
        private Object deleted_at;
        private String room_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getProducer() {
            return producer;
        }

        public void setProducer(int producer) {
            this.producer = producer;
        }

        public int getConsumer() {
            return consumer;
        }

        public void setConsumer(int consumer) {
            this.consumer = consumer;
        }

        public int getObserver() {
            return observer;
        }

        public void setObserver(int observer) {
            this.observer = observer;
        }

        public int getExecutor() {
            return executor;
        }

        public void setExecutor(int executor) {
            this.executor = executor;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Object getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(Object deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }
    }
}
