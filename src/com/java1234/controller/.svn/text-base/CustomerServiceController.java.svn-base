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
import com.java1234.entity.CustomerService;
import com.java1234.entity.PageBean;
import com.java1234.service.CustomerServiceService;
import com.java1234.util.StringUtil;

/**
 * 客户服务Controller类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customerService")
public class CustomerServiceController {

    @Resource
    private CustomerServiceService customerServiceService;

    // @initbinder注解的作用: 在实际操作中经常会碰到表单中的日期 字符串和Javabean中的日期类型的属性自动转换，
    // 而springMVC默认不支持这个格式的转换，所以必须要手动配置， 自定义数据类型的绑定才能实现这个功能。
    @InitBinder
    // WebDataBinder是用来绑定请求参数到指定的属性编辑器，可以继承WebBindingInitializer来实现一个全部controller共享的dataBiner
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    /**
     * 查询客户服务集合
     * 
     * @param page
     * @param rows
     * @param s_customerService
     * @param createTimefrom
     * @param createTimeto
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam(value = "page", required = true) String page,
            @RequestParam(value = "rows", required = false) String rows,
            CustomerService s_customerService, String createTimefrom,
            String createTimeto, HttpServletResponse response)
                    throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page),
                Integer.parseInt(rows));
        Map<Object, Object> map = new HashMap<>();
        map.put("customer",
                StringUtil.formatLike(s_customerService.getCustomer()));
        map.put("overview",
                StringUtil.formatLike(s_customerService.getOverview()));
        map.put("serveType", s_customerService.getServeType());
        map.put("state", s_customerService.getState());
        map.put("createTimefrom", createTimefrom);
        map.put("createTimeto", createTimeto);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("page", page);
        List<CustomerService> contactList = customerServiceService
                .findCustomerService(map1);
        Long total = customerServiceService.getTotalCustomerService(map1);
        // JSONArray
        // jsonArray=JSONArray.fromObject(customerServiceList,jsonConfig);
        // result.put("rows", jsonArray);

        // ResponseUtil.write(response, result);
        map.put("rows", contactList);
        map.put("total", total);
        System.out.println(map);
        return mapper.writeValueAsString(map);
    }

    /**
     * 保存客户服务信息
     * 
     * @param customerService
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(CustomerService customerService,
            HttpServletResponse response) throws Exception {
        int resultTotal = 0; // 操作的记录条数
        if (customerService.getId() == null) {
            resultTotal = customerServiceService
                    .addCustomerService(customerService);
        } else {
            resultTotal = customerServiceService
                    .updateCustomerService(customerService);
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> map1 = new HashMap<String, Boolean>();
        if (resultTotal > 0) { // 执行成功
            map1.put("success", true);
        } else {
            map1.put("success", false);
        }
        return mapper.writeValueAsString(map1);
    }

    /**
     * 删除客户服务
     * 
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids,
            HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            customerServiceService
                    .deleteCustomerService(Integer.parseInt(idsStr[i]));
        }
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
    
    public String findById(@RequestParam(value = "id") String id,
            HttpServletResponse response) throws Exception {
        @SuppressWarnings("unused")
        CustomerService customerService = customerServiceService
                .findById(Integer.parseInt(id));
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("rawtypes")
        Map<String, List> map2 = new HashMap<String,List>();
        Map<String,Object>map1 = new HashMap<String,Object>();
        map1.put("id", id);
        List<CustomerService> contactList = customerServiceService
                .findCustomerService(map1);
        map2.put("rows", contactList);
        return mapper.writeValueAsString(map2);
    }

}
