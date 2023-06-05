package core.gateway;

import core.responseModel.UserTransaction;

public interface TransferGateway extends Gateway{
	void transfer(String source, String destination, int amount);
	UserTransaction[] getTopTransaction();
	UserTransaction[] getOveralTopTransaction();
}
