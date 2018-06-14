package util;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class m2b {

    public static BufferedImage mat2BI(Mat mat) {
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        int type = mat.channels() == 1 ?
                BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

        if (type == BufferedImage.TYPE_3BYTE_BGR) {
            for (int i = 0; i < dataSize; i += 3) {
                byte blue = data[i + 0];
                data[i + 0] = data[i + 2];
                data[i + 2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

        return image;
    }

}