package br.unipe.cc.mlpIII.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unipe.cc.mlpIII.modelo.Titular;
import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SuppressWarnings("unchecked")
public class TelaEditTitular extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaEditTitularScene;
	private Db db = new Db();
	private boolean inclusion;
	private Titular titular = new Titular();
	
	private TextField textFieldNome;
	private TextField textFieldCPF;
	private TextField textFieldIdentidade;
	private TextField textFieldTelefone;
    private ComboBox<String> comboBoxEndereco;

	private Button btnConfirmar;
	private Button btnCancelar;

	
	public TelaEditTitular(){
		this.inclusion = true;
		db.abrirConexao();
	}
	
	public TelaEditTitular(Titular titular){
		this.inclusion = false;
		this.titular = titular;
		db.abrirConexao();
	}	
	
	public void show(){
		telaEditTitularScene.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaEditTitularScene = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaEditTitular.fxml"));
			scene = new Scene(root);

			telaEditTitularScene.setTitle((inclusion ? "Incluir " : "Editar ") + " titular");
			telaEditTitularScene.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaEditTitularScene.setScene(scene);
			telaEditTitularScene.show();
			
			textFieldNome = (TextField) scene.lookup("#textFieldNome");
			textFieldCPF = (TextField) scene.lookup("#textFieldCPF");
			textFieldIdentidade = (TextField) scene.lookup("#textFieldIdentidade");
			textFieldTelefone = (TextField) scene.lookup("#textFieldTelefone");
			comboBoxEndereco = (ComboBox<String>) scene.lookup("#comboBoxEndereco");
			comboBoxEndereco.setItems(getComboBoxData());
			
			textFieldNome.setText(titular.getNome());
			textFieldCPF.setText(titular.getCpf());
			textFieldIdentidade.setText(titular.getIdentidade());
			textFieldTelefone.setText(titular.getTelefone());
			comboBoxEndereco.getSelectionModel().select(titular.getIDEndereco()-1);

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
					fecharTelaEditTitulares();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fecharTelaEditTitulares(){
		db.fecharConexao();
		telaEditTitularScene.hide();
	}
	
	public void confirmar(){
		if (inclusion){
			db.comando("INSERT INTO titular (nome, cpf, identidade, telefone, endereco) VALUES ('"+textFieldNome.getText()+"','"+textFieldCPF.getText()+"','"+textFieldIdentidade.getText()+"','"+textFieldTelefone.getText()+"',"+(comboBoxEndereco.getSelectionModel().getSelectedIndex()+1)+")");
		} else {
			db.comando("UPDATE titular SET nome = '"+textFieldNome.getText()+"', cpf = '"+textFieldCPF.getText()+"', identidade = '"+textFieldIdentidade.getText()+"', telefone = '"+textFieldTelefone.getText()+"', endereco = "+(comboBoxEndereco.getSelectionModel().getSelectedIndex()+1)+" WHERE id = "+titular.getId());
		}

		fecharTelaEditTitulares();
	}
	
	private ObservableList<String> getComboBoxData() {
		List<String> list = new ArrayList<>();
		
		db.consulta("SELECT * FROM endereco");
		
		try {
			while(db.getRs().next()){
				list.add(db.getRs().getString("rua") + ", " + db.getRs().getString("numero") + " - " + db.getRs().getString("cep") + " - " + db.getRs().getString("estado") + ". " + db.getRs().getString("pais"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<String> data = FXCollections.observableList(list);

		return data;
	}
}
