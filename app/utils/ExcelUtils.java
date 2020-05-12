package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import play.Logger;
import play.Play;
import results.RenderExcel;
import validation.Utils;

public class ExcelUtils {

    public static void renderExcel(File file){
    	throw new RenderExcel(file);
    }
    
    public static void renderExcel(File file,String name){
    	throw new RenderExcel(file,name);
    }
    
	public File writeExcel(String table, String[] names, String[] columns,String sql){
		try{
			File folder = new File(Play.applicationPath + "/public/export_excel/" + Utils.datepath());
			if(!folder.exists()) folder.mkdirs();
			String write_file = folder.getAbsolutePath() + File.separator + table + "-" + System.nanoTime() + ".xls";;
			OutputStream os = new FileOutputStream(write_file);
			WritableWorkbook wb = Workbook.createWorkbook(os);
	        // 构建Excel sheet  
	        WritableSheet sheet = wb.createSheet(table, 0);
	        for(int i=0;i<names.length;i++){
	        	Label name = new Label(i, 0, names[i]);
	        	sheet.addCell(name);
	        }
	        ResultSet rs = play.db.DB.executeQuery(sql);
	        int j = 1;
			while(rs.next()){
	            Label val = null;
	            for(int i=0;i<columns.length;i++){ 
	                val = new Label(i, j, rs.getString(columns[i]));
	                sheet.addCell(val);
	            }
	            j++;
			}
			wb.write();  wb.close();  os.close();
			return new File(write_file);
		}catch (Exception e) {
			Logger.error(e,"导出excel出错;%s",table);
		}
		return null;
	}
}
