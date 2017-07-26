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
import com.java1234.entity.Order;
import com.java1234.entity.PageBean;
import com.java1234.service.OrderService;
import com.java1234.util.StringUtil;

/**
 * <p>
 * Title: OrderController
 * </p>
 * <p>
 * Description: 订单Controller类
 * </p>
 * 
 * @author ChengPeng.Li
 * @date 2016年6月21日下午2:36:41
 * @version 1.2
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 查询订单集合
     * 
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "rows", required = false) String rows,
            @RequestParam(value = "cusId", required = false) String cusId,
            HttpServletResponse response) throws Exception {
        if (StringUtil.isEmpty(cusId)) {
            return null;
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page),
                Integer.parseInt(rows));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Order>> result = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("cusId", cusId);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Order> orderList = orderService.findOrder(map);
        result.put("rows", orderList);
        return mapper.writeValueAsString(result);

        // 2016-6-21 json改成Jackson
        // Map<String,Object> map=new HashMap<String,Object>();
        // map.put("cusId", cusId);
        // map.put("start", pageBean.getStart());
        // map.put("size", pageBean.getPageSize());
        // List<Order> orderList=orderService.findOrder(map);
        // JsonConfig jsonConfig=new JsonConfig();
        // jsonConfig.registerJsonValueProcessor(java.util.Date.class, new
        // DateJsonValueProcessor("yyyy-MM-dd"));
        // Long total=orderService.getTotalOrder(map);
        // JSONObject result=new JSONObject();
        // JSONArray jsonArray=JSONArray.fromObject(orderList,jsonConfig);
        // result.put("rows", jsonArray);
        // result.put("total", total);
        // ResponseUtil.write(response, result);
        // return null;
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
    public String findById(@RequestParam(value = "id") String id,
            HttpServletResponse response) throws Exception {
        Order order = orderService.findById(Integer.parseInt(id));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(order);

        // 2016-6-21 json改成Jackson
        // JsonConfig jsonConfig=new JsonConfig();
        // jsonConfig.registerJsonValueProcessor(java.util.Date.class, new
        // DateJsonValueProcessor("yyyy-MM-dd"));
        // JSONObject jsonObject=JSONObject.fromObject(order,jsonConfig);
        // ResponseUtil.write(response, jsonObject);
        // return null;
    }
}
