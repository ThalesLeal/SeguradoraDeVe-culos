package br.unipe.cc.mlpIII.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Veiculo;
import br.unipe.cc.mlpIII.pertinencia.Db;
import br.unipe.cc.mlpIII.report.RelatorioVeiculos;
import br.unipe.cc.mlpIII.util.EntidadeNaoEncontradaException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SuppressWarnings({"rawtypes","unchecked"})
public class TelaCadastroVeiculo extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaCadastroSeguroStage;
	private Db db = new Db();
	private ResultSet segurosInDataBase;
	private TableView<Veiculo> table;
	private ObservableList<Veiculo> veiculos;
	private TableColumn colunaId = new TableColumn("ID");
	private TableColumn colunaPlaca = new TableColumn("Placa");
	private TableColumn colunaModelo = new TableColumn("Modelo");
	private TableColumn colunaChassis = new TableColumn("Chassis");
	private TableColumn colunaAno = new TableColumn("Ano");
	private TableColumn colunaFabricante = new TableColumn("Fabricante");
	private TableColumn colunaTitular = new TableColumn("Titular");
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnDeletar;
	private Button btnRelatorio;
	private Stage telaEditStage = new Stage();
	
	public TelaCadastroVeiculo(){
		db.abrirConexao();
	}
	
	public void show(){
		telaCadastroSeguroStage.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaCadastroSeguroStage = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaCadastroEnderecos.fxml"));
			scene = new Scene(root);

			table = (TableView<Veiculo>) scene.lookup("#table");
			veiculos = getInitialTableData();
			table.setItems(veiculos);
			
			btnIncluir = (Button) scene.lookup("#btnIncluir");
			btnIncluir.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					incluir();
				}
			});
			
			btnAlterar = (Button) scene.lookup("#btnAlterar");
			btnAlterar.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					alterar();
				}
			});
			
			btnDeletar = (Button) scene.lookup("#btnDeletar");
			btnDeletar.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (deletarNoBancoDedados(table.getSelectionModel().getSelectedItem().getId())){
						veiculos.remove(table.getSelectionModel().getSelectedItem());
					}
				}
			});
			
			btnRelatorio = (Button) scene.lookup("#btnRelatorio");
			btnRelatorio.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						relatorio();
					} catch (EntidadeNaoEncontradaException e) {
						e.printStackTrace();
					}
				}
			});

			colunaId.setCellValueFactory(new PropertyValueFactory<Veiculo, Integer>("id"));
			colunaPlaca.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("placa"));
			colunaModelo.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("modelo"));
			colunaChassis.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("chassis"));
			colunaAno.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("ano"));
			colunaFabricante.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("fabricante"));
			colunaTitular.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("titular"));

			table.getColumns().setAll(colunaId, colunaPlaca, colunaModelo, colunaChassis, colunaAno, colunaFabricante, colunaTitular);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			telaCadastroSeguroStage.setTitle("Cadastro de Veiculos");
			telaCadastroSeguroStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaCadastroSeguroStage.setScene(scene);
			telaCadastroSeguroStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void fecharTelaCadastroEnderecos(){
		db.fecharConexao();
		telaCadastroSeguroStage.hide();
	}

	private ObservableList<Veiculo> getInitialTableData() {
		List<Veiculo> list = new ArrayList<>();
		
		segurosInDataBase = db.consulta("SELECT * FROM veiculo");
		
		try {
			while(segurosInDataBase.next()){
				list.add(new Veiculo(segurosInDataBase.getInt("id"), segurosInDataBase.getString("placa"), segurosInDataBase.getString("modelo"), segurosInDataBase.getString("chassis"), segurosInDataBase.getString("ano"), segurosInDataBase.getString("fabricante"), segurosInDataBase.getInt("titular")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<Veiculo> data = FXCollections.observableList(list);

		return data;
	}
	
	private boolean deletarNoBancoDedados(int id) {
		Alert dialogConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");

        dialogConfirmacao.setTitle("Excluir registro");
        dialogConfirmacao.setHeaderText("Deseja realmente excluir o registro selecionado?");
        dialogConfirmacao.getButtonTypes().setAll(btnSim, btnNao);
        dialogConfirmacao.showAndWait();
        if (dialogConfirmacao.getResult() == btnSim){
        	if (db.comando("DELETE FROM veiculo WHERE id = " + id))
        		return true;
        	else{
        		Alert warning = new Alert(Alert.AlertType.WARNING);
        		warning.setTitle("Erro");
        		warning.setHeaderText("Não foi possível deletar o registro no banco de dados.");
        		warning.showAndWait();
        		return false;
        	}
        } else {
        	return false;
        }
	}
	
	private void incluir(){
		TelaEditVeiculo telaEditEnderecos = new TelaEditVeiculo();
		try {
			telaEditEnderecos.start(telaEditStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				veiculos = getInitialTableData();
				table.setItems(veiculos);
			}
		});
	}
	
	private void alterar(){
		if (table.getSelectionModel().getSelectedItem() != null){
			TelaEditVeiculo telaEditVeiculo = new TelaEditVeiculo(table.getSelectionModel().getSelectedItem());
			try {
				telaEditVeiculo.start(telaEditStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					veiculos = getInitialTableData();
					table.setItems(veiculos);
				}
			});
		}
	}
	
	private void relatorio() throws EntidadeNaoEncontradaException{
		if (table.getSelectionModel().getSelectedItem() != null){
			Set<Veiculo> st = new TreeSet<Veiculo>(table.getSelectionModel().getSelectedItems()); 
			RelatorioVeiculos rt = new RelatorioVeiculos(st);
			rt.print();
			rt.end();
			
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Relatório");
            dialogoInfo.setHeaderText("Relatório gerado!!!");
            dialogoInfo.showAndWait();
		} else {
			EntidadeNaoEncontradaException entidadeNaoEncontradaException = new EntidadeNaoEncontradaException();
			throw entidadeNaoEncontradaException;
		}		
	}
}
