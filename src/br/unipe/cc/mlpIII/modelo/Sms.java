package br.unipe.cc.mlpIII.modelo;

import br.com.facilitamovel.bean.Retorno;
import br.com.facilitamovel.bean.SmsSimples;
import br.com.facilitamovel.service.CheckCredit;
import br.com.facilitamovel.service.SendMessage;

public class Sms {
	private String numeroCelular;
	private String menssagem;
	private static final String usuario = "r3d-line";
	private static final String senha = "detran123";

	public Sms(){
		
	}
	
	public Sms(String numeroCelular, String menssagem){
		this.numeroCelular = numeroCelular;
		this.menssagem = menssagem;
	}
	
	public void enviar() throws Exception{
		SmsSimples sms = new SmsSimples();
		sms.setUser(usuario);
		sms.setPassword(senha);
		sms.setDestinatario(numeroCelular);
		sms.setMessage(menssagem);

		Retorno retorno = SendMessage.simpleSend(sms);

		System.out.println("Codigo:" + retorno.getCodigo());
		System.out.println("Descricao:" + retorno.getMensagem());
	}
	
	public static void checkCredits(String usuario, String senha) throws Exception {
		try {
			System.out.println(CheckCredit.checkRealCredit(usuario, senha));
		} catch (Exception e) {
			e.printStackTrace(); //Possivelmente LOGIN INVALIDO
		}
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getMenssagem() {
		return menssagem;
	}

	public void setMenssagem(String menssagem) {
		this.menssagem = menssagem;
	}
	
}
