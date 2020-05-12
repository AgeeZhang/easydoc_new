package controllers;


import java.util.List;

import com.google.gson.Gson;

import models.Result;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	@Before
	static void checkAccess() {
		if (!session.contains("username")) {
			Secure.login();
		}
	}
	
	protected static User connect() {
		return User.findById(Long.parseLong(session.get("userid")));
	}
	
    public static void index() {
        render();
    }
    
    public static void iframe_blank() {
		render();
	}
    
    protected static String getJSON(String jsonString) {
		return new Gson().toJson(jsonString);
	}
    
    public static void result(String title,String message,String url,boolean flag) {
    	set_default_result(title, message, url,flag);
		render("@result");
	}
    
    protected static void set_default_result(String title,String message,String url,boolean flag) {
		Result result = new Result(title,message);
		if(flag){
			result.addLink("继续添加", url);
		}
		result.addLink("关闭窗口","javascript:close_dialog_realod();");
		renderArgs.put("result", result);
	}
    
    protected static void set_tab(int tab){
		if(renderArgs.get("tab_index")==null) renderArgs.put("tab_index", tab);
	}

}