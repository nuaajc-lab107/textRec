import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class painterr extends Frame {

    JButton skip = new JButton("跳过");
    JButton ok = new JButton("确定");
    int i = 0;
    String filepath = "D:\\test\\test.xls";
    boolean isExist = true;

    public painterr(String impath, int[] arr) {

        JFrame jferr = new JFrame();

        Container container = jferr.getContentPane();
        //jferr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel num = new JLabel("企业编号");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        //c.weighty = 1;
        jp.add(num, c);

        JTextField numtx = new JTextField(20);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        //c.weighty = 1;
        jp.add(numtx, c);

        JLabel name = new JLabel("企业名称");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 0;
        //c.weighty = 1;
        jp.add(name, c);

        JTextField nametx = new JTextField(20);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 4;
        c.gridy = 0;
        //c.weighty = 1;
        jp.add(nametx, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        jp.add(ok, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        jp.add(skip, c);

        jp.setPreferredSize(new Dimension(500, 100));

        container.add(jp, BorderLayout.SOUTH);

        JPanel JP = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponents(g);

                try {
                    Image image = ImageIO.read(new File(impath + arr[0] + ".png"));
                    g.drawImage(image, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        container.add(JP, BorderLayout.CENTER);


        jferr.setSize(900, 900);
        jferr.show();

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numtx.getText().equals(""))
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业编号", "warning", JOptionPane.WARNING_MESSAGE);
                else if (nametx.getText().equals(""))
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业名称", "warning", JOptionPane.WARNING_MESSAGE);
                else {
                    String numlast = numtx.getText();
                    String namelast = nametx.getText();
                    try {
                        FileInputStream fileInput = new FileInputStream(filepath);
                        HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
                        fileInput.close();

                        HSSFSheet sheet = workbook.getSheetAt(0);

                        FileOutputStream out = new FileOutputStream(filepath);
                        HSSFRow row = sheet.getRow(arr[i]);
                        HSSFCell NUM = row.createCell(0);
                        NUM.setCellValue(numlast);
                        HSSFCell NAME = row.createCell(1);
                        NAME.setCellValue(namelast);
                        workbook.write(out);
                        out.close();

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(numtx.getText());
                    System.out.println(nametx.getText());

                    JP.setVisible(false);

                    if(isExist) {
                        i++;
                    }
                    JPanel imagePanel = new JPanel() {
                        public void paintComponent(Graphics g) {
                            super.paintComponents(g);

                            try {
                                if (arr[i] != 0) {
                                    Image image = ImageIO.read(new File(impath + arr[i] + ".png"));
                                    g.drawImage(image, 0, 0, null);
                                } else if (i > arr.length) {
                                    JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                                    Image image = ImageIO.read(new File("img/none.jpg"));
                                    g.drawImage(image, 0, 0, null);
                                    isExist = false;
                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                                    Image image = ImageIO.read(new File("img/none.jpg"));
                                    g.drawImage(image, 0, 0, null);
                                    isExist = false;
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    container.add(imagePanel, BorderLayout.CENTER);
                    System.out.println(i);
                    new PaintThread().start();
                }
            }

            class PaintThread extends Thread {
                @Override
                public void run() {
                    numtx.setText("");
                    nametx.setText("");
                    jferr.repaint();
                    //System.out.println("ok");
                }
            }
        });

        skip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JP.setVisible(false);


                JPanel imagePanel = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponents(g);

                        try {
                            if (arr[i] != 0) {
                                Image image = ImageIO.read(new File(impath + arr[i] + ".png"));
                                g.drawImage(image, 0, 0, null);
                            } else {
                                JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                                    Image image = ImageIO.read(new File("img/none.jpg"));
                                    g.drawImage(image, 0, 0, null);
                                isExist = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (isExist) {
                    i++;
                }

                System.out.println(i);
                container.add(imagePanel, BorderLayout.CENTER);
                System.out.println(i);
                new PaintThread().start();
            }

            class PaintThread extends Thread {
                @Override
                public void run() {
                    jferr.repaint();
                }
            }
        });
    }


    private BufferedImage mat2BI(Mat mat) {
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