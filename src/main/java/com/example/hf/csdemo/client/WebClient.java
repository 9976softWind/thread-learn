package com.example.hf.csdemo.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author hf
 * @date 2024/6/7
 */
public class WebClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String messageA = "";
        try {
            //创建客户端套接字，连接服务器的IP地址和端口
            Socket socket = new Socket("127.0.0.1",8888);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            while (socket.isConnected()){
                System.out.print("已连接到服务器，请输入消息：" + '\t');
                // 输入消息
                messageA = scanner.nextLine();
                //发送消息给服务器
                outputStream.write(messageA.getBytes());
                outputStream.flush();
                //接受服务器的响应
                byte[] bytes = new byte[1024];
                int length = inputStream.read(bytes);
                String response = new String(bytes, 0, length);
                System.out.println( "服务器应答：" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
