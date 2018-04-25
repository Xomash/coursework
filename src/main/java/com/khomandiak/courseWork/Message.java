package com.khomandiak.courseWork;

import java.util.List;

/**
 * Created by o.khomandiak on 27.03.2018.
 */

public class Message {
    private String subject;
    private String startTime;
    private String viberTtl;
    private String smsTtl;
    private String priority;
    private String imgUrl;
    private String captionTxt;
    private String captionUrl;
    private String viberTxt;
    private String smsTxt;
    private String contractCode;
    private String phoneNumber;
    private List<String> params;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getViberTtl() {
        return viberTtl;
    }

    public void setViberTtl(String viberTtl) {
        this.viberTtl = viberTtl;
    }

    public String getSmsTtl() {
        return smsTtl;
    }

    public void setSmsTtl(String smsTtl) {
        this.smsTtl = smsTtl;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCaptionTxt() {
        return captionTxt;
    }

    public void setCaptionTxt(String captionTxt) {
        this.captionTxt = captionTxt;
    }

    public String getCaptionUrl() {
        return captionUrl;
    }

    public void setCaptionUrl(String captionUrl) {
        this.captionUrl = captionUrl;
    }

    public String getViberTxt() {
        return viberTxt;
    }

    public void setViberTxt(String viberTxt) {
        this.viberTxt = viberTxt;
    }

    public String getSmsTxt() {
        return smsTxt;
    }

    public void setSmsTxt(String smsTxt) {
        this.smsTxt = smsTxt;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Message{" +
                "subject='" + subject + '\'' +
                ", startTime=" + startTime +
                ", viberTtl='" + viberTtl + '\'' +
                ", smsTtl='" + smsTtl + '\'' +
                ", priority=" + priority +
                ", imgUrl='" + imgUrl + '\'' +
                ", captionTxt='" + captionTxt + '\'' +
                ", captionUrl='" + captionUrl + '\'' +
                ", viberTxt='" + viberTxt + '\'' +
                ", smsTxt='" + smsTxt + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", params=" + params +
                '}';
    }
}
