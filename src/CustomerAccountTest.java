import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class CustomerAccountTest {

	CustomerAccount ca;
	CustomerAccountDAO cad;
	
	@BeforeEach
	void setUp() throws Exception {
		ca = new CustomerAccount();
		cad = mock(CustomerAccountDAO.class);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateNewAccount1stException() throws SQLException, NoAccountCreatedException {
		String name = "a";
		String phone = "1";
		when(cad.newAcctNumber(name, phone)).thenReturn(null);
		ca.createNewAccount(name, phone, cad);
	}

	@Test
	void testCreateNewAccount2ndException() throws SQLException, NoAccountCreatedException {
		String name = "a";
		String phone = "1";
		when(cad.newAcctNumber(name, phone)).thenReturn("1111222233334444");
		when(cad.saveAccount(ca)).thenReturn(false);
		ca.createNewAccount(name, phone, cad);
	}
	
	@Test
	void testCreateNewAccountBothWork() throws SQLException, NoAccountCreatedException {
		String name = "a";
		String phone = "1";
		when(cad.newAcctNumber(name, phone)).thenReturn("1111222233334444");
		when(cad.saveAccount(ca)).thenReturn(true);
		ca.createNewAccount(name, phone, cad);
	}
	
	@Test
	void testupdateCustomerNameException() throws SQLException, NoSuchCustomerAccountException {
		String acctNum = "1";
		String name = "a";
		when(cad.updateAccount(ca)).thenReturn(false);
		ca.updateCustomerName(acctNum, name, cad);
	}
	
	@Test
	void testupdateCustomerNameWork() throws SQLException, NoSuchCustomerAccountException {
		String acctNum = "1";
		String name = "a";
		when(cad.updateAccount(ca)).thenReturn(true);
		ca.updateCustomerName(acctNum, name, cad);
	}
}
