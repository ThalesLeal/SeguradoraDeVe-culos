package br.unipe.cc.mlpIII.gui;

import br.unipe.cc.mlpIII.modelo.Sms;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaSmsBox extends Application{
	private Scene scene;
	private Parent root;
	private Stage telaSmsBoxStage;
	
	private TextField textFieldNumero;
	private TextArea textAreaMensagem;
	
	private Button btnEnviar;
	private Button btnCancelar;
	
	private Sms sms = new Sms();

	public void show(){
		telaSmsBoxStage.show();
	}	
	
	@Override
	public void start(Stage telaSmsBoxStage) throws Exception {
		this.telaSmsBoxStage = telaSmsBoxStage;
		
		try {
			root = FXMLLoader.load(getClass().getResource("TelaSmsBox.fxml"));
			scene = new Scene(root);
			
			textFieldNumero = (TextField) scene.lookup("#textFieldNumero");
			textAreaMensagem = (TextArea) scene.lookup("#textAreaMensagem");

			btnEnviar = (Button) scene.lookup("#btnEnviar");
			btnEnviar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (enviarMensagem()){
						Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
			            dialogoInfo.setTitle("Sms enviado");
			            dialogoInfo.setHeaderText("Sua menssagem foi enviada com sucesso!!!");
			            dialogoInfo.showAndWait();
			            
						fecharTelaSmsBox();
					} else {
						Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
			            dialogoInfo.setTitle("Erro ao enviar Sms");
			            dialogoInfo.setHeaderText("Não foi possível enviar sua mensagem.");
			            dialogoInfo.showAndWait();
					}
				}
			});
			
			btnCancelar = (Button) scene.lookup("#btnCancelar");
			btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					fecharTelaSmsBox();
				}
			});
			
			this.telaSmsBoxStage.setTitle("Enviar Sms");
			this.telaSmsBoxStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			this.telaSmsBoxStage.setScene(scene);
			this.telaSmsBoxStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharTelaSmsBox(){
		telaSmsBoxStage.hide();
	}
	
	public boolean enviarMensagem(){
		sms.setNumeroCelular(textFieldNumero.getText());
		sms.setMenssagem(textAreaMensagem.getText());
		
		try {
			sms.enviar();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
