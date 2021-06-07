package br.unibrasil.shared;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DTOMensagemBase implements Serializable{

	private static final long serialVersionUID = 2893148236158848790L;
	private ClientUser clientActual;
	private List<ClientUser> users;
	private Comunication comunicationMansages;
	
	public ClientUser getClientActual() {
		return clientActual;
	}
	public void setClientActual(ClientUser clientActual) {
		this.clientActual = clientActual;
	}
	public List<ClientUser> getUsers() {
		return users;
	}
	public void setUsers(List<ClientUser> users) {
		this.users = users;
	}
	
	
}
