package br.unipe.cc.mlpIII.util;

import java.io.Serializable;

public class EntidadeNaoEncontradaException extends Exception implements Serializable {
	private static final long serialVersionUID = -8148450242912468650L;
	
	public EntidadeNaoEncontradaException(){
		super("EntidadeNaoEncontradaException");
	}
}
