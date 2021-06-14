package br.unibrasil.applicationClient.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

import br.unibrasil.shared.CodigoComunication;
import br.unibrasil.shared.Comunication;
import br.unibrasil.shared.DTOMensagemBase;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ReceiveMessages implements Runnable{
	
	private Socket ServerSocket;
	private IClientSocket iClientSocket;
	private ObjectInputStream objServerSocket;
	private Stage primaryStage;

	public ReceiveMessages(Socket ServerSocket,IClientSocket iClientSocket, Stage primaryStage) {
		this.ServerSocket = ServerSocket;		
		this.iClientSocket = iClientSocket;
		this.primaryStage = primaryStage;
	}	

	@Override
	public void run() {	
		
		try 
		{				
			CodigoComunication recebido = CodigoComunication.CONVERSATION;				
			while (!recebido.equals(CodigoComunication.EXIT)) {
				objServerSocket = new ObjectInputStream(ServerSocket.getInputStream());	
				DTOMensagemBase dtoMensagemBase = (DTOMensagemBase) objServerSocket.readObject();
				Comunication comunication = dtoMensagemBase.getComunicationMensagems();
				if (comunication.getCodigoComunication() == CodigoComunication.CONVERSATION)
				{
					Platform.runLater(new Updater(iClientSocket, dtoMensagemBase));
				}
				else if (comunication.getCodigoComunication() == CodigoComunication.LOGIN)
				{
					Platform.runLater(new Updater(iClientSocket, dtoMensagemBase));
				}
				else if (comunication.getCodigoComunication() == CodigoComunication.EXIT)
				{
					recebido = comunication.getCodigoComunication();
				}
			}
			System.out.println("Client Encerrado");
			ServerSocket.close();
			primaryStage.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
