package com.shuangyulin.controller;



import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shuangyulin.utils.ExportExcelUtil;
import com.shuangyulin.utils.UserException;
import com.shuangyulin.service.EmployggService;
import com.shuangyulin.po.Employgg;



//Employgg管理控制层
@Controller
@RequestMapping("/Employgg")
public class EmployggController extends BaseController {

    /*业务层对象*/
    @Resource EmployggService employggService;

	@InitBinder("positionObj")
	public void initBinderpositionObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("positionObj.");
	}
	@InitBinder("employgg")
	public void initBinderEmploygg(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employgg.");
	}
	/*跳转到添加Employgg视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Employgg());
		return "Employgg_add";
	}

	/*客户端ajax方式提交添加员工信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Employgg employgg, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(employggService.getEmploygg(employgg.getEmployggNo()) != null) {
			message = "编号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
        employggService.addEmploygg(employgg);
        message = "添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询员工信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String employggNo, String employggDesc,String name,String employggDay,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (employggNo == null) employggNo = "";
		if (name == null) name = "";
		if (employggDay == null) employggDay = "";
		if (employggDesc == null) employggDesc = "";
		if(rows != 0)employggService.setRows(rows);
		List<Employgg> employggList = employggService.queryEmploygg(employggNo, employggDesc, name, employggDay, page);
	    /*计算总的页数和总的记录数*/
	    employggService.queryTotalPageAndRecordNumber(employggNo, employggDesc, name, employggDay);
	    /*获取到总的页码数目*/
	    int totalPage = employggService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = employggService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Employgg employgg:employggList) {
			JSONObject jsonEmploygg = employgg.getJsonObject();
			jsonArray.put(jsonEmploygg);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询员工信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Employgg> employggList = employggService.queryAllEmploygg();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Employgg employgg:employggList) {
			JSONObject jsonEmploygg = new JSONObject();
			jsonEmploygg.accumulate("employggNo", employgg.getEmployggNo());
			jsonEmploygg.accumulate("name", employgg.getName());
			jsonArray.put(jsonEmploygg);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询员工信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String employggNo,String employggDesc,String name,String employggDay,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (employggNo == null) employggNo = "";
		if (name == null) name = "";
		if (employggDay == null) employggDay = "";
		if (employggDesc == null) employggDesc = "";
		List<Employgg> employggList = employggService.queryEmploygg(employggNo, employggDesc, name, employggDay, currentPage);
	    /*计算总的页数和总的记录数*/
	    employggService.queryTotalPageAndRecordNumber(employggNo, employggDesc, name, employggDay);
	    /*获取到总的页码数目*/
	    int totalPage = employggService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = employggService.getRecordNumber();
	    request.setAttribute("employggList",  employggList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("employggNo", employggNo);
	    request.setAttribute("employggDesc", employggDesc);
	    request.setAttribute("name", name);
	    request.setAttribute("employggDay", employggDay);
		return "Employgg/employgg_frontquery_result"; 
	}

     /*前台查询Employgg信息*/
	@RequestMapping(value="/{employggNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String employggNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键employggNo获取Employgg对象*/
        Employgg employgg = employggService.getEmploygg(employggNo);
        request.setAttribute("employgg",  employgg);
        return "Employgg/employgg_frontshow";
	}

	/*ajax方式显示员工修改jsp视图页*/
	@RequestMapping(value="/{employggNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String employggNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键employggNo获取Employgg对象*/
        Employgg employgg = employggService.getEmploygg(employggNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonEmploygg = employgg.getJsonObject();
		out.println(jsonEmploygg.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新员工信息*/
	@RequestMapping(value = "/{employggNo}/update", method = RequestMethod.POST)
	public void update(@Validated Employgg employgg, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			employggService.updateEmploygg(employgg);
			message = "更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除员工信息*/
	@RequestMapping(value="/{employggNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String employggNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  employggService.deleteEmploygg(employggNo);
	            request.setAttribute("message", "删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条员工记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String employggNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = employggService.deleteEmployggs(employggNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出员工信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String employggNo, String employggDesc,String name,String employggDay, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(employggNo == null) employggNo = "";
        if(name == null) name = "";
        if(employggDay == null) employggDay = "";
        if(employggDesc == null) employggDesc = "";
        List<Employgg> employggList = employggService.queryEmploygg(employggNo,employggDesc,name,employggDay);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Employgg信息记录"; 
        String[] headers = { "编号","姓名","发布日期","公告"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<employggList.size();i++) {
        	Employgg employgg = employggList.get(i); 
        	dataset.add(new String[]{employgg.getEmployggNo(),employgg.getEmployggDesc(),employgg.getName(),employgg.getEmployggDay(),employgg.getEmployggDesc()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Employgg.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
