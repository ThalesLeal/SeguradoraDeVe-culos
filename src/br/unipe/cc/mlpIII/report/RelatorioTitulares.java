package br.unipe.cc.mlpIII.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Titular;

public class RelatorioTitulares {
	private String arquivo = "titulares.txt";
	private FileWriter fw;
	private PrintWriter pw;

	private Set<Titular> titulares = new TreeSet<Titular>();

	public RelatorioTitulares(Set<Titular> titulares){
		try {
			this.titulares = titulares;
			this.fw = new FileWriter(arquivo);
			this.pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		pw.println("**************************************************************************************************************************");
		pw.println("                                            Relatório de Titulares");
		pw.println("**************************************************************************************************************************");
		for	(Titular t : titulares){ 
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
