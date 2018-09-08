package jisheng.mytest;

import java.net.*;
public class Client {

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[])throws Exception{
	    final int length=100;
	    String host="localhost";
	    int port=8002;
	    Socket[] sockets=new Socket[length];
	    for(int i=0;i<length;i++){     // 试图建立100次连接
	      sockets[i]=new Socket(host, port);
	      System.out.println("第"+(i+1)+"次连接成功");
	      Thread.sleep(3000*1000);
	    }
	   
	    for(int i=0;i<length;i++){
	      sockets[i].close();      //断开连接
	    } 

	}
}
