import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sourceforge.tess4j.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;

public class test{


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

        for (int i=1; i<50; i++) {

            String imageFile = "C:\\Users\\Aye10032\\Downloads\\1_20180208150251_x4hzz\\" + i;

            try {
                BufferedImage bufferedImage;

                try {

                    bufferedImage = ImageIO.read(new File(imageFile + ".png"));

                    BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

                    newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

                    ImageIO.write(newBufferedImage, "jpg", new File(imageFile + ".jpg"));


                } catch (IOException e) {

                    e.printStackTrace();

                }

                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                Mat src = Imgcodecs.imread(imageFile + ".jpg",0);

                if (src.empty()) {
                    throw new Exception("no file");
                }

                Mat dst = new Mat();
                Imgproc.threshold(src, dst, 110.0, 265.0, Imgproc.THRESH_BINARY);

                /*Mat dst = new Mat();
                float width=src.width();
                float height=src.height();
                Imgproc.resize(dst1,dst,new Size(width/2,height/2));*/

                System.out.println("Done");

                /*Mat dst1 = new Mat();
                Imgproc.threshold(src, dst1, 228.0, 256.0, Imgproc.THRESH_BINARY_INV);
                Mat dst2 = new Mat();
                Imgproc.threshold(src, dst2, 7, 256.0, Imgproc.THRESH_BINARY_INV);
                Mat dstn = new Mat();
                Core.absdiff(dst1,dst2,dstn);
                Mat dst = new Mat();
                Core.bitwise_not(dstn,dst);

                Imgcodecs.imwrite("D:\\test\\"+i+".jpg",dst);*/

                Rectangle rect = new Rectangle(0,40,600, 40);
                String result = instance.doOCR(mat2BI(dst), rect);

                String str = result.replace(" 二 ", ":");
                String stro = str.replace("二 ",":");

                System.out.print(stro);

                String num, name, numlast, namelast;

                if (stro.contains(":")) {
                    int stnum = stro.indexOf(':');
                    int ennum = stro.indexOf('\n');
                    int stname = stro.indexOf(':', ennum);

                    num = stro.substring(stnum + 1, ennum);
                    name = stro.substring(stname + 1);

                    numlast = fix.fixnum(num);
                    namelast = fix.fixname(name);

            /*
            System.out.println(stnum);
            System.out.println(ennum);
            System.out.print(stname);
            */

                    System.out.println(numlast);
                    System.out.println(namelast);
                } else {
                    numlast = "error! picture wrong";
                    namelast = "error! picture wrong";
                }

                FileOutputStream out = new FileOutputStream(filepath);
                HSSFRow row = sheet.createRow(i);
                HSSFCell NUM = row.createCell(0);
                NUM.setCellValue(numlast);
                HSSFCell NAME = row.createCell(1);
                NAME.setCellValue(namelast);
                workbook.write(out);
                out.close();

            } catch (TesseractException e) {
                System.err.print(e.getMessage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
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


}



