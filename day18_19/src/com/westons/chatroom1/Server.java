package com.westons.chatroom1;


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//定义服务器端
public class Server {
    public static void main(String[] args) throws IOException {

        //服务器端点
        ServerSocket serverSocket = new ServerSocket(6600);
        //
        Map<String,Socket> map=new HashMap<>();
        //线程池
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        System.out.println("等待连接中.......");
        for(int i=0;i<=10;i++){
            Socket socket = serverSocket.accept();
            System.out.println("有新的用户连接了"+socket.getChannel()+socket.getPort());
            executorService.execute(new Server1(socket,map));
        }
    }
}

//定义一个类Server1实现Runnable接口
class Server1 implements Runnable{
    private  Socket socket;
    private Map<String,Socket> map;
    public Server1(Socket socket,Map<String,Socket> map){
        this.socket=socket;
        this.map=map;
    }
    @Override
    public void run() {

        try {
            //获取客户端的信息
            Scanner scanner=new Scanner(socket.getInputStream());
            String string=null;
            while(true){
                if(scanner.hasNext()){
                    string=scanner.nextLine();
                    //防止输入时有空格，换行符当做输入数据，用正则表达式去掉空格，换行符
                    Pattern pattern=Pattern.compile("\r\n|\r|\n");
                    Matcher matcher=pattern.matcher(string);
                    string = matcher.replaceAll("");
                }
                //用户注册,群聊，私聊，退出
                if(string.equals("r")){
                    System.out.println("欢迎注册");
                    register(socket);
                }else if(string.equals("g")){
                    System.out.println("欢迎加入群聊");
                    grouptalk();
                }else if(string.equals("p")){
                    System.out.println("你已开启私聊");
                    privatetalk();
                }else if(string.equals("exit")){
                    //确定用户名子
                    String name=getUserName(socket);
                    System.out.println("用户"+name+"已经退出了群聊");
                }else{
                    System.out.println("你输入的选项错误，请按照提示进行选择输入");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用户退出实现，从map集合中清除退出的用户的名字
    private String getUserName(Socket socket) {
        String name=null;
        Set<Map.Entry<String, Socket>> entrySet = map.entrySet();
        for(Map.Entry<String, Socket> entries:entrySet){
            Boolean b=entries.getValue().equals(socket);
            if(b){
                map.remove(entries.getKey());
                name=entries.getKey();
            }
        }
        return name;
    }

    //私聊实现
    private void privatetalk() throws IOException {
        System.out.println("请输入你想聊天的用户的名字");
        Scanner scanner=new Scanner(System.in);
        String name1 = scanner.next();
        boolean b = map.containsKey(name1);
        if (!b) {
            System.out.println("对不起，对方不在群聊里");
        }else{
            System.out.println("请输入你想对他说的话：");
            String message=scanner.next();
            System.out.println("你对"+name1+"说"+message);
            PrintStream printStream=new PrintStream(socket.getOutputStream(),true);
            printStream.println(message);
        }
    }

    //群聊实现
    private void grouptalk() throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请说出你想对大家说的话：");
        String message = scanner.next();
        Set<Map.Entry<String, Socket>> entrySet = map.entrySet();
        for(Map.Entry<String, Socket> entries:entrySet){
            String name=entries.getKey();
            Socket socket=entries.getValue();
            System.out.println("用户"+name+"说："+message);
            PrintStream printStream=new PrintStream(socket.getOutputStream(),true);
            printStream.println(message);
        }
    }

    //注册实现
    private void register(Socket socket) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = scanner.next();
        System.out.println("用户"+username+"已经上线");
        map.put(username,socket);
        System.out.println("当前群中有"+map.size()+"名用户在线");
    }
}