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
			//ClientUser cli = (ClientUser) ois.readObject();
			ClientUser cli =  (ClientUser)  ois.readUnshared();
			iMultiComunication.SetNewClient(cli,client);	
			
		
			String Mensagem = "";
			while(!Mensagem.toUpperCase().equals("SAIR"))
			{				
				System.out.println("Aguardando Nova Mensagem");			
				Comunication comunication = (Comunication) ois.readObject();
				//Comunication  comunication = (Comunication) ois.readUnshared();
				System.out.println(comunication.getLastItemMensagem().getMensage());
				//Mensagem = comunication.getMesage();
				iMultiComunication.SetItemConversationStorage(comunication);
				iMultiComunication.SendSpecificCliet(comunication.getComunicationID());		
				
				
			}
			
/*			
 * 		
			
		//	Scanner scanner = new Scanner(client.getInputStream());
			String recebido = "";
			
			while((!recebido.toUpperCase().equals("SAIR"))&&(!recebido.toUpperCase().equals("FECHAR"))) 
			{
				count++; 
				try {
					//recebido = scanner.nextLine();
					
					iMultiComunication.SendAll(recebido);
					System.out.println("-SERVER mensage: " + recebido);	
					//cli = (ClientUser) ois.readObject();
					//recebido = cli.getMensage();
					
				} catch (Exception e) {
					recebido = "SAIR";
				}					
			}	
			client.close();O server não morrer*/
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("deu ruim");
		}
	}
}

