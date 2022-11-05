package utils;

import java.awt.*;

/**
 * 获取电脑屏幕配置
 */
public class ScreenUtils {
//    获取电脑宽度
    public static int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
//    获取电脑高度
    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}
