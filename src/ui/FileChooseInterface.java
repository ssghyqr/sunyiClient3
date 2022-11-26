package ui;

import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FileChooseInterface {
    final int WIDTH = 768;
    final int HEIGHT = 370;
    int picWIDTH = 0;
    int picHEIGHT = 0;
    public void setPicWIDTH(int picWIDTH) {
        this.picWIDTH = picWIDTH;
    }
    public void setPicHEIGHT(int picHEIGHT) {
        this.picHEIGHT = picHEIGHT;
    }

    //创建窗口对象
    JFrame jf = new JFrame("测试JFileChooser");
    //创建菜单条
    JMenuBar jmb = new JMenuBar();
    //创建菜单
    JMenu jMenu = new JMenu("文件");
    JMenuItem open = new JMenuItem(new AbstractAction("打开") {
        @Override
        public void actionPerformed(ActionEvent e) {
            //显示一个文件选择器
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.showOpenDialog(jf);
            //获取用户选择的文件
            File file = fileChooser.getSelectedFile();
            setImageUrl(file.getAbsolutePath());
            //进行显示
            try {
                image = ImageIO.read(file);
                drawArea.repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    });

    JMenuItem save = new JMenuItem(new AbstractAction("另存为") {
        @Override
        public void actionPerformed(ActionEvent e) {
            //显示一个文件选择器
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.showSaveDialog(jf);

            //获取用户选择的保存文件路径
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write((RenderedImage) image,"jpeg",file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    });
    Image image;
    String imageUrl = null;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setWidthAndHeight() {
        ImageIcon ico = new ImageIcon(getImageUrl());
        System.out.println(picHEIGHT + "" + picWIDTH);
        picWIDTH = ico.getIconWidth();
        picHEIGHT = ico.getIconHeight();
    }

    //swing提供的组件，都使用了图像缓冲区技术
    private class MyCanvas extends JPanel{
        @Override
        public void paint(Graphics g) {
            if (getImageUrl() != null) {
//                System.out.println("chance");
                ImageIcon ico = new ImageIcon(getImageUrl());
//                ico.setImage(ico.getImage().getScaledInstance(ico.getIconWidth(), ico.getIconWidth(), Image.SCALE_DEFAULT));
                ico.setImage(ico.getImage().getScaledInstance(768, 576, Image.SCALE_DEFAULT));
                Image image1 = ico.getImage();
                g.drawImage(image1,0,0,null);
            } else {
                //把图片绘制到组件上即可
                g.drawImage(image,0,0,null);
            }
        }
    }
    MyCanvas drawArea = new MyCanvas();

    public void init(){
        //        登陆界面总大小
        jf.setBounds((ScreenUtils.getScreenWidth() / 2) - WIDTH / 2, (ScreenUtils.getScreenHeight() / 2) - HEIGHT / 2,
                WIDTH, HEIGHT);
        //        不可修改大小
        jf.setResizable(false);
        //组装视图
//        jMenu.add(open);
        jMenu.add(save);
        jmb.add(jMenu);
        jf.setJMenuBar(jmb);
        //图片显示区域
        drawArea.setPreferredSize(new Dimension(768,576));
//        drawArea.setPreferredSize(new Dimension(picWIDTH, picHEIGHT));
        jf.add(drawArea);
//        jf.add(new JScrollPane(drawArea));
        //显示jf
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

}
