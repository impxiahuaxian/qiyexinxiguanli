package com.shuangyulin.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.shuangyulin.po.Employgg;

import com.shuangyulin.mapper.EmployggMapper;
@Service
public class EmployggService {

	@Resource EmployggMapper employggMapper;
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

    /*添加*/
    public void addEmploygg(Employgg employgg) throws Exception {
    	employggMapper.addEmploygg(employgg);
    }

    /*按照查询条件分页查询记录*/
    public ArrayList<Employgg> queryEmploygg(String employggNo,String employggDesc,String name,String employggDay,Integer currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!employggNo.equals("")) where = where + " and t_employgg.employggNo like '%" + employggNo + "%'";
    	if(!employggDesc.equals("")) where = where + " and t_employgg.employggDesc like '%" + employggDesc + "%'";
    	if(!name.equals("")) where = where + " and t_employgg.name like '%" + name + "%'";
    	if(!employggDay.equals("")) where = where + " and t_employgg.employggDay like '%" + employggDay + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return employggMapper.queryEmploygg(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Employgg> queryEmploygg(String employggNo,String employggDesc,String name,String employggDay) throws Exception  { 
     	String where = "where 1=1";
    	if(!employggNo.equals("")) where = where + " and t_employgg.employggNo like '%" + employggNo + "%'";
    	if(!employggDesc.equals("")) where = where + " and t_employgg.employggDesc like '%" + employggDesc + "%'";
    	if(!name.equals("")) where = where + " and t_employgg.name like '%" + name + "%'";
    	if(!employggDay.equals("")) where = where + " and t_employgg.employggDay like '%" + employggDay + "%'";
    	return employggMapper.queryEmployggList(where);
    }

    /*查询所有员工记录*/
    public ArrayList<Employgg> queryAllEmploygg()  throws Exception {
        return employggMapper.queryEmployggList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String employggNo,String employggDesc,String name,String employggDay) throws Exception {
     	String where = "where 1=1";
    	if(!employggNo.equals("")) where = where + " and t_employgg.employggNo like '%" + employggNo + "%'";
    	if(!employggDesc.equals("")) where = where + " and t_employgg.employggDesc like '%" + employggDesc + "%'";
    	if(!name.equals("")) where = where + " and t_employgg.name like '%" + name + "%'";
    	if(!employggDay.equals("")) where = where + " and t_employgg.employggDay like '%" + employggDay + "%'";
        recordNumber = employggMapper.queryEmployggCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取员工记录*/
    public Employgg getEmploygg(String employggNo) throws Exception  {
        Employgg employgg = employggMapper.getEmploygg(employggNo);
        return employgg;
    }

    /*更新员工记录*/
    public void updateEmploygg(Employgg employgg) throws Exception {
        employggMapper.updateEmploygg(employgg);
    }

    /*删除一条员工记录*/
    public void deleteEmploygg (String employggNo) throws Exception {
        employggMapper.deleteEmploygg(employggNo);
    }

    /*删除多条员工信息*/
    public int deleteEmployggs (String employggNos) throws Exception {
    	String _employggNos[] = employggNos.split(",");
    	for(String _employggNo: _employggNos) {
    		employggMapper.deleteEmploygg(_employggNo);
    	}
    	return _employggNos.length;
    }
}
