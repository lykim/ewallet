package core.gateway;

import core.model.User;

public interface UserGateway extends Gateway{
	void create(User user);
	User findUserByUsername(String username);
}
