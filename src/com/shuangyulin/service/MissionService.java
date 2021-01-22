package com.shuangyulin.service;
	import java.util.ArrayList;
	import javax.annotation.Resource; 
	import org.springframework.stereotype.Service;
	import com.shuangyulin.po.Mission;

	import com.shuangyulin.mapper.MissionMapper;
	@Service
	public class MissionService {
		@Resource MissionMapper missionMapper;
	    /*每页显示记录数目*/
	    private int rows = 10;;
	    public int getRows() {
			return rows;
		}
		public void setRows(int rows) {
			this.rows = rows;
		}

	    /*保存查询后总的页数*/
	    private int totalPage;
	    public void setTotalPage(int totalPage) {
	        this.totalPage = totalPage;
	    }
	    public int getTotalPage() {
	        return totalPage;
	    }

	    /*保存查询到的总记录数*/
	    private int recordNumber;
	    public void setRecordNumber(int recordNumber) {
	        this.recordNumber = recordNumber;
	    }
	    public int getRecordNumber() {
	        return recordNumber;
	    }

	    /*添加部门记录*/
	    public void addMission(Mission mission) throws Exception {
	    	missionMapper.addMission(mission);
	    }

	    /*按照查询条件分页查询部门记录*/
	    public ArrayList<Mission> queryMission(int currentPage) throws Exception { 
	     	String where = "where 1=1";
	    	int startIndex = (currentPage-1) * this.rows;
	    	return missionMapper.queryMission(where, startIndex, this.rows);
	    }

	    /*按照查询条件查询所有记录*/
	    public ArrayList<Mission> queryMission() throws Exception  { 
	     	String where = "where 1=1";
	    	return missionMapper.queryMissionList(where);
	    }

	    /*查询所有部门记录*/
	    public ArrayList<Mission> queryAllMission()  throws Exception {
	        return missionMapper.queryMissionList("where 1=1");
	    }

	    /*当前查询条件下计算总的页数和记录数*/
	    public void queryTotalPageAndRecordNumber() throws Exception {
	     	String where = "where 1=1";
	        recordNumber = missionMapper.queryMissionCount(where);
	        int mod = recordNumber % this.rows;
	        totalPage = recordNumber / this.rows;
	        if(mod != 0) totalPage++;
	    }

	    /*根据主键获取部门记录*/
	    public Mission getMission(String missionName) throws Exception  {
	    	Mission mission = missionMapper.getMission(missionName);
	        return mission;
	    }

	    /*更新部门记录*/
	    public void updateMission(Mission mission) throws Exception {
	    	missionMapper.updateMission(mission);
	    }

	    /*删除一条部门记录*/
	    public void deleteMission (String missionName) throws Exception {
	    	missionMapper.deleteMission(missionName);
	    }

	    /*删除多条部门信息*/
	    public int deleteMissions (String missionNames) throws Exception {
	    	String _missionNames[] = missionNames.split(",");
	    	for(String _missionName: _missionNames) {
	    		missionMapper.deleteMission(_missionName);
	    	}
	    	return _missionNames.length;
	    }
	}


