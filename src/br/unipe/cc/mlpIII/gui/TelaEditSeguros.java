package br.unipe.cc.mlpIII.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unipe.cc.mlpIII.modelo.Seguro;
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
public class TelaEditSeguros extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaEditSegurosScene;
	private Db db = new Db();
	private boolean inclusion;
	private Seguro seguro = new Seguro();

	private TextField textFieldDescricao;
	private TextField textFieldVigencia;
	private TextField textFieldValor;
    private ComboBox<String> comboBocTitular;
	
	private Button btnConfirmar;
	private Button btnCancelar;
	
	public TelaEditSeguros(){
		this.inclusion = true;
		db.abrirConexao();
	}
	
	public TelaEditSeguros(Seguro seguro){
		this.inclusion = false;
		this.seguro = seguro;
		db.abrirConexao();
	}	
	
	public void show(){
		telaEditSegurosScene.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaEditSegurosScene = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaEditSeguros.fxml"));
			scene = new Scene(root);

			telaEditSegurosScene.setTitle((inclusion ? "Incluir " : "Editar ") + " seguros");
			telaEditSegurosScene.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaEditSegurosScene.setScene(scene);
			telaEditSegurosScene.show();
			
			textFieldDescricao = (TextField) scene.lookup("#textFieldDescricao");
			textFieldVigencia = (TextField) scene.lookup("#textFieldVigencia");
			textFieldValor = (TextField) scene.lookup("#textFieldValor");
		    comboBocTitular = (ComboBox<String>) scene.lookup("#comboBocTitular");
		    comboBocTitular.setItems(getComboBoxData());
			
		    textFieldDescricao.setText(seguro.getDescricao());
		    textFieldVigencia.setText(seguro.getVigencia());
		    textFieldValor.setText(Double.toString(seguro.getValor()));
		    comboBocTitular.getSelectionModel().select(seguro.getIDTitular()-1);
			
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
					fecharTelaEditSeguros();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fecharTelaEditSeguros(){
		db.fecharConexao();
		telaEditSegurosScene.hide();
	}
	
	public void confirmar(){
		if (inclusion){
			db.comando("INSERT INTO seguro (valor, descricao, vigencia, titular) VALUES ("+textFieldValor.getText()+",'"+textFieldDescricao.getText()+"','"+textFieldVigencia.getText()+"', "+(comboBocTitular.getSelectionModel().getSelectedIndex()+1)+")");
		} else {
			db.comando("UPDATE seguro SET valor = "+textFieldValor.getText()+", descricao = '"+textFieldDescricao.getText()+"', vigencia = '"+textFieldVigencia.getText()+"', titular = "+(comboBocTitular.getSelectionModel().getSelectedIndex()+1)+" WHERE id = "+seguro.getId());
		}

		fecharTelaEditSeguros();
	}
	
	private ObservableList<String> getComboBoxData() {
		List<String> list = new ArrayList<>();
		
		db.consulta("SELECT * FROM titular");
		
		try {
			while(db.getRs().next()){
				list.add(db.getRs().getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<String> data = FXCollections.observableList(list);

		return data;
	}
}
