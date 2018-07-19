package util;

import javax.swing.*;
import java.awt.*;

public class mess extends JFrame {

    static String nametex[] = new String[4];
    static String numtex[] = new String[4];

    public mess(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        LayoutUtil.add(p,GridBagConstraints.NONE,GridBagConstraints.CENTER,0,0,0,0,1,1,new JLabel("处理中..."));
        getContentPane().add(p,BorderLayout.CENTER);
        nametex[0] = "`/乙\\\"晶】(中国)有..司";
        nametex[1] = "爱芙趣商贸（上海）有限公司";
        nametex[2] = "广州市霖泰服饰有限公司";
        nametex[3] = "淄博爵尊商贸有限公司";
        numtex[0] = "._";
        numtex[1] = "913100005500968873";
        numtex[2] = "4401042027636";
        numtex[3] = "370306200013356_1";
    }

    public static String getName(int i){
        String re = new String();
        re = nametex[i];
        return re;
    }

    public static String getNum(int i){
        String gn = new String();
        gn = numtex[i];
        return gn;
    }
}
