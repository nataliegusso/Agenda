package controler;

import java.util.List;
import java.util.Scanner;

import model.dao.AgendaDao;
import model.dao.DaoFactory;
import model.entities.Agenda;

public class Operations {
	Scanner sc = new Scanner(System.in);
	AgendaDao agendaDao = DaoFactory.createAgendaDao();  //Create em outra classe
	
	public void inserir(List<Agenda> list) {
		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Nome do contato: ");
			String name = sc.nextLine().toLowerCase();
				
			for (Agenda a : list) { //Testar existência
				if (a.getName().equals(name)) {
					System.out.println("Este contato já foi registrado");
					return;
				}
			}
			System.out.print("Telefone: ");
			String phone = sc.nextLine();
			
			Agenda newAgenda = new Agenda(null, name, phone);
			agendaDao.insert(newAgenda);
			
			System.out.print("Gostaria de inserir mais algum contato (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();
		}while (answer == 'y');
	}

			  
	public void excluir(List<Agenda> list) {
		listar(list);
		
		System.out.printf("\nInforme a posição a ser excluída: ");
		int id = sc.nextInt();

		try {
			agendaDao.deleteById(id);
		} catch (IndexOutOfBoundsException e) {  // está fora do intervalo válido (de 0 até agenda.size()-1)
			System.out.printf("Error: invalid position" + e.getMessage());
		}
	}
	
	
	public void atualizar(List<Agenda> list) {
		System.out.printf("\nInforme a posição a ser atualizada: ");
		int id = sc.nextInt();
		Agenda ag = agendaDao.findById(id);

		System.out.println("");
		sc.nextLine();
		System.out.print("Nome: ");
		String nome = sc.nextLine().toLowerCase();
		ag.setName(nome);

/*		int cont = 0;
		for (Agenda p : list) {		//testar não existência
			if (!p.getName().equals(nome)) {
				cont++;
				if (cont == list.size()) {
					System.out.println("Este contato nunca foi registrado.");
				return;
				}
			}
		}
*/		System.out.print("Telefone: ");
		String phone = sc.nextLine();
		ag.setPhone(phone);
		
		for (Agenda pers : list) {
			if (pers.getName().equals(nome)){
				pers.setName(nome);
				pers.setPhone(phone);
			} 
		}
		agendaDao.update(ag);
	}
		 
	
	public void listar(List<Agenda> list) {
		list = agendaDao.findAll();
		for (Agenda obj : list) {
			System.out.println(obj);
		}
	}
}
