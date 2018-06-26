package util;

public class config {

    static String inputPath = "";
    static String exopPath = "C:\\Users\\Aye10032\\Documents\\textRec";
    static String imopPath = "C:\\Users\\Aye10032\\Documents\\textRec\\err";

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
}
