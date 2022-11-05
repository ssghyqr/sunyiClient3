package components;

import javax.swing.*;
import java.awt.*;

public class RoundBorder_TextField extends JTextField {
    private static final long serialVersionUID = -1946802815417758252L;

    public RoundBorder_TextField(int columns) {
        super(columns);
//        设置输入框四周margin值
        setMargin(new Insets(0, 5, 0, 5));
    }

    @Override
    protected void paintBorder(Graphics g) {
        int h = getHeight();// 从JComponent类获取高宽
        int w = getWidth();
        Graphics2D g2d = (Graphics2D) g.create();
//        设置画笔颜色
        g2d.setPaint(Color.gray);
        Shape shape = g2d.getClip();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(shape);
//        绘画
        g2d.drawRoundRect(0, 0, w - 2, h - 2, h, h);
        g2d.dispose();
        super.paintBorder(g2d);
    }
}
