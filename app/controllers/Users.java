package controllers;


import java.io.File;
import java.util.List;

import models.Depart;
import models.User;
import utils.MD5;
import utils.PagedList;
import utils.SelectTree;
import utils.SelectTreeUtils;

public class Users extends Application{

	
	public static void index() {
		render();
	}
	
	public static void left(){
		List<Depart> list = Depart.find("1=1 order by sort ").fetch();
		render(list);
	}
	
	public static void list(String orderBy,String order) {
		PagedList<User> pagedList = new PagedList<User>();
		if(params.get("pid")==null){
			User.findPage(pagedList,params.get("search"), params.get("field"), orderBy, order, params.get("condition"),null);
		}else{
			String where = "depart.id = " + params.get("pid");
			User.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),where);
		}
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		User object = new User();
		render(object);
	}
	
	public static void create() {
		User object = new User();
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			render("@blank",object);
		}
		object.password=MD5.hash(object.password);
		object.login=0;
		object.save();
		result("保存员工成功","保存员工单成功!","/users/blank",true);
	}
	
	public static void show(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void detail(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void save(Long id) {
//		object.password=MD5.hash(object.password);
		User object = User.findById(id);
		validation.valid(object.edit("object", params.all()));
		if(params.get("object.password2")!=null&&!params.get("object.password2").trim().equals("")&&!object.password.equals(MD5.hash(params.get("object.password2")))){
			object.password=MD5.hash(params.get("object.password2"));
		}
		if(validation.hasErrors()){
			render("@show",object);
		}
		object.save();
		result("保存员工成功","保存员工成功!","/users/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				User user= User.findById(_id);
				if(user!=null) user.delete();
			}
		}else if(id!=null){
			User user= User.findById(id);
			if(user==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			user.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}
	
	public static void select() {
		List<Depart> list = Depart.find("flag = 1 order by sort").fetch();
		List<SelectTree> departs = SelectTreeUtils.setTree(list);
		render(departs);
	}
}
