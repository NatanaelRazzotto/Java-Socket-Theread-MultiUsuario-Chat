package br.unibrasil.applicationServer;

import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import br.unibrasil.shared.*;

public class MultiComunication implements Runnable {
	
	private Socket client;
	private IMultiComunication iMultiComunication;

	public MultiComunication(IMultiComunication iMultiComunication, Socket client)
	{
		this.client = client;	
		this.iMultiComunication = iMultiComunication;
	}
	
	public void run() 
	{	
		try {
			int count = 0;
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			ClientUser cli =  (ClientUser)  ois.readUnshared();
			iMultiComunication.SetNewClient(cli,client);	
					
			String Mensagem = "";
			while(!Mensagem.toUpperCase().equals("SAIR"))
			{				
				try {
					System.out.println("Aguardando Nova Mensagem");			
					Comunication comunication = (Comunication) ois.readObject();
					//Comunication  comunication = (Comunication) ois.readUnshared();
					System.out.println(comunication.getLastItemMensagem().getMensage());
					//Mensagem = comunication.getMesage();
					iMultiComunication.SetItemConversationStorage(comunication);
					iMultiComunication.SendSpecificCliet(comunication.getComunicationID());		
				} catch (Exception e) {
					Mensagem = "SAIR";
				}				
			}
			client.close();
			iMultiComunication.removeClientOfConnection(cli);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("O Server Encontrou Problemas");
		}
	}
}

