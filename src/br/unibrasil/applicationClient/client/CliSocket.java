package br.unibrasil.applicationClient.client;

import br.unibrasil.applicationClient.application.IChatPrincipal;
import br.unibrasil.shared.ClientUser;
import br.unibrasil.shared.Comunication;
import br.unibrasil.shared.DTOMensagemBase;

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
	
	private IChatPrincipal chatPrincipal;
	
	public CliSocket(IChatPrincipal chatPrincipal,String host,int port) {
		this.chatPrincipal = chatPrincipal;
		this.host = host;
		this.port = port;		
	//	clientUser = new ClientUser();
	}
	public void clientExecute(ClientUser clientUser) throws ClassNotFoundException {		
		DTOMensagemBase dtoMensagemBase = null;
		try {
			client = new Socket(host,port);
			
			objClient = new ObjectOutputStream(client.getOutputStream());
			objClient.writeObject(clientUser);		
			
			//ObjectInputStream objServerSocket = new ObjectInputStream(client.getInputStream());
			
			//ObjectInputStream objServeSocket = new ObjectInputStream(client.getInputStream());	
			//DTOMensagemBase dtoMensagemBse = (DTOMensagemBase) objServeSocket.readObject();
			
			
			Thread receiver = new Thread(new ReceiveMessages(client, this));
		    receiver.start();				
		
			
		} catch (IOException e) {

			e.printStackTrace();
		}			
	}
	public Comunication sendNewMesagesComunication(Comunication communication) {
		try 
		{
			objClient.writeObject(communication);
			objClient.flush();
			System.out.println("Nova mensagem submetida");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	public DTOMensagemBase receiveMensagesFromServidor() {
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public void Execute() {		
		try {
			Socket client = new Socket(host,port);			
			ObjectOutputStream objClient = new ObjectOutputStream(client.getOutputStream());
			System.out.println("Cliente conectado no servidor");			
			System.out.println("Para encerrar o Cliente: SAIR;");	
			//clientUser.setMensage("Teste Hello");
			objClient.writeObject(clientUser);
			
			//new Thread(new ReceiveMessages(client.getInputStream())).start();
			
			/*String linha = "";
			while ((!linha.toUpperCase().equals("SAIR"))&&(!linha.toUpperCase().equals("FECHAR"))) {
				clientUser.setMensage(teclado.nextLine());
				objClient.writeObject(clientUser);
			//	linha = teclado.nextLine();
			//	saida.println(linha);	
			}	*/	
			
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
		if (dtoMensagemBase.getComunicationMensagems() !=null)
		{
			chatPrincipal.UpdateChat(dtoMensagemBase.getComunicationMensagems());
		}
		
		
		/*if (dtoMensagemBase != null)
		{					
			setClientSender(dtoMensagemBase.getClientActual());
			listUsersOnlineBind(dtoMensagemBase);
		}	*/		
		
	}
}
