package com.smd.webapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smd.webapi.handler.MandatoryInfoException;
import com.smd.webapi.model.User;

@Repository
public class UserRepository {
	private static int ID_USERS = 1;
	
	private List<User> users;
	
	public UserRepository() {
		users = new ArrayList<>();
		
		User gleyson = new User("gleyson", "password");
		gleyson.setId(ID_USERS++);
		User frank = new User("frank", "masterpass");
		frank.setId(ID_USERS++);
		
		users.add(gleyson);
		users.add(frank);
	}
	
	public void save(User user) {
		if(user.getLogin() == null)
			throw new MandatoryInfoException("Login");
		
		if(user.getPassword() == null)
			throw new MandatoryInfoException("Password");
		
		if (user.getId() == null || user.getId() == 0) {
			System.out.println("SAVE - Recebendo o usuário na camada de repositório");
			user.setId(ID_USERS++);
			users.add(user);
		} else {
			System.out.println("UPDATE - Recebendo o usuário na camada de repositório");
			for(User u : users) {
				if(u.getId() == user.getId()) {
					u.setLogin(user.getLogin());
					u.setPassword(user.getPassword());
				}
			}
		}

		System.out.println(user);
	}

	public void deleteById(Integer id) {
		System.out.println(String.format("DELETE/id - Recebendo o id: %d para excluir um usuário", id));
		users.removeIf(user -> user.getId() == id);
	}

	public List<User> findAll() {
		System.out.println("LIST - Listando os usários do sistema");
		return users;
	}

	public User findById(Integer id) {
		System.out.println(String.format("FIND/id - Recebendo o id: %d para localizar um usuário", id));
		return users.stream().filter(user -> user.getId() == id).findFirst().get();
	}

	public User findByLogin(String login) {
		System.out.println(String.format("FIND/login - Recebendo o login: %s para localizar um usuário", login));
		return users.stream().filter(user -> user.getLogin() == login).findFirst().get();
	}

}
