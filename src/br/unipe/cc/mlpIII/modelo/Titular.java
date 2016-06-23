package br.unipe.cc.mlpIII.modelo;

import java.sql.SQLException;

import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Titular implements Comparable<Titular>{
	private SimpleIntegerProperty id;
	private SimpleStringProperty nome;
	private SimpleStringProperty cpf;
	private SimpleStringProperty identidade;
	private SimpleStringProperty telefone;
	private Endereco endereco;
	private Db db = new Db();

	public Titular(){
		this.id = new SimpleIntegerProperty(0);
		this.nome = new SimpleStringProperty("");
		this.cpf = new SimpleStringProperty("");
		this.identidade = new SimpleStringProperty("");
		this.telefone = new SimpleStringProperty("");
		this.endereco = new Endereco();
	}
	
	public Titular(int id, String nome, String cpf, String identidade, String telefone, int idEndereco) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.cpf = new SimpleStringProperty(cpf);
		this.identidade = new SimpleStringProperty(identidade);
		this.telefone = new SimpleStringProperty(telefone);
		this.endereco = getEnderecoById(idEndereco);
	}
	
	private Endereco getEnderecoById(int idEndereco) {
		Endereco titular = null;
		
		db.abrirConexao();
		
		db.consulta("SELECT * FROM endereco where id = " + idEndereco);
		
		try {
			while(db.getRs().next()){
				titular = new Endereco(db.getRs().getInt("id"), db.getRs().getString("rua"), db.getRs().getString("cep"), db.getRs().getString("numero"), db.getRs().getString("estado"), db.getRs().getString("pais"));
			}
		} catch (SQLException e) {
			titular = new Endereco();
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
	public String getNome() {
		return nome.get();
	}
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	public String getCpf() {
		return cpf.get();
	}
	public void setCpf(String cpf) {
		this.cpf.set(cpf);
	}
	public String getIdentidade() {
		return identidade.get();
	}
	public void setIdentidade(String identidade) {
		this.identidade.set(identidade);
	}
	public String getTelefone() {
		return telefone.get();
	}
	public void setTelefone(String telefone) {
		this.telefone.set(telefone);
	}
	public String getEndereco() {
		return endereco.toString();
	}
	public int getIDEndereco() {
		return endereco.getId();
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "id=" + id.get() + ", nome=" + nome.get() + ", cpf=" + cpf.get() + ", identidade=" + identidade.get() + ", telefone=" + telefone.get() + ", endereco=" + endereco;
	}

	@Override
	public int compareTo(Titular titular) {
		if (id.get() < titular.id.get()){
			return -1;
		}
		if (id.get() > titular.id.get()){
			return 1;
		}
		return 0;
	}
}
