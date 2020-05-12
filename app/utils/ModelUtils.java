package utils;

import java.util.List;

import javax.persistence.Query;

import models.TreeModel;
import play.db.jpa.JPA;
import validation.Utils;

public class ModelUtils {
	
	public static List find(String className,String defaultField,String search, String searchField, String orderBy, String order,String condition){
		String q = "from "+className;
		if(search!=null && !search.equals("")){
			 String searchQuery = getSearchQuery(searchField,condition,defaultField);
			 if (!searchQuery.equals("")) {
                 q += " where (" + searchQuery + ")";
             }
		}
        if (orderBy == null && order == null) {
            orderBy = "id";
            order = "ASC";
        }
        if (orderBy == null && order != null) {
            orderBy = "id";
        }
        if (order == null || (!order.equals("ASC") && !order.equals("DESC"))) {
            order = "ASC";
        }
        q += " order by " + orderBy + " " + order;
        Query query = JPA.em().createQuery(q);
        if (search != null && !search.equals("") && q.indexOf("?1") != -1) {
        	if(condition.equals("like"))
        		query.setParameter(1, "%" + search.toLowerCase() + "%");
        	else
        		query.setParameter(1,  search.toLowerCase());
        }
        return query.getResultList();
	}
	
	
	public static Long count(String className,String defaultField,String search, String searchField, String condition,String where) {
         String q = "select count(e) from " + className + " e";
         if (search != null && !search.equals("")) {
             String searchQuery = getSearchQuery(searchField,condition,defaultField);
             if (!searchQuery.equals("")) {
                 q += " where (" + searchQuery + ")";
             }
             q += (where != null ? " and " + where : "");
         }else {
             q += (where != null ? " where " + where : "");
         }
         Query query = JPA.em().createQuery(q);
         if (search != null && !search.equals("") && q.indexOf("?1") != -1) {
             query.setParameter(1, "%" + search.toLowerCase() + "%");
         }
         return Long.decode(query.getSingleResult().toString());
     }
	 
	 public static List findPage(int firstResult,int pageLength,String className,String defaultField,String search, String searchField, String orderBy, String order,String condition,String where){
		String q = "from "+className;
		if(search!=null && !search.equals("")){
			 String searchQuery = getSearchQuery(searchField,condition,defaultField);
			 if (!searchQuery.equals("")) {
				 q += " where (" + searchQuery + ")";
	         }
			 q += (where != null ? " and " + where : "");
		}else {
            q += (where != null ? " where " + where : "");
        }
	    if (orderBy == null && order == null) {
	         orderBy = "id";
	         order = "ASC";
	    }
	    if (orderBy == null && order != null) {
	    	orderBy = "id";
	    }
	    if (order == null || (!order.equals("ASC") && !order.equals("DESC"))) {
	        order = "ASC";
	    }
	    q += " order by " + orderBy + " " + order;
	    Query query = JPA.em().createQuery(q);
	    if (search != null && !search.equals("") && q.indexOf("?1") != -1) {
	    	if(condition.equals("like"))
	    		query.setParameter(1, "%" + search.toLowerCase() + "%");
	    	else
	    		query.setParameter(1,  search.toLowerCase());
	    }
	    query.setFirstResult(firstResult);
        query.setMaxResults(pageLength);
	    return query.getResultList();
	}
	 
	public static String getSearchQuery(String searchField,String condition,String fields) {
        String q = "";
        if (searchField != null && !searchField.equals("")) {
        	if (!q.equals("")) {
                q += " or ";
            }
        	q += searchField + " " + condition + " ?1";
        }else{
        	String[] _fields = fields.substring(1,fields.length()-1).replaceAll("'", "").split(",");
        	for(String field : _fields){
        		if (!q.equals("")) {
                    q += " or ";
                }
        		q += field + " " + condition + " ?1";
        	}
        }
        return q;
    }
	
	public static String getTreeCid(Long id,String className,String pid,String oldpid,String cid){
		if(id==null){
			return getTreeCid(className,pid);
		}else{
			String q = "from "+className + " e where id = " + id;
			Query query = JPA.em().createQuery(q);
			Object object = query.getSingleResult();
			if(object!=null && object instanceof TreeModel) {
				TreeModel model = (TreeModel) object;
				if(oldpid.equals(model.pid)) return cid;
				return getTreeCid(className, pid);
			}
		}
		return "";
	}
	
	public static String getTreeCid(String className,String pid){
		String q = "select max(cid) from "+className + " e where e.pid = ?1";
		Query query = JPA.em().createQuery(q);
		query.setParameter(1, pid);
		Object result = query.getSingleResult();
		return Utils.tree_code(pid, result);
	}
}
