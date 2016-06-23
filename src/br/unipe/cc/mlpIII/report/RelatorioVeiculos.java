package br.unipe.cc.mlpIII.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Veiculo;

public class RelatorioVeiculos {
	private String arquivo = "veiculos.txt";
	private FileWriter fw;
	private PrintWriter pw;

	private Set<Veiculo> veiculos = new TreeSet<Veiculo>();

	public RelatorioVeiculos(Set<Veiculo> veiculos){
		try {
			this.veiculos = veiculos;
			this.fw = new FileWriter(arquivo);
			this.pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		pw.println("**************************************************************************************************************************");
		pw.println("                                            Relatório de Veiculos");
		pw.println("**************************************************************************************************************************");
		for	(Veiculo t : veiculos){ 
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
