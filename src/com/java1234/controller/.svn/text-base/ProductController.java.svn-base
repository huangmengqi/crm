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
import com.java1234.entity.PageBean;
import com.java1234.entity.Product;
import com.java1234.service.ProductService;
import com.java1234.util.StringUtil;

/**
 * 产品Controller类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Resource
	private ProductService productService;

	/**
	 * 查询用户集合
	 * 
	 * @param page
	 * @param rows
	 * @param s_product
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows, Product s_product,
			HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productName", StringUtil.formatLike(s_product.getProductName()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Product> productList = productService.findProduct(map);
		Long total = productService.getTotalProduct(map);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", productList);
		result.put("total", total);
		System.out.println(mapper.writeValueAsString(result));
		return mapper.writeValueAsString(result);
	}

}
