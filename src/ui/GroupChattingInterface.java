package ui;
import components.RoundLightHight_Button;
import service.FileClientService;
import service.MessageClientService;
import service.UserClientService;
import utils.PathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 群组之间聊天界面
 */
public class GroupChattingInterface {
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

    JFrame jf = new JFrame("群聊界面");
//    群聊中自己的id
    public Long ownId;
//    用户id列表
    public String[] usersId;
    public String[] getUsersId() {
        return usersId;
    }
    public void setUsersId(String[] usersId) {
        this.usersId = usersId;
    }
    //    用户名列表
    String[] users;
    JList<String> userList;
    public Long getOwnId() {
        return ownId;
    }
    public void setOwnId(Long ownId) {
        this.ownId = ownId;
    }
    //    得到用户数组
    public String[] getUsers() {
        return users;
    }
//    设置用户数组
    public void setUsers(String[] users) {
        this.users = users;
    }
//    设置用列表
    public void setUserList(JList<String> userList) {
        this.userList = userList;
    }

    //        组装消息显示界面
    JScrollPane messagePane = new JScrollPane();

    JPanel messageJPanel = new JPanel();

    JTextArea messageDesc = new JTextArea();

    //    显示接收消息
    public void receiveMessage(String text) {
        JLabel jLabel = new JLabel(text);
        Font font = new Font("楷体", Font.PLAIN, 20);
        jLabel.setFont(font);
        jLabel.setBounds(0,0,150,100);
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
    }

    //    显示消息
    public void showMessage(String text) {
        JLabel jLabel = new JLabel(text);
        Font font = new Font("楷体", Font.PLAIN, 20);
        jLabel.setFont(font);
        jLabel.setBounds(0,0,150,100);
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
        messageClientService.sendMessageToAll(text, String.valueOf(ownId), Arrays.stream(usersId).toList());
    }
    //    显示获取的图片
    public void getPicture(String url) {
        String path = url;
        ImageIcon icon = new ImageIcon(path);
        //改变图片大小
        icon.setImage(icon.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
        JLabel jLabel = new JLabel(icon, JLabel.CENTER);
//        添加事件
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileChooseInterface fileChooseInterface = new FileChooseInterface();
                fileChooseInterface.setImageUrl(url);
                fileChooseInterface.init();
            }
        });
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
    }

    //    显示图片
    public void showPicture(String url, String fileName) throws IOException {
        String path = url;
        ImageIcon icon = new ImageIcon(path);
        //改变图片大小
        icon.setImage(icon.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
        JLabel jLabel = new JLabel(icon, JLabel.CENTER);
//        添加事件
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileChooseInterface fileChooseInterface = new FileChooseInterface();
                fileChooseInterface.setImageUrl(url);
                fileChooseInterface.init();
            }
        });
//        发送socket文件
        String dest = PathUtils.getPicPath(fileName);
//        String dest = "C:\\Users\\28458\\Desktop\\homework\\大三实践领域\\sunyiClient\\src\\fileGet\\" + fileName;
//        群发文件消息
        fileClientService.sendFileToAll(url, dest, String.valueOf(ownId), List.of(usersId));
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
    }

    public void init() {
//        设置消息框排列
        BoxLayout boxLayout = new BoxLayout(messageJPanel, BoxLayout.Y_AXIS);
        messageJPanel.setLayout(boxLayout);
//        设置组件大小
        userList.setPreferredSize(new Dimension(150, 300));
        messagePane.setPreferredSize(new Dimension(220, 330));
        messageDesc.setPreferredSize(new Dimension(220, 70));
        //组装左边区域
        Box leftBox = Box.createVerticalBox();
        Box btnBox = Box.createHorizontalBox();
        RoundLightHight_Button sendBtn = new RoundLightHight_Button("发送消息");
        RoundLightHight_Button pictureBtn = new RoundLightHight_Button("发送图片");
        //        添加按钮点击事件
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sendText =  messageDesc.getText();
                if (sendText == null || sendText.length() == 0) {
                    JOptionPane.showMessageDialog(jf, "发送消息不能为空");
                    return;
                }
//                发送成功,在对话框内显示
                messageDesc.setText("");
                showMessage(sendText);
            }
        });
        pictureBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File directory = new File(".");
//                    String pictureUrl = directory.getCanonicalPath() + "\\src\\image\\";
//                    showPicture(pictureUrl);
//                    开发文件选择器
                JFileChooser jFileChooser = new JFileChooser(".");
                jFileChooser.showOpenDialog(jf);
                File file = jFileChooser.getSelectedFile();
                //                    得到绝对路径
                String fileName = file.getAbsolutePath();
//                获得文件名字
                String []name = fileName.split("\\\\");
                try {
                    showPicture(fileName, name[name.length - 1]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnBox.add(sendBtn);
        btnBox.add(Box.createHorizontalStrut(30));
        btnBox.add(pictureBtn);
        leftBox.add(new JScrollPane(messageDesc));
        leftBox.add(btnBox);

        JSplitPane left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messagePane, leftBox);
        left.setOneTouchExpandable(true);
        left.setDividerSize(10);
        left.resetToPreferredSizes();

//        组装右边区域
        Box rightBox = Box.createVerticalBox();
        Font friendFont = new Font("楷体", Font.PLAIN, 15);
        JLabel friendLabel = new JLabel();
        friendLabel.setFont(friendFont);
        friendLabel.setForeground(Color.black);
        friendLabel.setText("群聊成员");
        rightBox.add(friendLabel);
        rightBox.add(userList);

        JSplitPane userContent = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, rightBox);
        userContent.setContinuousLayout(true);

        jf.add(userContent);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
}
