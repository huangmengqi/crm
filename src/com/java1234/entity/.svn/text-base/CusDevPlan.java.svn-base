package com.java1234.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * Title: CusDevPlan
 * </p>
 * <p>
 * Description:客户开发计划界面操作的javaBean
 * </p>
 * 
 * @author PeiYing.Guo
 * @date 2016年6月21日下午2:38:20
 * @version 1.0
 */
public class CusDevPlan {

    private Integer id; // 编号
    private SaleChance saleChance; // 销售机会
    private String planItem; // 计划项
    private Date planDate; // 计划日期
    private String exeAffect; // 执行效果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SaleChance getSaleChance() {
        return saleChance;
    }

    public void setSaleChance(SaleChance saleChance) {
        this.saleChance = saleChance;
    }

    public String getPlanItem() {
        return planItem;
    }

    public void setPlanItem(String planItem) {
        this.planItem = planItem;
    }

    // 日期格式的转换
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getExeAffect() {
        return exeAffect;
    }

    public void setExeAffect(String exeAffect) {
        this.exeAffect = exeAffect;
    }

}
