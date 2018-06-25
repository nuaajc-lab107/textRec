
import javax.swing.*;

public class test extends JFrame {

    public static void main(String[] args) {
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        chooseFile window1 = new chooseFile();
        window1.setTitle("文字提取1.0");
        window1.setBounds(500, 300, 700, 500);
        window1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window1.show();

    }

}