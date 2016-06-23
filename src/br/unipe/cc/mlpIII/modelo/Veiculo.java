package br.unipe.cc.mlpIII.modelo;

import java.sql.SQLException;

import br.unipe.cc.mlpIII.pertinencia.Db;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Veiculo implements Comparable<Veiculo>{
	private SimpleIntegerProperty id;
	private SimpleStringProperty placa;
	private SimpleStringProperty modelo;
	private SimpleStringProperty chassis;
	private SimpleStringProperty ano;
	private SimpleStringProperty fabricante;
	private Titular titular;
	private Db db = new Db();

	public Veiculo(){
		id = new SimpleIntegerProperty(0);
		placa = new SimpleStringProperty("");
		modelo = new SimpleStringProperty("");
		chassis = new SimpleStringProperty("");
		ano = new SimpleStringProperty("");
		fabricante = new SimpleStringProperty("");
		titular = new Titular();
	}
	
	public Veiculo(int id, String placa, String modelo, String chassis, String ano, String fabricante, int idTitular) {
		this.id = new SimpleIntegerProperty(id);
		this.placa = new SimpleStringProperty(placa);
		this.modelo = new SimpleStringProperty(modelo);
		this.chassis = new SimpleStringProperty(chassis);
		this.ano = new SimpleStringProperty(ano);
		this.fabricante = new SimpleStringProperty(fabricante);
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

	public String getPlaca() {
		return placa.get();
	}

	public void setPlaca(String placa) {
		this.placa.set(placa);
	}

	public String getModelo() {
		return modelo.get();
	}

	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}

	public String getChassis() {
		return chassis.get();
	}

	public void setChassis(String chassis) {
		this.chassis.set(chassis);
	}

	public String getAno() {
		return ano.get();
	}

	public void setAno(String ano) {
		this.ano.set(ano);
	}

	public String getFabricante() {
		return fabricante.get();
	}

	public void setFabricante(String fabricante) {
		this.fabricante.set(fabricante);
	}

	public String getTitular() {
		return titular.getNome();
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}
	public int getIDTitular(){
		return titular.getId();
	}

	@Override
	public String toString() {
		return "id=" + id.get() + ", placa=" + placa.get() + ", modelo=" + modelo.get() + ", chassis=" + chassis.get() + ", ano=" + ano.get() + ", fabricante=" + fabricante.get() + ", titular=" + titular;
	}

	@Override
	public int compareTo(Veiculo veiculo) {
		if (id.get() < veiculo.id.get()){
			return -1;
		}
		if (id.get() > veiculo.id.get()){
			return 1;
		}
		return 0;
	}
}
