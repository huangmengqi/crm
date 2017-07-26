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
import com.java1234.entity.CustomerLoss;
import com.java1234.entity.PageBean;
import com.java1234.service.CustomerLossService;
import com.java1234.util.StringUtil;

/**
 * 客户流失Controller类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customerLoss")
public class CustomerLossController {

	@Resource
	private CustomerLossService customerLossService;
	
	/**
	 * 查询客户流失集合
	 * @param page
	 * @param rows
	 * @param s_customerLoss
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,CustomerLoss s_customerLoss,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cusName", StringUtil.formatLike(s_customerLoss.getCusName()));
		map.put("cusManager", StringUtil.formatLike(s_customerLoss.getCusManager()));
		map.put("state", s_customerLoss.getState());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerLoss> customerLossList=customerLossService.findCustomerLoss(map);
		Long total=customerLossService.getTotalCutomerLoss(map);
		
		

		ObjectMapper mapper = new ObjectMapper();

      result.put("rows", customerLossList);
	result.put("total", total);
		return mapper.writeValueAsString(result);
	}
	
	/**
	 * 通过id查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		 ObjectMapper mapper = new ObjectMapper();
		 CustomerLoss customerLoss=customerLossService.findById(Integer.parseInt(id));
			return mapper.writeValueAsString(customerLoss);
	}
	
	/**
	 * 确认客户流失
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmLoss")
	@ResponseBody
	public String confirmLoss(int id,HttpServletResponse response)throws Exception{
		CustomerLoss customerLoss=new CustomerLoss();
		customerLoss.setId(id);
		customerLoss.setState(1); // 设置标识
		customerLossService.updateCustomerLoss(customerLoss);
		 ObjectMapper mapper = new ObjectMapper();
	        Map<String,Boolean> result=new HashMap<>();
		result.put("success", true);
		return mapper.writeValueAsString(result);
	}
}
