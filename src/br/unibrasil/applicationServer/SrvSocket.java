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
//	private ArrayList<PrintStream> listPrintStreams = new ArrayList<PrintStream>();
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
					
					//listPrintStreams.add(new PrintStream(client.getOutputStream()));
					new Thread(new MultiComunication(this,client)).start();
				}
			//server.close();  

		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	@Override
	public synchronized void SendAll(String mensage) {
		//listPrintStreams.forEach(saida -> saida.println(mensage));
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
		
		dtoMensagemBase.setClientActual(user);
		dtoMensagemBase.setUsers(getUserOnline());
		//SendListClientsConenction(dtoMensagemBase,socketClient);
		SendGenericClient(dtoMensagemBase);
	}
	
	private List<ClientUser> getUserOnline()
	{
		Set<ClientUser> users = listClientsConnected.keySet();
		return new ArrayList<ClientUser>(users);
	}
	
	private void preparingSendMensagems(Comunication comunicate,Socket Client) 
	{
		DTOMensagemBase dtoMensagemBase = new DTOMensagemBase();
		dtoMensagemBase.setComunicationMensagems(comunicate);
		dtoMensagemBase.setUsers(getUserOnline());
		SendListClientsConenctionn(dtoMensagemBase,Client);
		
	}
	
	public void SendListClientsConenctionn(DTOMensagemBase dtoMensagemBase ,Socket Client)
	{
		try {
			ObjectOutputStream objClient = new ObjectOutputStream(Client.getOutputStream());
			objClient.writeObject(dtoMensagemBase);
			objClient.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void SendListClientsConenction(DTOMensagemBase dtoMensagemBase ,Socket Client)
	{
		try {
			ObjectOutputStream objClient = new ObjectOutputStream(Client.getOutputStream());
			objClient.writeObject(dtoMensagemBase);
			objClient.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public synchronized void SendSpecificCliet(String comunicationID) 
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
							break;
						}
						
					}					
					System.out.println(comunicate.getLastItemMensagem().getMensage());
					preparingSendMensagems(comunicate,socket);
				//	ObjectOutputStream objSocketClient = new ObjectOutputStream(socket.getOutputStream());
				//	objSocketClient.writeObject(comunicate);

				}		
			}

	}
	public synchronized void SendGenericClient(DTOMensagemBase dtoMensagemBase ) 
	{
		for (Socket socket : listClientsConnected.values())
		{
			SendListClientsConenction(dtoMensagemBase,socket);
		}	
	}
	

	/*public synchronized Comunication getSetItemConversationStorage(Comunication newComunication)
	{
		boolean validate = false;
		for (Comunication comunication : comunicationStorage) 
		{
			if (comunication.getComunicationID() == newComunication.getComunicationID())
			{
				comunication.set
				return comunication;				
			}
		}
		return null;		
	}*/

	
	
	@Override
	public synchronized void SetItemConversationStorage(Comunication newComunication) {
		Comunication comunicate = getItemConversationStorage(newComunication.getComunicationID());
		if (comunicate!=null)
		{
			//comunicate = newComunication;
			for (Mensagem mensagem : newComunication.getMensage()) {
				comunicate.setItemMensagem(mensagem);
			}
			setItemConversationStorage(comunicate);
		}
		else
		{
			comunicationStorage.add(newComunication);			
		}
		
	}
	public synchronized boolean setItemConversationStorage(Comunication newComunication)
	{
		int limite = newComunication.getComunicationID().length()-1;
		String arrayID[] = new String[limite];
		arrayID = newComunication.getComunicationID().split("-");
		String condicional = arrayID[0]+"-"+arrayID[1];
		String condicionalInvertido =  arrayID[1]+"-"+arrayID[0];
		for (Comunication comunication : comunicationStorage) 
		{			
			if ((comunication.getComunicationID().equals(condicional)||comunication.getComunicationID().equals(condicionalInvertido)))
			{
				comunication = newComunication;
				return true;				
			}
		}
		return false;		
	}
	@Override
	public synchronized Comunication getItemConversationStorage(String comunicationID)
	{
		int limite = comunicationID.length()-1;
		String arrayID[] = new String[limite];
		arrayID = comunicationID.split("-");
		String condicional = arrayID[0]+"-"+arrayID[1];
		String condicionalInvertido =  arrayID[1]+"-"+arrayID[0];
		for (Comunication comunication : comunicationStorage) 
		{
			//String a = comunication.getComunicationID().toString();
			if ((comunication.getComunicationID().equals(condicional)||comunication.getComunicationID().equals(condicionalInvertido)))
			{
				return comunication;				
			}
		}
		return null;		
	}
	@Override
	public void removeClientOfConnection(ClientUser cli) {
			
		boolean validate = false;
		for (ClientUser clientSearch : listClientsConnected.keySet())
		{
			if (clientSearch.getClientUserID() == cli.getClientUserID())
			{
				listClientsConnected.remove(clientSearch);
				validate = true;
				break;
			}			
		}	
		if (validate) {		
			
			Mensagem mensagem = new Mensagem();
			mensagem.setClientSender(cli);
			mensagem.setMensage("Usuário " + cli.getNomeUser() + " Desconectou da Sala....");
			List<Mensagem> mensagems = new ArrayList<Mensagem>();
			mensagems.add(mensagem);
			Comunication comunication = new Comunication();
			comunication.setMensage(mensagems);
			DTOMensagemBase dtoMensagemBase = new DTOMensagemBase();
			dtoMensagemBase.setUsers(getUserOnline());
			dtoMensagemBase.setComunicationMensagems(comunication);
			SendGenericClient(dtoMensagemBase);
		}
		
	}

}
