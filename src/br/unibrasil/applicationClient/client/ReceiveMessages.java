package br.unibrasil.applicationClient.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

import br.unibrasil.shared.DTOMensagemBase;
import javafx.application.Platform;

public class ReceiveMessages implements Runnable{
	
	private Socket ServerSocket;
	private IClientSocket iClientSocket;
	private ObjectInputStream objServerSocket;

	public ReceiveMessages(Socket ServerSocket,IClientSocket iClientSocket) {
		this.ServerSocket = ServerSocket;		
		this.iClientSocket = iClientSocket;
	}	

	@Override
	public void run() {	
		
		try 
		{				
			String recebido = "";				
			while (!recebido.toUpperCase().equals("SAIR")) {
				objServerSocket = new ObjectInputStream(ServerSocket.getInputStream());	
				DTOMensagemBase dtoMensagemBase = (DTOMensagemBase) objServerSocket.readObject();
				
				Platform.runLater(new Updater(iClientSocket, dtoMensagemBase));
				//iClientSocket.UpdateChat(dtoMensagemBase);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
