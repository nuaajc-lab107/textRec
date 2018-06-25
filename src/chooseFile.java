import util.LayoutUtil;
import util.Num;
import util.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chooseFile extends JFrame implements ActionListener {

    private JLabel inputJL = new JLabel("输入地址");
    private JTextField inputJF = new JTextField();
    private JButton inputJB = new JButton("...");
    private JButton cancel = new JButton("取消 ");
    private JButton next1 = new JButton("下一步");
    JFileChooser chooserInput = new JFileChooser();
    Num nu = new Num();

    public chooseFile() {
        chooserInput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 0, 0, 1, 1, inputJL, new Insets(0, 10, 0, 10));
        LayoutUtil.add(p, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 0, 5, 1, inputJF);
        LayoutUtil.add(p, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 8, 0, 1, 1, inputJB, new Insets(0, 10, 0, 10));
        getContentPane().add(p, BorderLayout.CENTER);

        JPanel pd = new JPanel();
        pd.setBorder(BorderFactory.createLoweredBevelBorder());
        pd.setLayout(new GridBagLayout());
        LayoutUtil.add(pd, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 0, 0, 2, 1, new JLabel());
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 3, 0, 1, 1, cancel, new Insets(0, 10, 0, 10));
        LayoutUtil.add(pd, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, 4, 0, 1, 1, next1, new Insets(0, 10, 0, 10));
        getContentPane().add(pd, BorderLayout.SOUTH);

        inputJB.addActionListener(this);
        next1.addActionListener(this);
        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == inputJB) {
            int result = chooserInput.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) ;
            inputJF.setText(chooserInput.getSelectedFile().getAbsolutePath());
            config.setInputPath(chooserInput.getSelectedFile().getAbsolutePath());
            System.out.println(config.getInputPath());
        } else if (source == next1) {
            String imageFile = config.getInputPath() + "\\";
            ImageDeal imageDeal = new ImageDeal(nu, imageFile);
            MatDeal matDeal = new MatDeal(nu, imageFile);

            int y = JOptionPane.showConfirmDialog(null,"您选择的目录是："+imageFile,"",JOptionPane.YES_NO_OPTION);

            try {
                imageDeal.t.join();
                matDeal.t.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (y ==0) {
                if (!matDeal.t.isAlive()) {
                    System.out.println(y);
                    this.hide();
                    new dealWin();
                }
            }else {

            }
        }
    }
}
