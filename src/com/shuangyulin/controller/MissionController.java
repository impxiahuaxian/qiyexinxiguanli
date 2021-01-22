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
	import com.shuangyulin.service.MissionService;
	import com.shuangyulin.po.Mission;

	
	//Mission管理控制层
	@Controller
	@RequestMapping("/Mission")
	public class MissionController  extends BaseController {

	    /*业务层对象*/
	    @Resource MissionService missionService;

		@InitBinder("mission")
		public void initBinderMission(WebDataBinder binder) {
			binder.setFieldDefaultPrefix("mission.");
		}
		/*跳转到添加Mission视图*/
		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public String add(Model model,HttpServletRequest request) throws Exception {
			model.addAttribute(new Mission());
			return "Mission_add";
		}

		/*客户端ajax方式提交添加任务信息*/
		@RequestMapping(value = "/add", method = RequestMethod.POST)
		public void add(@Validated Mission mission, BindingResult br,
				Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
			String message = "";
			boolean success = false;
			if (br.hasErrors()) {
				message = "输入信息不符合要求！";
				writeJsonResponse(response, success, message);
				return ;
			}
			if(missionService.getMission(mission.getMissionName()) != null) {
				message = "任务已经存在！";
				writeJsonResponse(response, success, message);
				return ;
			}
			missionService.addMission(mission);
	        message = "任务创建成功!";
	        success = true;
	        writeJsonResponse(response, success, message);
		}
		/*ajax方式按照查询条件分页查询任务信息*/
		@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
		public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
			if (page==null || page == 0) page = 1;
			if(rows != 0)missionService.setRows(rows);
			List<Mission> missionList = missionService.queryMission(page);
		    /*计算总的页数和总的记录数*/
			missionService.queryTotalPageAndRecordNumber();
		    /*获取到总的页码数目*/
		    int totalPage = missionService.getTotalPage();
		    /*当前查询条件下总记录数*/
		    int recordNumber = missionService.getRecordNumber();
	        response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			//将要被返回到客户端的对象
			JSONObject jsonObj=new JSONObject();
			jsonObj.accumulate("total", recordNumber);
			JSONArray jsonArray = new JSONArray();
			for(Mission mission:missionList) {
				JSONObject jsonMission = mission.getJsonObject();
				jsonArray.put(jsonMission);
			}
			jsonObj.accumulate("rows", jsonArray);
			out.println(jsonObj.toString());
			out.flush();
			out.close();
		}

		/*ajax方式按照查询条件分页查询任务信息*/
		@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
		public void listAll(HttpServletResponse response) throws Exception {
			List<Mission> missionList = missionService.queryAllMission();
	        response.setContentType("text/json;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			JSONArray jsonArray = new JSONArray();
			for(Mission mission:missionList) {
				JSONObject jsonMission = new JSONObject();
				jsonMission.accumulate("missionName", mission.getMissionName());
				jsonMission.accumulate("missionPeople", mission.getMissionPeople());
				jsonArray.put(jsonMission);
			}
			out.println(jsonArray.toString());
			out.flush();
			out.close();
		}

		/*前台按照查询条件分页查询任务信息*/
		@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
		public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
			if (currentPage==null || currentPage == 0) currentPage = 1;
			List<Mission> missionList = missionService.queryMission(currentPage);
		    /*计算总的页数和总的记录数*/
			missionService.queryTotalPageAndRecordNumber();
		    /*获取到总的页码数目*/
		    int totalPage = missionService.getTotalPage();
		    /*当前查询条件下总记录数*/
		    int recordNumber = missionService.getRecordNumber();
		    request.setAttribute("missionList",  missionList);
		    request.setAttribute("totalPage", totalPage);
		    request.setAttribute("recordNumber", recordNumber);
		    request.setAttribute("currentPage", currentPage);
			return "Mission/mission_frontquery_result"; 
		}

	     /*前台查询Mission信息*/
		@RequestMapping(value="/{missionName}/frontshow",method=RequestMethod.GET)
		public String frontshow(@PathVariable String missionName,Model model,HttpServletRequest request) throws Exception {
			/*根据主键missionName获取Mission对象*/
			Mission mission = missionService.getMission(missionName);

	        request.setAttribute("mission",  mission);
	        return "Mission/mission_frontshow";
		}

		/*ajax方式显示部门修改jsp视图页*/
		@RequestMapping(value="/{missionName}/update",method=RequestMethod.GET)
		public void update(@PathVariable String missionName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
	        /*根据主键missionName获取Mission对象*/
			Mission mission = missionService.getMission(missionName);

	        response.setContentType("text/json;charset=UTF-8");
	        PrintWriter out = response.getWriter();
			//将要被返回到客户端的对象 
			JSONObject jsonMission = mission.getJsonObject();
			out.println(jsonMission.toString());
			out.flush();
			out.close();
		}

		/*ajax方式更新部门信息*/
		@RequestMapping(value = "/{missionName}/update", method = RequestMethod.POST)
		public void update(@Validated Mission mission, BindingResult br,
				Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
			String message = "";
	    	boolean success = false;
			if (br.hasErrors()) { 
				message = "输入的信息有错误！";
				writeJsonResponse(response, success, message);
				return;
			}
			try {
				missionService.updateMission(mission);
				message = "任务更新成功!";
				success = true;
				writeJsonResponse(response, success, message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "任务更新失败!";
				writeJsonResponse(response, success, message); 
			}
		}
	    /*删除部门信息*/
		@RequestMapping(value="/{missionName}/delete",method=RequestMethod.GET)
		public String delete(@PathVariable String missionName,HttpServletRequest request) throws UnsupportedEncodingException {
			  try {
				  missionService.deleteMission(missionName);
		            request.setAttribute("message", "任务删除成功!");
		            return "message";
		        } catch (Exception e) { 
		            e.printStackTrace();
		            request.setAttribute("error", "任务删除失败!");
					return "error";

		        }

		}

		/*ajax方式删除多条任务记录*/
		@RequestMapping(value="/deletes",method=RequestMethod.POST)
		public void delete(String missionNames,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
			String message = "";
	    	boolean success = false;
	        try { 
	        	int count = missionService.deleteMissions(missionNames);
	        	success = true;
	        	message = count + "条记录删除成功";
	        	writeJsonResponse(response, success, message);
	        } catch (Exception e) { 
	            //e.printStackTrace();
	            message = "有记录存在外键约束,删除失败";
	            writeJsonResponse(response, success, message);
	        }
		}

		/*按照查询条件导出任务信息到Excel*/
		@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
		public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
	        List<Mission> missionList = missionService.queryMission();
	        ExportExcelUtil ex = new ExportExcelUtil();
	        String title = "Mission信息记录"; 
	        String[] headers = { "任务名称","负责人"};
	        List<String[]> dataset = new ArrayList<String[]>(); 
	        for(int i=0;i<missionList.size();i++) {
	        	Mission mission = missionList.get(i); 
	        	dataset.add(new String[]{mission.getMissionName(),mission.getMissionPeople()});
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
				response.setHeader("Content-disposition","attachment; filename="+"Mission.xls");//filename是下载的xls的名，建议最好用英文 
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



