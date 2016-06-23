package br.unipe.cc.mlpIII.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import br.unipe.cc.mlpIII.modelo.Seguro;
import junit.framework.TestCase;

public class RelatorioSegurosTest extends TestCase {
	private String arquivo = "seguros.txt";
	private FileWriter fw;
	private PrintWriter pw;

	private Set<Seguro> seguros = new TreeSet<Seguro>();

	@Override
	protected void setUp() throws Exception {
		Set<Seguro> st = new TreeSet<Seguro>();
		
		try {
			this.seguros = st;
			this.fw = new FileWriter(arquivo);
			this.pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testPrint(){
		pw.println("**************************************************************************************************************************");
		pw.println("                                            Relatório de Seguros");
		pw.println("**************************************************************************************************************************");
		for	(Seguro t : seguros){ 
			pw.println(t);
		}
		pw.println("**************************************************************************************************************************");
	}

	public void testEnd(){
		try {
			fw.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		testEnd();
	}

}
