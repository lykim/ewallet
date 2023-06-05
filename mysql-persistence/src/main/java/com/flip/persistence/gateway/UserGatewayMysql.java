package com.flip.persistence.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.flip.persistence.DataSource;

import core.gateway.UserGateway;
import core.gateway.inmemory.InMemorytorage;
import core.model.User;
import core.utils.CollectionUtils;

public class UserGatewayMysql implements UserGateway{
	Collection<User> users = InMemorytorage.users;
	private static UserGateway INSTANCE;
	
	private UserGatewayMysql() {}
	
	public static UserGateway getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UserGatewayMysql();
		}
		return INSTANCE;
	}

	@Override
	public void clean() {
		try(Connection connection = DataSource.getConnection()) {			
			connection.setAutoCommit(false);
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM user");
			stmt.execute();
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}
		
	}

	@Override
	public void create(User user) {
		try(Connection connection = DataSource.getConnection()) {
			connection.setAutoCommit(false);
			saveUser(connection, user);
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}
	}
	
	private void saveUser(Connection connection, User user) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO user (username, balance_amount ) VALUES ( ?,?)");
		stmt.setString(1, user.getUsername());
		stmt.setInt(2,  user.getBalanceAmount());
		stmt.execute();
	}

	@Override
	public User findUserByUsername(String username) {
		try(Connection connection = DataSource.getConnection()) {
			PreparedStatement stmt= connection.prepareStatement("SELECT * "
					+ " FROM user u" 
					+ " WHERE u.username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();  
			List<User> users = mapUserFromResultSet(rs);
			if(CollectionUtils.isEmpty(users)) return null;
			return users.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("database error",e);
		}
	}
	
	private List<User> mapUserFromResultSet(ResultSet rs) throws SQLException {
		List<User> users = new ArrayList<User>();
		while(rs.next()){
			users.add(createUserFromRs(rs));
		}
		return users;
	}
	
	private User createUserFromRs(ResultSet rs)  throws SQLException {
		User user = new User(rs.getString("username"));
		user.setBalanceAmount(rs.getInt("balance_amount"));
		return user;
	}

	@Override
	public void addBalance(String username, int amount) {
		try(Connection connection = DataSource.getConnection()) {
			connection.setAutoCommit(false);
			addBalance(connection, username, amount);
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}
		
	}
	
	private void addBalance(Connection connection, String username, int amount)throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"UPDATE user SET balance_amount = balance_amount + ? WHERE username = ?");
		stmt.setInt(1,  amount);
		stmt.setString(2, username);
		stmt.execute();
	}
}
