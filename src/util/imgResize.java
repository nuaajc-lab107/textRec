package util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;

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

}
