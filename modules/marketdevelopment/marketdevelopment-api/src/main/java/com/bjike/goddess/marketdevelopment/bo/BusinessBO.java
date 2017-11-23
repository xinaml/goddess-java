package com.bjike.goddess.marketdevelopment.bo;

import com.bjike.goddess.common.api.bo.BaseBO;

/**
 * 业务方向分类业务传输对象
 *
 * @Author: [ zhuangkaiqin ]
 * @Date: [ 2017-11-18 02:56 ]
 * @Description: [ 业务方向分类业务传输对象 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public class BusinessBO extends BaseBO {

    /**
     * 业务方向编号
     */
    private String businessNum;

    /**
     * 业务方向分类
     */
    private String businessType;


    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}