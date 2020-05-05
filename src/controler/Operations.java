package controler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.AgendaDao;
import model.dao.DaoFactory;
import model.entities.Agenda;

public class Operations {
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
	
	AgendaDao agendaDao = DaoFactory.createAgendaDao();  //Create em outra classe
	
	public void inserir(List<Agenda> list) throws ParseException {
		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Nome do contato: ");
			String name = sc.nextLine();
				
			for (Agenda a : list) { //Testar existência
				if (a.getName().equals(name)) {
					System.out.println("Este contato já foi registrado");
					return;
				}
			}
			System.out.print("Telefone fixo: ");
			String phone = sc.nextLine();
			
			System.out.print("Celular: ");
			String cellphone = sc.nextLine();

			System.out.print("E-mail: ");
			String email = sc.nextLine();
			
			System.out.print("Aniversário (dd/MM): ");
			Date birthdate = sdf.parse(sc.next()); 
			
			
			Agenda newAgenda = new Agenda(null, name, phone, cellphone, email, birthdate);
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
	
	
	public void atualizar(List<Agenda> list) throws ParseException {
		listar(list);
		
		System.out.printf("\nInforme a posição a ser atualizada: ");
		int id = sc.nextInt();
		Agenda ag = agendaDao.findById(id);

		System.out.println("");
		sc.nextLine();
		System.out.print("Nome: ");
		String nome = sc.nextLine();
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
*/		System.out.print("Telefone fixo: ");
		String phone = sc.nextLine();
		ag.setPhone(phone);
		
		System.out.print("Celular: ");
		String cellphone = sc.nextLine();
		ag.setCellphone(cellphone);

		System.out.print("E-mail: ");
		String email = sc.nextLine();
		ag.setEmail(email);

		System.out.print("Birthday: ");
		Date birthdate = sdf.parse(sc.next());
		ag.setBirthdate(birthdate);

		for (Agenda person : list) {
			if (person.getName().equals(nome)){
				person.setName(nome);
				person.setPhone(phone);
				person.setCellphone(cellphone);
				person.setEmail(email);
				person.setBirthdate(birthdate);
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
