package core.gateway.inmemory;

import java.util.ArrayList;
import java.util.Collection;

import core.model.User;

public class InMemorytorage {
	public static Collection<User> users;
	static {
		users = new ArrayList<User>();
	}
}
