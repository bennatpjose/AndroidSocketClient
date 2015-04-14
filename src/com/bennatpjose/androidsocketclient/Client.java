package com.bennatpjose.androidsocketclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Client extends Activity {

	private Socket socket;
	private TextView text ;
	private BufferedReader input;
	Handler updateConversationHandler;
	Thread serverThread = null;
	private static final int SERVERPORT = 5556;
	private static final String SERVER_IP = "10.0.2.2";
	public static  boolean GAVIN_IS_AWSOME = true;
	PrintWriter out ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		text = (TextView) findViewById(R.id.tvIncomingMessages);
		//text.setText(">>>"+"\n");
		new Thread(new ClientThread()).start();
	
		
	}
	
	
	public void onClick(View view) {
		try {
			EditText et = (EditText) findViewById(R.id.etMessage);
			String str = et.getText().toString();
			 out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),
					true);
			out.println(str);
			//text.setText(text.getText()+">"+input.readLine());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	


	
	class ClientThread implements Runnable {
		private String msg;
		private String msgsd;
		

		public void updateUIThread(String str) {
			this.msg = str;
		}
		@Override
		public void run() {

			try {
				InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

				socket = new Socket(serverAddr, SERVERPORT);
				
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    //String msgsd;
				//text.setText(text.getText()+">"+input.readLine());
				while(GAVIN_IS_AWSOME)
				{		//input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msgsd = input.readLine();
					text.post(new Runnable(){
					
					public void run(){						
						
							text.setText(text.getText()+">>"+msgsd+"\n");
						
					}
					});
					
				}
				
			}	catch (IOException e){
						e.printStackTrace();
					}
				catch (Exception e)
		{
			e.printStackTrace();
		}	

	}
	}
}
