package core.model;

public class User {
	private String username;
	private int balanceAmount;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public int getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(int balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	public void addBalance(int amount) {
		this.balanceAmount += amount;
	}
	
	public void substractBalance(int amount) {
		this.balanceAmount -= amount;
	}
	
}
