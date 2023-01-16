package br.com.bonabox.business.util;

public interface GenerateUID {
	
	String generatingCodigo(int length);
	
	String generatingCodigo();
	
	GenerateUID build(IGenerateCodigo generateCodigo);
	
}
