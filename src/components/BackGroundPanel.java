package components;
import javax.swing.*;
import java.awt.*;
/**
 * 背景图片组件
 */
public class BackGroundPanel extends JPanel {
    //声明图片
    private Image backIcon;
    public BackGroundPanel(Image backIcon){
        this.backIcon = backIcon;
    }

    //    注意:这里不能重写paint方法,因为这里要绘制panel内部的组件
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制背景
        g.drawImage(backIcon,0,0,this.getWidth(),this.getHeight(),null);
    }
}
