package br.unibrasil.shared;

import java.io.Serializable;

public class ClientUser implements Serializable{

	private static final long serialVersionUID = -1990682105391806077L;
	private int clientUserID;
	//private int DestinatarioUserID;
	private String nomeUser;
	//private String email;
	private String senha;
//	private String mensage;
	
	
/*	public int getDestinatarioUserID() {
		return DestinatarioUserID;
	}
	public void setDestinatarioUserID(int destinatarioUserID) {
		DestinatarioUserID = destinatarioUserID;
	}
	public String getMensage() {
		return mensage;
	}
	public void setMensage(String mensage) {
		this.mensage = mensage;
	}*/
	public int getClientUserID() {
		return clientUserID;
	}
	public void setClientUserID(int clientUserID) {
		this.clientUserID = clientUserID;
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}
/*	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}*/
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public ClientUser(String nomeUser, String senha) {
		super();
		this.nomeUser = nomeUser;
		this.senha = senha;
	}
	
  /* @Override
    public String toString() {
        return "Employee [nomeUser=" + nomeUser + ", senha=" + senha + "]";
    }*/
	
    public String toString() {
        return "NomeUser=[" + nomeUser + "] IdUser=["+clientUserID+"]";
    }


}
