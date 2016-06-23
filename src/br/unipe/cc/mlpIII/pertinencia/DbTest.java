package br.unipe.cc.mlpIII.pertinencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

public class DbTest extends TestCase{
	private String urlHost;
	private String porta;
	private String usuario;
	private String senha;
	private String banco;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	@Override
	protected void setUp() throws Exception {
		urlHost = "localhost";
		porta = "3306";
		usuario = "root";
		senha = "123456";
		banco = "seguros";
		testAbrirConexao();
	}
	
	public void testAbrirConexao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + urlHost + ":" + porta + "/" + banco + "?useSSL=false", usuario, senha);
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testFecharConexao(){

		try {
			con.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		con = null;
		st = null;
		rs = null;
	}
	
	public ResultSet testConsulta(String stringQuery){
		try {
			rs = st.executeQuery(stringQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	public boolean testComando(String stringQuery){
		try {
			st.execute(stringQuery);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	protected void tearDown() throws Exception {
		
	}
	
}
