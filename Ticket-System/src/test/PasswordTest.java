package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import model.Account;

class PasswordTest {
	static ArrayList<Account> accounts = new ArrayList<Account>();
	
	public static Collection generateAccountsForPasswordTest() {
		Account test1 = new Account("test", " ");
		Account test2 = new Account("test", "123");
		return Arrays.asList(new Object[][] {
			{test1, " ",true},
			{test1, "1",false},
			{test2, "123",true},
			{test2, null,false},
		});	
	}
	
	@ParameterizedTest (name = "{index}: password({1}) = {2}")
	@DisplayName ("Checking Password")
	@MethodSource ("generateAccountsForPasswordTest")
	void passwordTest(Account account, String password, boolean result) {	
		assertEquals(result, account.isValidPassword(password));
	}

}
