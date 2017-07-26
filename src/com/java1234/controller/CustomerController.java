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
import com.java1234.entity.Customer;
import com.java1234.entity.CustomerFw;
import com.java1234.entity.CustomerGc;
import com.java1234.entity.CustomerGx;
import com.java1234.entity.PageBean;
import com.java1234.service.CustomerService;
import com.java1234.util.DateUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * 客户Controller类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Resource
	private CustomerService customerService;

	/**
	 * 查询客户集合
	 * 
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows, Customer s_customer,
			HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", StringUtil.formatLike(s_customer.getName()));
		map.put("khno", StringUtil.formatLike(s_customer.getKhno()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Customer> customerList = customerService.findCustomer(map);
		Long total = customerService.getTotalCustomer(map);

		ObjectMapper mapper = new ObjectMapper();
		result.put("rows", customerList);
		result.put("total", total);
		return mapper.writeValueAsString(result);
	}

	/**
	 * 添加客户
	 * 
	 * @param customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Customer customer, HttpServletResponse response) throws Exception {
		int resultTotal = 0; // 操作的记录条数
		if (customer.getId() == null) {
			customer.setKhno("KH" + DateUtil.getCurrentDateStr());
			resultTotal = customerService.addCustomer(customer);
		} else {
			resultTotal = customerService.updateCustomer(customer);
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Boolean> result = new HashMap<>();
		if (resultTotal > 0) { // 执行成功
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return mapper.writeValueAsString(result);
	}

	/**
	 * 删除销售机会
	 * 
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
		customerService.deleteCustomer(Integer.parseInt(ids));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Boolean> result = new HashMap<>();
		result.put("success", true);
		return mapper.writeValueAsString(result);

	}

	/**
	 * 通过id查找实体
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = customerService.findById(Integer.parseInt(id));
		return mapper.writeValueAsString(customer);
	}

	/**
	 * 查询客户分析
	 * 
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCutomerGx")
	@ResponseBody
	public String findCutomerGx(String name, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", StringUtil.formatLike(name));
		List<CustomerGx> customerGxList = customerService.findCutomerGx(map);
		Long total = customerService.getTotalCustomerGx(map);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", customerGxList);
		result.put("total", total);
		return mapper.writeValueAsString(result);

		// 2016-06-21 14：40 json改造成Jackson
		// ObjectMapper mapper = new ObjectMapper();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("name", StringUtil.formatLike(name));
		// List<CustomerGx> customerGxList = customerService.findCutomerGx(map);
		// Long total = customerService.getTotalCustomerGx(map);
		// Map<String, Object> result = new HashMap<>();
		// result.put("rows", customerGxList);
		// result.put("total", total);
		// return mapper.writeValueAsString(result);
	}

	/**
	 * 查询客户构成
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCutomerGc")
	@ResponseBody
	public String findCustomerGc(HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<CustomerGc> customerGcList = customerService.findCustomerGc();
		return mapper.writeValueAsString(customerGcList);

		// 2016-06-21 14：40 json改造成Jackson
		// ObjectMapper mapper = new ObjectMapper();
		// List<CustomerGc> customerGcList = customerService.findCustomerGc();
		// return mapper.writeValueAsString(customerGcList);
	}

	/**
	 * 查询客户服务
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCutomerFw")
	@ResponseBody
	public String findCustomerFw(HttpServletResponse response) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		List<CustomerFw> customerFwList = customerService.findCustomerFw();
		return mapper.writeValueAsString(customerFwList);

		// 2016-06-21 14：40 json改造成Jackson
		// ObjectMapper mapper = new ObjectMapper();
		// List<CustomerFw> customerFwList = customerService.findCustomerFw();
		// return mapper.writeValueAsString(customerFwList);
	}

}
