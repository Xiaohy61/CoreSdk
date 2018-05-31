package com.skyward.android.myapp.bean;

import java.util.List;

/**
 * @author: skyward
 * date: 2018/4/23
 * desc:
 */
public class UploadImageResultBean {


    /**
     * Status : true
     * Msg : 新增成功
     * Data : {"fileName":["https://ta.tymapi.com:443/UploadImages/ImageCenter/2018-4-23/63660099267165250316_实心向下.png"]}
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

    @Override
    public String toString() {
        return "UploadImageResultBean{" +
                "Status=" + Status +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }

    public static class DataBean {
        private List<String> fileName;

        public List<String> getFileName() {
            return fileName;
        }

        public void setFileName(List<String> fileName) {
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "fileName=" + fileName +
                    '}';
        }
    }
}
