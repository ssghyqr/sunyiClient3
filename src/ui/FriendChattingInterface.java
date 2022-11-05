package ui;
import components.RoundLightHight_Button;
import service.*;
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
import java.util.List;

/**
 * 用户之间聊天界面
 */
public class FriendChattingInterface {
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

    JFrame jf = new JFrame("私聊界面");
    String[] users;
    JList<String> userList;
//    私聊双方id的list
    public List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public void setUserList(JList<String> userList) {
        this.userList = userList;
    }

    //        组装消息显示界面
    JScrollPane messagePane = new JScrollPane();
    JPanel messageJPanel = new JPanel();
    JTextArea messageDesc = new JTextArea();
//    显示接收消息
    public void receiveMessage(String text) {
//        System.out.println("接收私发消息");
        if(text.equals("发送用户未上线")) {
            text = "发送用户未上线";
        }
        JLabel jLabel = new JLabel(text);
        Font font = new Font("楷体", Font.PLAIN, 20);
        jLabel.setFont(font);
        jLabel.setBounds(0,0,150,100);
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
    }
//    显示发送消息
    public void showMessage(String text) {
        JLabel jLabel = new JLabel(text);
        Font font = new Font("楷体", Font.PLAIN, 20);
        jLabel.setFont(font);
        jLabel.setBounds(0,0,150,100);
        messageJPanel.add(jLabel);
        messagePane.getViewport().add(messageJPanel);
//        真正调用socket发送消息
        messageClientService.sendMessageToOne(text, String.valueOf(idList.get(0)), String.valueOf(idList.get(1)));
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
//        String dest = PathUtils.getPicPath(fileName);
//        String dest1 =
        String dest = "E:\\" + fileName;
        fileClientService.sendFileToOne(url, dest, String.valueOf(idList.get(0)), String.valueOf(idList.get(1)));
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
        friendLabel.setText("联系双方");
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
