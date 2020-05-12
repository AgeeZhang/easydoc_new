package controllers;


import java.util.List;

import models.Menu;
import models.Role;
import play.data.validation.Valid;
import utils.PagedList;

public class Roles extends Application{

	public static void list(String orderBy,String order) {
		PagedList<Role> pagedList = new PagedList<Role>();
		Role.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),null);
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		Role object = new Role();
		render(object,menu_p,menu_c);
	}
	
	public static void create(String[] menus) {
		Role object = new Role();
		validation.valid(object.edit("object",params.all()));
		if(validation.hasErrors()){
			List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
			List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
			render("@blank",menu_p,menu_c,object);
		}
		String menu = "";
		if(menus!=null&&menus.length>0){
			for(String str : menus){
				if(menu.length()>0){
					menu = menu + ",";
				}
				menu = menu + str;
			}
		}
		object.menu = menu;
		object.save();
		result("保存权限成功","保存权限成功!","/roles/create",true);
	}
	
	public static void show(long id) {
		Role object = Role.findById(id);
		notFoundIfNull(object);
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		render(object,menu_p,menu_c);
	}
	
	public static void detail(long id) {
		Role object = Role.findById(id);
		notFoundIfNull(object);
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		render(object,menu_p,menu_c);
	}
	
	public static void save(Long id ,String[] menus) {
		Role object = Role.findById(id);
		notFoundIfNull(object);
		validation.valid(object.edit("object",params.all()));
		if(validation.hasErrors()){
			List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
			List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
			render("@blank",menu_p,menu_c,object);
		}
		String menu = "";
		if(menus!=null&&menus.length>0){
			for(String str : menus){
				if(menu.length()>0){
					menu = menu + ",";
				}
				menu = menu + str;
			}
		}
		object.menu = menu;
		object.save();
		result("保存权限成功","保存权限成功!","/roles/create",false);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Role role = Role.findById(_id);
				if(role!=null) role.delete();
			}
		}else if(id!=null){
			Role role = Role.findById(id);
			if(role==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			role.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}
}