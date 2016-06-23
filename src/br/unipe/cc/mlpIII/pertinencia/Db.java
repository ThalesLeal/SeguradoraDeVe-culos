package br.unipe.cc.mlpIII.pertinencia;

import java.sql.*;

public class Db {
	private String urlHost;
	private String porta;
	private String usuario;
	private String senha;
	private String banco;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Db(){
		urlHost = "localhost";
		porta = "3306";
		usuario = "root";
		senha = "123456";
		banco = "seguros";
	}
	
	public String getUrlHost() {
		return urlHost;
	}

	public void setUrlHost(String urlHost) {
		this.urlHost = urlHost;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public void abrirConexao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + urlHost + ":" + porta + "/" + banco + "?useSSL=false", usuario, senha);
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO: Mensagem do catch
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); //TODO: Mensagem do catch
		}
	}
	
	public void fecharConexao(){

		try {
			con.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO: Mensagem do catch
		}
		
		con = null;
		st = null;
		rs = null;
	}
	
	public ResultSet consulta(String stringQuery){
		try {
			rs = st.executeQuery(stringQuery);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO: Mensagem do catch
		}

		return rs;
	}
	
	public boolean comando(String stringQuery){
		try {
			st.execute(stringQuery);
			return true;
		} catch (SQLException e) {
			//e.printStackTrace(); //TODO: Mensagem do catch
			return false;
		}
	}
}
