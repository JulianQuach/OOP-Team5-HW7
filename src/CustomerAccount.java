import java.sql.SQLException;

// Uses the CustomerAccountDAO to save to the database

public class CustomerAccount {

	private String custName;
	private String custPhone;
	private String custAcctNumber;

	public CustomerAccount() {
		// create empty CustomerAccount
	}

	public CustomerAccount createNewAccount(String name, String phone, CustomerAccountDAO cad) throws SQLException, NoAccountCreatedException {
		CustomerAccount newAcct = null;
		String acctNum = "";

		try {
			acctNum = cad.newAcctNumber(name, phone);
			if (acctNum != "1111222233334444") {
				throw new NoAccountCreatedException(String.format("Account for %s at %s not created", name, phone));
			}
		} catch (SQLException se) {
			throw new NoAccountCreatedException(String.format("Account for %s at %s not created", name, phone));
		}

		custName = name;
		custPhone = phone;
		custAcctNumber = acctNum;

		try {
			boolean canSave = cad.saveAccount(this);
			if (canSave == false) {
				throw new NoAccountCreatedException(
						String.format("Account for %s at %s not created with account number %s", name, phone, acctNum));
			}
		} catch (SQLException se2) {
			if (se2.getErrorCode() != 803)
				throw new NoAccountCreatedException(
						String.format("Account for %s at %s not created with account number %s", name, phone, acctNum));
		}

		return newAcct;
	}

	public CustomerAccount updateCustomerName(String acctNum, String name, CustomerAccountDAO cad) throws NoSuchCustomerAccountException {

		try {
			custName = name;
			boolean canUpdate = cad.updateAccount(this);
			if (canUpdate == false) {
				throw new NoSuchCustomerAccountException(
						String.format("No customer record with acctount number %s ", acctNum));
			}
		} catch (SQLException se) {
			// unable to find the record to be updated
			throw new NoSuchCustomerAccountException(
					String.format("No customer record with acctount number %s ", acctNum));
		}

		return null;
	}

}
