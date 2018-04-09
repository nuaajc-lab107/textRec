import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sourceforge.tess4j.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class test{

    public static void main(String[] args){

        File imageFile = new File("D:\\test\\1s.jpg");
        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        String filepath = "D:\\test\\test.xls";

        try {
            String result = instance.doOCR(imageFile);
            System.out.print(result);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("test");
            FileOutputStream out =new FileOutputStream(filepath);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(result);
            workbook.write(out);
            out.close();
        }catch (TesseractException e){
            System.err.print(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
