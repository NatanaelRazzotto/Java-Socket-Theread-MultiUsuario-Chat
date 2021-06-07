package br.unibrasil.applicationServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import br.unibrasil.shared.*;

public class SrvSocket implements IMultiComunication {
	private ArrayList<PrintStream> listPrintStreams = new ArrayList<PrintStream>();
	private HashMap<ClientUser, Socket> listClientsConnected = new HashMap<ClientUser, Socket>();
	private List<Comunication> comunicationStorage = new ArrayList<Comunication>();
	
	public void execute() {
		try {
			ServerSocket server = new ServerSocket(12345);	
			
				while(true) 
				{					
					System.out.println("Aguardando conexão");
					Socket client = server.accept();
					System.out.println("Conectou!!");
					
					listPrintStreams.add(new PrintStream(client.getOutputStream()));
					new Thread(new MultiComunication(this,client)).start();
				}
			//server.close();  

		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	@Override
	public synchronized void SendAll(String mensage) {
		listPrintStreams.forEach(saida -> saida.println(mensage));
	}
	@Override
	public synchronized void SetNewClient(ClientUser user, Socket socketClient) {
		DTOMensagemBase dtoMensagemBase = new DTOMensagemBase();
		int qtdConected = listClientsConnected.size();		
		if (qtdConected == 0)
		{
			user.setClientUserID(1);
			listClientsConnected.put(user, socketClient);	

		}
		else
		{
			user.setClientUserID(qtdConected + 1);	
			listClientsConnected.put(user, socketClient);	

		}	
		Set<ClientUser> users = listClientsConnected.keySet();
		List<ClientUser> list = new ArrayList<ClientUser>(users);
		
		dtoMensagemBase.setClientActual(user);
		dtoMensagemBase.setUsers(list);
		SendListClientsConenction(dtoMensagemBase,socketClient);
	}
	
	public void SendListClientsConenction(DTOMensagemBase dtoMensagemBase ,Socket Client)
	{
		try {
			ObjectOutputStream objClient = new ObjectOutputStream(Client.getOutputStream());
			objClient.writeObject(dtoMensagemBase);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public synchronized void SendSpecificCliet(String comunicationID) 
	{
		try 
		{
			Comunication comunicate = getItemConversationStorage(comunicationID);
			if (comunicate!=null)
			{
				for (ClientUser cliente : comunicate.getClientsComunication()) {
					
					Socket socket = null;
					for (ClientUser clientSearch : listClientsConnected.keySet())
					{
						if (clientSearch.getClientUserID() == cliente.getClientUserID())
						{
							socket = listClientsConnected.get(clientSearch);							
						}
						
					}					
					System.out.println(comunicate.getLastItemMensagem().getMensage());
					ObjectOutputStream objSocketClient = new ObjectOutputStream(socket.getOutputStream());
					objSocketClient.writeObject(comunicate);

				}		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public synchronized void SetItemConversationStorage(Comunication newComunication) {
		Comunication comunicate = getItemConversationStorage(newComunication.getComunicationID());
		if (comunicate!=null)
		{
			comunicate = newComunication;			
		}
		else
		{
			comunicationStorage.add(newComunication);			
		}
		
	}
	@Override
	public synchronized Comunication getItemConversationStorage(String comunicationID)
	{
		for (Comunication comunication : comunicationStorage) 
		{
			if (comunication.getComunicationID() == comunicationID)
			{
				return comunication;				
			}
		}
		return null;		
	}

}
