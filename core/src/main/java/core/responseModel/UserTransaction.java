package core.responseModel;

public class UserTransaction {
	public UserTransaction() {};
	public UserTransaction(String username, int amount) {
		this.username = username;
		this.amount = amount;
	};
	public String username;
	public int amount;
}
