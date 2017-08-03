import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class UDPClient extends JFrame implements WindowListener,MouseListener,KeyListener
{
public JTextField username;
public JTextArea message_area;
public JTextField send_area;
public JTextField address;
public JTextField port;
public JList onlineUsers;
public TextField Name;
public DatagramSocket clientSocket;
public ArrayList<String> OnlineUsers;
public static String username1;


		
private void connectToServer() throws IOException{
    System.out.println("Trying to connect to the server... \n");
    System.out.println("Succesfull connection!");   
   
       
}
		//send message to server
public void send(){
			
			   try{
				     
				   	  clientSocket = new DatagramSocket();
				   	 
				      InetAddress IP = InetAddress.getByName(address.getText());
				      byte[] sendData = new byte[1024];
				      
				      String sentence = send_area.getText();
				      System.out.println("mesaj	"+sentence);
				      sendData = sentence.getBytes();
				      
				      
				      //get port from GUI
				      String pport=new String();
				      pport=port.getText();
				      int p=Integer.valueOf(pport);
				      System.out.println(p);
				      System.out.println(IP);
				     
				   
				      
				      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP,p);
				      clientSocket.send(sendPacket);
				      //clear text box upon pressing enter
				      send_area.setText("");

				      
				      
				      //set text to textarea (message_area and set a new line at each message)
				     
				      String newLine = System.getProperty("line.separator");
				      String timeStamp = new SimpleDateFormat("[yyyy/MM/dd]   [HH:mm:ss]  ").format(Calendar.getInstance().getTime());
				     // String usrr=new String();
				     // usrr=username.getText();
				     // username1=username.getText();
				      String user = new String();
					  user=username.getText();
				      message_area.append(newLine+timeStamp+"   "+user+":   "+sentence);
				     sentence=null;
				      //clientSocket.close();
				      }
				      catch(IOException e1){
				    	  e1.printStackTrace();
				    	  
					   }
				   }


private void send1(String message){
			   try{
				     
				   DatagramSocket clientSocket1 = new DatagramSocket();
				      InetAddress IP = InetAddress.getByName(address.getText());
				      
				      
				      byte[] sendData = new byte[1024];
				      sendData = message.getBytes();
				      
				      
				      //get port from GUI
				      String pport=new String();
				      pport=port.getText();
				      int p=Integer.valueOf(pport);
				      System.out.println(p);
				      System.out.println(IP);
				     
				   
				      
				      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP,p);
				      clientSocket1.send(sendPacket);
				      message=null;
				      clientSocket1.close();
				      }
				      catch(IOException e1){
				    	  e1.printStackTrace();
				    	  
					   }
				   }
				   

			   

		//receive message 
private void receive() throws InterruptedException{

	
	try {
		 
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		while(true){ 
		clientSocket.receive(receivePacket);
	      String msg = new String(receivePacket.getData());
	      System.out.println("message received:	"+msg);
	      String timeStamp = new SimpleDateFormat("[yyyy/MM/dd]   [HH:mm:ss]  ").format(Calendar.getInstance().getTime());
	      String newLine = System.getProperty("line.separator");
	      message_area.append(newLine+timeStamp+"SERVER:"+"   "+"  "+msg);
	      String trimmed=new String();
	      trimmed = new String(receivePacket.getData());	//we trim otherwise we get excess whitespace at the end
			  trimmed = trimmed.trim();
	      
	      message_area.append(newLine+timeStamp+"SERVER:"+"   "+"  "+trimmed);
	     trimmed=null;
	     msg=null;
	
	
	
		}
	} catch (final Exception e) {
		e.printStackTrace();
	}
         }
         


//	private void ListUpdate(){
//		final DefaultListModel<String> model = new DefaultListModel<String>();
//		final JList<String> onlineUsers = new JList<String>(model);
//		
//		
//		onlineUsers.setBorder(BorderFactory.createTitledBorder("Online Users"));
//		
//final Thread updater = new Thread() {
//    
//    @Override
//    public void run() {
//    	
//  
//
//    	String user = new String();
//	    user=username.getText();
//	    System.out.println(user);
//    	model.addElement(user);
//        
//    	try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    
//};
//updater.start();
//
//	}

UDPClient(String s){
		super(s);
		
	//GUI
		
		this.addWindowListener(this);
		this.setSize(800,600);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		

		message_area=new JTextArea();
		message_area.setEditable(false);
		this.add(message_area,"Center");
		message_area.setFont(new Font("Arial",Font.PLAIN,16));
		
		Panel p =new Panel();
		p.setLayout(new FlowLayout());
		
		send_area=new JTextField(30);
		
		send_area.setFont(new Font("Arial",Font.PLAIN,16));
		p.add(send_area);
		p.setBackground(new Color(221,221,221));
		
		
			
		//Menu of a client
		
		Panel M=new Panel();
		Label usernameLabel=new Label("Username:");
		username=new JTextField(10);
		Label adressLabel=new Label("ServerAddress:");
		Label portLabel=new Label("Port:");
		port=new JTextField("5000");
		address=new JTextField("localhost");
		
		M.setLayout(new FlowLayout());
		M.add(usernameLabel);
		M.add(username);
		M.add(adressLabel);
		M.add(address);
		M.add(portLabel);
		M.add(port);
		Label connectToLabel=new Label("Connect to:");
		TextField connectTo=new TextField(10);
		M.add(connectToLabel);
		M.add(connectTo);
		//list of online users
			/*	String usr=new String();
				usr=username.getText();
				
				ArrayList<String>data =new ArrayList<String>();
				data.add(usr);
	*/			Panel ListPanel=new Panel();
				DefaultListModel<String> dlmA = new DefaultListModel<String>();
				JList<String> onlineUsers=new JList<String>(dlmA);
	//			dlmA.addElement(usr);
		//		System.out.println(usr);
				onlineUsers.setFixedCellWidth(150);
				onlineUsers.setFixedCellHeight(150);
				ListPanel.add(onlineUsers);
				onlineUsers.setBorder(BorderFactory.createTitledBorder("Online Users"));
				this.add(ListPanel,"East");
		
	
					
		Button connect=new Button("Connect");
		connect.addActionListener(new ActionListener(){
			   
			public void actionPerformed(ActionEvent arg0){
				   if(arg0.getSource() == connect){

					   
						   
					   try {
						   
						  connectToServer();
						  
						  	String user = new String();
						    user=username.getText();
						    message_area.append("Server: welcome to the server"+"  "+user+"   !");
						    send1(user+"  is online!");
						    
						    //    OnlineUsers.add(user);
						    //onlineUsers.setModel();
						    
						    
						   
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					   
					   }
				   }
				   });
					 	
		M.add(connect);
		this.add(M,"North");
		
			// send and receive message to/from server using send button as trigger
			//also clear textfield upon pressing enter
		Button send=new Button("Send");
		send.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   if(e.getSource() == send){
				send();
				
				send_area.setText("");
				
					try {
						receive();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
			//receive();
				
				} 
			   }
			   
			   );
		p.add(send);
		this.add(p,"South");
		
		//send message upon pressing enter
		send_area.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				send();
				 
				}
		});
		send_area.requestFocus();
		


		
		
		
		
		this.setVisible(true);

}




public static void main(String args[]) throws Exception
   {
	try{
	UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
    } catch (Exception r){};
   
	UDPClient client=new UDPClient("ChatClient");

   
   }
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
}