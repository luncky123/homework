package com.westons.chatroom1;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

//定义客户端
//发送数据用一个线程
//接收数据用一个线程
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6600);
        Send send= new Send(socket);
        Thread outThread=new Thread(send);

        Receive receive= new Receive(socket);
        Thread inThread=new Thread(receive);
        outThread.start();
        inThread.start();
    }
}

//发送数据用一个线程
class Send implements Runnable{
    private Socket client;
    public Send(Socket client){
        super();
        this.client=client;
    }

    @Override
    public void run(){
        Scanner scanner=new Scanner(System.in);
        scanner.useDelimiter("\n");
        PrintStream printStream;
        try{
            //定义新的打印流
            printStream = new PrintStream(client.getOutputStream(), true);
            while (true) {
                System.out.println("请选择：注册:r,群聊：g,私聊：p，退出：exit");
                while (scanner.hasNext()) {
                    String s = scanner.nextLine();
                    printStream.println(s);
                    if (s.equals("exit")) {
                        System.out.println("你已经退出了聊天群");
                        scanner.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

//接收数据用一个线程
class Receive implements Runnable{
    private Socket client;
    public Receive(Socket client){
        super();
        this.client=client;
    }
    @Override
    public void run() {
        try {
            Scanner scanner=new Scanner(client.getInputStream());
            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                System.out.println("服务器端响应："+scanner.nextLine());
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}