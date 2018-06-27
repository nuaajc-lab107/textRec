import util.LayoutUtil;
import util.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class setWin extends JFrame implements ActionListener {

    JFrame jfset = new JFrame();

    JLabel exopJL = new JLabel("excel文件输出目录：");
    JFileChooser exopChose = new JFileChooser();
    JTextField exopJF = new JTextField(config.getExopPath());
    JButton exopJB = new JButton("...");

    JLabel imopJL = new JLabel("误差图片输出位置:  ");
    JFileChooser imopChose = new JFileChooser();
    JTextField imopJF = new JTextField(config.getExopPath());
    JButton imopJB = new JButton("...");

    JButton cancel = new JButton("取消");
    JButton sure = new JButton("确定");

    JRadioButton tess = new JRadioButton("Tesseract-OCR");
    JRadioButton tens = new JRadioButton("TensorFlow");
    ButtonGroup whic = new ButtonGroup();

    public setWin() {

        File exop = new File(config.getExopPath());
        File imop = new File(config.getImopPath());


        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEtchedBorder());
        p.setLayout(new GridBagLayout());

        exopChose.setCurrentDirectory(exop);
        exopChose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        imopChose.setCurrentDirectory(imop);
        imopChose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //LayoutUtil.add(p,GridBagConstraints.VERTICAL,GridBagConstraints.CENTER,0,1,0,0,7,1,new JLabel());

        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 1, 1, 1, exopJL, new Insets(20, 10, 20, 10));
        LayoutUtil.add(p, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 1, 5, 1, exopJF, new Insets(20, 10, 20, 10));
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 7, 1, 1, 1, exopJB, new Insets(20, 10, 20, 10));

        //LayoutUtil.add(p,GridBagConstraints.VERTICAL,GridBagConstraints.CENTER,0,1,0,2,7,1,new JLabel());

        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 3, 1, 1, imopJL, new Insets(20, 10, 20, 10));
        LayoutUtil.add(p, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 3, 5, 1, imopJF, new Insets(20, 10, 20, 10));
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 7, 3, 1, 1, imopJB, new Insets(20, 10, 20, 10));

        LayoutUtil.add(p,GridBagConstraints.VERTICAL,GridBagConstraints.CENTER,0,1,0,4,7,1,new JLabel());

        JPanel ip1 = new JPanel();
        ip1.setLayout(new GridLayout(2,0));
        ip1.setBorder(BorderFactory.createTitledBorder("存疑图片分析选项："));

        whic.add(tess);
        ip1.add(tess);
        whic.add(tens);
        ip1.add(tens);

        if (config.getFlag() == 1){
            tess.setSelected(true);
        }else if (config.getFlag() == 2){
            tens.setSelected(true);
        }

        LayoutUtil.add(p,GridBagConstraints.BOTH,GridBagConstraints.CENTER,1,1,0,5,8,1,ip1);

        getContentPane().add(p, BorderLayout.CENTER);

        JPanel pd = new JPanel();
        pd.setLayout(new GridBagLayout());
        LayoutUtil.add(pd, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 0, 0, 2, 1, new JLabel());
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 3, 0, 1, 1, cancel, new Insets(5, 10, 5, 10));
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 4, 0, 1, 1, sure, new Insets(5, 10, 5, 10));

        getContentPane().add(pd, BorderLayout.SOUTH);

        exopJB.addActionListener(this);
        imopJB.addActionListener(this);
        sure.addActionListener(this);
        cancel.addActionListener(this);
        tess.addActionListener(this);
        tens.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exopJB) {
            int result = exopChose.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) ;
            exopJF.setText(exopChose.getSelectedFile().getAbsolutePath());
        }else if (source == imopJB){
            int result = imopChose.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) ;
            imopJF.setText(imopChose.getSelectedFile().getAbsolutePath());
        }else if (source == tess){
            config.setFlag(1);
        }else if (source == tens){
            config.setFlag(2);
        }else if (source == cancel){
            this.hide();
        }else if (source == sure){
            config.setExopPath(exopJF.getText());
            config.setExopPath(imopJF.getText());
            this.hide();
        }
    }
}
