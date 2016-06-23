package br.unipe.cc.mlpIII.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaPrincipalStage;
	private Stage telaCadastroEnderecosStage;
	private Stage telaCadastroSegurosStage;
	private Stage telaCadastroTitularStage;
	private Stage telaCadastroVeiculoStage;
	private Stage telaSmsBoxStage;
	private Button btnEnderecos;
	private Button btnSeguros;
	private Button btnTitulares;
	private Button btnVeiculos;
	private Button btnSms;
	
	public void show(){
		telaPrincipalStage.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaPrincipalStage = primaryStage;
		
		TelaCadastroEnderecos telaCadastroEnderecos = new TelaCadastroEnderecos();
		TelaCadastroSeguros telaCadastroSeguros = new TelaCadastroSeguros();
		TelaCadastroTitular telaCadastroTitular = new TelaCadastroTitular();
		TelaCadastroVeiculo telaCadastroVeiculo = new TelaCadastroVeiculo();
		TelaSmsBox telaSmsBox = new TelaSmsBox();
		
		try {
			root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
			scene = new Scene(root);
			
			this.telaPrincipalStage.setTitle("DELL SEGUROS");
			this.telaPrincipalStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			this.telaPrincipalStage.setScene(scene);
			this.telaPrincipalStage.show();
			
			btnEnderecos = (Button) scene.lookup("#btnEnderecos");
			btnEnderecos.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						telaCadastroEnderecosStage = new Stage();
						telaCadastroEnderecos.start(telaCadastroEnderecosStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
			});
			
			btnSeguros = (Button) scene.lookup("#btnSeguros");
			btnSeguros.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						telaCadastroSegurosStage = new Stage();
						telaCadastroSeguros.start(telaCadastroSegurosStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
			});

			btnTitulares = (Button) scene.lookup("#btnTitulares");
			btnTitulares.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						telaCadastroTitularStage = new Stage();
						telaCadastroTitular.start(telaCadastroTitularStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
			});

			btnVeiculos = (Button) scene.lookup("#btnVeiculos");
			btnVeiculos.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						telaCadastroVeiculoStage = new Stage();
						telaCadastroVeiculo.start(telaCadastroVeiculoStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
			});
			
			btnSms = (Button) scene.lookup("#btnSms");
			btnSms.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						telaSmsBoxStage = new Stage();
						telaSmsBox.start(telaSmsBoxStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
