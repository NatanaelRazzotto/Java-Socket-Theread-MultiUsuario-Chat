package br.unibrasil.applicationServer;

import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import br.unibrasil.shared.*;

public class MultiComunication implements Runnable {
	private ServerSocket server;
	private Socket client;
	private IMultiComunication iMultiComunication;

	public MultiComunication(IMultiComunication iMultiComunication, Socket client,ServerSocket server)
	{
		this.client = client;	
		this.iMultiComunication = iMultiComunication;
		this.server = server;
	}
	
	public void run() 
	{	
		try {
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			ClientUser cli =  (ClientUser)  ois.readUnshared();
			iMultiComunication.SetNewClient(cli,client);	
					
			CodigoComunication Mensagem = CodigoComunication.CONVERSATION;
			while(!Mensagem.equals(CodigoComunication.EXIT))
			{				
				try {
					System.out.println("Aguardando Nova Mensagem");			
					Comunication comunication = (Comunication) ois.readObject();
					if (comunication.getCodigoComunication().equals(CodigoComunication.CONVERSATION))
					{
						System.out.println("Mensagem recebida: " +comunication.getLastItemMensagem().getMensage());
						iMultiComunication.SetItemConversationStorage(comunication);
						iMultiComunication.SendSpecificCliet(comunication.getComunicationID());	
					}
					else if(comunication.getCodigoComunication().equals(CodigoComunication.EXIT))
					{
						Mensagem = CodigoComunication.EXIT;
						iMultiComunication.exitClient(client);
						System.out.println("O Usuário saiu da seção");
						client.close();
						iMultiComunication.removeClientOfConnection(cli);
					}
					else if(comunication.getCodigoComunication().equals(CodigoComunication.SERVEROUT))
					{
						Mensagem = CodigoComunication.EXIT;
						//iMultiComunication.exitAllClients();
						System.out.println("O Usuário saiu da seção");
						client.close();
						server.close();
						//iMultiComunication.removeClientOfConnection(cli);
					}
				} catch (Exception e) {
					Mensagem = CodigoComunication.EXIT;
					client.close();
					iMultiComunication.removeClientOfConnection(cli);
				}				
			}


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("O Server Encontrou Problemas");
		}
	}
}

