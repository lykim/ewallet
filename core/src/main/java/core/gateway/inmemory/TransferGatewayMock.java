package core.gateway.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import core.exception.BusinessException;
import core.gateway.TransferGateway;
import core.model.Transfer;
import core.model.User;
import core.responseModel.UserTransaction;

public class TransferGatewayMock implements TransferGateway{
	Collection<Transfer> transfers = InMemorytorage.transfers;
	Collection<User> users = InMemorytorage.users;
	private static TransferGateway INSTANCE;
	
	private TransferGatewayMock() {}
	
	public static TransferGateway getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TransferGatewayMock();
		}
		return INSTANCE;
	}
	@Override
	public void clean() {
		transfers.clear();
	}

	@Override
	public void transfer(String source, String destination, int amount) {
		try {
			transfers.add(new Transfer(source,destination,amount));
			User sourceUser = findUserByUsername(source);
			User destinationUser = findUserByUsername(destination);
			sourceUser.substractBalance(amount);
			destinationUser.addBalance(amount);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(500, "Server error");
		}
	}
	private User findUserByUsername(String username) {
		Optional<User> optionalUser =  users.stream().filter(
					user -> username.equals(user.getUsername()))
				.findAny();
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public UserTransaction[] getTopTransaction() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		List<Transfer> userList = transfers.stream().sorted(Comparator.comparing(Transfer::getAmount).reversed()).collect(Collectors.toList());
		for(int i=0; i< userList.size(); i++) {
			Transfer transfer = userList.get(i);
			int maxValue = map.getOrDefault(transfer.getSource(), 0);
			int newValue = transfer.getAmount();
			if(Math.abs(newValue) > Math.abs(maxValue)) {
				map.put(transfer.getSource(), -newValue);
			}
		}
		for(int i=0; i< userList.size(); i++) {
			Transfer transfer = userList.get(i);
			int maxValue = map.getOrDefault(transfer.getDestination(), 0);
			int newValue = transfer.getAmount();
			if(Math.abs(newValue) > Math.abs(maxValue)) {
				map.put(transfer.getDestination(), newValue);
			}
		}
		UserTransaction[] result =map.entrySet().stream()
				 .sorted((a, b) ->
				    Integer.valueOf(Math.abs(b.getValue()))
				    .compareTo(Math.abs(a.getValue()))
				).map(entry -> new UserTransaction(entry.getKey(), entry.getValue()))
				 .limit(10)
				.toArray(UserTransaction[]::new);
		for(UserTransaction res : result) {
			System.out.println(res.username + " = " + res.amount);
		}
		return result;
	}

	@Override
	public UserTransaction[] getOveralTopTransaction() {
		Map<String, Integer> mapTransaction = new HashMap<>();
		for(Transfer transfer : transfers) {
			Integer maxValue = mapTransaction.getOrDefault(transfer.getSource(), 0);
			mapTransaction.put(transfer.getSource(), maxValue + transfer.getAmount());
		}
		return mapTransaction.entrySet().stream()
					.sorted(Comparator.comparing(Entry::getValue, Comparator.reverseOrder()))
					.map(entry -> new UserTransaction(entry.getKey(), entry.getValue()))
					.toArray(UserTransaction[]::new);
	}

}
