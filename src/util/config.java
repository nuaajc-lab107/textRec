package util;

public class config {

    static String inputPath = "";
    static String exopPath = "D:\\textRec";
    static String imopPath = "D:\\textRec\\err";
    static String tempPath = "D:\\textRec\\temp";
    static int flag = 1;

    public static String getInputPath() {
        return inputPath;
    }

    public static void setInputPath(String src) {
        inputPath = src;
    }

    public static String getExopPath() {
        return exopPath;
    }

    public static void setExopPath(String exop) {
        exopPath = exop;
    }

    public static String getImopPath() {
        return imopPath;
    }

    public static void setImopPath(String imoppath) {
        inputPath = imoppath;
    }

    public static String tempPath(){
        return tempPath;
    }

    public static int getFlag(){
        return flag;
    }

    public static void setFlag(int f){
        flag = f;
    }
}
