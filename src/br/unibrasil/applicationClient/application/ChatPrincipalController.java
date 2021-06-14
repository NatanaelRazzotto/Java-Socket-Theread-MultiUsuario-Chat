package br.unibrasil.applicationClient.application;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.unibrasil.applicationClient.client.CliSocket;
import br.unibrasil.shared.ClientUser;
import br.unibrasil.shared.CodigoComunication;
import br.unibrasil.shared.Comunication;
import br.unibrasil.shared.DTOMensagemBase;
import br.unibrasil.shared.Mensagem;
import javafx.application.Platform;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatPrincipalController implements Initializable , IChatPrincipal{

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
	private Text txtIdUserSeccao;
	
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
	@FXML
	private Button btnClose;
	
	@FXML
	private Text txtNameUserActual;
	@FXML
	private Text txtNameUserInChannel;
	
	
	private CliSocket clientSocket;	
	private ClientUser clientUser;
	private Comunication comunication;
	private ObservableList<ClientUser> usersOnline;
	private Stage primaryStage;
	
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
			if ((!txtFNameUser.getText().isEmpty()))
			{			
				ClientUser clientUser = new ClientUser(txtFNameUser.getText(),"");
				clientSocket = new CliSocket(this,"127.0.0.1",60000, primaryStage);
				clientSocket.clientExecute(clientUser);					
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
		String idComunication = "";
		List<ClientUser> clients = new ArrayList<ClientUser>();		
		int destinateIDList = listUsersOnline.getSelectionModel().getSelectedIndex();
		ClientUser clientRecipient = usersOnline.get(destinateIDList);
		
		
		if (clientUser.getClientUserID() == clientRecipient.getClientUserID())
		{
			clients.add(clientUser);
			idComunication = Integer.toString(clientUser.getClientUserID());
			idComunication += "-" + Integer.toString(clientUser.getClientUserID());
		}
		else
		{
			clients.add(clientUser);
			clients.add(clientRecipient);
			idComunication = Integer.toString(clientUser.getClientUserID());
			idComunication += "-"+ clientRecipient.getClientUserID();
		}		

		comunication.setComunicationID(idComunication);
		comunication.setClientsComunication(clients);
	} 
	@FXML
	private void btnSendOnClick(ActionEvent event)
	{
		try {
			Mensagem mensagem = new Mensagem();
			mensagem.setClientSender(clientUser);
			mensagem.setMensage(txtFNewMessage.getText());

			if (!mensagem.getMensage().isEmpty())
			{
				if (comunication.getComunicationID() != null)
				{
					Comunication comunica = new Comunication();
					comunica.setCodigoComunication(CodigoComunication.CONVERSATION);
					comunica.setComunicationID(comunication.getComunicationID());
					comunica.setClientsComunication(comunication.getClientsComunication());
					comunica.setItemMensagem(mensagem);
					boolean checkSend = clientSocket.sendNewMesagesComunication(comunica);		
					if (checkSend)
					{
						txtFNewMessage.clear();					
					}
					else
					{
						System.out.println("A Mensagem não foi enviada!");
					}
				}
				else
				{
					txtAComunication.setText("!!! Defina quem recebe a mensagem !!!!");
				}
			}
			else
			{
				System.out.println("Mensagem em branco");
			}			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@FXML
	private void handleBtnClose(ActionEvent event)
	{
		clientSocket.CloseServer();
		System.out.println("Encerrando Server");
	}

	@Override
	public void UpdateFromPropertsUser(ClientUser clientActual) {
		if ((clientUser==null))
		{
			paneLogin.setVisible(false);
			paneChat.setDisable(false);
			this.clientUser = clientActual;
			txtNameUserSeccao.setText(clientActual.getNomeUser());	
			txtIdUserSeccao.setText(Integer.toString(clientActual.getClientUserID()));
		}
	}

	@Override
	public void UpdateFromUsersOnline(List<ClientUser> users) {	
		
		usersOnline.clear();
		for (ClientUser clientUser : users) {
			usersOnline.add(clientUser);
		}

	}

	@Override
	public void UpdateChat(Comunication comunicationMensagems) {
		
		setPropertsComunication(comunicationMensagems);
		if (comunicationMensagems.getComunicationID() != null)
		{
			if (comunicationMensagems.getComunicationID().equals(comunication.getComunicationID()))
			{
				showMensages(comunicationMensagems.getMensage());			
			}
			else
			{
				txtAComunication.clear();
				showMensages(comunicationMensagems.getMensage());				
			}	
		}
		else
		{
			showMensages(comunicationMensagems.getMensage());	
		}
	}
	private void setPropertsComunication (Comunication users) {
		showPropertsChat(); 
		if (users.getComunicationID() != null)
		{
			comunication.setComunicationID(users.getComunicationID());
			comunication.setClientsComunication(users.getClientsComunication());
		}
	}
	private void showMensages(List<Mensagem> listMensagens) {
		String content = "";
		for (Mensagem mensagem : listMensagens) {
			content += "[ Enviada por: " + mensagem.getClientSender().getNomeUser()+"] " + mensagem.getMensage() + "\n";
		}
		txtAComunication.setText(content);
		
	}
	private void showPropertsChat() 
	{
		if (clientUser !=null)
		{
			txtNameUserActual.setText(clientUser.getNomeUser());			
		}
		if (comunication != null)
		{
			String content = "";
			List<ClientUser> usersClients = comunication.getClientsComunication();
			for (ClientUser clientUser : usersClients) {
				content += "><" +clientUser.getNomeUser();
			}
			txtNameUserInChannel.setText(content);			
		}
		
	}

	public void setStageAndSetup(Stage primaryStage) {
		this.primaryStage = primaryStage;		
		System.out.println("Referenciando a Stage da Aplicação");
	} 
	public void setCloseClient() {
		System.out.println("Encerrando Client");
		clientSocket.CloseClient();
		
	} 

}
