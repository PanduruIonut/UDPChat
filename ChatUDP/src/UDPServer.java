import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class UDPServer extends JFrame{
	
	int Port=5000;
    DatagramSocket serverSocket = new DatagramSocket(Port);
    ServerSocket serverSocket1=new ServerSocket(Port);
    

    
   
	
    
    private void  waitForConnection() throws IOException{
		  System.out.println("Server is running...");
	         System.out.println("Waiting for clients...");
	         
	  }
    
    
    
    
    UDPServer(String s) throws IOException{
		super(s);	
	
		
		
		JFrame f = new JFrame("Server");

	      JTextArea ta = new JTextArea();
	      ta.setEditable(false);
	      f.setLayout(new BorderLayout());

	      f.add(new JScrollPane(ta), BorderLayout.CENTER);

	      f.setSize(400, 400);

	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      f.setVisible(true);
	    ta.append("Server started on port 5000");
		waitForConnection();
	         
				
	  
	            while(true)
	               {
//	            		
	            	
	            	byte[] receiveData = new byte[1024];
		            byte[] sendData = new byte[1024];	
	            	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	                  serverSocket.receive(receivePacket);
	                  String sentence = new String( receivePacket.getData());
	                  String trimmed=new String();
	        	      trimmed = new String(receivePacket.getData());	//we trim otherwise we get excess whitespace at the end
	        			  trimmed = trimmed.trim();
	                  String newLine = System.getProperty("line.separator");
				      String timeStamp = new SimpleDateFormat("[yyyy/MM/dd]   [HH:mm:ss]  ").format(Calendar.getInstance().getTime());
				      ta.append(newLine+timeStamp+"   "+sentence);
	                  System.out.println("  RECEIVED message: " + trimmed);
	                  ta.append(newLine+timeStamp+"   "+trimmed);
	                  
	                  
	                  
	                  InetAddress IPAddress = receivePacket.getAddress();
	                  int port = receivePacket.getPort();
	                  sendData =sentence.getBytes();
	                  DatagramPacket sendPacket =
	                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
	                  serverSocket.send(sendPacket);
	        	   
				      
			   }
		   
	               }
	               

   public static void main(String args[]) throws IOException {

	   UDPServer sv =new UDPServer("Server");


	}
	}





