package util;

import java.awt.*;

public class LayoutUtil {

    public static void add(Container container, int fill, int anchor, int weightx, int weighty, int x, int y, int width, int heigth, Component component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;//填充方式
        constraints.anchor = anchor;//组件比网格小时在网格中的填塞方式
        constraints.weightx = weightx;//与fill字段配合使用
        constraints.weighty = weighty;
        constraints.gridx = x;//起始的网格坐标
        constraints.gridy = y;
        constraints.gridwidth = width;//所占的格数
        constraints.gridheight = heigth;
        container.add(component, constraints);
    }

    public static void add(Container container, int fill, int anchor, int weightx, int weighty, int x, int y, int width, int heigth, Component component, Insets insets) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = insets;//外部边界
        constraints.fill = fill;
        constraints.anchor = anchor;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = heigth;
        container.add(component, constraints);
    }

}
