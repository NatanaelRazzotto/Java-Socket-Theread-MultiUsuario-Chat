package br.unibrasil.applicationClient.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import br.unibrasil.shared.DTOMensagemBase;

public class ReceiveMessages implements Runnable{
	
	private InputStream inputServerSocket;
	private IClientSocket iClientSocket;

	public ReceiveMessages(InputStream inputServerSocket,IClientSocket iClientSocket) {
		this.inputServerSocket = inputServerSocket;		
		this.iClientSocket = iClientSocket;
	}	

	@Override
	public void run() {
		try 
		{	
			String recebido = "";
			ObjectInputStream objServerSocket = new ObjectInputStream(inputServerSocket);		
			while (!recebido.toUpperCase().equals("SAIR")) {
				DTOMensagemBase dtoMensagemBase = (DTOMensagemBase) objServerSocket.readObject();
				System.out.println("Recebeu Mensagem");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
