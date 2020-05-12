package models;

import java.util.List;

import javax.persistence.*;
import play.db.jpa.Model;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Word extends Model{

	@ManyToOne
	public WordType wordType;	//字典类别
	@ManyToOne
	public Word word;
	public String name;			//名称
	public Integer sort;		//排序
	public Integer flag;		//状态
	public String notice;		//备注

	
	public static void findPage(PagedList<Word> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Word.class.getName(), "['name']", search, searchField, condition,where);
		List<Word> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Word.class.getName(), "['name']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public static List<Word> findWordByWordType(Long id){
		return find("wordType.id=?",id).fetch();
	}
}