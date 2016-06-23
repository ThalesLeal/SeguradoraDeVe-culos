package br.unipe.cc.mlpIII.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Endereco;
import br.unipe.cc.mlpIII.pertinencia.Db;
import br.unipe.cc.mlpIII.report.RelatorioEnderecos;
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
public class TelaCadastroEnderecos extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaCadastroEnderecosStage;
	private Db db = new Db();
	private ResultSet enderecosInDataBase;
	private TableView<Endereco> table;
	private ObservableList<Endereco> enderecos;
	private TableColumn colunaId = new TableColumn("ID");
	private TableColumn colunaRua = new TableColumn("Rua");
	private TableColumn colunaCep = new TableColumn("CEP");
	private TableColumn colunaNumero = new TableColumn("Número");
	private TableColumn colunaEstado = new TableColumn("Estado");
	private TableColumn colunaPais = new TableColumn("Pais");
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnDeletar;
	private Button btnRelatorio;
	private Stage telaEditStage = new Stage();
	

	public TelaCadastroEnderecos(){
		db.abrirConexao();
	}
	
	public void show(){
		telaCadastroEnderecosStage.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.telaCadastroEnderecosStage = primaryStage;

		try {
			root = FXMLLoader.load(getClass().getResource("TelaCadastroEnderecos.fxml"));
			scene = new Scene(root);

			table = (TableView<Endereco>) scene.lookup("#table");
			enderecos = getInitialTableData();
			table.setItems(enderecos);

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
						enderecos.remove(table.getSelectionModel().getSelectedItem());
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

			colunaId.setCellValueFactory(new PropertyValueFactory<Endereco, Integer>("id"));
			colunaRua.setCellValueFactory(new PropertyValueFactory<Endereco, String>("rua"));
			colunaCep.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cep"));
			colunaNumero.setCellValueFactory(new PropertyValueFactory<Endereco, String>("numero"));
			colunaEstado.setCellValueFactory(new PropertyValueFactory<Endereco, String>("estado"));
			colunaPais.setCellValueFactory(new PropertyValueFactory<Endereco, String>("pais"));

			table.getColumns().setAll(colunaId, colunaRua, colunaCep, colunaNumero, colunaEstado, colunaPais);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			telaCadastroEnderecosStage.setTitle("Cadastro de Endereços");
			telaCadastroEnderecosStage.getIcons().add(new Image("file:imagens\\seguro.png"));
			telaCadastroEnderecosStage.setScene(scene);
			telaCadastroEnderecosStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void fecharTelaCadastroEnderecos(){
		db.fecharConexao();
		telaCadastroEnderecosStage.hide();
	}

	private ObservableList<Endereco> getInitialTableData() {
		List<Endereco> list = new ArrayList<>();
		
		enderecosInDataBase = db.consulta("SELECT * FROM endereco");
		
		try {
			while(enderecosInDataBase.next()){
				list.add(new Endereco(enderecosInDataBase.getInt("id"), enderecosInDataBase.getString("rua"), enderecosInDataBase.getString("cep"), enderecosInDataBase.getString("numero"), enderecosInDataBase.getString("estado"), enderecosInDataBase.getString("pais")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<Endereco> data = FXCollections.observableList(list);

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
        	if (db.comando("DELETE FROM endereco WHERE id = " + id))
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
		TelaEditEnderecos telaEditEnderecos = new TelaEditEnderecos();
		try {
			telaEditEnderecos.start(telaEditStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				enderecos = getInitialTableData();
				table.setItems(enderecos);
			}
		});
	}
	
	private void alterar(){
		if (table.getSelectionModel().getSelectedItem() != null){
			TelaEditEnderecos telaEditEnderecos = new TelaEditEnderecos(table.getSelectionModel().getSelectedItem());
			try {
				telaEditEnderecos.start(telaEditStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					enderecos = getInitialTableData();
					table.setItems(enderecos);
				}
			});
		}
	}
	
	private void relatorio() throws EntidadeNaoEncontradaException{
		if (table.getSelectionModel().getSelectedItem() != null){
			Set<Endereco> st = new TreeSet<Endereco>(table.getSelectionModel().getSelectedItems()); 
			RelatorioEnderecos rt = new RelatorioEnderecos(st);
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
