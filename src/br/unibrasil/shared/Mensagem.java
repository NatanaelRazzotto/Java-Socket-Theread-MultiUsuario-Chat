package br.unibrasil.shared;

import java.io.Serializable;

public class Mensagem implements Serializable{
	private static final long serialVersionUID = 237395856344274148L;
	private ClientUser clientSender;
	private String mensage;
	
	public ClientUser getClientSender() {
		return clientSender;
	}
	public void setClientSender(ClientUser clientSender) {
		this.clientSender = clientSender;
	}
	public String getMensage() {
		return mensage;
	}
	public void setMensage(String mensage) {
		this.mensage = mensage;
	}
	
	
	
	
	
}
