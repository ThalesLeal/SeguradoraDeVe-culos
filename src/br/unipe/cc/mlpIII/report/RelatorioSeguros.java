package br.unipe.cc.mlpIII.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Seguro;

public class RelatorioSeguros {
	private String arquivo = "seguros.txt";
	private FileWriter fw;
	private PrintWriter pw;

	private Set<Seguro> seguros = new TreeSet<Seguro>();

	public RelatorioSeguros(Set<Seguro> seguros){
		try {
			this.seguros = seguros;
			this.fw = new FileWriter(arquivo);
			this.pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		pw.println("**************************************************************************************************************************");
		pw.println("                                            Relatório de Seguros");
		pw.println("**************************************************************************************************************************");
		for	(Seguro t : seguros){ 
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
