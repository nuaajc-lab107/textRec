import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.imgResize;
import util.imgRotate;
import util.config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class painterr extends Frame {

    JButton skip = new JButton("跳过");
    JButton ok = new JButton("确定");
    ImageIcon sun = new ImageIcon("img/iconsun.png");
    JButton shun = new JButton(sun);
    JPanel newpanel = new JPanel();
    JPanel paneldel = new JPanel();
    int i = 0;
    int j = 0;
    String filepath = config.getExopPath() + "\\fin.xls";
    boolean isExist = true;
    boolean isShun = false;

    public painterr(String impath, int[] arr) {

        JFrame jferr = new JFrame();

        Container container = jferr.getContentPane();

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel num = new JLabel("企业编号");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        jp.add(num, c);

        JTextField numtx = new JTextField(20);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        jp.add(numtx, c);

        JLabel name = new JLabel("企业名称");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 0;
        jp.add(name, c);

        JTextField nametx = new JTextField(20);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 4;
        c.gridy = 0;
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

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        jp.add(shun, c);

        jp.setPreferredSize(new Dimension(500, 100));

        container.add(jp, BorderLayout.SOUTH);

        JPanel JP = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponents(g);

                String path = impath + arr[0] + ".png";
                Image image = null;
                try {
                    image = imgResize.remin(900, 750, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 0, 0, null);
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

                    JP.setVisible(false);
                    paneldel.setVisible(false);
                    j = 0;
                    isShun = false;

                    if (isExist) {
                        i++;
                    }
                    newpanel = new JPanel() {
                        public void paintComponent(Graphics g) {
                            super.paintComponents(g);

                            try {
                                if (!isShun) {
                                    if (arr[i] != 0) {
                                        String path = impath + arr[i] + ".png";
                                        Image image = imgResize.remin(900, 750, path);
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
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    container.add(newpanel, BorderLayout.CENTER);
                    //System.out.println(i);
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
                paneldel.setVisible(false);
                j = 0;
                isShun = false;

                try {
                    FileInputStream fileInput = new FileInputStream(filepath);
                    HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
                    fileInput.close();

                    HSSFSheet sheet = workbook.getSheetAt(0);

                    FileOutputStream out = new FileOutputStream(filepath);
                    HSSFRow row = sheet.getRow(arr[i]);
                    HSSFCell NUM = row.createCell(0);
                    NUM.setCellValue("图片无法识别或不存在");
                    HSSFCell NAME = row.createCell(1);
                    NAME.setCellValue("图片无法识别或不存在");
                    workbook.write(out);
                    out.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                newpanel = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponents(g);

                        try {
                            if (!isShun) {
                                if (arr[i] != 0) {
                                    //Image image = ImageIO.read(new File(impath + arr[i] + ".png"));
                                    String path = impath + arr[i] + ".png";
                                    Image image = imgResize.remin(900, 750, path);

                                    g.drawImage(image, 0, 0, null);
                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                                    Image image = ImageIO.read(new File("img/none.jpg"));
                                    g.drawImage(image, 0, 0, null);
                                    isExist = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (isExist) {
                    i++;
                }

                //System.out.println(i);
                container.add(newpanel, BorderLayout.CENTER);
                //System.out.println(i);
                new PaintThread().start();
            }

            class PaintThread extends Thread {
                @Override
                public void run() {
                    jferr.repaint();
                }
            }
        });

        shun.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                j++;
                JP.setVisible(false);
                newpanel.setVisible(false);
                isShun = true;

                paneldel = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponents(g);

                        try {
                            if (isShun) {
                                if (arr[i] != 0) {

                                    String path = impath + arr[i] + ".png";
                                    Image image = imgRotate.imro(j, path);
                                    g.drawImage(image, 0, 0, null);
                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                                    Image image = ImageIO.read(new File("img/none.jpg"));
                                    g.drawImage(image, 0, 0, null);
                                    isExist = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };

                container.add(paneldel, BorderLayout.CENTER);

                new newPaint().start();
            }

            class newPaint extends Thread {
                @Override
                public void run() {
                    jferr.repaint();
                }
            }

        });

    }
}