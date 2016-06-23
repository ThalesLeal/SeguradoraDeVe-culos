package br.unipe.cc.mlpIII.util;

import java.io.Serializable;

public class EntidadeJaCadastradaException extends Exception implements Serializable{
	private static final long serialVersionUID = 5297659532728907826L;

	public EntidadeJaCadastradaException(){
		super("EntidadeJaCadastradaException");
	}
}
