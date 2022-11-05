package ui;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Result;
import components.RoundLightHight_Button;
import domain.*;
import service.*;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 好友列表
 */
public class FriendInterface {
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

    //    登录用户的角色
    private UserDTO userDTO;
//    去往添加好友页面
    private String[] adds = new String[]{"添加"};
//    登出按键
    private String[] logOut = new String[]{"登出"};
    //    存放好友的名字
    private String[] friends;
//    存放好友的id
    public List<Long> friendsId = new ArrayList<>();
    //    存放群聊
    private String[] groups;

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String[] getFriends() {
        return friends;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    //    组件
    private JFrame friendFrame = new JFrame("好友列表");

    final int WIDTH = 400;

    final int HEIGHT = 800;

    //    初始化好友列表
    public List<String> initFriendList(Long id) {
        Map<String, Object> params = new HashMap<>();
        List<String> initList = new ArrayList<>();
        params.put("id", id);
        GetUtils.getWithParams(HttpUrlUtils.getUrl("/contact/findAll"), params, new SuccessListener() {
            @Override
            public void success(String result) {
                Result resultList = JSONObject.parseObject(result, Result.class);
                List friendListDTOList = new ObjectMapper().convertValue(resultList.getData(), List.class);
                for (Object friendListDTO : friendListDTOList) {
                    initList.add(new ObjectMapper().convertValue(friendListDTO, FriendListDTO.class).getUserName());
                    friendsId.add(new ObjectMapper().convertValue(friendListDTO, FriendListDTO.class).getUserId());
                }
            }
        }, new FailListener() {
            @Override
            public void fail() {
                JOptionPane.showMessageDialog(friendFrame, "获取好友列表失败");
            }
        });
        return initList;
    }

    //    获取用户加入群聊
    public List<String> initGroupList(Long id) {
        Map<String, Object> params = new HashMap<>();
        List<String> initList = new ArrayList<>();
        params.put("id", id);
        GetUtils.getWithParams(HttpUrlUtils.getUrl("/usergroup/getUserGroupList"), params, new SuccessListener() {
            @Override
            public void success(String result) {
                Result resultList = JSONObject.parseObject(result, Result.class);
//                System.out.println(resultList);
                List groupListDTOList = new ObjectMapper().convertValue(resultList.getData(), List.class);
                for (Object groupListDTO : groupListDTOList) {
                    initList.add(new ObjectMapper().convertValue(groupListDTO, GroupListDTO.class).getGroupName());
                }
            }
        }, new FailListener() {
            @Override
            public void fail() {
                JOptionPane.showMessageDialog(friendFrame, "获取好友列表失败");
            }
        });
        return initList;
    }

    //    合并数组
    public String[] concatString(String[] str1, String[] str2) {
        int strLen1 = str1.length;// 保存第一个数组长度
        int strLen2 = str2.length;// 保存第二个数组长度
        str1 = Arrays.copyOf(str1, strLen1 + strLen2);// 扩容
        System.arraycopy(str2, 0, str1, strLen1, strLen2);// 将第二个数组与第一个数组合并
        return str1;
    }

    //    初始化界面
    public void init() throws IOException {
        //        打开添加好友列表
        //        组装好友列表
        JList friendJList = new JList(concatString(adds, concatString(friends, concatString(groups, logOut))));
        friendJList.setCellRenderer(new MyRenderer());
        friendFrame.add(new JScrollPane(friendJList));
        //        做上角logo图标
        friendFrame.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("1.jpg"))));

        //        添加JList点击事件
        friendJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int i = friendJList.getSelectedIndex();
//                去往好友添加页面
                if(i == 0) {
                    AddFriendOrGroupInterface addFriendOrGroupInterface = new AddFriendOrGroupInterface();
                    addFriendOrGroupInterface.setOwnId(userDTO.getUserId());
                    addFriendOrGroupInterface.setOwnDTO(userDTO);
                    addFriendOrGroupInterface.setMessageClientService(messageClientService);
                    addFriendOrGroupInterface.setFileClientService(fileClientService);
                    addFriendOrGroupInterface.setUserClientService(userClientService);
                    try {
                        addFriendOrGroupInterface.init();
                        friendFrame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
//                去往私聊页面
                else if (i < friends.length + 1) {
//                    初始化私聊
                    FriendChattingInterface friendChattingInterface = new FriendChattingInterface();
                    String[] user = new String[]{userDTO.getUserName(), friends[i - 1]};
                    friendChattingInterface.setUsers(user);
                    JList<String> list = new JList<>(friendChattingInterface.getUsers());
                    friendChattingInterface.setUserList(list);
//                    传递双方id
                    List<Long> listId = new ArrayList<>();
                    listId.add(userDTO.getUserId());
                    listId.add(friendsId.get(i - 1));
                    friendChattingInterface.setIdList(listId);
//                    传递服务层对象
                    friendChattingInterface.setFileClientService(fileClientService);
                    friendChattingInterface.setMessageClientService(messageClientService);
                    friendChattingInterface.setUserClientService(userClientService);
//                    传递私聊界面对象
                    ClientConnectServerThread clientConnectServerThread =
                            ManageClientConnectServerThread.getClientConnectServerThread(String.valueOf(listId.get(0)));
                    clientConnectServerThread.setFriendChattingInterface(friendChattingInterface);
                    friendChattingInterface.init();
                } else if(i < friends.length + groups.length + 1){
                    int groupIndex = i - friends.length - 1;
                    String groupName = groups[groupIndex];
                    System.out.println(groupName);
                    Map<String, String> params = new HashMap<>();
                    params.put("groupName", groupName);
//                    用群聊名称获得id
                    PostUtils.postWithParams(HttpUrlUtils.getUrl("/usergroup/findNameId"), params, new SuccessListener() {
                        @Override
                        public void success(String resultJson) throws IOException {
                            Result result = JSONObject.parseObject(resultJson, Result.class);
                            if (result.getCode().equals("400")) {
                                JOptionPane.showMessageDialog(friendFrame, result.getMsg());
                                return;
                            } else {
//                                根据群聊id获取群内所有成员
                                GroupDTO groupDTO = new ObjectMapper().convertValue(result.getData(), GroupDTO.class);
//                                群聊成员列表
                                List<String> groupMemberDTOS = new ArrayList<>();
                                List<String> groupMemberIdDTOS = new ArrayList<>();
//                                配置参数
                                Map<String, Object> paramId = new HashMap<>();
                                paramId.put("id", groupDTO.getGroupId());
                                GetUtils.getWithParams(HttpUrlUtils.getUrl("/groupmember/getAll"), paramId, new SuccessListener() {
                                    @Override
                                    public void success(String resultGroupMember) throws IOException {
                                        Result result_group_member = JSONObject.parseObject(resultGroupMember, Result.class);
                                        if (result_group_member.getCode().equals("400")) {
                                            JOptionPane.showMessageDialog(friendFrame, result_group_member.getMsg());
                                            return;
                                        } else {
//                                            获取群聊组员名称
                                            List groupMemberDTO = new ObjectMapper().convertValue(result_group_member.getData(), List.class);
                                            for (Object groupMember : groupMemberDTO) {
                                                groupMemberDTOS.add(new ObjectMapper().convertValue(groupMember, GroupMemberDTO.class).getUserName());
                                                groupMemberIdDTOS.add(String.valueOf(new ObjectMapper().convertValue(groupMember, GroupMemberDTO.class).getUserId()));
                                            }
//                                            初始化群聊
                                            GroupChattingInterface groupChattingInterface = new GroupChattingInterface();
//                                            初始化群聊用户列表
                                            String[] array_group_users = groupMemberDTOS.toArray(new String[groupMemberDTOS.size()]);
                                            groupChattingInterface.setUsers(array_group_users);
                                            JList<String> list = new JList<>(groupChattingInterface.getUsers());
                                            groupChattingInterface.setUserList(list);
//                                            初始化用户id列表
                                            groupChattingInterface.setUsersId(groupMemberIdDTOS.toArray(new String[groupMemberIdDTOS.size()]));
                                            groupChattingInterface.setOwnId(userDTO.getUserId());
//                                            传递应用层
                                            groupChattingInterface.setFileClientService(fileClientService);
                                            groupChattingInterface.setMessageClientService(messageClientService);
                                            groupChattingInterface.setUserClientService(userClientService);
                                            //                    传递私聊界面对象
                                            ClientConnectServerThread clientConnectServerThread =
                                                    ManageClientConnectServerThread.getClientConnectServerThread(String.valueOf(userDTO.getUserId()));
                                            clientConnectServerThread.setGroupChattingInterface(groupChattingInterface);
                                            groupChattingInterface.init();
                                        }
                                    }
                                }, new FailListener() {
                                    @Override
                                    public void fail() {
                                        JOptionPane.showMessageDialog(friendFrame, "进入群聊失败");
                                    }
                                });
                            }
                        }
                    }, new FailListener() {
                        @Override
                        public void fail() {
                            JOptionPane.showMessageDialog(friendFrame, "进入群聊失败");
                        }
                    });
                } else {
                    userClientService.logout();
                    friendFrame.dispose();
                    System.out.println("登出");
                }
            }
        });
//        friendFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        friendFrame.pack();
        friendFrame.setVisible(true);
    }

    private class MyRenderer extends JPanel implements ListCellRenderer {
        private String name;
        private ImageIcon icon;
        //记录背景色
        private Color backGround;
        //记录前景色：文字的颜色
        private Color forceGround;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            //重置成员变量的值
            this.name = value.toString();
            try {
                this.icon = new ImageIcon(PathUtils.getRealPath("1.gif"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.backGround = isSelected ? list.getSelectionBackground() : list.getBackground();
            this.forceGround = isSelected ? list.getSelectionForeground() : list.getForeground();
            return this;
        }

        //        组件框的大小
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        ;

        //绘制列表项的内容
        @Override
        public void paint(Graphics g) {
            int imageWidth = icon.getImage().getWidth(null);
            int imageHeight = icon.getImage().getHeight(null);
            //填充背景矩形
            g.setColor(backGround);
            g.fillRect(0, 0, getWidth(), getHeight());
            //绘制头像
            g.drawImage(icon.getImage(), 10, 10, null);
//            g.drawImage(icon.getImage(), this.getWidth() / 2 - imageWidth / 2, 10, null);
            //绘制昵称
            g.setColor(forceGround);
            g.setFont(new Font("StSong", Font.BOLD, 18));
            g.drawString(this.name, this.getWidth() / 2, imageHeight / 2 + 10);
//            g.drawString(this.name, this.getWidth() / 2 - this.name.length() * 10 / 2, imageHeight + 30);
        }
    }
}
