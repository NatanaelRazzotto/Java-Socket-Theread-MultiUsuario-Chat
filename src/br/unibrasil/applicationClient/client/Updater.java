package br.unibrasil.applicationClient.client;

import br.unibrasil.shared.DTOMensagemBase;

public class Updater implements Runnable{

	IClientSocket iClientSocket;
	DTOMensagemBase dtoMensagemBase;
	
	public Updater(IClientSocket iClientSocket, DTOMensagemBase dtoMensagemBase) {
		this.iClientSocket = iClientSocket;
		this.dtoMensagemBase = dtoMensagemBase;
		
	}
	@Override
	public void run() {
		iClientSocket.UpdateChat(dtoMensagemBase);		
	}

}
