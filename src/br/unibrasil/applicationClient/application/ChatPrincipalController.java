package br.unibrasil.applicationClient.application;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.unibrasil.applicationClient.client.CliSocket;
import br.unibrasil.shared.ClientUser;
import br.unibrasil.shared.Comunication;
import br.unibrasil.shared.DTOMensagemBase;
import br.unibrasil.shared.Mensagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ChatPrincipalController implements Initializable{

	@FXML
	private Pane paneLogin;
	@FXML
	private TextField txtFNameUser;
	@FXML
	private TextField txtFNameSenha;
	@FXML 
	private Text txtStatusLogin;
	@FXML
	private Button btnLogarSecao;
	@FXML 
	private Button btnIniciarConversa;
	@FXML 
	private Text txtNameUserSeccao;
	
	@FXML
	private ListView<ClientUser> listUsersOnline;
	
	@FXML 
	private Pane paneChat;
	@FXML
	private TextField txtFNewMessage;
	@FXML
	private TextArea txtAComunication;
	@FXML
	private Button btnSend;
	
	
	private CliSocket clientSocket;	
	private ClientUser clientUser;
	private Comunication comunication;
	private ObservableList<ClientUser> usersOnline;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.comunication = new Comunication();
		usersOnline = FXCollections.observableArrayList();
		listUsersOnline.setItems(usersOnline);		
	}
	
	@FXML
	private void btnLogarOnClick(ActionEvent event)
	{
		
		try 
		{					
			if ((!txtFNameUser.getText().isEmpty())&&(!txtFNameSenha.getText().isEmpty()))
			{			
				ClientUser clientUser = new ClientUser(txtFNameUser.getText(), txtFNameSenha.getText());
				clientSocket = new CliSocket("127.0.0.1",12345);
				//
				clientSocket.clientExecute(clientUser);	//DTOMensagemBase dtoMensagemBase = 
			/*	if (dtoMensagemBase != null)
				{					
					setClientSender(dtoMensagemBase.getClientActual());
					listUsersOnlineBind(dtoMensagemBase);
				}	*/			
				
			}
			else
			{
				txtStatusLogin.setText("Não foi Possivel Logar!");
				
			}	
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	@FXML 
	private void handleListViewClick(ActionEvent event) {		
		String idComunication = Integer.toString(clientUser.getClientUserID());
		//System.out.println(listUsersOnline.getSelectionModel().getSelectedItem());
		int destinateIDList = listUsersOnline.getSelectionModel().getSelectedIndex();
		ClientUser clientRecipient = usersOnline.get(destinateIDList);
		idComunication += "-"+ clientRecipient.getClientUserID();
		System.out.println(clientRecipient.getNomeUser());
		
		List<ClientUser> clients = new ArrayList<ClientUser>();
		clients.add(clientUser);
		clients.add(clientRecipient);
		comunication.setComunicationID(idComunication);
		comunication.setClientsComunication(clients);
	} 
	
	@FXML
	private void btnSendOnClick(ActionEvent event)
	{
		try {
			Mensagem mensagem = new Mensagem();
			mensagem.setClientSender(clientUser);
			mensagem.setMensage(txtFNewMessage.getText());//txtFNewMessage.getText()//"Teste de Socket"
			
			//String mensagem = txtFNewMessage.getText();
			if (!mensagem.getMensage().isEmpty())
			{
				comunication.setItemMensagem(mensagem);
				clientSocket.sendNewMesagesComunication(comunication);				
			}
			else
			{
				System.out.println("Mensagem em branco");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setClientSender(ClientUser userClient) 
	{		
		paneLogin.setVisible(false);
		paneChat.setDisable(false);
		this.clientUser = userClient;
		txtNameUserSeccao.setText(userClient.getNomeUser());
		
	}
	
	private void listUsersOnlineBind(DTOMensagemBase dtoMensagems) {
		for (ClientUser clientUser : dtoMensagems.getUsers()) {
			usersOnline.add(clientUser);
		}		
	}

}
