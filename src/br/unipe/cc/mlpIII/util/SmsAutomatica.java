package br.unipe.cc.mlpIII.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.unipe.cc.mlpIII.modelo.Sms;
import br.unipe.cc.mlpIII.pertinencia.Db;

public class SmsAutomatica extends Thread{
	private Sms sms = new Sms();
	private Db db = new Db();
	private Random randomize = new Random();
	private List<String> numerosCelulares = new ArrayList<String>();
	private String[] mensagens = { "Mensagem 1" ,
								   "Mensagem 2" ,
								   "Mensagem 3" ,
								   "Mensagem 4" ,
								   "Mensagem 5" ,
								   "Mensagem 6" ,
								   "Mensagem 7" ,
								   "Mensagem 8" ,
								   "Mensagem 9" ,
								   "Mensagem 10" };

	@Override
	public void run() {
		pegarNumeros();

		while (true){
			int sortNumeroCelular = randomize.nextInt( numerosCelulares.size() );
			int sortMensagem = randomize.nextInt( mensagens.length );

			System.out.println(numerosCelulares.get(sortNumeroCelular));
			
			sms.setNumeroCelular( numerosCelulares.get(sortNumeroCelular) );
			sms.setMenssagem( mensagens[sortMensagem] );

			try {
				sms.enviar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void pegarNumeros(){
		db.abrirConexao();
		
		db.consulta("SELECT telefone FROM titular");
		
		try {
			while(db.getRs().next()){
				numerosCelulares.add(db.getRs().getString("telefone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.fecharConexao();
	}
}
