import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;

public class test{

    static String imageFile ="C:\\Users\\Aye10032\\Downloads\\1_20180208150251_x4hzz\\5";

    public static void main(String[] args){

        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        String filepath = "D:\\test\\test.xls";


        try {

            firstDo();

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat src = Imgcodecs.imread(imageFile+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

            if (src.empty()){
                throw new Exception("no file");
            }

            Mat dst = new Mat();
            Imgproc.threshold(src,dst,100.0,200.0,Imgproc.THRESH_BINARY);

            Rectangle rect = new Rectangle(600,100);
            String result = instance.doOCR(mat2BI(dst),rect);
            System.out.print(result);
/*
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("test");
            FileOutputStream out =new FileOutputStream(filepath);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(result);
            workbook.write(out);
            out.close();
            */
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

