import util.LayoutUtil;
import util.Num;
import util.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class showResult extends JFrame implements ActionListener {

    JButton exJB = new JButton("打开表格");
    JButton fiJB = new JButton("打开文件夹");
    JButton imJB = new JButton("未识别图片");
    JButton reJB = new JButton("重新开始");

    Num nu;
    String imageFile;

    public showResult(Num nu, String imageFile) {

        this.nu = nu;
        this.imageFile = imageFile;
        int x = 0;
        for (int i = 0; i < nu.exp.length; i++) {
            if (nu.exp[i] != 0)
                x = i;
        }

        JFrame jf = new JFrame();
        Container container = jf.getContentPane();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEtchedBorder());
        p.setLayout(new GridBagLayout());

        LayoutUtil.add(p,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,0,1,1,new JLabel("本次共识别图片 50 张"));
        LayoutUtil.add(p,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,1,1,1,new JLabel("其中不确定 "+ x +" 张"));
        LayoutUtil.add(p,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,2,1,1,new JLabel("无法识别 1 张"));

        container.add(p, BorderLayout.CENTER);

        JPanel pd = new JPanel();
        pd.setBorder(BorderFactory.createLoweredBevelBorder());
        pd.setLayout(new GridBagLayout());

        LayoutUtil.add(pd,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,0,1,1,imJB);
        LayoutUtil.add(pd,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,1,0,1,1,exJB);
        LayoutUtil.add(pd,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,1,1,1,fiJB);
        LayoutUtil.add(pd,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,1,1,1,1,reJB);

        container.add(pd,BorderLayout.SOUTH);

        jf.setTitle("文字提取1.0 - 结果");
        jf.setBounds(500, 300, 700, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.show();

        imJB.addActionListener(this);
        exJB.addActionListener(this);
        fiJB.addActionListener(this);
        reJB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == imJB){
            if (nu.exp[0] != 0) {
                new painterr(imageFile + "\\", nu.exp);
            }
            else{
                JOptionPane.showMessageDialog(this,"本次识别很完美，\n没有存疑图片");
            }
        }else if (source == exJB){
            try {
                Desktop.getDesktop().open(new File(config.getExopPath() + "\\fin.xls"));
            } catch (IOException o) {
                o.printStackTrace();
            }
        }else if (source == fiJB){
            try {
                Desktop.getDesktop().open(new File(config.getExopPath()));
            } catch (IOException o) {
                o.printStackTrace();
            }
        }else if (source == reJB){
            int y = JOptionPane.showConfirmDialog(null, "您将关闭当前任务重新开始\n确定重新开始吗？", "", JOptionPane.YES_NO_OPTION);
            if (y == 0){
                this.hide();
                chooseFile window1 = new chooseFile();
                window1.setTitle("文字提取1.0");
                window1.setBounds(500, 300, 700, 500);
                window1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window1.show();
            }
        }
    }
}
