package ui;

import com.alibaba.fastjson.JSONObject;
import common.Result;
import components.BackGroundPanel;
import components.RoundBorder_TextField;
import components.RoundLightHight_Button;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterInterface {
    JFrame registerJFrame = new JFrame("注册");
    final int WIDTH = 768;
    final int HEIGHT = 370;

    public void init() throws IOException {
        //        登陆界面总大小
        registerJFrame.setBounds((ScreenUtils.getScreenWidth() / 2) - WIDTH / 2, (ScreenUtils.getScreenHeight() / 2) - HEIGHT / 2,
                WIDTH, HEIGHT);
//        不可修改大小
        registerJFrame.setResizable(false);
//        设置logo图标
        registerJFrame.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.jpg"))));
//        设置背景图片
        BackGroundPanel backGroundPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("login.png"))));
        backGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //        用户名盒子
        Box userAccountBox = Box.createHorizontalBox();
        Font userAccountFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel userAccountLabel = new JLabel();
//        设置字体
        userAccountLabel.setFont(userAccountFont);
//        设置字体颜色
        userAccountLabel.setForeground(Color.white);
//        标签文本
        userAccountLabel.setText("用户账号");
//        自定义圆角边框
        RoundBorder_TextField userAccountTextField = new RoundBorder_TextField(15);
//        设置文本域透明
        userAccountTextField.setOpaque(false);
        userAccountBox.add(userAccountLabel);
        userAccountBox.add(Box.createHorizontalStrut(20));
        userAccountBox.add(userAccountTextField);

//        密码盒子
        Box passwordBox = Box.createHorizontalBox();
        Font passwordFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel passwordLabel = new JLabel();
//        设置字体
        passwordLabel.setFont(passwordFont);
//        设置字体颜色
        passwordLabel.setForeground(Color.white);
//        标签文本
        passwordLabel.setText("密码");
//        自定义圆角边框
        RoundBorder_TextField passwordField = new RoundBorder_TextField(15);
//        设置文本域透明
        passwordField.setOpaque(false);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(passwordField);

//        用户名盒子
        Box userNameBox = Box.createHorizontalBox();
        Font userNameFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel userNameLabel = new JLabel();
//        设置字体
        userNameLabel.setFont(userNameFont);
//        设置字体颜色
        userNameLabel.setForeground(Color.white);
//        标签文本
        userNameLabel.setText("用户名");
//        自定义圆角边框
        RoundBorder_TextField userNameField = new RoundBorder_TextField(15);
//        设置文本域透明
        userNameField.setOpaque(false);
        userNameBox.add(userNameLabel);
        userNameBox.add(Box.createHorizontalStrut(20));
        userNameBox.add(userNameField);

//        用户简介盒子
        Box userIntroductionBox = Box.createHorizontalBox();
        Font userIntroductionFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel userIntroductionLabel = new JLabel();
//        设置字体
        userIntroductionLabel.setFont(userIntroductionFont);
//        设置字体颜色
        userIntroductionLabel.setForeground(Color.white);
//        标签文本
        userIntroductionLabel.setText("用户简介");
//        自定义圆角边框
        RoundBorder_TextField userIntroductionField = new RoundBorder_TextField(15);
//        设置文本域透明
        userIntroductionField.setOpaque(false);
        userIntroductionBox.add(userIntroductionLabel);
        userIntroductionBox.add(Box.createHorizontalStrut(20));
        userIntroductionBox.add(userIntroductionField);

//        手机号盒子
        Box userPhoneBox = Box.createHorizontalBox();
        Font userPhoneFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel userPhoneLabel = new JLabel();
//        设置字体
        userPhoneLabel.setFont(userPhoneFont);
//        设置字体颜色
        userPhoneLabel.setForeground(Color.white);
//        标签文本
        userPhoneLabel.setText("用户手机号");
//        自定义圆角边框
        RoundBorder_TextField userPhoneField = new RoundBorder_TextField(15);
//        设置文本域透明
        userPhoneField.setOpaque(false);
        userPhoneBox.add(userPhoneLabel);
        userPhoneBox.add(Box.createHorizontalStrut(20));
        userPhoneBox.add(userPhoneField);

        //        组装按钮
        Box loginBtnBox = Box.createHorizontalBox();
        RoundLightHight_Button loginBtn = new RoundLightHight_Button("登录界面");
        RoundLightHight_Button registerBtn = new RoundLightHight_Button("注册");
//        组装按钮事件
//        注册按钮事件
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                "userAccount":"5555",
//                        "userPassword":"5555",
//                        "userName":"5555",
//                        "userIntroduction":"5555",
//                        "userPhone":"5555"
                String userAccount = userAccountTextField.getText().trim();
                String userPassword = passwordField.getText().trim();
                String userName = userNameField.getText().trim();
                String userIntroduction = userIntroductionField.getText().trim();
                String userPhone = userPhoneField.getText().trim();
                if (userAccount == null || userAccount.length() == 0) {
                    JOptionPane.showMessageDialog(registerJFrame, "用户账号为空");
                    return;
                }
                if (userPassword == null || userPassword.length() == 0) {
                    JOptionPane.showMessageDialog(registerBtn, "密码为空");
                    return;
                }
                if (userName == null || userName.length() == 0) {
                    JOptionPane.showMessageDialog(registerBtn, "密码为空");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("userAccount", userAccount);
                params.put("userPassword", userPassword);
                params.put("userName", userName);
                params.put("userIntroduction", userIntroduction);
                params.put("userPhone", userPhone);
                try {
                    HttpClientUtil.registerPost(HttpUrlUtils.getUrl("/user/register"), params, new SuccessListener() {
                        @Override
                        public void success(String resultJson) {
                            Result result = JSONObject.parseObject(resultJson, Result.class);
                            if (result.getCode().equals("400")) {
                                JOptionPane.showMessageDialog(registerJFrame, result.getMsg());
                                return;
                            } else {
                                JOptionPane.showMessageDialog(registerJFrame, "注册成功");
                            }
                        }
                    }, new FailListener() {
                        @Override
                        public void fail() {
                            JOptionPane.showMessageDialog(registerJFrame, "注册失败");
                        }
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

//        返回登陆界面
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LoginInterface().init();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                registerJFrame.dispose();
            }
        });

        loginBtnBox.add(loginBtn);
        loginBtnBox.add(Box.createHorizontalStrut(100));
        loginBtnBox.add(registerBtn);

        //        组装注册页面
        Box vBaseRegister = Box.createVerticalBox();
        vBaseRegister.add(Box.createVerticalStrut(80));
        vBaseRegister.add(userAccountBox);
        vBaseRegister.add(Box.createVerticalStrut(20));
        vBaseRegister.add(passwordBox);
        vBaseRegister.add(Box.createVerticalStrut(20));
        vBaseRegister.add(userNameBox);
        vBaseRegister.add(Box.createVerticalStrut(20));
        vBaseRegister.add(userIntroductionBox);
        vBaseRegister.add(Box.createVerticalStrut(20));
        vBaseRegister.add(userPhoneBox);
        vBaseRegister.add(Box.createVerticalStrut(20));
        vBaseRegister.add(loginBtnBox);


        backGroundPanel.add(vBaseRegister);
        registerJFrame.add(backGroundPanel);
//        页面显示
        registerJFrame.setVisible(true);
    }
}
