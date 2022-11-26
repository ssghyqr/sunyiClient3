package ui;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Result;
import components.BackGroundPanel;
import components.RoundBorder_TextField;
import components.RoundLightHight_Button;
import domain.UserDTO;
import service.*;
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

/**
 * 登录界面
 */
public class LoginInterface {
    private UserClientService userClientService = new UserClientService();//对象是用于登录服务/注册用户
    private MessageClientService messageClientService = new MessageClientService();//对象用户私聊/群聊.
    private FileClientService fileClientService = new FileClientService();//该对象用户传输文件
    JFrame jFrameLogin = new JFrame("登陆界面");
    final int WIDTH = 768;
    final int HEIGHT = 370;

    //    初始化组装视图
    public void init() throws IOException {

//        登陆界面总大小
        jFrameLogin.setBounds((ScreenUtils.getScreenWidth() / 2) - WIDTH / 2, (ScreenUtils.getScreenHeight() / 2) - HEIGHT / 2,
                WIDTH, HEIGHT);
//        不可修改大小
        jFrameLogin.setResizable(false);
//        设置logo图标
        jFrameLogin.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("1.jpg"))));
        //设置窗口的内容,BackGroundPanel为继承JPanel属性
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("login.png"))));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

//        用户名盒子
        Box usernameBox = Box.createHorizontalBox();
        Font usernameFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel userLabel = new JLabel();
//        设置字体
        userLabel.setFont(usernameFont);
//        设置字体颜色
        userLabel.setForeground(Color.white);
//        标签文本
        userLabel.setText("用户账号");
//        自定义圆角边框
        RoundBorder_TextField userTextField = new RoundBorder_TextField(15);
//        设置文本域透明
        userTextField.setOpaque(false);
        usernameBox.add(userLabel);
        usernameBox.add(Box.createHorizontalStrut(20));
        usernameBox.add(userTextField);

//        密码盒子
        Box passwordBox = Box.createHorizontalBox();
        Font passwordFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel passwordLabel = new JLabel();
        passwordLabel.setFont(passwordFont);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setText("密码        ");
        RoundBorder_TextField passwordField = new RoundBorder_TextField(15);
        passwordField.setOpaque(false);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(15));
        passwordBox.add(passwordField);
//        组装按钮
        Box loginBtnBox = Box.createHorizontalBox();
        RoundLightHight_Button loginBtn = new RoundLightHight_Button("登录");
        RoundLightHight_Button registerBtn = new RoundLightHight_Button("注册");
//        登录和注册按钮的监听器
//        注册按钮事件监听
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                点击跳转到注册页面
                try {
                    new RegisterInterface().init();
                    jFrameLogin.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
//        登录按钮事件监听
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userAccount = userTextField.getText().trim();
                String userPassword = passwordField.getText().trim();
                if (userAccount == null || userAccount.length() == 0) {
                    JOptionPane.showMessageDialog(jFrameLogin, "用户账号为空");
                    return;
                }
                if (userPassword == null || userPassword.length() == 0) {
                    JOptionPane.showMessageDialog(jFrameLogin, "密码为空");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("userAccount", userAccount);
                params.put("userPassword", userPassword);
                try {
                    HttpClientUtil.loginPost(HttpUrlUtils.getUrl("/user/login"), params, new SuccessListener() {
                        @Override
                        public void success(String resultJson) throws IOException {
                            Result result = JSONObject.parseObject(resultJson, Result.class);
                            if (result.getCode().equals("400")) {
                                JOptionPane.showMessageDialog(jFrameLogin, result.getMsg());
                                return;
                            } else {
//                                java对象映射
                                UserDTO userDTO = new ObjectMapper().convertValue(result.getData(), UserDTO.class);
                                FriendInterface friendInterface = new FriendInterface();
                                friendInterface.setUserDTO(userDTO);
                                friendInterface.setFriends(friendInterface.initFriendList(userDTO.getUserId()).toArray(new String[friendInterface.initFriendList(userDTO.getUserId()).size()]));
                                friendInterface.setGroups(friendInterface.initGroupList(userDTO.getUserId()).toArray(new String[friendInterface.initGroupList(userDTO.getUserId()).size()]));
//                                设置服务层对象
                                friendInterface.setFileClientService(fileClientService);
                                friendInterface.setMessageClientService(messageClientService);
                                friendInterface.setUserClientService(userClientService);
                                friendInterface.init();
//                                尝试连接socket服务器,使得服务器得到这个client的线程
//                                userClientService存有当前登录用户的id
                                userClientService.checkUser(String.valueOf(userDTO.getUserId()), userDTO.getUserName());
//                                销毁自己
                                jFrameLogin.dispose();
                            }
                        }
                    }, new FailListener() {
                        @Override
                        public void fail() {
                            JOptionPane.showMessageDialog(jFrameLogin, "登陆失败");
                        }
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        loginBtnBox.add(loginBtn);
        loginBtnBox.add(Box.createHorizontalStrut(100));
        loginBtnBox.add(registerBtn);

//        组装整体界面
        Box loginBox = Box.createVerticalBox();
        loginBox.add(Box.createVerticalStrut(120));
        loginBox.add(usernameBox);
        loginBox.add(Box.createVerticalStrut(20));
        loginBox.add(passwordBox);
        loginBox.add(Box.createVerticalStrut(20));
        loginBox.add(loginBtnBox);

        bgPanel.add(loginBox);
        jFrameLogin.add(bgPanel);
        jFrameLogin.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new LoginInterface().init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
