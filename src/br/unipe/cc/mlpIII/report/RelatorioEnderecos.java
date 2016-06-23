package br.unipe.cc.mlpIII.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Endereco;

public class RelatorioEnderecos {
	private String arquivo = "enderecos.txt";
	private FileWriter fw;
	private PrintWriter pw;

	private Set<Endereco> enderecos = new TreeSet<Endereco>();

	public RelatorioEnderecos(Set<Endereco> enderecos){
		try {
			this.enderecos = enderecos;
			this.fw = new FileWriter(arquivo);
			this.pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		pw.println("**************************************************************************************************************************");
		pw.println("                                            Relatório de Endereços");
		pw.println("**************************************************************************************************************************");
		for	(Endereco t : enderecos){ 
			pw.println(t);
		}
		pw.println("**************************************************************************************************************************");
	}
	
	public void end(){
		try {
			fw.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
