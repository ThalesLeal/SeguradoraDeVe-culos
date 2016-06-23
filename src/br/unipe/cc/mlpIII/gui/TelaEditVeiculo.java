package br.unipe.cc.mlpIII.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unipe.cc.mlpIII.modelo.Veiculo;
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
public class TelaEditVeiculo extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaEditVeiculoScene;
	private Db db = new Db();
	private boolean inclusion;
	private Veiculo veiculo = new Veiculo();

	private TextField textFieldPlaca;
	private TextField textFieldModelo;
	private TextField textFieldChassis;
	private TextField textFieldAno;
	private TextField textFieldFabricante;
	private ComboBox<String> comboBocTitular;
	
	private Button btnConfirmar;
	private Button btnCancelar;

	public TelaEditVeiculo(){
		this.inclusion = true;
		db.abrirConexao();
	}
	
	public TelaEditVeiculo(Veiculo veiculo){
		this.inclusion = false;
		this.veiculo = veiculo;
		db.abrirConexao();
	}	
	
	
	public void show(){
		telaEditVeiculoScene.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaEditVeiculoScene = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaEditVeiculo.fxml"));
			scene = new Scene(root);

			telaEditVeiculoScene.setTitle((inclusion ? "Incluir " : "Editar ") + " veiculo");
			telaEditVeiculoScene.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaEditVeiculoScene.setScene(scene);
			telaEditVeiculoScene.show();
			
			textFieldPlaca = (TextField) scene.lookup("#textFieldPlaca");
			textFieldModelo = (TextField) scene.lookup("#textFieldModelo");
			textFieldChassis = (TextField) scene.lookup("#textFieldChassis");
			textFieldAno = (TextField) scene.lookup("#textFieldAno");
			textFieldFabricante = (TextField) scene.lookup("#textFieldFabricante");
			comboBocTitular = (ComboBox<String>) scene.lookup("#comboBocTitular");
			comboBocTitular.setItems(getComboBoxData());
			
			textFieldPlaca.setText(veiculo.getPlaca());
			textFieldModelo.setText(veiculo.getModelo());
			textFieldChassis.setText(veiculo.getChassis());
			textFieldAno.setText(veiculo.getAno());
			textFieldFabricante.setText(veiculo.getFabricante());
			comboBocTitular.getSelectionModel().select(veiculo.getIDTitular()-1);
			
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
					fecharTelaEditVeiculos();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fecharTelaEditVeiculos(){
		db.fecharConexao();
		telaEditVeiculoScene.hide();
	}
	
	public void confirmar(){
		if (inclusion){
			db.comando("INSERT INTO veiculo (placa, modelo, chassis, ano, fabricante, titular) VALUES ('"+textFieldPlaca.getText()+"','"+textFieldModelo.getText()+"','"+textFieldChassis.getText()+"','"+textFieldAno.getText()+"','"+textFieldFabricante.getText()+"', "+(comboBocTitular.getSelectionModel().getSelectedIndex() + 1)+")");
		} else {
			db.comando("UPDATE veiculo SET placa = '"+textFieldPlaca.getText()+"', modelo = '"+textFieldModelo.getText()+"', chassis = '"+textFieldChassis.getText()+"', ano = '"+textFieldAno.getText()+"', fabricante = '"+textFieldFabricante.getText()+"', titular = "+(comboBocTitular.getSelectionModel().getSelectedIndex() + 1)+" WHERE id = "+veiculo.getId());
		}

		fecharTelaEditVeiculos();
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
