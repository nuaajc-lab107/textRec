import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sourceforge.tess4j.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;

public class test{

    static String imageFile ="C:\\Users\\Aye10032\\Downloads\\1_20180208150251_x4hzz\\16";

    public static void main(String[] args){

        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        String filepath = "D:\\test\\test.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("test");
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        HSSFRow osisjdjs = sheet.createRow(0);
        HSSFCell numtop = osisjdjs.createCell(0);
        numtop.setCellValue("企业注册号");
        HSSFCell nametop = osisjdjs.createCell(1);
        nametop.setCellValue("企业名称");


        try {

            firstDo();

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat src = Imgcodecs.imread(imageFile+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

            if (src.empty()){
                throw new Exception("no file");
            }

            Mat dst = new Mat();
            Imgproc.threshold(src,dst,110.0,225.0,Imgproc.THRESH_BINARY);
            Imgcodecs.imwrite("D:\\test\\result.jpg",dst);

            Rectangle rect = new Rectangle(600,80);
            String result = instance.doOCR(mat2BI(dst),rect);

            String str1 = result.replace(" 二 ",":");
            String str2 = str1.replace('…','5');

            System.out.print(str2);

            String num,name;

            if (str2.contains(":")) {
                int stnum = str2.indexOf(':');
                int ennum = str2.indexOf('\n');
                int stname = str2.indexOf(':', ennum);

                num = str2.substring(stnum + 1, ennum);
                name = str2.substring(stname + 1);

            /*
            System.out.println(stnum);
            System.out.println(ennum);
            System.out.print(stname);
            */

                System.out.println(num);
                System.out.println(name);
            }else {
                num = "error! picture wrong";
                name = "error! picture wrong";
            }

            FileOutputStream out =new FileOutputStream(filepath);
            HSSFRow row = sheet.createRow(1);
            HSSFCell NUM = row.createCell(0);
            NUM.setCellValue(num);
            HSSFCell NAME = row.createCell(1);
            NAME.setCellValue(name);
            workbook.write(out);
            out.close();

        }catch (TesseractException e){
            System.err.print(e.getMessage());
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */ catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static BufferedImage mat2BI(Mat mat){
        int dataSize = mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0,0,data);
        int type = mat.channels()==1?
                BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;

        if(type==BufferedImage.TYPE_3BYTE_BGR){
            for(int i=0; i<dataSize; i+=3){
                byte blue = data[i+0];
                data[i+0] = data[i+2];
                data[i+2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(),mat.rows(),type);
        image.getRaster().setDataElements(0,0,mat.cols(),mat.rows(),data);

        return image;
    }

    public static void firstDo(){

        BufferedImage bufferedImage;

        try {

            bufferedImage = ImageIO.read(new File(imageFile+".png"));

            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            ImageIO.write(newBufferedImage, "jpg", new File(imageFile+".jpg"));

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


}

