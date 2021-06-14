package br.unibrasil.applicationServer;

import java.net.Socket;

import br.unibrasil.shared.*;

public interface IMultiComunication {
	void SetNewClient(ClientUser user, Socket socketClient);
	void SendListClientsConenction(DTOMensagemBase dtoMensagemBase,Socket Client);
	void SendSpecificCliet(String comunicationID);
	void SetItemConversationStorage(Comunication comunication);
	Comunication getItemConversationStorage(String comunicationID);
	void removeClientOfConnection(ClientUser cli);
	void exitClient(Socket socketClient);
	void exitAllClients();
}
