package br.unipe.cc.mlpIII.modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Endereco implements Comparable<Endereco>{
	private SimpleIntegerProperty id;
	private SimpleStringProperty rua;
	private SimpleStringProperty cep;
	private SimpleStringProperty numero;
	private SimpleStringProperty estado;
	private SimpleStringProperty pais;

	public Endereco(){
		this.id = new SimpleIntegerProperty(0);
		this.rua = new SimpleStringProperty("");
		this.cep = new SimpleStringProperty("");
		this.numero = new SimpleStringProperty("");
		this.estado = new SimpleStringProperty("");
		this.pais = new SimpleStringProperty("");
	}
	
	public Endereco(int id, String rua, String cep, String numero, String estado, String pais) {
		this.id = new SimpleIntegerProperty(id);
		this.rua = new SimpleStringProperty(rua);
		this.cep = new SimpleStringProperty(cep);
		this.numero = new SimpleStringProperty(numero);
		this.estado = new SimpleStringProperty(estado);
		this.pais = new SimpleStringProperty(pais);
	}
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getRua() {
		return rua.get();
	}
	public void setRua(String rua) {
		this.rua.set(rua);
	}
	public String getCep() {
		return cep.get();
	}
	public void setCep(String cep) {
		this.cep.set(cep);
	}
	public String getNumero() {
		return numero.get();
	}
	public void setNumero(String numero) {
		this.numero.set(numero);
	}
	public String getEstado() {
		return estado.get();
	}
	public void setEstado(String estado) {
		this.estado.set(estado);
	}
	public String getPais() {
		return pais.get();
	}
	public void setPais(String pais) {
		this.pais.set(pais);
	}

	@Override
	public String toString() {
		return rua.get() + ", " + numero.get() + " - " + cep.get() + " - " + estado.get() + ". " + pais.get();
	}

	@Override
	public int compareTo(Endereco endereco) {
		if (id.get() < endereco.id.get()){
			return -1;
		}
		if (id.get() > endereco.id.get()){
			return 1;
		}
		return 0;
	}

}
