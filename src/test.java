import java.io.File;
import net.sourceforge.tess4j.*;

public class test{

    public static void main(String[] args){

        File imageFile = new File("D:\\test\\1s.jpg");
        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");

        try {
            String result = instance.doOCR(imageFile);
            System.out.print(result);
        }catch (TesseractException e){
            System.err.print(e.getMessage());
        }

    }

}
