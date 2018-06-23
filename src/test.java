import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;

public class test extends JFrame implements ActionListener {

    JButton btnOpen = new JButton("加载");
    JButton btnStart = new JButton("开始");
    JTextArea txtLog = new JTextArea();
    JFileChooser chooser = new JFileChooser();
    Num nu = new Num();
    boolean dealEnd = false;

    private void load(){

        String imageFile = chooser.getSelectedFile()+"\\";

        ImageDeal imageDeal = new ImageDeal(nu,imageFile);
        MatDeal matDeal = new MatDeal(nu,imageFile);

        try {
            System.out.println("waiting for threads to finish");
            imageDeal.t.join();
            matDeal.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!matDeal.t.isAlive()){
            dealEnd = true;
            txtLog.append("loading final\n");
        }

    }

    private void start() {
        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");
        String filepath = "D:\\test\\test.xls";
        String imageFile = chooser.getSelectedFile()+"\\";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("test");
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        HSSFRow osisjdjs = sheet.createRow(0);
        HSSFCell numtop = osisjdjs.createCell(0);
        numtop.setCellValue("企业注册号");
        HSSFCell nametop = osisjdjs.createCell(1);
        nametop.setCellValue("企业名称");

        NumRec numRec = new NumRec(nu,imageFile);
        NameRec nameRec = new NameRec(nu,imageFile);

        if (dealEnd) {
            try {
                //System.out.println("开始识别");
                numRec.t.join();
                nameRec.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i <= 50; i++){
            try {
                FileOutputStream out = new FileOutputStream(filepath);
                HSSFRow row = sheet.createRow(i);
                HSSFCell NUM = row.createCell(0);
                NUM.setCellValue(nu.numarr[i]);
                HSSFCell NAME = row.createCell(1);
                NAME.setCellValue(nu.namearr[i]);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < nu.exp.length; i++) {
            if (nu.exp[i]!=0)
                System.out.println(nu.exp[i]);//txtLog.append(nu.exp[i]+" ");
        }

        if (nu.exp[0]!=0)
            new painterr(chooser.getSelectedFile()+"\\",nu.exp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source==btnOpen){
            txtLog.append("Loading... ...\n"/*"File:"+chooser.getSelectedFile()+"is open\n"*/);
            int result = chooser.showOpenDialog(this);
            if (result==JFileChooser.APPROVE_OPTION);
            load();
        }
        if (source== btnStart){
            txtLog.append("Start");
            //txtLog.repaint();
            start();
        }
    }

    public test(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setTitle("老子又不是前端，做个屁的界面美化");
        JToolBar toolBar = new JToolBar();
        toolBar.add(btnOpen);
        toolBar.add(btnStart);
        btnOpen.addActionListener(this);
        btnStart.addActionListener(this);
        Container c = this.getContentPane();
        c.add(toolBar,BorderLayout.NORTH);
        c.add(new JScrollPane(txtLog),BorderLayout.CENTER);
        setBounds(500,400,700,500);
        chooser.setCurrentDirectory(new File("D:\\"));
    }

    public static void main(String[] args){
        try {
            String lookAndFeel ="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        new test().show();

    }
}

class Num {
    int i=1;
    boolean flag = false;

    int[] exp = new int[10];
    int j = 0;

    String[] numarr = new String[70];
    String[] namearr = new String[70];

    BufferedImage stayimg;
}

class ImageDeal implements Runnable{
    Num num ;
    Thread t;
    String path;

    public ImageDeal(Num num,String path)
    {
        t = new Thread(this);
        this.num = num;
        this.path = path;
        t.start();
    }

    @Override
    public void run()
    {
        while(num.i<= 50)
        {
            synchronized (num) {
                if(num.flag)
                {
                    try {
                        num.wait();
                    } catch (Exception e) {
                    }
                }
                else {
                    try {
                        BufferedImage bufferedImage;

                        bufferedImage = ImageIO.read(new File(path + num.i + ".png"));

                        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

                        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

                        ImageIO.write(newBufferedImage, "jpg", new File(path + num.i + "n.jpg"));

                        //num.stayimg = newBufferedImage;
                        //System.out.println(num.i+"imag done");
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


class MatDeal implements Runnable{
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
    public void run()
    {
        while(num.i<=50)
        {
            synchronized (num){
                if(!num.flag)
                {
                    try
                    {
                        num.wait();
                    } catch (Exception e)
                    {}
                }
                else {
                    /*BufferedImage img = num.stayimg;

                    int h = img.getHeight();
                    int w = img.getWidth();
                    int rgb = img.getRGB(0, 0);

                    int[][] gray = new int[w][h];
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < h; y++) {
                            gray[x][y] = dimg.getGray(img.getRGB(x, y));
                        }
                    }

                    BufferedImage nbi=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
                    int SW=140;
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < h; y++) {
                            if(dimg.getAverageColor(gray, x, y, w, h)>SW){
                                int max=new Color(255,255,255).getRGB();
                                nbi.setRGB(x, y, max);
                            }else{
                                int min=new Color(0,0,0).getRGB();
                                nbi.setRGB(x, y, min);
                            }
                        }
                    }
                    try {
                        ImageIO.write(nbi, "jpg", new File(path + num.i +".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                    Mat src = Imgcodecs.imread(path+num.i+"n.jpg");
                    Mat dst = new Mat();
                    Imgproc.threshold(src, dst, 110.0, 265.0, Imgproc.THRESH_BINARY);
                    Imgcodecs.imwrite(path + num.i +".jpg",dst);
                    //System.out.println("去水印"+num.i);
                    num.i++;
                    num.flag = false;
                    num.notify();
                }
            }
        }
    }
}

class NumRec implements Runnable{

    Num num;
    Thread t;
    String path;


    public NumRec(Num num,String path) {
        t = new Thread(this);
        this.num = num;
        this.path = path;
        t.start();
    }

    @Override
    public void run() {

        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");

        for (int i = 1; i <= 50; i++){
            try {
                BufferedImage bufferedImage;

                bufferedImage = ImageIO.read(new File(path + i + ".jpg"));

                Rectangle rect = new Rectangle(0, 0, 450, 40);
                String result = instance.doOCR(bufferedImage, rect);

                String str = result.replace(" 二 ", ":");
                String stro = str.replace("二 ", ":");

                //System.out.print(stro);

                String numfi, numlast;

                if (stro.contains(":")) {
                    int start = stro.indexOf(':');

                    numfi = stro.substring(start + 1);

                    numlast = fix.fixnum(numfi);

                    //System.out.println(numlast);
                    num.numarr[i] = numlast;


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
        }
    }
}

class NameRec implements Runnable{

    Num num;
    Thread t;
    String path;


    public NameRec(Num num,String path) {
        t = new Thread(this);
        this.num = num;
        this.path = path;
        t.start();
    }

    @Override
    public void run() {

        ITesseract instance = new Tesseract();
        instance.setLanguage("chi_sim");

        for (int i = 1; i <= 50; i++){
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

                    System.out.println(i);
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
        }
    }
}

class dimg {

    public dimg(BufferedImage img) throws IOException {
        int h = img.getHeight();
        int w = img.getWidth();
        int rgb = img.getRGB(0, 0);

        int[][] gray = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                gray[x][y] = getGray(img.getRGB(x, y));
            }
        }

        BufferedImage nbi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        int SW = 110;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (getAverageColor(gray, x, y, w, h) > SW) {
                    int max = new Color(255, 255, 255).getRGB();
                    nbi.setRGB(x, y, max);
                } else {
                    int min = new Color(0, 0, 0).getRGB();
                    nbi.setRGB(x, y, min);
                }
            }
        }
        ImageIO.write(nbi, "jpg", new File("D:\\test\\二值化后_无压缩.jpg"));
    }

    public static int getGray(int rgb) {
        String str = Integer.toHexString(rgb);
        /*int r = Integer.parseInt(str.substring(2, 4), 16);
        int g = Integer.parseInt(str.substring(4, 6), 16);
        int b = Integer.parseInt(str.substring(6, 8), 16);*/
        Color c = new Color(rgb);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int top = (r + g + b) / 3;
        return (int) (top);
    }

    public static int getAverageColor(int[][] gray, int x, int y, int w, int h) {
        int rs = gray[x][y]
                + (x == 0 ? 255 : gray[x - 1][y])
                + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
                + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])
                + (y == 0 ? 255 : gray[x][y - 1])
                + (y == h - 1 ? 255 : gray[x][y + 1])
                + (x == w - 1 ? 255 : gray[x + 1][y])
                + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
                + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
        return rs / 9;
    }

}



