package validation;

import java.util.Date;

import utils.DateUtils;


public class Utils {

	public static String tree_code(String pid,Object cid) {
		if(cid == null){
			return "0".equals(pid) ? "001" : pid+"001";
		}
		String code = cid.toString();
		if("0".equals(code))return pid+"001";
		String cidfirst = code.substring(0, code.length() - 3);
		String cidend = code.substring(code.length() - 3, code.length());
		Integer icid = Integer.parseInt(cidend); 
		icid += 1;
		String cidresult = String.valueOf(icid);
		int i = 3 - cidresult.length();
		for (int j = 0; j < i; j++) {
			cidresult = "0" + cidresult;
		}
		return cidfirst + cidresult;
	}
	
	public static String makeAllWordFirstLetterUpperCase(String name) {
		String[] strs = name.split("_");
		String result = "";
		String preStr = "";
		for (int i = 0; i < strs.length; i++) {
			if (preStr.length() == 1) {
				result += strs[i];
			} else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		return result;
	}
	
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}
	
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}
	
	private static String changeFirstCharacterCase(String str,
			boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}
	
	public static String datepath(){
		return DateUtils.format(new Date(), "yyyy-MM/dd") + "/";
	}

}
