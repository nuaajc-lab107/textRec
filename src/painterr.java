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
    //JTextField nameJF = new JTextField();
    //JTextField numJF = new JTextField();
    JComboBox nameJCB = new JComboBox();
    JComboBox numJCB = new JComboBox();
    JLabel nameJL = new JLabel("企业名称");
    JLabel numJL = new JLabel("企业注册号");

    Num nu;
    int i = 0;
    int j = 1;
    String filepath = config.getExopPath() + "\\fin.xls";

    Image image;

    public painterr(Num nu) throws Exception {
        this.nu = nu;
        image = imgResize.remin(900, 750, config.getInputPath() + "\\" + nu.exp[i] + ".png");
        //image = ImageIO.read(new File(config.getInputPath() + "\\" + nu.exp[i] + ".png"));


        getContentPane().add(new printImg(), BorderLayout.CENTER);

        JPanel dp = new JPanel();
        dp.setLayout(new GridBagLayout());

        nameJCB.setEditable(true);
        numJCB.setEditable(true);

        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 0, 1, 1, nameJL, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 0, 3, 1, nameJCB, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 5, 0, 1, 1, numJL, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 6, 0, 3, 1, numJCB, new Insets(10, 10, 10, 10));

        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 3, 1, 1, 1, sure, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 8, 1, 1, 1, next, new Insets(10, 10, 10, 10));
        LayoutUtil.add(dp, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 10, 1, 1, 1, shun, new Insets(10, 10, 10, 10));

        getContentPane().add(dp, BorderLayout.SOUTH);

        sure.addActionListener(this);
        next.addActionListener(this);
        shun.addActionListener(this);

        String[] resultname = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",1);
        String[] resultnum = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",0);
        for (int i = 0; i < resultname.length;i++){
            if (resultname[i] != null){
                nameJCB.addItem(resultname[i]);
            }
        }
        for (int i = 0; i < resultnum.length;i++){
            if (resultnum[i] != null){
                numJCB.addItem(resultnum[i]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == sure) {
            //System.out.println(nu.exp[i]);
            outer:
            if (nu.exp[i] != 0) {
                if (nameJCB.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业名称!", "warning", JOptionPane.WARNING_MESSAGE);
                } else if (numJCB.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "请输入企业注册号!", "warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    String numlast = numJCB.getSelectedItem().toString();
                    String namelast = nameJCB.getSelectedItem().toString();
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
                    i++;
                    nameJCB.removeAllItems();
                    numJCB.removeAllItems();
                    String[] resultname = new String[10];
                    try {
                        resultname = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",1);
                        String[] resultnum = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",0);
                        for (int i = 0; i < resultname.length;i++){
                            if (resultname[i] != null){
                                nameJCB.addItem(resultname[i]);
                            }
                        }
                        for (int i = 0; i < resultnum.length;i++){
                            if (resultnum[i] != null){
                                numJCB.addItem(resultnum[i]);
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    if (nu.exp[i] == 0){
                        JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                        try {
                            image = ImageIO.read(new File("img/none.jpg"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        repaint();
                    }else if (nu.exp[i] != 0){
                        String path = config.getInputPath() + "\\" + nu.exp[i] + ".png";

                        try {
                            image = imgResize.remin(900, 750, path);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        repaint();
                    }
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
            //System.out.println(nu.exp[i]);
            outer:
            if (nu.exp[i] != 0) {
                //System.out.println(i);
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
                i++;
                nameJCB.removeAllItems();
                numJCB.removeAllItems();
                String[] resultname = new String[10];
                try {
                    resultname = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",1);
                    String[] resultnum = lnstance.doOCR(config.getInputPath() + "\\" + nu.exp[i] + ".png",0);
                    for (int i = 0; i < resultname.length;i++)
                    {
                        if (resultname[i] != null){
                            nameJCB.addItem(resultname[i]);
                        }
                    }
                    for (int i = 0; i < resultnum.length;i++)
                    {
                        if (resultnum[i] != null){
                            numJCB.addItem(resultnum[i]);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (nu.exp[i] == 0){
                    JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                    try {
                        image = ImageIO.read(new File("img/none.jpg"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    repaint();
                }else {
                    String path = config.getInputPath() + "\\" + nu.exp[i] + ".png";
                    try {
                        image = imgResize.remin(900, 750, path);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    repaint();
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
        } else if (source == shun) {
            if (nu.exp[i + 1] != 0) {
                String path = config.getInputPath() + "\\" + nu.exp[i - 1] + ".png";
                try {
                    image = imgRotate.imro(j, path);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                repaint();
                j++;
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "已经是最后一张了", "warning", JOptionPane.WARNING_MESSAGE);
                try {
                    image = ImageIO.read(new File("img/none.jpg"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
            }
        }
    }

    class printImg extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 1, 1, null);
        }
    }
}


