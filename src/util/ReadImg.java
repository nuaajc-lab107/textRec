package util;

import java.io.*;

public class ReadImg {

    public static int i = 0;

    public ReadImg() {
    }

    public static void writeIO(File f, int level) throws IOException {

        for (int j = 0; j < level; j++) {
        }

        if (!f.isDirectory()) {
            if (f.getName().endsWith(".PNG") || f.getName().endsWith(".png")) {
                String name = new String();
                int end = f.getName().lastIndexOf('.');
                String las = f.getName().substring(0, end);
                if (las.contains("png")) {
                    String str1 = las.replace("png", "");
                    name = str1 + ".png";
                    File output = new File(config.getInputPath() + "\\" + name);
                    InputStream is = new FileInputStream(f);
                    OutputStream os = new FileOutputStream(output);
                    byte[] flush = new byte[1024];
                    int len = 0;
                    while (-1 != (len = is.read(flush))) {
                        os.write(flush, 0, len);
                    }
                    os.flush();
                    os.close();
                    is.close();
                    f.delete();
                }
                i++;
            }
        } else if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File temp : files) {
                writeIO(temp, level + 1);
            }
        }
    }

}
