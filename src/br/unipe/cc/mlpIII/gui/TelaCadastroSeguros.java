package br.unipe.cc.mlpIII.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Seguro;
import br.unipe.cc.mlpIII.pertinencia.Db;
import br.unipe.cc.mlpIII.report.RelatorioSeguros;
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
public class TelaCadastroSeguros extends Application {
	private Scene scene;
	private Parent root;
	private Stage telaCadastroSeguroStage;
	private Db db = new Db();
	private ResultSet segurosInDataBase;
	private TableView<Seguro> table;
	private ObservableList<Seguro> seguros;
	private TableColumn colunaId = new TableColumn("ID");
	private TableColumn colunaValor = new TableColumn("Valor");
	private TableColumn colunaDescricao = new TableColumn("Descrição");
	private TableColumn colunaVigencia = new TableColumn("Vigência");
	private TableColumn colunaTitular = new TableColumn("Titular");
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnDeletar;
	private Button btnRelatorio;
	private Stage telaEditStage = new Stage();

	public TelaCadastroSeguros(){
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

			table = (TableView<Seguro>) scene.lookup("#table");
			seguros = getInitialTableData();
			table.setItems(seguros);
			
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
						seguros.remove(table.getSelectionModel().getSelectedItem());
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

			colunaId.setCellValueFactory(new PropertyValueFactory<Seguro, Integer>("id"));
			colunaValor.setCellValueFactory(new PropertyValueFactory<Seguro, String>("valor"));
			colunaDescricao.setCellValueFactory(new PropertyValueFactory<Seguro, String>("descricao"));
			colunaVigencia.setCellValueFactory(new PropertyValueFactory<Seguro, String>("vigencia"));
			colunaTitular.setCellValueFactory(new PropertyValueFactory<Seguro, String>("titular"));

			table.getColumns().setAll(colunaId, colunaValor, colunaDescricao, colunaVigencia, colunaTitular);
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			telaCadastroSeguroStage.setTitle("Cadastro de Seguros");
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

	private ObservableList<Seguro> getInitialTableData() {
		List<Seguro> list = new ArrayList<>();
		
		segurosInDataBase = db.consulta("SELECT * FROM seguro");
		
		try {
			while(segurosInDataBase.next()){
				list.add(new Seguro(segurosInDataBase.getInt("id"), segurosInDataBase.getDouble("valor"), segurosInDataBase.getString("descricao"), segurosInDataBase.getString("vigencia"), segurosInDataBase.getInt("titular")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<Seguro> data = FXCollections.observableList(list);

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
        	if (db.comando("DELETE FROM seguro WHERE id = " + id))
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
		TelaEditSeguros telaEditEnderecos = new TelaEditSeguros();
		try {
			telaEditEnderecos.start(telaEditStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				seguros = getInitialTableData();
				table.setItems(seguros);
			}
		});
	}
	
	private void alterar(){
		if (table.getSelectionModel().getSelectedItem() != null){
			TelaEditSeguros telaEditEnderecos = new TelaEditSeguros(table.getSelectionModel().getSelectedItem());
			try {
				telaEditEnderecos.start(telaEditStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			telaEditStage.setOnHiding( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					seguros = getInitialTableData();
					table.setItems(seguros);
				}
			});
		}
	}
	
	private void relatorio() throws EntidadeNaoEncontradaException{
		if (table.getSelectionModel().getSelectedItem() != null){
			Set<Seguro> st = new TreeSet<Seguro>(table.getSelectionModel().getSelectedItems()); 
			RelatorioSeguros rt = new RelatorioSeguros(st);
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
