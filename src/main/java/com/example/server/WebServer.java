package com.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author hf
 * @date 2024/6/7
 */
public class WebServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String messageB = "";
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器已启动，等待客户端连接中");
            //等待客户端连接
            Socket socket = serverSocket.accept();
            //获取输入输出流
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            while (true){
                byte[] bytes = new byte[1024];
                int length = inputStream.read(bytes);
                String message = new String(bytes, 0, length);
                System.out.println("接收到客户端消息:" + message);
                System.out.print("输入消息返回给客户端：" + '\t');
                messageB = scanner.nextLine();
                //通过输出流发消息给客户端
                outputStream.write(messageB.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
