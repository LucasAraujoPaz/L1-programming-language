package l1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class L1 {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		final String instrucoesDeUso = "Uso: \n" + "check <file> \n" + "run <input?> <file> \n"; 
		
		Testes.asseverar(args.length > 0, instrucoesDeUso);
	    final String comando = args[0];
	    switch (comando) {
	    	case "check" -> {
	    		Testes.asseverar(args.length > 1, instrucoesDeUso);
		    	final String arquivo = args[1];
				final String codigoFonte = Files.readString(Paths.get(arquivo));
				Parser.checar(codigoFonte);
	    	}
	    	case "run" -> {
	    		Testes.asseverar(args.length > 1, instrucoesDeUso);
	    		final String input = args.length == 2 ? "" : args[1];
	    		final String arquivo = args.length == 2 ? args[1] : args[2];
    			final String codigoFonte = Files.readString(Paths.get(arquivo));
    			System.out.println(Parser.rodar(input, codigoFonte));
	    	}
	    	default -> throw new IllegalArgumentException(instrucoesDeUso);
	    };
	}
}
