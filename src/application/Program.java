package application;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controler.Operations;
import entities.Agenda;

public class Program {

	public static void main(String[] args) throws IOException, ParseException {
		Scanner sc = new Scanner(System.in);
		
		List<Agenda> agenda = new ArrayList<>();
		Operations operation = new Operations();
		operation.importar(agenda);

		int opcao;
		do {
			System.out.printf("\n###  MENU PRINCIPAL  ###\n");
			System.out.printf("[ 1 ] Inserir Contato\n");
			System.out.printf("[ 2 ] Excluir Contato\n");
			System.out.printf("[ 3 ] Atualizar Contato\n");
			System.out.printf("[ 4 ] Listar Contatos\n");
			System.out.printf("[ 0 ] Encerrar o Programa\n");
			System.out.printf("\nOpção Desejada: ");
			opcao = sc.nextInt();
			sc.nextLine();
		 
			switch (opcao) {
				case 1: operation.inserir(agenda); break;
				case 2: operation.excluir(agenda); break;
				case 3: operation.atualizar(agenda); break;
				case 4: operation.listar(agenda); break;
			}
		} while (opcao == 1 || opcao == 2 || opcao == 3 || opcao == 4);
		sc.close();
	}
}


