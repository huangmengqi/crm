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
import com.java1234.entity.Contact;
import com.java1234.service.ContactService;

/**
 * <p>
 * Title: ContactController
 * </p>
 * <p>
 * Description: 交往记录Controller类
 * </p>
 * 
 * @author ChengPeng.Li
 * @date 2016年6月21日下午2:39:51
 * @version 1.0
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

    @Resource
    private ContactService contactService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    /**
     * 查询交往记录集合
     * 
     * @param cusId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam(value = "cusId") String cusId,
            HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Contact>> result = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("cusId", cusId);
        List<Contact> contactList = contactService.findContact(map);
        result.put("rows", contactList);
        return mapper.writeValueAsString(result);

        // 2016-6-21 json改成Jackson
        // Map<String,Object> map=new HashMap<String,Object>();
        // map.put("cusId", cusId);
        // List<Contact> contactList=contactService.findContact(map);
        // JSONObject result=new JSONObject();
        // JsonConfig jsonConfig=new JsonConfig();
        // jsonConfig.setExcludes(new String[]{"customer"});
        // jsonConfig.registerJsonValueProcessor(java.util.Date.class, new
        // DateJsonValueProcessor("yyyy-MM-dd"));
        // JSONArray jsonArray=JSONArray.fromObject(contactList,jsonConfig);
        // result.put("rows", jsonArray);
        // ResponseUtil.write(response, result);
        // return null;
    }

    /**
     * 添加交往记录
     * 
     * @param contact
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save(Contact contact, HttpServletResponse response)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<>();
        int resultTotal = 0; // 操作的记录条数
        if (contact.getId() == null) {
            resultTotal = contactService.addContact(contact);
        } else {
            resultTotal = contactService.updateContact(contact);
        }
        if (resultTotal > 0) { // 执行成功
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        System.out.println(mapper.writeValueAsString(result));
        return mapper.writeValueAsString(result);

        // 2016-6-21 json改成Jackson
        // int resultTotal=0; // 操作的记录条数
        // if(contact.getId()==null){
        // resultTotal=contactService.addContact(contact);
        // }else{
        // resultTotal=contactService.updateContact(contact);
        // }
        // JSONObject result=new JSONObject();
        // if(resultTotal>0){ // 执行成功
        // result.put("success", true);
        // }else{
        // result.put("success", false);
        // }
        // ResponseUtil.write(response, result);
        // return null;
    }

    /**
     * 删除交往记录
     * 
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value = "id") String id,
            HttpServletResponse response) throws Exception {
        contactService.deleteContact(Integer.parseInt(id));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", true);
        return mapper.writeValueAsString(result);

        // 2016-6-21 json改成Jackson
        // JSONObject result=new JSONObject();
        // result.put("success", true);
        // ResponseUtil.write(response, result);
        // return null;
    }
}
