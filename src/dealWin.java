import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import util.LayoutUtil;
import util.Num;
import util.config;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class dealWin extends JFrame implements ChangeListener {

    Num nu = new Num();
    JButton allBT = new JButton("取消");
    JTextArea txtlog = new JTextArea();
    JScrollPane jsp = new JScrollPane(txtlog);
    JProgressBar progressBar = new JProgressBar(0, 100);
    String imageFile = config.getInputPath() + "\\";

    public dealWin() {
        JFrame jf = new JFrame();
        Container container = jf.getContentPane();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        progressBar.addChangeListener(this);

        txtlog.setEditable(false);
        progressBar.setStringPainted(true);
        //progressBar.setSize(new Dimension(500,20));
        //progressBar.setPreferredSize(new Dimension(400, 20));
        progressBar.setBorderPainted(true);
        progressBar.setBackground(Color.pink);
        //p.add(progressBar);
        LayoutUtil.add(p, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 0, 0, 1, 1, progressBar,new Insets(60,50,0,50));
        LayoutUtil.add(p,GridBagConstraints.BOTH,GridBagConstraints.CENTER,1,1,0,1,1,1,jsp,new Insets(50,45,50,45));

        container.add(p, BorderLayout.CENTER);

        JPanel pd = new JPanel();
        pd.setBorder(BorderFactory.createLoweredBevelBorder());
        pd.setLayout(new GridBagLayout());

        LayoutUtil.add(pd, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 0, 0, 3, 1, new JLabel());
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 4, 0, 1, 1, allBT, new Insets(5, 10, 5, 10));

        container.add(pd, BorderLayout.SOUTH);

        jf.setTitle("文字提取1.0");
        jf.setBounds(500, 300, 700, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.show();

        new Thread(new numRec(nu, imageFile)).start();
        new Thread(new nameRec(nu, imageFile)).start();
        new Thread(new rep(nu,progressBar,txtlog,jsp)).start();

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (progressBar.getValue() == 100){
            if (nu.exp[0]!=0) {
                new painterr(imageFile + "\\", nu.exp);
                allBT.setText("查看结果");
            }
        }
    }
}

class dorec {

    private static int flag = 1;
    private static Object synObj = new Object();

    public void rep(Num num, JProgressBar progressBar, JTextArea txtlog, JScrollPane jsp) throws InterruptedException {
        while (true){
            synchronized (synObj) {
                while (flag != 1) {
                    synObj.wait();
                }
                Dimension d = progressBar.getSize();
                Rectangle rect = new Rectangle(0, 0, d.width,d.height);
                progressBar.setValue(num.valueb);
                progressBar.paintImmediately(rect);
                txtlog.append(num.numarr[num.i]);
                txtlog.append(num.namearr[num.i]);
                jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
                flag = 2;
                synObj.notifyAll();
            }
        }
    }

    public void numRec(Num num, String path) throws InterruptedException {
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");

        for (int i = 1; i <= 50; i++) {
            synchronized (synObj) {
                while (flag != 2) {
                    synObj.wait();
                }
                try {
                    BufferedImage bufferedImage;

                    bufferedImage = ImageIO.read(new File(path + i + ".jpg"));

                    Rectangle rect = new Rectangle(0, 0, 450, 40);
                    String result = instance.doOCR(bufferedImage, rect);

                    String numfi, numlast;

                    if (result.contains(":")) {
                        numfi = result.substring(14);

                        //System.out.println(numfi);
                        num.numarr[i] = numfi;

                    } else {
                        numlast = "error! picture wrong";

                        //System.out.println(numlast);
                        num.numarr[i] = numlast;

                        num.exp[num.j] = i;
                        num.j++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
                flag = 3;
                synObj.notifyAll();
            }
        }
    }

    public void nameRec(Num num, String path) throws InterruptedException {
        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        for (int i = 1; i <= 50; i++) {
            synchronized (synObj) {
                while (flag != 3) {
                    synObj.wait();
                }
                try {
                    BufferedImage bufferedImage;

                    bufferedImage = ImageIO.read(new File(path + i + ".jpg"));

                    Rectangle rect = new Rectangle(0, 40, 600, 40);
                    String result = instance.doOCR(bufferedImage, rect);

                    String str = result.replace(" 二 ", ":");
                    String stro = str.replace("二 ", ":");

                    //System.out.print(stro);

                    String name, namelast;

                    if (stro.contains(":")) {
                        int start = stro.indexOf(':');

                        name = stro.substring(start + 1);

                        namelast = fix.fixnum(name);
                        //System.out.println(namelast);
                        num.namearr[i] = namelast;
                    } else {
                        namelast = "error! picture wrong";

                        System.out.println(namelast);
                        num.namearr[i] = namelast;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
                num.i = i;
                num.valueb += 2;
                flag = 1;
                synObj.notifyAll();
            }
        }
    }
}

class rep implements Runnable {

    JProgressBar jProgressBar;
    JTextArea txtlog;
    JScrollPane jsp;
    Num num;

    public rep(Num nu, JProgressBar jp, JTextArea txtlog, JScrollPane jsp) {
        this.num = nu;
        this.jProgressBar = jp;
        this.txtlog = txtlog;
        this.jsp = jsp;
    }

    @Override
    public void run() {
        try {
            new dorec().rep(num,jProgressBar, txtlog,jsp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class numRec implements Runnable {

    Num num;
    String path;

    public numRec(Num nu, String imageFile) {
        this.num = nu;
        this.path = imageFile;
    }

    @Override
    public void run() {
        try {
            new dorec().numRec(num, path);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class nameRec implements Runnable {

    Num num;
    String path;

    public nameRec(Num nu, String imageFile) {
        this.num = nu;
        this.path = imageFile;
    }

    @Override
    public void run() {
        try {
            new dorec().nameRec(num, path);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ImageDeal implements Runnable {
    Num num;
    Thread t;
    String path;

    public ImageDeal(Num num, String path) {
        t = new Thread(this);
        this.num = num;
        this.path = path;
        t.start();
    }

    @Override
    public void run() {
        while (num.i <= 50) {
            synchronized (num) {
                if (num.flag) {
                    try {
                        num.wait();
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        BufferedImage bufferedImage;

                        bufferedImage = ImageIO.read(new File(path + num.i + ".png"));

                        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

                        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

                        ImageIO.write(newBufferedImage, "jpg", new File(path + num.i + "n.jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    num.flag = true;
                    num.notify();
                }
            }
        }
    }
}


class MatDeal implements Runnable {
    Num num;
    Thread t;
    String path;

    public MatDeal(Num num, String path) {
        t = new Thread(this);
        this.num = num;
        this.path = path;
        t.start();
    }

    @Override
    public void run() {
        while (num.i <= 50) {
            synchronized (num) {
                if (!num.flag) {
                    try {
                        num.wait();
                    } catch (Exception e) {
                    }
                } else {
                    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                    Mat src = Imgcodecs.imread(path + num.i + "n.jpg");
                    Mat dst = new Mat();
                    Imgproc.threshold(src, dst, 110.0, 265.0, Imgproc.THRESH_BINARY);
                    Imgcodecs.imwrite(path + num.i + ".jpg", dst);
                    num.i++;
                    num.flag = false;
                    num.notify();
                }
            }
        }
    }
}
