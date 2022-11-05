package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * 自定义圆角高亮按钮
 */
public class RoundLightHight_Button extends JButton {
    private static final long serialVersionUID = 1L;
//    设置颜色
    public static final Color BUTTON_COLOR1 = new Color(187, 238, 255);
    public static final Color BUTTON_COLOR2 = new Color(7, 188, 252);
//    public static final Color BUTTON_BAK_COLOR1_2 = new Color(58, 128, 255,
//            255);
//    public static final Color BUTTON_BAK_COLOR2_2 = new Color(180, 230, 250,
//            255);
    public static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
//    按钮是否被鼠标经过
    private boolean hover;

    public RoundLightHight_Button(String text) {
        super(text);
        // 去除默认按钮样式
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(BUTTON_FOREGROUND_COLOR);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

//    绘制按钮组件
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int h = getHeight();
        int w = getWidth();
        float tran = 1F;
        if (!hover) {
            tran = 0.7F;
        }
        //步骤2
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint p1;
        GradientPaint p2;
        if (getModel().isPressed()) {
            p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
                    new Color(100, 100, 100));
            p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,
                    new Color(255, 255, 255, 100));
        } else {
            p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1,
                    new Color(0, 0, 0));
            p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
                    h - 3, new Color(0, 0, 0, 50));
        }
//设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                tran));
        Shape clip = g2d.getClip();
        //绘制整个按钮
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
                h - 1, h, h);
        g2d.clip(r2d);
        GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
                h, BUTTON_COLOR2, true);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        //鼠标移入就绘制立体效果
        if (hover) {
            RoundRectangle2D.Float r2d2 = new RoundRectangle2D.Float(5, 2,
                    w - 10, h / 2 - 1, h / 2, h / 2);
            g2d.clip(r2d2);
//            GradientPaint gp2 = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
//                    h / 2, BUTTON_COLOR2, true);
//            g2d.setPaint(gp2);
            g2d.fillRect(5, 2, w - 10, h / 2);
        }
        g2d.setClip(clip);
        //绘制边框
        g2d.setPaint(p1);
        g2d.drawRoundRect(0, 0, w - 1, h - 1, h, h);
        g2d.setPaint(p2);
        g2d.drawRoundRect(1, 1, w - 3, h - 3, h - 2, h - 2);
        g2d.dispose();
        super.paintComponent(g);
    }
}
