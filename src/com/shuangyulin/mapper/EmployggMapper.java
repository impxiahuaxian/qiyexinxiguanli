package com.shuangyulin.mapper;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.shuangyulin.po.Employgg;



public interface EmployggMapper {
	/*添加员工信息*/
	public void addEmploygg(Employgg employgg) throws Exception;

	/*按照查询条件分页查询员工记录*/
	public ArrayList<Employgg> queryEmploygg(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有员工记录*/
	public ArrayList<Employgg> queryEmployggList(@Param("where") String where) throws Exception;

	/*按照查询条件的员工记录数*/
	public int queryEmployggCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条员工记录*/
	public Employgg getEmploygg(String employggNo) throws Exception;

	/*更新员工记录*/
	public void updateEmploygg(Employgg employgg) throws Exception;

	/*删除员工记录*/
	public void deleteEmploygg(String employggNo) throws Exception;

}
