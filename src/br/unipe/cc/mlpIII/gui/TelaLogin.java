package br.unipe.cc.mlpIII.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaLogin extends Application{
	private TextField user;
	private PasswordField pass;
	private ResultSet usersInDataBase;
	private Db db = new Db();
	private Scene scene;
	private Parent root;
	private Stage telaLoginStage;
	private Button login;
	private boolean autorizado;
	
	public TelaLogin(){
		db.abrirConexao();
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.telaLoginStage = primaryStage;
		
		try {
			root = FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));
			scene = new Scene(root);
			
			this.telaLoginStage.setTitle("DELL SEGUROS");
			this.telaLoginStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			this.telaLoginStage.setScene(scene);
			this.telaLoginStage.show();
			
			login = (Button) scene.lookup("#login");
			user = (TextField) scene.lookup("#user");
			pass = (PasswordField) scene.lookup("#pass");

			login.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					login();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void login() {
		boolean achou = false;

		usersInDataBase = db.consulta("SELECT * FROM usuario WHERE id = '" + user.getText() + "' and senha = '" + pass.getText() + "'" );
		
		try {
			while(usersInDataBase.next()){
				achou = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (achou) {
			autorizado = true;
			fecharTelaLogin();
		} else
			erroLogin();
	}
	
	public void fecharTelaLogin(){
		db.fecharConexao();
		telaLoginStage.hide();
	}
	
	public void erroLogin(){
		Alert warning = new Alert(Alert.AlertType.WARNING);
		warning.setTitle("Erro de login");
		warning.setHeaderText("Usuário ou senha estão incorretos, tente novamente.");
		warning.showAndWait();
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	
}
