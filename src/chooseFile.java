import util.LayoutUtil;
import util.Num;
import util.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class chooseFile extends JFrame implements ActionListener {

    private JLabel inputJL = new JLabel("输入地址");
    private JTextField inputJF = new JTextField("");
    private JButton inputJB = new JButton("...");
    private JButton cancel = new JButton("取消 ");
    private JButton next1 = new JButton("下一步");
    private JButton setJB = new JButton("高级选项");
    JFileChooser chooserInput = new JFileChooser();
    Num nu = new Num();

    public chooseFile() {
        chooserInput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEtchedBorder());
        p.setLayout(new GridBagLayout());

        LayoutUtil.add(p, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 0, 1, 0, 0, 1, 1, new JLabel());
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 1, 1, 1, inputJL, new Insets(0, 10, 0, 10));
        LayoutUtil.add(p, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 1, 5, 1, inputJF);
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 8, 1, 1, 1, inputJB, new Insets(0, 10, 0, 10));
        LayoutUtil.add(p, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 0, 2, 0, 2, 1, 1, new JLabel());
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 4, 3, 1, 1, setJB, new Insets(0, 10, 0, 10));
        LayoutUtil.add(p, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 0, 1, 0, 4, 1, 1, new JLabel());
        getContentPane().add(p, BorderLayout.CENTER);

        JPanel pd = new JPanel();
        pd.setLayout(new GridBagLayout());
        LayoutUtil.add(pd, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 0, 0, 2, 1, new JLabel());
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 3, 0, 1, 1, cancel, new Insets(5, 10, 5, 10));
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 4, 0, 1, 1, next1, new Insets(5, 10, 5, 10));
        getContentPane().add(pd, BorderLayout.SOUTH);

        inputJB.addActionListener(this);
        next1.addActionListener(this);
        cancel.addActionListener(this);
        setJB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == inputJB) {
            int result = chooserInput.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) ;
            inputJF.setText(chooserInput.getSelectedFile().getAbsolutePath());
            config.setInputPath(inputJF.getText());
        } else if (source == next1) {
            String imageFile = config.getInputPath() + "\\";
            ImageDeal imageDeal = new ImageDeal(nu, imageFile);
            MatDeal matDeal = new MatDeal(nu, imageFile);

            if (inputJF.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "请正确选择目录！", "warning", JOptionPane.YES_NO_OPTION);
            } else {
                File exop = new File(config.getExopPath());
                File imop = new File(config.getImopPath());
                File temp = new File(config.tempPath());
                if (!exop.exists()) {
                    exop.mkdirs();
                    imop.mkdirs();
                    temp.mkdirs();

                } else if (!imop.exists()) {
                    imop.mkdirs();
                    temp.mkdirs();

                } else {

                    int y = JOptionPane.showConfirmDialog(null, "您选择的目录是：" + imageFile, "", JOptionPane.YES_NO_OPTION);

                    try {
                        imageDeal.t.join();
                        matDeal.t.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    if (y == 0) {
                        if (!matDeal.t.isAlive()) {
                            this.hide();
                            new dealWin();
                        }
                    }
                }
            }
        } else if (source == cancel) {
            int y = JOptionPane.showConfirmDialog(this, "确定要退出吗？", "", JOptionPane.YES_NO_OPTION);

            if (y == 0) {
                System.exit(0);
            }
        } else if (source == setJB) {
            File exop = new File(config.getExopPath());
            File imop = new File(config.getImopPath());
            File temp = new File(config.tempPath());
            if (!exop.exists()) {
                exop.mkdirs();
                imop.mkdirs();
                temp.mkdirs();
                setWin win = new setWin();
                win.setTitle("高级设置");
                win.setBounds(500, 250, 700, 700);
                win.show();
            } else if (!imop.exists()) {
                imop.mkdirs();
                temp.mkdirs();
                setWin win = new setWin();
                win.setTitle("高级设置");
                win.setBounds(500, 250, 700, 700);
                win.show();
            } else {
                setWin win = new setWin();
                win.setTitle("高级设置");
                win.setBounds(500, 250, 700, 700);
                win.show();
            }
        }
    }
}
