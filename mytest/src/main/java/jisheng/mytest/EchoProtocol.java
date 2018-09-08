package jisheng.mytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;





public class EchoProtocol  implements Runnable {
  private static final int BUFSIZE = 64; // Size (in bytes) of I/O buffer
  private Socket clientSocket; // Socket connect to client
//  private Logger logger; // Server logger


  public EchoProtocol(Socket clientSocket, Logger logger) {
    this.clientSocket = clientSocket;

  }
  
  public static String bytesToHexString(byte[] src){
    StringBuilder stringBuilder = new StringBuilder();
    if (src == null || src.length <= 0) {
      return null;
    }
    for (int i = 0; i < src.length; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toHexString(v);

//      stringBuilder.append(i + ":");

      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }

  public static void handleEchoClient(Socket clientSocket, Logger loggers) {
    try {
	      SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
	      SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
	      String clientIP=clientSocket.getInetAddress().toString();
//	      System.out.println("==================="+clientIP);
	      
	      // /192.168.9.102:56160
	      System.out.println("Handling client at" + clientAddress + "/" + df.format(new Date()));
	      String  address = clientAddress.toString();
	      String ip = address.substring(1, 14);
	      int port = clientSocket.getPort();
	      sendInfo(ip,port);
	      
	      // 获取 硬件上传过 来的数据信息
	      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8")); 
	      BufferedReader bufferedReader = new BufferedReader(in);
	      String line = null;
	      StringBuilder stringBuilder = new StringBuilder();
	      while((line = bufferedReader.readLine()) != null){
	          stringBuilder.append(line);
	      }
	      String inStr = stringBuilder.toString();
	      if(!CommonStringUtil.isEmptyBoolean(inStr)){
	    	  ThreadSystemCostingStatistic1 s1 = new ThreadSystemCostingStatistic1(inStr,clientAddress.toString());
			  s1.start();
	      }
	      // 处理客户端数据    
	      System.out.println("客户端发过来的内容:" + inStr);
      

    } catch (IOException ex) {
      logger.error(ex.getMessage().toString()+"==="+ex.getStackTrace().toString());
    } finally {
      try {
        clientSocket.close();
      } catch (IOException e) {
      }
    }
  }

  @Override
  public void run() {
    handleEchoClient(this.clientSocket, null);
  }
  
  
  /**
   * 发送消息
   * @param ip
   * @param port
   */
  public static void sendInfo(String ip,int port){
	  try { 
      	// 192.168.0.150:1236   // 21304D410D0A
      	Socket socket=null; 
         socket=new Socket(ip, port,false);
//      	socket=new Socket("192.168.0.100", 8185);
         //向客户端发送消息 
         OutputStream os=socket.getOutputStream(); 
         os.write(("!0MA").getBytes()); 
         System.out.println("数据已发送!!!");
	         InputStream is=socket.getInputStream(); 
	         //接受客户端的响应 
	         byte[] b=new byte[1024]; 
	         is.read(b); 
	         System.out.println(new String(b)); 
         os.close();
     } catch (IOException e) { 
         // TODO Auto-generated catch block 
         e.printStackTrace(); 
     }
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}

/**
 * @classDesc ：
 *	使用 新的线程来处理数据
 * @creater: 陈伏宝
 * @creationDate:2018年7月4日下午5:34:29
 */
class ThreadSystemCostingStatistic1 extends Thread{ 
	private String str;
	private String ip;
    public ThreadSystemCostingStatistic1(String str,String ip) {  
       this.str=str;
       this.ip=ip;
    }
    public void run() {  
 			if(!CommonStringUtil.isEmptyBoolean(str)&&!CommonStringUtil.isEmptyBoolean(ip)){
			try {
				  Analysis.calculateNum(str,ip);
				
			}
			catch (Exception e) {
				
			}
		}
    }  
}





















