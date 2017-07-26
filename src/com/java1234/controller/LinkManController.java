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
import com.java1234.entity.LinkMan;
import com.java1234.service.LinkManService;

/**
 * <p>Title: LinkManController</p>
 * <p>Description: 联系人Controller类</p>
 * @author	ChaoWei.Wang
 * @date	2016年6月21日下午2:33:25
 * @version 1.1
 */
@Controller
@RequestMapping("/linkMan")
public class LinkManController {

	@Resource
	private LinkManService linkManService;

	/**
	 * 查询联系人集合
	 * 
	 * @param cusId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(value = "cusId") String cusId, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<LinkMan>> result = new HashMap<>();
		Map<String,Object> map = new HashMap<>();
		map.put("cusId", cusId);
		List<LinkMan> linkManList = linkManService.findLinkMan(map);
		result.put("rows", linkManList);
		//使用jackson的ObjectMapper的writeValueAsString方法可以把数据输出成json字符串
		return mapper.writeValueAsString(result);
	}

	/**
	 * 添加联系人
	 * 
	 * @param linkMan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(LinkMan linkMan, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Boolean> result = new HashMap<>();

		int resultTotal = 0; // 操作的记录条数
		if (linkMan.getId() == null) {
			resultTotal = linkManService.addLinkMan(linkMan);
		} else {
			resultTotal = linkManService.updateLinkMan(linkMan);
		}

		if (resultTotal > 0) { // 执行成功
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		mapper.writeValueAsString(result);
		return ;
	}

	/**
	 * 删除联系人
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {

		int a=linkManService.deleteLinkMan(Integer.parseInt(id));
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(a);
		//实现动态刷新
		return ;
	}
}
