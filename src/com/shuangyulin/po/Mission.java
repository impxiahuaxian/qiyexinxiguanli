package com.shuangyulin.po;

import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Mission {

	 /*任务名称*/
    @NotEmpty(message="任务名称不能为空")
    private String missionName;
    public String getMissionName(){
        return missionName;
    }
    public void setMissionName(String missionName){
        this.missionName = missionName;
    }

    /*负责人*/
    @NotEmpty(message="负责人不能为空")
    private String missionPeople;
    public String getMissionPeople() {
        return missionPeople;
    }
    public void setMissionPeople(String missionPeople) {
        this.missionPeople = missionPeople;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonMission=new JSONObject(); 
		jsonMission.accumulate("missionName", this.getMissionName());
		jsonMission.accumulate("missionPeople", this.getMissionPeople());
		return jsonMission;
    }}
