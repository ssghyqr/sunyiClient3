package service;

import common.Message;
import common.MessageType;
import ui.FriendChattingInterface;
import ui.GroupChattingInterface;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    //该线程需要持有Socket
    private Socket socket;

    //构造器可以接受一个Socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

//    当前一登陆用户列表
    public String[] onlineUsers;
    public String[] getOnlineUsers() {
        return onlineUsers;
    }
    public void setOnlineUsers(String[] onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

//    私聊界面对象,用于呈现发送接受消息时文本框得到消息
    FriendChattingInterface friendChattingInterface;
    public FriendChattingInterface getFriendChattingInterface() {
        return friendChattingInterface;
    }
    public void setFriendChattingInterface(FriendChattingInterface friendChattingInterface) {
        this.friendChattingInterface = friendChattingInterface;
    }
//    群聊界面对象
    GroupChattingInterface groupChattingInterface;
    public GroupChattingInterface getGroupChattingInterface() {
        return groupChattingInterface;
    }
    public void setGroupChattingInterface(GroupChattingInterface groupChattingInterface) {
        this.groupChattingInterface = groupChattingInterface;
    }

    //
    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，因此我们while循环，保持的原理就是阻塞和无限循环
        while (true) {
            try {
                System.out.println("客户端线程，等待从读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message对象,线程会阻塞在这里
                Message message = (Message) ois.readObject();
                //注意，后面我们需要去使用message
                //判断这个message类型，然后做相应的业务处理

                //如果是读取到的是 服务端返回的在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表信息，并显示
                    //规定
//                    String[] onlineUsers = message.getContent().split(" ");
                    onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=======当前在线用户列表========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户: " + onlineUsers[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {//普通的聊天消息
                    //把从服务器转发的消息，显示到控制台即可
                    System.out.println("\n" + message.getSender()
                            + " 对 " + message.getGetter() + " 说: " + message.getContent());
                    friendChattingInterface.receiveMessage(message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
//                    群发聊天信息
                    //显示在客户端的控制台
                    System.out.println("\n" + message.getSender() + " 对大家说: " + message.getContent());
                    groupChattingInterface.receiveMessage(message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {//如果是文件消息
                    //让用户指定保存路径。。。
                    System.out.println("\n" + message.getSender() + " 给 " + message.getGetter()
                            + " 发文件: " + message.getSrc() + " 到我的电脑的目录 " + message.getDest());
                    //取出message的文件字节数组，通过文件输出流写出到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest(), true);
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
//                    显示接收到的图片
                    friendChattingInterface.getPicture(message.getDest());
                    System.out.println("\n"+ message.getDest() +"保存文件成功~");

                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES_ALL)) {
                    //让用户指定保存路径。。。
                    System.out.println("\n" + message.getSender() + " 给大伙发文件: " + message.getSrc()
                            + " 到我的电脑的目录 " + message.getDest());
                    //取出message的文件字节数组，通过文件输出流写出到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest(), true);
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
//                    显示接收到的图片
                    groupChattingInterface.getPicture(message.getDest());
                    System.out.println("\n"+ message.getDest() +"群发保存文件成功~");
                } else {
                    System.out.println("是其他类型的message, 暂时不处理....");
                }
            } catch (Exception e) {
                return;
//                e.printStackTrace();
            }
        }
    }
    //为了更方便的得到Socket
    public Socket getSocket() {
        return socket;
    }
}
