package core.gateway.inmemory;

import java.util.ArrayList;
import java.util.Collection;

import core.model.Transfer;
import core.model.User;

public class InMemorytorage {
	public static Collection<User> users;
	public static Collection<Transfer> transfers;
	static {
		users = new ArrayList<User>();
		transfers = new ArrayList<Transfer>();
	}
}
