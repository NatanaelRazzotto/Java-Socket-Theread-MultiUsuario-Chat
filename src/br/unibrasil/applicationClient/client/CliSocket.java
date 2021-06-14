package br.unibrasil.applicationClient.client;

import br.unibrasil.applicationClient.application.IChatPrincipal;
import br.unibrasil.shared.ClientUser;
import br.unibrasil.shared.CodigoComunication;
import br.unibrasil.shared.Comunication;
import br.unibrasil.shared.DTOMensagemBase;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class CliSocket implements IClientSocket{
	private String host;
	private int port;
	private ClientUser clientUser;
	private Socket client;
	private ObjectOutputStream objClient;
	private Stage primaryStage;
	
	private IChatPrincipal chatPrincipal;
	
	public CliSocket(IChatPrincipal chatPrincipal,String host,int port,Stage primaryStage) {
		this.chatPrincipal = chatPrincipal;
		this.host = host;
		this.port = port;	
		this.primaryStage = primaryStage;
	}
	public void clientExecute(ClientUser clientUser) throws ClassNotFoundException {	
		
		try {
			client = new Socket(host,port);
			
			objClient = new ObjectOutputStream(client.getOutputStream());
			objClient.writeObject(clientUser);		

			Thread receiver = new Thread(new ReceiveMessages(client, this, primaryStage));
		    receiver.start();				
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	public boolean sendNewMesagesComunication(Comunication communication) {
		try 
		{
			objClient.writeObject(communication);
			objClient.flush();
			System.out.println("Nova mensagem submetida");
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	public void Execute() {		
		try {
			Socket client = new Socket(host,port);			
			ObjectOutputStream objClient = new ObjectOutputStream(client.getOutputStream());
			System.out.println("Cliente conectado no servidor");			
			System.out.println("Para encerrar o Cliente: SAIR;");	
			objClient.writeObject(clientUser);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void UpdateChat(DTOMensagemBase dtoMensagemBase) {		
	
		if ((dtoMensagemBase.getClientActual()!=null))
		{
			chatPrincipal.UpdateFromPropertsUser(dtoMensagemBase.getClientActual());			
		}				
		if (dtoMensagemBase.getUsers().size() > 0)
		{
			chatPrincipal.UpdateFromUsersOnline(dtoMensagemBase.getUsers());
		}
		if (dtoMensagemBase.getComunicationMensagems().getCodigoComunication() != CodigoComunication.LOGIN)
		{
			chatPrincipal.UpdateChat(dtoMensagemBase.getComunicationMensagems());
		}	
		
	}
	public void CloseClient() {
		Comunication comunication = new Comunication();
		comunication.setCodigoComunication(CodigoComunication.EXIT);
		sendNewMesagesComunication(comunication);		
	}
	public void CloseServer() {
		Comunication comunication = new Comunication();
		comunication.setCodigoComunication(CodigoComunication.SERVEROUT);
		sendNewMesagesComunication(comunication);		
	}
}
