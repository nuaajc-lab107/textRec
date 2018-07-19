import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class painterr extends JFrame implements ActionListener {

    JButton next = new JButton("跳过");
    JButton sure = new JButton("确定");
    ImageIcon sun = new ImageIcon("img/iconsun.png");
    JButton shun = new JButton(sun);
    JTextField nameJF = new JTextField();
    JTextField numJF = new JTextField();
    JLabel nameJL = new JLabel("企业名称");
    JLabel numJL = new JLabel("企业注册号");

    Num nu;
    int i = 0;
    int j = 0;
    String filepath = config.getExopPath() + "\\fin.xls";

    Image image;

    public painterr(Num nu) throws IOException {
        this.nu = nu;
        image = ImageIO.read(new File(config.getInputPath() + "\\" + nu.exp[0] + ".png"));
        //i++;

        getContentPane().add(new printImg(), BorderLayout.CENTER);

        JPanel dp = new JPanel();
        dp.setLayout(new GridBagLayout());

        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 0, 1, 1, nameJL, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 0, 3, 1, nameJF, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 5, 0, 1, 1, numJL, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 6, 0, 3, 1, numJF, new Insets(10, 10, 10, 10));

        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 3, 1, 1, 1, sure, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 8, 1, 1, 1, next, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 10, 1, 1, 1, shun, new Insets(10, 10, 10, 10));

        getContentPane().add(dp, BorderLayout.SOUTH);

        sure.addActionListener(this);
        next.addActionListener(this);
        shun.addActionListener(this);

        nameJF.setText("");
        numJF.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == sure) {
            System.out.println(nu.exp[i]);
            if (nu.exp[i] != 0) {
                if (nameJF.getText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业名称!", "warning", JOptionPane.WARNING_MESSAGE);
                } else if (numJF.getText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业注册号!", "warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    String numlast = numJF.getText();
                    String namelast = nameJF.getText();
                    try {
                        FileInputStream fileInput = new FileInputStream(filepath);
                        HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
                        fileInput.close();

                        HSSFSheet sheet = workbook.getSheetAt(0);

                        FileOutputStream out = new FileOutputStream(filepath);
                        HSSFRow row = sheet.getRow(nu.exp[i]);
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
                    String path = config.getInputPath() + "\\" + nu.exp[i] + ".png";
                    try {
                        image = imgResize.remin(900, 750, path);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    nameJF.setText("");
                    numJF.setText("");
                    repaint();
                    i++;
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                try {
                    image = ImageIO.read(new File("img/none.jpg"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
            }
        } else if (source == next) {
            System.out.println(nu.exp[i]);
            if (nu.exp[i] != 0) {
                try {
                    FileInputStream fileInput = new FileInputStream(filepath);
                    HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
                    fileInput.close();

                    HSSFSheet sheet = workbook.getSheetAt(0);
                    HSSFCellStyle style = workbook.createCellStyle();
                    HSSFFont font = workbook.createFont();
                    font.setColor(HSSFColor.RED.index);
                    style.setFont(font);

                    FileOutputStream out = new FileOutputStream(filepath);
                    HSSFRow row = sheet.getRow(nu.exp[i]);
                    HSSFCell NUM = row.createCell(0);
                    NUM.setCellStyle(style);
                    NUM.setCellValue("图片无法识别或不存在");
                    HSSFCell NAME = row.createCell(1);
                    NAME.setCellStyle(style);
                    NAME.setCellValue("图片无法识别或不存在");
                    workbook.write(out);
                    out.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                String path = config.getInputPath() + "\\" + nu.exp[i] + ".png";
                try {
                    image = imgResize.remin(900, 750, path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                repaint();
                i++;
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                try {
                    image = ImageIO.read(new File("img/none.jpg"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
            }
        } else if (source == shun) {
            String path = config.getInputPath() + "\\" + nu.exp[i] + ".png";
            try {
                image = imgRotate.imro(j, path);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            repaint();
            j++;
        }
    }

    class printImg extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 1, 1, null);
        }
    }
}


