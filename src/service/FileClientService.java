package service;

import common.Message;
import common.MessageGroup;
import common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 该类/对象完成 文件传输服务
 */
public class FileClientService {
    /**
     *
     * @param src 源文件
     * @param dest 把该文件传输到对方的哪个目录
     * @param senderId 发送用户id
     * @param getterId 接收用户id
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {
        //读取src文件  -->  message
        MessageGroup message = new MessageGroup();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        //需要将文件读取
        FileInputStream fileInputStream = null;
//        fileBytes是文件全称的二进制字符数组，注意这里创建了一个文件对象
        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组
            //将文件对应的字节数组设置message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println("\n" + senderId + " 给 " + getterId + " 发送文件: " + src
                + " 到对方的电脑的目录 " + dest);
        //发送
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群聊发送文件
     * @param src
     * @param dest
     * @param senderId
     * @param getterIdList
     */
    public void sendFileToAll(String src, String dest, String senderId, List<String> getterIdList) {
        //读取src文件  -->  message
        MessageGroup message = new MessageGroup();
        message.setMesType(MessageType.MESSAGE_FILE_MES_ALL);
        message.setSender(senderId);
//        message.setGetter(getterId);
        message.setGetterList(getterIdList);
        message.setSrc(src);
        message.setDest(dest);
        //需要将文件读取
        FileInputStream fileInputStream = null;
//        fileBytes是文件全称的二进制字符数组，注意这里创建了一个文件对象
        byte[] fileBytes = new byte[(int)new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组
            //将文件对应的字节数组设置message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println("\n" + senderId + " 给大伙儿发送文件: " + src
                + " 到对方的电脑的目录 " + dest);
        //发送
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
