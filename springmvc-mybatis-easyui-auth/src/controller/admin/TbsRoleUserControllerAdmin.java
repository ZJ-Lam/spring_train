
package controller.admin;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bean.TbsRoleUserBean;
import bean.model.TbsRoleUserModel;
import service.TbsRoleUserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import util.spring.MyTimestampPropertyEdit;
import util.core.MethodUtil;
import util.json.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/admin/TbsRoleUser")
public class TbsRoleUserControllerAdmin extends BaseController{	private final static Logger log= Logger.getLogger(TbsRoleUserControllerAdmin.class);
	private static  MethodUtil util = new MethodUtil();
	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private TbsRoleUserService<TbsRoleUserBean> tbsRoleUserService; 
	
	//参数类
	private List<TbsRoleUserBean> listTbsRoleUser = null;
	private TbsRoleUserBean tbsRoleUserBean = new TbsRoleUserBean();  //JavaBean
	//private TbsRoleUserModel tbsRoleUserModel=new TbsRoleUserModel(); //分页模型
	
		@InitBinder//必须有一个参数WebDataBinder 日期类型装换
	public void initBinder(WebDataBinder binder) {
		    binder.registerCustomEditor(Timestamp.class,new MyTimestampPropertyEdit()); //使用自定义属性编辑器
		//  binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}	/**
	 * 
	 * <br>
	 * <b>功能：</b>主页<br>
	 * <b>作者：</b>肖财高<br>
	 * <b>日期：</b> 2013-4-25 <br>
	 * @return
	 */
	@RequestMapping("/index.html")
	public ModelAndView list(/*TbsRoleUserModel tbsRoleUserModel*/){
		return new ModelAndView("admin/TbsRoleUser/TbsRoleUserIndex");
	}	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>查询方法<br>
	 * <b>作者：</b>肖财高<br>
	 * <b>日期：</b> 2013-5-12 <br>
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param tbsMenuChildModel
	 * @param searchAnds
	 * @param searchColumnNames
	 * @param searchConditions
	 * @param searchVals
	 * @return
	 */
	@RequestMapping("/listData.html")
	@ResponseBody
	public String listData(String page,String rows,String sort,String order,TbsRoleUserModel tbsRoleUserModel,String searchAnds,String searchColumnNames,String searchConditions,String searchVals){
		String result = "[]";
		System.out.println("page:"+page+"|rows:"+rows+"|sort:"+sort+"|order:"+order+"TbsRoleUserModel:"+tbsRoleUserModel+"|searchAnds:"+searchAnds+"|searchColumnNames:"+searchColumnNames);
		if(null!=searchColumnNames && searchColumnNames.trim().length()>0){
			StringBuffer sb=new StringBuffer();
			String[] searchColumnNameArray=searchColumnNames.split("\\,");
			String[] searchAndsArray=searchAnds.split("\\,");
			String[] searchConditionsArray=searchConditions.split("\\,");
			String[] searchValsArray=searchVals.split("\\,");
			System.out.println(Arrays.toString(searchColumnNameArray));
			for (int i = 0; i < searchColumnNameArray.length; i++) {
				if (searchColumnNameArray[i].trim().length() > 0 && searchConditionsArray[i].trim().length()>0) {
					if (i == 0) {
						sb.append(searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + searchValsArray[i].trim());
					} else {
						sb.append(" " + searchAndsArray[i].trim() + " " + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + searchValsArray[i].trim());
					}
				}
			}
			if(sb.length()>0){
				System.out.println("sb:"+sb.toString());
				Map<String,Object> map=new HashMap<String, Object>();
				util.core.PageUtil pageUtil=new util.core.PageUtil();
				pageUtil.setAndCondition(sb.toString());
				pageUtil.setPageId(Integer.parseInt(page));
				pageUtil.setPageSize(Integer.parseInt(rows));
				map.put("pageUtil", pageUtil);
				try {
					listTbsRoleUser = tbsRoleUserService.selectByMapPaging(map);
					result="{\"total\":\""+pageUtil.getRowCount()+"\",\"rows\":"+com.alibaba.fastjson.JSON.toJSONString(listTbsRoleUser)+"}";
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		}
		try{
			tbsRoleUserModel.getPageUtil().setPageId(Integer.parseInt(page)); //当前页
			tbsRoleUserModel.getPageUtil().setPageSize(Integer.parseInt(rows));//显示X条
			//tbsRoleUserModel.getPageUtil().setOrderField(sort);      //排序字段名称
			//tbsRoleUserModel.getPageUtil().setOrderDirection(true);  //true false 表示 正序与倒序
			listTbsRoleUser = tbsRoleUserService.selectByModel(tbsRoleUserModel);
			result="{\"total\":\""+tbsRoleUserModel.getPageUtil().getRowCount()+"\",\"rows\":"+com.alibaba.fastjson.JSON.toJSONString(listTbsRoleUser)+"}";
		}catch(Exception e){
			log.error(e);
		}
		return result;
	}
    /**
     * 
     * <br>
     * <b>功能：</b>转向<br>
     * <b>作者：</b>wolf<br>
     * <b>日期：</b> 2013-1-22 <br>
     * @param ids
     * @param map
     * @return
     */
	@RequestMapping("/edit.html") 
	public String edit(){
		return "admin/TbsRoleUser/TbsRoleUserEdit";
	}
	    /**
     * 
     * <br>
     * <b>功能：</b>增加 TbsRoleUserBean<br>
     * <b>作者：</b>wolf<br>
     * <b>日期：</b> 2012-10-11 <br>
     * @return
     */
	@RequestMapping("/add.html")
	public void add(TbsRoleUserBean tbsRoleUserBean,HttpServletResponse response){
		tbsRoleUserBean.setId(util.getUid().toString());//设置主键
		System.out.println("tbsRoleUserBean:"+tbsRoleUserBean.toString());
	    try {
			if(tbsRoleUserService.insert(tbsRoleUserBean)>0){
				util.toJsonMsg(response, 0, null);
				return;
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	    util.toJsonMsg(response, 1, null);
	}    /**
     * 
     * <br>
     * <b>功能：</b>保存 TbsRoleUserBean<br>
     * <b>作者：</b>wolf<br>
     * <b>日期：</b> 2012-10-11 <br>
     * @return
     */
	@RequestMapping("/save.html") 
	public void save(TbsRoleUserBean tbsRoleUserBean,HttpServletResponse response){
		try{
		 	if(tbsRoleUserService.updateByPrimaryKey(tbsRoleUserBean)>0){
			    util.toJsonMsg(response, 0, null);
			    return;
			 }
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			e.printStackTrace();
		}
	}
	    /**
     * 
     * <br>
     * <b>功能：</b>删除 TbsRoleUserBean<br>
     * <b>作者：</b>肖财高<br>
     * <b>日期：</b> 2013-4-25 <br>
     * @param ids
     * @param response
     */
	@RequestMapping("/del.html") 
	public void del(String[] ids,HttpServletResponse response){
		System.out.println("del-ids:"+Arrays.toString(ids));
		try{
			 if(null!=ids && ids.length>0){
				 if(tbsRoleUserService.deleteByPrimaryKeys(ids)>0){
					 util.toJsonMsg(response, 0, null);
					 return;
				 }
			 }
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}
	}

