package util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class imgResize {

    public static Image reimg(int[] arrx, int i, String impathx) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String path = impathx + arrx[i] + ".png";
        System.out.println(path);
        Mat src = Imgcodecs.imread(path);
        System.out.println("loadsuccess");
        Mat dst = new Mat();

        if (src.height() > src.width()) {
            if (src.height() > 900) {
                Imgproc.resize(src, dst, new Size(900, 900));
            } else {
                dst = src.clone();
            }
        } else if (src.height() < src.width()) {
            if (src.width() > 900) {
                Imgproc.resize(src, dst, new Size(900, 900));
            } else {
                dst = src.clone();
            }
        }

        BufferedImage end = m2b.mat2BI(dst);

        return end;
    }

    public static Image remin(int width, int height, String path) throws Exception{

        double sx = 0.0;
        double sy = 0.0;

        File file = new File(path);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }

        BufferedImage bi = ImageIO.read(file);

        sx = (double) width / bi.getWidth();
        sy = (double) height / bi.getHeight();

        if (bi.getWidth()>bi.getHeight()){
            sy = sx;
        }else if (bi.getHeight()>=bi.getWidth()){
            sx = sy;
        }

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
        Image zoomImage = op.filter(bi, null);

        return zoomImage;
    }

}
