package utils;

import java.io.File;
import java.io.IOException;

public class PathUtils {
    private static final String P_PATH = "\\src\\image\\";
    private static final String PIC_PATH = "\\src\\fileGet\\";

    public static String getRealPath(String relativePath) throws IOException {
        File directory = new File("");
        String url = directory.getCanonicalPath();
        return url + P_PATH + relativePath;
    }

    public static String getPicPath(String relativePath) throws IOException {
        File directory = new File("");
        String url = directory.getCanonicalPath();
        return url + PIC_PATH + relativePath;
    }
}
