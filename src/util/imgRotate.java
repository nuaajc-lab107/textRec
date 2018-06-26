package util;

import org.opencv.core.Core;
import org.opencv.core.Point;
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

public class imgRotate {

    public static Image imro(int[] arrx, int i, int j, String impathx) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String path = impathx + arrx[i] + ".png";
        Mat src = Imgcodecs.imread(path);
        Mat dst1 = src.clone();

        Point center = new Point(src.width() / 2, src.height() / 2);
        Mat affineTrans = Imgproc.getRotationMatrix2D(center, 90.0 * j, 1.0);

        Size size = new Size();

        if (j % 2 == 0 || j == 0) {
            size = new Size(src.height(), src.width());
        } else {
            size = src.size();
        }

        Imgproc.warpAffine(src, dst1, affineTrans, size, Imgproc.INTER_NEAREST);
        System.out.println(size);
        Imgcodecs.imwrite("img/new.jpg", dst1);

        Image image = m2b.mat2BI(dst1);

        //Image image = imgResize.remin(dst1);

        return image;
    }

    public static Image imro(int j, String path) throws Exception {

        int swidth = 0;
        int sheight = 0;
        int x;
        int y;

        File file = new File(path);
        if (!file.isFile()) {
            throw new Exception();
        }

        BufferedImage bi = ImageIO.read(file);

        int degree = 90 * j;
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;
        double theta = Math.toRadians(degree);

        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = bi.getWidth();
            sheight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = bi.getWidth();
            swidth = bi.getHeight();
        } else {
            swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
            sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
        }

        x = (swidth / 2) - (bi.getWidth() / 2);
        y = (sheight / 2) - (bi.getHeight() / 2);

        BufferedImage spinImage = new BufferedImage(swidth, sheight, bi.getType());
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.white);
        gs.fillRect(0, 0, swidth, sheight);

        AffineTransform at = new AffineTransform();
        at.rotate(theta, swidth / 2, sheight / 2);
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(bi, spinImage);
        File sf = new File("img/temp1.png");
        ImageIO.write(spinImage, "png", sf);

        Image fina = imgResize.remin(900, 750, "img/temp1.png");

        return fina;

    }

}
