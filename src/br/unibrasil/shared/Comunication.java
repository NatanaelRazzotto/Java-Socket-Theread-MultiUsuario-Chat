package br.unibrasil.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comunication implements Serializable{
	private static final long serialVersionUID = 7715544495365543785L;
	private CodigoComunication codigoComunication;
	private String ComunicationID;	
	private List<ClientUser> clientsComunication;
	private List<Mensagem> mensages;
	

	public Comunication() {
		super();		
		this.clientsComunication = new ArrayList<ClientUser>();
		this.mensages = new ArrayList<Mensagem>();
	}
	public List<ClientUser> getClientsComunication() {
		return clientsComunication;
	}
	public void setClientsComunication(List<ClientUser> clientsComunication) {
		this.clientsComunication = clientsComunication;
	}
	public String getComunicationID() {
		return ComunicationID;
	}
	public void setComunicationID(String comunicationID) {
		ComunicationID = comunicationID;
	}
	public List<Mensagem> getMensage() {
		return mensages;
	}
	public void setMensage(List<Mensagem> mensages) {
		this.mensages = mensages;
	}
	public void setItemMensagem(Mensagem newMensagem) {
		this.mensages.add(newMensagem);
	}
	public Mensagem getLastItemMensagem() {
		return this.mensages.get(mensages.size()-1);
	}
	public CodigoComunication getCodigoComunication() {
		return codigoComunication;
	}
	public void setCodigoComunication(CodigoComunication codigoComunication) {
		this.codigoComunication = codigoComunication;
	}
	
	
	
}
