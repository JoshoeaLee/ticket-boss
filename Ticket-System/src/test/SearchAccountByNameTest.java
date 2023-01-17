/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import model.Account;
import model.AccountDatabase;

class SearchAccountByNameTest {

	static ArrayList<Account> accounts = new ArrayList<Account>();
	static AccountDatabase accountDatabase = new AccountDatabase();

	@BeforeAll
	public static void init() {
		Account test1 = new Account("Tina", "123");
		Account test2 = new Account("qq", "qwe");
		accountDatabase.addAccount(test1);
		accountDatabase.addAccount(test2);
	}

	public static Collection<String> generateUserName() {
		return Arrays.asList(new String[] { null });
	}
	
	// Test if the user name is null, which means it does not exist in the database
	@ParameterizedTest
	@DisplayName("check account by null username")
	@MethodSource("generateUserName")
	void searchAccountByNametestNull1(String username) {
		Account account = accountDatabase.searchAccountByUserName(username);
		assertNull(account);
	}
	
	@Test
	void searchAccountByNameTestNull2() {
		Account account = accountDatabase.searchAccountByUserName(null);
		assertNull(account);		
	}

	// Test if the user name exists in the database
	@ParameterizedTest
	@DisplayName("check account by name found")
	@ValueSource(strings = { "Tina", "qq" })
	void searchAccountByNametestPass(String username) {
		Account account = accountDatabase.searchAccountByUserName(username);
		assertNotNull(account);
		assertEquals(username, account.getUserName());
	}

	// Test if the user name is not null, but not exists in the database
	@ParameterizedTest
	@DisplayName("check account by name, not found")
	@ValueSource(strings = { "t", " bb", "1235" })
	void searchAccountByNametestFail(String username) {
		Account account = accountDatabase.searchAccountByUserName(username);
		assertNull(account);
		assertEquals(null, account);
	}

}
