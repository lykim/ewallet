package core.model;

public class Transfer {
	private String source;
	private String destination;
	private int amount;
	
	public Transfer() {}
	
	public Transfer(String source, String destination, int amount) {
		this.source = source;
		this.destination = destination;
		this.amount = amount;
	}
	public String getSource() {
		return source;
	}
	public String getDestination() {
		return destination;
	}
	public int getAmount() {
		return amount;
	}
}
