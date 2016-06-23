package br.unipe.cc.mlpIII.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Titular;
import br.unipe.cc.mlpIII.pertinencia.Db;
import br.unipe.cc.mlpIII.report.RelatorioTitulares;
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
public class TelaCadastroTitular extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaCadastroTitularStage;
	private Db db = new Db();
	private ResultSet titularesInDataBase;
	private TableView<Titular> table;
	private ObservableList<Titular> titulares;
	private TableColumn colunaId = new TableColumn("ID");
	private TableColumn colunaNome = new TableColumn("Nome");
	private TableColumn colunaCpf = new TableColumn("CPF");
	private TableColumn colunaIdentidade = new TableColumn("Identidade");
	private TableColumn colunaTelefone = new TableColumn("Telefone");
	private TableColumn colunaEndereco = new TableColumn("Endeço");
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnDeletar;
	private Button btnRelatorio;
	private Stage telaEditStage = new Stage();
	
	public TelaCadastroTitular(){
		db.abrirConexao();
	}
	
	public void show(){
		telaCadastroTitularStage.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaCadastroTitularStage = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaCadastroEnderecos.fxml"));
			scene = new Scene(root);

			table = (TableView<Titular>) scene.lookup("#table");
			titulares = getInitialTableData();
			table.setItems(titulares);
			
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
						titulares.remove(table.getSelectionModel().getSelectedItem());
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

			colunaId.setCellValueFactory(new PropertyValueFactory<Titular, Integer>("id"));
			colunaNome.setCellValueFactory(new PropertyValueFactory<Titular, String>("nome"));
			colunaCpf.setCellValueFactory(new PropertyValueFactory<Titular, String>("cpf"));
			colunaIdentidade.setCellValueFactory(new PropertyValueFactory<Titular, String>("identidade"));
			colunaTelefone.setCellValueFactory(new PropertyValueFactory<Titular, String>("telefone"));
			colunaEndereco.setCellValueFactory(new PropertyValueFactory<Titular, String>("endereco"));

			table.getColumns().setAll(colunaId, colunaNome, colunaCpf, colunaIdentidade, colunaTelefone, colunaEndereco);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			telaCadastroTitularStage.setTitle("Cadastro de Titulares");
			telaCadastroTitularStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaCadastroTitularStage.setScene(scene);
			telaCadastroTitularStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void fecharTelaCadastroEnderecos(){
		db.fecharConexao();
		telaCadastroTitularStage.hide();
	}

	private ObservableList<Titular> getInitialTableData() {
		List<Titular> list = new ArrayList<>();
		
		titularesInDataBase = db.consulta("SELECT * FROM titular");
		
		try {
			while(titularesInDataBase.next()){
				list.add(new Titular(titularesInDataBase.getInt("id"), titularesInDataBase.getString("nome"), titularesInDataBase.getString("cpf"), titularesInDataBase.getString("identidade"), titularesInDataBase.getString("telefone"), titularesInDataBase.getInt("endereco")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<Titular> data = FXCollections.observableList(list);

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
        	if (db.comando("DELETE FROM titular WHERE id = " + id))
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
		TelaEditTitular telaEditEnderecos = new TelaEditTitular();
		try {
			telaEditEnderecos.start(telaEditStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				titulares = getInitialTableData();
				table.setItems(titulares);
			}
		});
	}
	
	private void alterar(){
		if (table.getSelectionModel().getSelectedItem() != null){
			TelaEditTitular telaEditEnderecos = new TelaEditTitular(table.getSelectionModel().getSelectedItem());
			try {
				telaEditEnderecos.start(telaEditStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					titulares = getInitialTableData();
					table.setItems(titulares);
				}
			});
		}
	}
	
	private void relatorio() throws EntidadeNaoEncontradaException{
		if (table.getSelectionModel().getSelectedItem() != null){
			Set<Titular> st = new TreeSet<Titular>(table.getSelectionModel().getSelectedItems()); 
			RelatorioTitulares rt = new RelatorioTitulares(st);
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
