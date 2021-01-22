package com.shuangyulin.mapper;
	import java.util.ArrayList;
	import org.apache.ibatis.annotations.Param;
	import com.shuangyulin.po.Mission;

	public interface MissionMapper {
		/*添加部门信息*/
		public void addMission(Mission mission) throws Exception;

		/*按照查询条件分页查询部门记录*/
		public ArrayList<Mission> queryMission(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

		/*按照查询条件查询所有部门记录*/
		public ArrayList<Mission> queryMissionList(@Param("where") String where) throws Exception;

		/*按照查询条件的部门记录数*/
		public int queryMissionCount(@Param("where") String where) throws Exception; 

		/*根据主键查询某条部门记录*/
		public Mission getMission(String missionName) throws Exception;

		/*更新部门记录*/
		public void updateMission(Mission mission) throws Exception;

		/*删除部门记录*/
		public void deleteMission(String missionName) throws Exception;

	}



