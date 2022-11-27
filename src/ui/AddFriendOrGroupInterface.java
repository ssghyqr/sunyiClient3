package ui;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Result;
import components.BackGroundPanel;
import components.RoundBorder_TextField;
import components.RoundLightHight_Button;
import domain.GroupDTO;
import domain.UserDTO;
import service.FileClientService;
import service.MessageClientService;
import service.UserClientService;
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
 * 添加好友或者加入群聊界面
 */
public class AddFriendOrGroupInterface {
//    应用层对象
    private UserClientService userClientService;
    private MessageClientService messageClientService;
    private FileClientService fileClientService;

    public UserClientService getUserClientService() {
        return userClientService;
    }

    public void setUserClientService(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    public MessageClientService getMessageClientService() {
        return messageClientService;
    }

    public void setMessageClientService(MessageClientService messageClientService) {
        this.messageClientService = messageClientService;
    }

    public FileClientService getFileClientService() {
        return fileClientService;
    }

    public void setFileClientService(FileClientService fileClientService) {
        this.fileClientService = fileClientService;
    }

    private JFrame addFrame = new JFrame("添加好友或群聊");
    //    设置长和宽
    final int WIDTH = 768;
    final int HEIGHT = 370;
    //    自身对象
    private Long ownId = 1L;

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
    }

    public Long getOwnId() {
        return ownId;
    }

    public UserDTO ownDTO;

    public UserDTO getOwnDTO() {
        return ownDTO;
    }

    public void setOwnDTO(UserDTO ownDTO) {
        this.ownDTO = ownDTO;
    }

    //    查询添加用户对象
    public UserDTO userDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

//    查询加入群聊对象
    public GroupDTO joinGroupDTO;

    public GroupDTO getJoinGroupDTO() {
        return joinGroupDTO;
    }

    public void setJoinGroupDTO(GroupDTO joinGroupDTO) {
        this.joinGroupDTO = joinGroupDTO;
    }

    //    返回好友列表函数
    public void backBtn(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                FriendInterface friendInterface = new FriendInterface();
                friendInterface.setUserDTO(ownDTO);
                friendInterface.setFriends(friendInterface.initFriendList(ownDTO.getUserId()).toArray(new String[friendInterface.initFriendList(ownDTO.getUserId()).size()]));
                friendInterface.setGroups(friendInterface.initGroupList(ownDTO.getUserId()).toArray(new String[friendInterface.initGroupList(ownDTO.getUserId()).size()]));
                try {
//                                设置服务层对象
                    friendInterface.setFileClientService(fileClientService);
                    friendInterface.setMessageClientService(messageClientService);
                    friendInterface.setUserClientService(userClientService);
                    friendInterface.init();
                    addFrame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    //    群聊盒子中按钮添加时间函数
    public void addGroupBtn(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("addGroup");
                Map<String, Object> params = new HashMap<>();
                params.put("userId", getOwnId());
//                params.put("userId", getOwnDTO().getUserId());
                params.put("groupId", getJoinGroupDTO().getGroupId());
                try {
                    HttpClientUtil.addGroupMemberPost(HttpUrlUtils.getUrl("/groupmember/add"), params, new SuccessListener() {
                        @Override
                        public void success(String resultJson) throws IOException {
//                            System.out.println(resultJson);
                            Result result = JSONObject.parseObject(resultJson, Result.class);
                            if(result.getCode().equals("400")) {
                                JOptionPane.showMessageDialog(addFrame, result.getMsg());
                            } else {
                                JOptionPane.showMessageDialog(addFrame, result.getMsg());
                            }
                        }
                    }, new FailListener() {
                        @Override
                        public void fail() {
                            JOptionPane.showMessageDialog(addFrame, "获取群聊失败");
                        }
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    //    用户添加盒子中按钮添加事件函数
    public void addUserBtn(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Object> params = new HashMap<>();
                params.put("ownId", getOwnId());
//                params.put("ownId", getOwnDTO().getUserId());
                params.put("userId", getUserDTO().getUserId());
                params.put("userAccount", getUserDTO().getUserAccount());
                params.put("userName", getUserDTO().getUserName());
                try {
                    HttpClientUtil.contactCheckPost(HttpUrlUtils.getUrl("/contact/check"), params, new SuccessListener() {
                        @Override
                        public void success(String resultJson) throws IOException {
                            Result result = JSONObject.parseObject(resultJson, Result.class);
                            if (result.getMsg().equals("已是好友")) {
                                JOptionPane.showMessageDialog(addFrame, "已是好友");
                            } else {
                                HttpClientUtil.contactAddPost(HttpUrlUtils.getUrl("/contact/add"), params, new SuccessListener() {
                                    @Override
                                    public void success(String resultJsonAdd) throws IOException {
                                        Result resultAdd = JSONObject.parseObject(resultJsonAdd, Result.class);
                                        if (resultAdd.getCode().equals("400")) {
                                            JOptionPane.showMessageDialog(addFrame, resultAdd.getMsg());
                                        } else {
                                            JOptionPane.showMessageDialog(addFrame, "添加好友成功");
                                        }
                                    }
                                }, new FailListener() {
                                    @Override
                                    public void fail() {
                                        JOptionPane.showMessageDialog(addFrame, "查询失败");
                                    }
                                });
                            }
                        }
                    }, new FailListener() {
                        @Override
                        public void fail() {
                            JOptionPane.showMessageDialog(addFrame, "添加好友失败");
                        }
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void init() throws IOException {
        //        界面总大小
        addFrame.setBounds((ScreenUtils.getScreenWidth() / 2) - WIDTH / 2, (ScreenUtils.getScreenHeight() / 2) - HEIGHT / 2,
                WIDTH, HEIGHT);
        //        不可修改大小
        addFrame.setResizable(false);
//        设置logo图标
        addFrame.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("1.jpg"))));
        //设置窗口的内容,BackGroundPanel为继承JPanel属性
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("login.png"))));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

//        搜索群聊或者搜索用户盒子
        Box searchBox = Box.createHorizontalBox();
        Font searchFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel searchLabel = new JLabel();
        searchLabel.setFont(searchFont);
        searchLabel.setForeground(Color.white);
        searchLabel.setText("搜索用户或群聊");
        //        自定义圆角边框
        RoundBorder_TextField searchTextField = new RoundBorder_TextField(15);
        searchTextField.setOpaque(false);
        searchBox.add(searchLabel);
        searchBox.add(Box.createHorizontalStrut(20));
        searchBox.add(searchTextField);
//        两个操作按钮
        Box btnBox = Box.createHorizontalBox();
        RoundLightHight_Button searchUserBtn = new RoundLightHight_Button("搜索用户");
        RoundLightHight_Button searchGroupBtn = new RoundLightHight_Button("搜索群聊");
        RoundLightHight_Button backBtn = new RoundLightHight_Button("返回好友列表");
        btnBox.add(searchUserBtn);
        btnBox.add(Box.createHorizontalStrut(20));
        btnBox.add(searchGroupBtn);
        btnBox.add(Box.createHorizontalStrut(20));
        btnBox.add(backBtn);
//        绑定返回按钮事件
        backBtn(backBtn);
//        搜索列表文本提示盒子
        Box messageBox = Box.createHorizontalBox();
        Font messageFont = new Font("微软雅黑", Font.PLAIN, 15);
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(messageFont);
        messageLabel.setForeground(Color.white);
        messageLabel.setText("查询结果列表:");
        messageBox.add(messageLabel);

//        搜索结果总的盒子
        JPanel resultSearchBox = new JPanel();
        resultSearchBox.setOpaque(false);
//        搜索结果单条盒子,用于添加好友或加入群聊
        Box resultSearch_box = Box.createHorizontalBox();
        //        搜索用户和搜索群聊按钮事件
        searchGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                重置组件
                resultSearch_box.removeAll();
                resultSearchBox.removeAll();
                resultSearchBox.repaint();
//                设置参数
                Map<String, String> params = new HashMap<>();
                String groupName = searchTextField.getText();
                if (groupName == null || groupName.length() == 0) {
                    JOptionPane.showMessageDialog(addFrame, "搜索群聊名为空");
                    return;
                }
                params.put("groupName", groupName);
                PostUtils.postWithParams(HttpUrlUtils.getUrl("/usergroup/findNameId"), params, new SuccessListener() {
                    @Override
                    public void success(String resultJson) throws IOException {
                        Result result = JSONObject.parseObject(resultJson, Result.class);
//                        System.out.println(result.getCode());
                        if (result.getCode().equals("400")) {
                            JOptionPane.showMessageDialog(addFrame, "查询群聊失败");
                        } else {
                            GroupDTO groupDTO = new ObjectMapper().convertValue(result.getData(), GroupDTO.class);
//                            设置群聊对象
                            setJoinGroupDTO(groupDTO);
                            Font groupFont = new Font("微软雅黑", Font.PLAIN, 15);
                            JLabel groupLabel = new JLabel();
                            groupLabel.setFont(groupFont);
                            groupLabel.setForeground(Color.white);
                            groupLabel.setText(groupDTO.getGroupName());
                            RoundLightHight_Button addGroupBtn = new RoundLightHight_Button("添加");
//                            绑定事件
                            addGroupBtn(addGroupBtn);
                            resultSearch_box.add(groupLabel);
                            resultSearch_box.add(Box.createHorizontalStrut(20));
                            resultSearch_box.add(addGroupBtn);
                            //        将单条盒子添加到总的盒子
                            resultSearchBox.add(resultSearch_box);
                            resultSearchBox.revalidate();
                        }
                    }
                }, new FailListener() {
                    @Override
                    public void fail() {
                        JOptionPane.showMessageDialog(addFrame, "搜索群聊失败");
                    }
                });
            }
        });
        searchUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                重置组件
                resultSearch_box.removeAll();
                resultSearchBox.removeAll();
                resultSearchBox.repaint();
//                设置参数
                Map<String, Object> params = new HashMap<>();
                String userName = searchTextField.getText();
                if (userName == null || userName.length() == 0) {
                    JOptionPane.showMessageDialog(addFrame, "搜索用户名为空");
                    return;
                }
                params.put("userName", userName);
                GetUtils.getWithParams(HttpUrlUtils.getUrl("/user/findByName"), params, new SuccessListener() {
                    @Override
                    public void success(String resultJson) throws IOException {
                        Result result = JSONObject.parseObject(resultJson, Result.class);
                        if (result.getCode().equals("400")) {
                            JOptionPane.showMessageDialog(addFrame, result.getMsg());
                            return;
                        } else {
//                                java对象映射
                            userDTO = new ObjectMapper().convertValue(result.getData(), UserDTO.class);
                            Font userFont = new Font("微软雅黑", Font.PLAIN, 15);
                            JLabel userLabel = new JLabel();
                            userLabel.setFont(userFont);
                            userLabel.setForeground(Color.white);
                            userLabel.setText(userDTO.getUserName());
                            RoundLightHight_Button addUserBtn = new RoundLightHight_Button("添加");
//                            绑定事件
                            addUserBtn(addUserBtn);
                            resultSearch_box.add(userLabel);
                            resultSearch_box.add(Box.createHorizontalStrut(20));
                            resultSearch_box.add(addUserBtn);
                            //        将单条盒子添加到总的盒子
                            resultSearchBox.add(resultSearch_box);
                            resultSearchBox.revalidate();
                        }
                    }
                }, new FailListener() {
                    @Override
                    public void fail() {
                        JOptionPane.showMessageDialog(addFrame, "搜索用户失败");
                    }
                });
            }
        });


//        整体搜索界面盒子
        Box searchFrameBox = Box.createVerticalBox();
        searchFrameBox.add(Box.createVerticalStrut(50));
        searchFrameBox.add(searchBox);
        searchFrameBox.add(Box.createVerticalStrut(20));
        searchFrameBox.add(btnBox);
        searchFrameBox.add(Box.createVerticalStrut(20));
        searchFrameBox.add(messageBox);
        searchFrameBox.add(Box.createVerticalStrut(20));
//        多条结果盒子
        searchFrameBox.add(resultSearchBox);

        bgPanel.add(searchFrameBox);
        addFrame.add(bgPanel);
        addFrame.setVisible(true);
    }
}
