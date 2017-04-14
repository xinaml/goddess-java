package com.bjike.goddess.firmreward.to;

import com.bjike.goddess.common.api.to.BaseTO;

/**
 * 奖品申请
 *
 * @Author: [ sunfengtao ]
 * @Date: [ 2017-04-13 09:04 ]
 * @Description: [  ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public class PrizeApplyTO extends BaseTO {

    /**
     * 奖励项目名称
     */
    private String awardItemName;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 项目组
     */
    private String projectTeam;

    /**
     * 获奖等级
     */
    private String awardGrade;

    /**
     * 奖品注意事项
     */
    private String awardNotes;

    /**
     * 运营财务部审批
     */
    private String yYFinanceApproval;

    /**
     * 总经办审批
     */
    private String zjbApproval;

    /**
     * 特殊情况备注
     */
    private String comment;

    /**
     * 奖品明细
     */
    private String[] prizeDetails;

    /**
     * 奖品购置途径
     */
    private String[] prizeBuyWays;

    /**
     * 奖品发放形式
     */
    private String[] prizeIssueForms;

    /**
     * 颁奖时间
     */
    private String[] awardTimes;


    public String getAwardItemName() {
        return awardItemName;
    }

    public void setAwardItemName(String awardItemName) {
        this.awardItemName = awardItemName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(String projectTeam) {
        this.projectTeam = projectTeam;
    }

    public String getAwardGrade() {
        return awardGrade;
    }

    public void setAwardGrade(String awardGrade) {
        this.awardGrade = awardGrade;
    }

    public String getAwardNotes() {
        return awardNotes;
    }

    public void setAwardNotes(String awardNotes) {
        this.awardNotes = awardNotes;
    }

    public String getYYFinanceApproval() {
        return yYFinanceApproval;
    }

    public void setYYFinanceApproval(String yYFinanceApproval) {
        this.yYFinanceApproval = yYFinanceApproval;
    }

    public String getZjbApproval() {
        return zjbApproval;
    }

    public void setZjbApproval(String zjbApproval) {
        this.zjbApproval = zjbApproval;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getyYFinanceApproval() {
        return yYFinanceApproval;
    }

    public void setyYFinanceApproval(String yYFinanceApproval) {
        this.yYFinanceApproval = yYFinanceApproval;
    }

    public String[] getPrizeDetails() {
        return prizeDetails;
    }

    public void setPrizeDetails(String[] prizeDetails) {
        this.prizeDetails = prizeDetails;
    }

    public String[] getPrizeBuyWays() {
        return prizeBuyWays;
    }

    public void setPrizeBuyWays(String[] prizeBuyWays) {
        this.prizeBuyWays = prizeBuyWays;
    }

    public String[] getPrizeIssueForms() {
        return prizeIssueForms;
    }

    public void setPrizeIssueForms(String[] prizeIssueForms) {
        this.prizeIssueForms = prizeIssueForms;
    }

    public String[] getAwardTimes() {
        return awardTimes;
    }

    public void setAwardTimes(String[] awardTimes) {
        this.awardTimes = awardTimes;
    }
}