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
import com.java1234.entity.CustomerReprieve;
import com.java1234.service.CustomerReprieveService;

/**
 * <p>Title: CustomerReprieveController</p>
 * <p>Description: 客户流失暂缓措施Controller类</p>
 * @author	ChaoWei.Wang
 * @date	2016年6月21日下午2:33:44
 * @version 1.1
 */
@Controller
@RequestMapping("/customerReprieve")
public class CustomerReprieveController {

	@Resource
	private CustomerReprieveService customerReprieveService;

	/**
	 * 查询客户流失暂缓措施集合
	 * 
	 * @param lossId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value = "lossId") String lossId, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<CustomerReprieve>> result = new HashMap<>();
		Map<String,Object> map = new HashMap<>();
		map.put("lossId", lossId);
		List<CustomerReprieve> customerReprieveList = customerReprieveService.findCustomerReprieve(map);
		result.put("rows", customerReprieveList);
		return mapper.writeValueAsString(result);
	}

	/**
	 * 添加客户流失暂缓措施
	 * 
	 * @param customerReprieve
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CustomerReprieve customerReprieve, HttpServletResponse response) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Boolean> result = new HashMap<>();

		int resultTotal = 0; // 操作的记录条数
		if (customerReprieve.getId() == null) {
			resultTotal = customerReprieveService.addCustomerReprieve(customerReprieve);
		} else {
			resultTotal = customerReprieveService.updateCustomerReprieve(customerReprieve);
		}

		if (resultTotal > 0) { // 执行成功
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return mapper.writeValueAsString(result);
	}

	/**
	 * 删除客户流失暂缓措施
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {

		customerReprieveService.deleteCustomerReprieve(Integer.parseInt(id));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Boolean> result = new HashMap<>();
		result.put("success", true);
		return mapper.writeValueAsString(result);
	}
}
