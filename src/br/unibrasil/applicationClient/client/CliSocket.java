package br.unibrasil.applicationClient.client;

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
	
	public CliSocket(String host,int port) {
		this.host = host;
		this.port = port;		
	//	clientUser = new ClientUser();
	}
	public void clientExecute(ClientUser clientUser) {		
		//DTOMensagemBase dtoMensagemBase = null;
		try {
			client = new Socket(host,port);
			
			objClient = new ObjectOutputStream(client.getOutputStream());
			objClient.writeObject(clientUser);				
	
			Thread receiver = new Thread(new ReceiveMessages(client.getInputStream(), this));
			receiver.start();	
			
		} catch (IOException e) {

			e.printStackTrace();
		}			
		//return dtoMensagemBase;
	}
	public Comunication sendNewMesagesComunication(Comunication communication) {
		try 
		{
			objClient.writeObject(communication);
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
}
