package models;

import java.util.*;

import javax.persistence.*;
import play.data.validation.*;

import play.db.jpa.Model;

import utils.ModelUtils;
import utils.PagedList;
import validation.*;

@Entity
public class User extends BaseModel{
	@Required @MaxSize(16) @MinSize(4) public String username;	//用户名
	@Required public String password;	//密码
	@Required public String truename;	//真实姓名
	public String sex;	//性别
	@Email public String email;	//电子邮件
	@Telephone public String tel;	//联系电话
	@Mobile public String mobile;	//移动电话
	@Figure public String cornet;	//短号
	public Integer login;	//登入次数
	public Integer status;	//状态
	@ManyToOne public Depart depart;	//部门
	public String post;	//职位
	@ManyToOne public Role role;	//权限
	public String remark;	//备注

	
	public static void findPage(PagedList<User> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(User.class.getName(), "['username','password','truename','sex','email','tel','mobile','cornet','status','depart','post','role','remark']", search, searchField, condition,where);
		List<User> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), User.class.getName(), "['username','password','truename','sex','email','tel','mobile','cornet','status','depart','post','role','remark']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
	public static List<User> findByDepart(long id) {
		return find("depart.id=? and status = 1",id).fetch();
	}

	@Override
	public String toString() {
		return truename;
	}
}