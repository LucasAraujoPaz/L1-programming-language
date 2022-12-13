package compilador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class L1 {
	
	private static final String EXTENSAO = ".l1";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		final String instrucoesDeUso = "Uso: \n" + "check <file> \n" + "run <input> <file> \n"; 
		
		Testes.asseverar(args.length > 0, instrucoesDeUso);
	    final String comando = args[0];
	    switch (comando) {
	    	case "check" -> {
	    		Testes.asseverar(args.length > 1, instrucoesDeUso);
		    	final String arquivo = args[1];
		    	Testes.asseverar( ! arquivo.endsWith(EXTENSAO), "Arquivo já compilado");
				final String codigoFonte = Files.readString(Paths.get(arquivo));
				Parser.checar(codigoFonte);
	    	}
	    	case "run" -> {
	    		Testes.asseverar(args.length > 2, instrucoesDeUso);
	    		final String input = args[1];
	    		final String arquivo = args[2];
    			final String codigoFonte = Files.readString(Paths.get(arquivo));
    			System.out.println(Parser.rodar(input, codigoFonte));
	    	}
	    	default -> throw new IllegalArgumentException(instrucoesDeUso);
	    };
	}
}
