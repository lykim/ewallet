package com.flip.persistence.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flip.persistence.DataSource;

import core.gateway.TransferGateway;
import core.model.User;
import core.responseModel.UserTransaction;
import core.utils.CollectionUtils;

public class TransferGatewayMysql implements TransferGateway{
private static TransferGateway INSTANCE;
	
	private TransferGatewayMysql() {}
	
	public static TransferGateway getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TransferGatewayMysql();
		}
		return INSTANCE;
	}
	
	@Override
	public void clean() {
		try(Connection connection = DataSource.getConnection()) {			
			connection.setAutoCommit(false);
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM transfer");
			stmt.execute();
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}
	}

	@Override
	public void transfer(String source, String destination, int amount) {
		try(Connection connection = DataSource.getConnection()) {
			connection.setAutoCommit(false);
			saveTransfer(connection, source, destination, amount);
			updateBalance(connection, source, -amount);
			updateBalance(connection, destination, amount);
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}
	}
	
	private void updateBalance(Connection connection, String username, int amount)throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"UPDATE user SET balance_amount = balance_amount + ? WHERE username = ?");
		stmt.setInt(1,  amount);
		stmt.setString(2, username);
		stmt.execute();
	}
	
	private void saveTransfer(Connection connection, String source, String dest, int amount) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO transfer (source, destination, amount ) VALUES (?, ?,?)");
		stmt.setString(1, source);
		stmt.setString(2, dest);
		stmt.setInt(3,  amount);
		stmt.execute();
	}

	@Override
	public UserTransaction[] getTopTransaction() {
		try(Connection connection = DataSource.getConnection()) {
			PreparedStatement stmt= connection.prepareStatement("SELECT source, Max(amount) as amt "
					+ " FROM transfer  " 
					+ " group by source  "
					+ " limit 10");
			ResultSet rs = stmt.executeQuery();  
			List<UserTransaction> users1 = mapUserTransactionBySourceFromResultSet(rs);
			
			PreparedStatement stmt2= connection.prepareStatement("SELECT destination, Max(amount) as amt "
					+ " FROM transfer t " 
					+ " group by destination  "
					+ " limit 10");
			ResultSet rs2 = stmt2.executeQuery();  
			List<UserTransaction> users2 = mapUserTransactionByDestinationFromResultSet(rs2);
			users1.addAll(users2);
			Map<String, Integer> map = new HashMap<>();
			for(UserTransaction user2 : users2) {
				int maxValue = map.getOrDefault(user2.username, 0);
				int newValue = user2.amount;
				if(Math.abs(newValue) > Math.abs(maxValue)) {
					map.put(user2.username, user2.amount);
				}
			}
			for(UserTransaction user1 : users1) {
				int maxValue = map.getOrDefault(user1.username, 0);
				int newValue = user1.amount;
				if(Math.abs(newValue) > Math.abs(maxValue)) {
					map.put(user1.username, user1.amount);
				}
			}
			
			UserTransaction[] result =map.entrySet().stream()
						 .sorted((a, b) ->
						    Integer.valueOf(Math.abs(b.getValue()))
						    .compareTo(Math.abs(a.getValue()))
						).map(entry -> new UserTransaction(entry.getKey(), entry.getValue()))
						.toArray(UserTransaction[]::new);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("database error",e);
		}
	}
	
	private List<UserTransaction> mapUserTransactionByDestinationFromResultSet(ResultSet rs) throws SQLException {
		List<UserTransaction> users = new ArrayList<UserTransaction>();
		while(rs.next()){
			users.add(createUserTransactionByDestinationFromRs(rs));
		}
		return users;
	} 
	
	private List<UserTransaction> mapUserTransactionBySourceFromResultSet(ResultSet rs) throws SQLException {
		List<UserTransaction> users = new ArrayList<UserTransaction>();
		while(rs.next()){
			users.add(createUserTransactionBySourceFromRs(rs));
		}
		return users;
	}
	
	private UserTransaction createUserTransactionBySourceFromRs(ResultSet rs)  throws SQLException {
		UserTransaction user = new UserTransaction();
		user.username = rs.getString("source");
		user.amount = -rs.getInt("amt");
		return user;
	}
	
	private UserTransaction createUserTransactionByDestinationFromRs(ResultSet rs)  throws SQLException {
		UserTransaction user = new UserTransaction();
		user.username = rs.getString("destination");
		user.amount = rs.getInt("amt");
		return user;
	}

	@Override
	public UserTransaction[] getOveralTopTransaction() {
		try(Connection connection = DataSource.getConnection()) {
			PreparedStatement stmt= connection.prepareStatement("SELECT source, SUM(amount) as amt "
					+ " FROM transfer " 
					+ " GROUP BY source limit 10");
			ResultSet rs = stmt.executeQuery();  
			List<UserTransaction> users1 = mapUserTransactionFromResultSet(rs);
			return users1.stream()
				.sorted((a, b) ->
			    Integer.valueOf(Math.abs(b.amount))
			    .compareTo(Math.abs(a.amount))
			).toArray(UserTransaction[]::new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("database error",e);
		}
	}
	
	private List<UserTransaction> mapUserTransactionFromResultSet(ResultSet rs) throws SQLException {
		List<UserTransaction> users = new ArrayList<UserTransaction>();
		while(rs.next()){
			users.add(createUserTransactionFromRs(rs));
		}
		return users;
	}

	private UserTransaction createUserTransactionFromRs(ResultSet rs)  throws SQLException {
		UserTransaction user = new UserTransaction();
		user.username = rs.getString("source");
		user.amount = rs.getInt("amt");
		return user;
	}
}
