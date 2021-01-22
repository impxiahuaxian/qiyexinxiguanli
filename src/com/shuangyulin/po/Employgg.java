package com.shuangyulin.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Employgg {
    /*编号*/
    @NotEmpty(message="员工编号不能为空")
    private String employggNo;
    public String getEmployggNo(){
        return employggNo;
    }
    public void setEmployggNo(String employggNo){
        this.employggNo = employggNo;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    /*发布日期*/
    @NotEmpty(message="发布日期不能为空")
    private String employggDay;
    public String getEmployggDay() {
        return employggDay;
    }
    public void setEmployggDay(String employggDay) {
        this.employggDay = employggDay;
    }

    /*公告*/
    private String employggDesc;
    public String getEmployggDesc() {
        return employggDesc;
    }
    public void setEmployggDesc(String employggDesc) {
        this.employggDesc = employggDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonEmploygg=new JSONObject(); 
		jsonEmploygg.accumulate("employggNo", this.getEmployggNo());
		jsonEmploygg.accumulate("name", this.getName());
		jsonEmploygg.accumulate("employggDay", this.getEmployggDay().length()>19?this.getEmployggDay().substring(0,19):this.getEmployggDay());
		jsonEmploygg.accumulate("employggDesc", this.getEmployggDesc());
		return jsonEmploygg;
    }}