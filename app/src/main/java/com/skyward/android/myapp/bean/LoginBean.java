package com.skyward.android.myapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by skyward on 2017/7/8.
 * email:
 */

public class LoginBean implements Serializable {


    /**
     * Status : true
     * Msg : 登录成功！
     * Data : {"WarehouseId":0,"PDAModuleCodes":["2000","2012","2020","2050","2110"],"SessionId":"20170317142336222628","UserId":2,"UserAccount":"admin","UserName":"总部管理员","BelongId":2,"PID":1,"OperateType":0}
     */

    private boolean Status;
    private String Msg;
    private DataBean Data;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * WarehouseId : 0
         * PDAModuleCodes : ["2000","2012","2020","2050","2110"]
         * SessionId : 20170317142336222628
         * UserId : 2
         * UserAccount : admin
         * UserName : 总部管理员
         * BelongId : 2
         * PID : 1
         * OperateType : 0
         */

        private int WarehouseId;
        private String SessionId;
        private int UserId;
        private String UserAccount;
        private String UserName;
        private int BelongId;
        private int PID;
        private int OperateType;
        private List<String> PDAModuleCodes;

        public int getWarehouseId() {
            return WarehouseId;
        }

        public void setWarehouseId(int WarehouseId) {
            this.WarehouseId = WarehouseId;
        }

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String SessionId) {
            this.SessionId = SessionId;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getUserAccount() {
            return UserAccount;
        }

        public void setUserAccount(String UserAccount) {
            this.UserAccount = UserAccount;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public int getBelongId() {
            return BelongId;
        }

        public void setBelongId(int BelongId) {
            this.BelongId = BelongId;
        }

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public int getOperateType() {
            return OperateType;
        }

        public void setOperateType(int OperateType) {
            this.OperateType = OperateType;
        }

        public List<String> getPDAModuleCodes() {
            return PDAModuleCodes;
        }

        public void setPDAModuleCodes(List<String> PDAModuleCodes) {
            this.PDAModuleCodes = PDAModuleCodes;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "WarehouseId=" + WarehouseId +
                    ", SessionId='" + SessionId + '\'' +
                    ", UserId=" + UserId +
                    ", UserAccount='" + UserAccount + '\'' +
                    ", UserName='" + UserName + '\'' +
                    ", BelongId=" + BelongId +
                    ", PID=" + PID +
                    ", OperateType=" + OperateType +
                    ", PDAModuleCodes=" + PDAModuleCodes +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "Status=" + Status +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
