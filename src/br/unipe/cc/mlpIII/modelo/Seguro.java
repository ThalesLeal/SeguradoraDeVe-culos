package br.unipe.cc.mlpIII.modelo;

import java.sql.SQLException;

import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Seguro implements Comparable<Seguro>{
	private SimpleIntegerProperty id;
	private SimpleDoubleProperty valor;
	private SimpleStringProperty descricao;
	private SimpleStringProperty vigencia;
	private Titular titular;
	private Db db = new Db();

	public Seguro(){
		id = new SimpleIntegerProperty(0);
		valor = new SimpleDoubleProperty(0);
		descricao = new SimpleStringProperty("");
		vigencia = new SimpleStringProperty("");
		titular = new Titular();
	}
	
	public Seguro(int id, double valor, String descricao, String vigencia, int idTitular) {
		this.id = new SimpleIntegerProperty(id);
		this.valor = new SimpleDoubleProperty(valor);
		this.descricao = new SimpleStringProperty(descricao);
		this.vigencia = new SimpleStringProperty(vigencia);
		this.titular = getTitularById(idTitular);
	}
	
	private Titular getTitularById(int idTitular) {
		Titular titular = null;
		
		db.abrirConexao();
		
		db.consulta("SELECT * FROM titular where id = " + idTitular);
		
		try {
			while(db.getRs().next()){
				titular = new Titular(db.getRs().getInt("id"), db.getRs().getString("nome"), db.getRs().getString("cpf"), db.getRs().getString("identidade"), db.getRs().getString("telefone"), db.getRs().getInt("endereco"));
			}
		} catch (SQLException e) {
			titular = new Titular();
			//e.printStackTrace();
		}
		
		db.fecharConexao();
		
		return titular;
	}
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public double getValor() {
		return valor.get();
	}
	public void setValor(double valor) {
		this.valor.set(valor);
	}
	public String getDescricao() {
		return descricao.get();
	}
	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
	}
	public String getVigencia() {
		return vigencia.get();
	}
	public void setVigencia(String vigencia) {
		this.vigencia.set(vigencia);
	}
	public String getTitular() {
		return titular.getNome();
	}
	public int getIDTitular() {
		return titular.getId();
	}
	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	@Override
	public String toString() {
		return "id=" + id.get() + ", valor=" + valor.get() + ", descricao=" + descricao.get() + ", vigencia=" + vigencia.get() + ", titular=" + titular;
	}

	@Override
	public int compareTo(Seguro seguro) {
		if (id.get() < seguro.id.get()){
			return -1;
		}
		if (id.get() > seguro.id.get()){
			return 1;
		}
		return 0;
	}
}
