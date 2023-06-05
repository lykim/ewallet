package core.gateway.inmemory;

import java.util.Collection;
import java.util.Optional;

import core.gateway.UserGateway;
import core.model.User;

public class UserGatewayMock implements UserGateway{
	Collection<User> users = InMemorytorage.users;
	private static UserGateway INSTANCE;
	
	private UserGatewayMock() {}
	
	public static UserGateway getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UserGatewayMock();
		}
		return INSTANCE;
	}
	@Override
	public void create(User user) {
		users.add(user);
	}
	
	
	@Override
	public void clean() {
		users.clear();
	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> optionalUser =  users.stream().filter(
					user -> username.equals(user.getUsername()))
				.findAny();
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public void addBalance(String username, int amount) {
		User user = findUserByUsername(username);
		if(user != null) {
			user.setBalanceAmount( user.getBalanceAmount() + amount );
		}
		
	}

	

}
