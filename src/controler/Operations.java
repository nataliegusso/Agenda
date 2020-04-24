package controler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Agenda;
import utilities.MyComparator;

public class Operations {
	Scanner sc = new Scanner(System.in);
	
	Agenda agenda = new Agenda();
	List<Agenda>lines = new ArrayList<>();
	
	public void importar(List<Agenda> list) throws ParseException {
		list.sort(new MyComparator());
		
		try (BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\agenda.txt"))){
			String itens = br.readLine(); 
			while (itens != null) {
				String[] fields = itens.split(", ");
				String name = fields[0];
				String phone = fields[1];

				list.add(new Agenda(name, phone));
				itens = br.readLine(); 
			}
		} catch (IOException e) {
			System.err.printf("Error reading file: " + e.getMessage());
		}
	}

		 
	public void exportar(List<Agenda> list) {
		try (PrintWriter bw = new PrintWriter(new FileWriter("c:\\temp\\agenda.txt"))) { 
			list.sort(new MyComparator()); 
			
			for (int i=0; i<list.size() ; i++) {
				bw.printf("%s%n", list.get(i));
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
	}
		
	public void inserir(List<Agenda> list) {
		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Nome do contato: ");
			String nome = sc.nextLine().toLowerCase();
				
			for (Agenda a : list) { //Testar existência
				if (a.getName().equals(nome)) {
					System.out.println("Este contato já foi registrado");
					return;
				}
			}
			System.out.print("Telefone: ");
			String phone = sc.nextLine();
			
			list.add(new Agenda(nome, phone));
			list.sort(new MyComparator());  
			
			exportar(list);
	
			System.out.print("Gostaria de inserir mais algum contato (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();
		}while (answer == 'y');
	}
		  
	public void excluir(List<Agenda> list) {
		listar(list);
		
		System.out.printf("\nInforme a posição a ser excluída: ");
		int i = sc.nextInt();

		try {
			list.remove(i-1);
		} catch (IndexOutOfBoundsException e) {  // está fora do intervalo válido (de 0 até agenda.size()-1)
			System.out.printf("Error: invalid position" + e.getMessage());
		}
		exportar(list);
	}
	
	public void atualizar(List<Agenda> list) {
		System.out.println("");
		sc.nextLine();
		System.out.print("Nome: ");
		String nome = sc.nextLine().toLowerCase();

		int cont = 0;
		for (Agenda p : list) {		//testar não existência
			if (!p.getName().equals(nome)) {
				cont++;
				if (cont == list.size()) {
					System.out.println("Este contato nunca foi registrado.");
				return;
				}
			}
		}
		System.out.print("Telefone: ");
		String phone = sc.nextLine();
		
		for (Agenda pers : list) {
			if (pers.getName().equals(nome)){
				pers.setName(nome);
				pers.setPhone(phone);
			} 
		}
		exportar(list);
	}
		 
	public void listar(List<Agenda> list) {
		int i=0;
		System.out.println();
		System.out.printf("AGENDA \n");
	    for (Agenda agenda : list) {
	    	System.out.print(i+1);
			System.out.print("- " + agenda.getName().substring(0,1).toUpperCase() + agenda.getName().substring(1));		
			System.out.printf(", " + agenda.getPhone() + "\n");
			i++;
		}
	}
}
