package com.java1234.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java1234.entity.PageBean;
import com.java1234.entity.SaleChance;
import com.java1234.service.SaleChanceService;
import com.java1234.util.StringUtil;

/**
 * <p>
 * Title: SaleChanceController
 * </p>
 * <p>
 * Description: 销售机会操作界面
 * </p>
 * 
 * @author PeiYing.Guo
 * @date 2016年6月21日下午2:35:42
 * @version 1.2
 */
@Controller
@RequestMapping("/saleChance")
public class SaleChanceController {

    @Resource
    private SaleChanceService saleChanceService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    /**
     * 查询销售机会集合
     * 
     * @param page
     * @param rows
     * @param s_saleChance
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "rows", required = false) String rows,
            SaleChance s_saleChance, HttpServletResponse response)
                    throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page),
                Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerName",
                StringUtil.formatLike(s_saleChance.getCustomerName()));
        map.put("overView", StringUtil.formatLike(s_saleChance.getOverView()));
        map.put("createMan",
                StringUtil.formatLike(s_saleChance.getCreateMan()));
        map.put("state", s_saleChance.getState());
        map.put("devResult", s_saleChance.getDevResult());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<SaleChance> saleChanceList = saleChanceService.findSaleChance(map);
        Long total = saleChanceService.getTotalSaleChance(map);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        result.put("rows", saleChanceList);
        result.put("total", total);
        return mapper.writeValueAsString(result);

        // 将json的格式转换成jackson格式
        // JSONObject result=new JSONObject();
        // JsonConfig jsonConfig=new JsonConfig();
        // jsonConfig.registerJsonValueProcessor(java.util.Date.class, new
        // DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        // JSONArray jsonArray=JSONArray.fromObject(saleChanceList,jsonConfig);
        // ResponseUtil.write(response, result);
    }

    /**
     * 添加销售机会
     * 
     * @param saleChance
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save(SaleChance saleChance, HttpServletResponse response)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<>();

        int resultTotal = 0; // 操作的记录条数
        if (StringUtil.isNotEmpty(saleChance.getAssignMan())) {
            saleChance.setState(1);
        } else {
            saleChance.setState(0); // 默认未分配状态
        }
        if (saleChance.getId() == null) {
            saleChance.setDevResult(0); // 添加的时候 默认未开发成功
            resultTotal = saleChanceService.addSaleChance(saleChance);
        } else {
            resultTotal = saleChanceService.updateSaleChance(saleChance);
        }
        if (resultTotal > 0) { // 执行成功
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return mapper.writeValueAsString(result);

        // 将json的格式转换成jackson格式
        // JSONObject result=new JSONObject();
        // ResponseUtil.write(response, result);
        // return null;
    }

    /**
     * 删除销售机会
     * 
     * @param ids
     *            要删除的项的id集合，用逗号隔开
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value = "ids") String ids,
            HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            saleChanceService.deleteSaleChance(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return mapper.writeValueAsString(result);

        // 将json的格式转换成jackson格式
        // JSONObject result = new JSONObject();
        // ResponseUtil.write(response, result);
        // return null;
    }

    /**
     * 修改客户开发状态
     * 
     * @param id
     * @param devResult
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateSaleChanceDevResult")
    @ResponseBody
    public String updateSaleChanceDevResult(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "devResult") String devResult,
            HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("devResult", devResult);
        int resultTotal = saleChanceService.updateSaleChanceDevResult(map);
        if (resultTotal > 0) { // 执行成功
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return mapper.writeValueAsString(result);

        // 将json的格式转换成jackson格式
        // JSONObject result = new JSONObject();
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
        ObjectMapper mapper = new ObjectMapper();
        SaleChance saleChance = saleChanceService
                .findById(Integer.parseInt(id));
        return mapper.writeValueAsString(saleChance);

        // 将json的格式转换成jackson格式
        // JsonConfig jsonConfig = new JsonConfig();
        // jsonConfig.registerJsonValueProcessor(java.util.Date.class,
        // new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        // JSONObject jsonObject = JSONObject.fromObject(saleChance,
        // jsonConfig);
        // ResponseUtil.write(response, jsonObject);
    }
}
