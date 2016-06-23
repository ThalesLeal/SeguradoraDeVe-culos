package br.unipe.cc.mlpIII.gui;

import br.unipe.cc.mlpIII.modelo.Endereco;
import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaEditEnderecos extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaEditEnderecosScene;
	private Db db = new Db();
	private boolean inclusion;
	private Endereco endereco = new Endereco();
	
	private TextField textFieldCEP;
	private TextField textFieldRua;
	private TextField textFieldNumero;
	private TextField textFieldEstado;
	private TextField textFieldPais;
	
	private Button btnConfirmar;
	private Button btnCancelar;
	
	public TelaEditEnderecos(){
		this.inclusion = true;
		db.abrirConexao();
	}
	
	public TelaEditEnderecos(Endereco endereco){
		this.inclusion = false;
		this.endereco = endereco;
		db.abrirConexao();
	}
	
	public void show(){
		telaEditEnderecosScene.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaEditEnderecosScene = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaEditEnderecos.fxml"));
			scene = new Scene(root);

			
			telaEditEnderecosScene.setTitle((inclusion ? "Incluir " : "Editar ") + " endereço");
			telaEditEnderecosScene.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaEditEnderecosScene.setScene(scene);
			telaEditEnderecosScene.show();

			textFieldCEP = (TextField) scene.lookup("#textFieldCEP");
			textFieldRua = (TextField) scene.lookup("#textFieldRua");
			textFieldNumero = (TextField) scene.lookup("#textFieldNumero");
			textFieldEstado = (TextField) scene.lookup("#textFieldEstado");
			textFieldPais = (TextField) scene.lookup("#textFieldPais");
			
			textFieldCEP.setText(endereco.getCep());
			textFieldEstado.setText(endereco.getEstado());
			textFieldNumero.setText(endereco.getNumero());
			textFieldPais.setText(endereco.getPais());
			textFieldRua.setText(endereco.getRua());
			
			btnConfirmar = (Button) scene.lookup("#btnConfirmar");
			btnConfirmar.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					confirmar();
				}
			});
			btnCancelar = (Button) scene.lookup("#btnCancelar");
			btnCancelar.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					fecharTelaEditEnderecos();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fecharTelaEditEnderecos(){
		db.fecharConexao();
		telaEditEnderecosScene.hide();
	}
	
	public void confirmar(){
		if (inclusion){
			db.comando("INSERT INTO endereco (rua, cep, numero, estado, pais) VALUES ('"+textFieldRua.getText()+"','"+textFieldCEP.getText()+"','"+textFieldNumero.getText()+"','"+textFieldEstado.getText()+"','"+textFieldPais.getText()+"')");
		} else {
			db.comando("UPDATE endereco SET rua = '"+textFieldRua.getText()+"', cep = '"+textFieldCEP.getText()+"', numero = '"+textFieldNumero.getText()+"', estado = '"+textFieldEstado.getText()+"', pais = '"+textFieldPais.getText()+"' WHERE id = "+endereco.getId());
		}

		fecharTelaEditEnderecos();
	}

}
