package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java1234.entity.DataDic;
import com.java1234.entity.PageBean;
import com.java1234.service.DataDicService;
import com.java1234.util.StringUtil;

/**
 * 数据字典Controller层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dataDic")
public class DataDicController {

	@Resource
	private DataDicService dataDicService;
	
	   
    /**
     * 查找数据字典集合 用于下拉框
     * @param dataDicName
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findDataDic")
    @ResponseBody
    public String dataDicComboList(String dataDicName,HttpServletResponse response)throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        map.put("dataDicName", dataDicName);
        List<DataDic> dataDicList = dataDicService.findDataDic(map);
        return mapper.writeValueAsString(dataDicList);
    }
    
	
	/**
	 * 查询所有的数据字典名称
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDataDicName")
	@ResponseBody
	public String dataDicNameComboList(HttpServletResponse response)throws Exception{
	    ObjectMapper mapper = new ObjectMapper();
	  //2016-6-21注释部分是json我们手动该成了 jackson
//	    JSONArray jsonArray=new JSONArray();
		List<DataDic> dataDicList=dataDicService.findAllDataDicName();
//		JSONArray rows=JSONArray.fromObject(dataDicList);
//		jsonArray.addAll(rows);
//		ResponseUtil.write(response, jsonArray);
		return mapper.writeValueAsString(dataDicList);
//		return null;
	}
	
	/**
	 * 查询数据字典集合
	 * @param page
	 * @param rows
	 * @param s_dataDic
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,DataDic s_dataDic,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		ObjectMapper mapper=new ObjectMapper();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dataDicName", s_dataDic.getDataDicName());
		map.put("dataDicValue", StringUtil.formatLike(s_dataDic.getDataDicValue()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<DataDic> dataDicList=dataDicService.findDataDic(map);
		Long total=dataDicService.getTotalDataDic(map);
		//2016-6-21注释部分是json我们手动该成了 jackson
//		JSONObject result=new JSONObject();
//		JSONArray jsonArray=JSONArray.fromObject(dataDicList);
		Map<String,Object> result=new HashMap<>();
		result.put("rows", dataDicList);
		result.put("total", total);
//		ResponseUtil.write(response, result);
		return mapper.writeValueAsString(result);
//		return null;
	}
	
	/**
	 * 添加数据字典
	 * @param dataDic
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(DataDic dataDic,HttpServletResponse response)throws Exception{
		ObjectMapper mapper=new ObjectMapper();
	    int resultTotal=0; // 操作的记录条数
		if(dataDic.getId()==null){
			resultTotal=dataDicService.addDataDic(dataDic);
		}else{
			resultTotal=dataDicService.updateDataDic(dataDic);
		}
	//2016-6-21注释部分是json我们手动该成了 jackson
		Map<String,Object> result=new HashMap<>();
		if(resultTotal>0){ // 执行成功
			result.put("success", true);
		}else{
			result.put("success", false);
		}
//		ResponseUtil.write(response, result);
//		return null;
		return mapper.writeValueAsString(result);
	}
	
	/**
	 * 删除数据字典
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
	  //2016-6-21注释部分是json我们手动该成了 jackson
	    //JSONObject result=new JSONObject();
	   ObjectMapper mapper =new ObjectMapper();
	    Map<String,Object> result=new HashMap<>();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			dataDicService.deleteDataDic(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
//		ResponseUtil.write(response, result);
//		return null;
		return mapper.writeValueAsString(result);
	}

}
