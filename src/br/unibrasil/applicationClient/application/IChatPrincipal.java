package br.unibrasil.applicationClient.application;

import java.util.List;

import br.unibrasil.shared.ClientUser;
import br.unibrasil.shared.Comunication;

public interface IChatPrincipal {

	void UpdateFromPropertsUser(ClientUser clientActual);

	void UpdateFromUsersOnline(List<ClientUser> users);

	void UpdateChat(Comunication comunicationMensagems);

}
