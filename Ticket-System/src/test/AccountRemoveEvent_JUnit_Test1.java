package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Account;
import model.BrowsingPageEvent;

class AccountRemoveEvent_JUnit_Test1 {

	private Account account = new Account("UserName","Password");
	private BrowsingPageEvent bpm1 = new BrowsingPageEvent("Six-Sixty",100.00,"Auckland","5","2022-11-11","Concert");
	private BrowsingPageEvent bpm2 = new BrowsingPageEvent("Bullet-Train",30.00,"Auckland","1","2022-11-10","Movies");
	private BrowsingPageEvent bpm3;
	
	public void addEvents() {
		this.account.saveEvent(bpm1);
	}

	@Test
	void testRemoveEvent_Size_success() {
		addEvents();
		this.account.removeEvent(bpm1);
		assertEquals(0, this.account.getSavedEvent().size());
	}
	
	@Test
	void testRemoveEvent_Return_success() {
		addEvents();
		assertEquals(true, this.account.removeEvent(bpm1));
	}

	@Test
	void testRemoveEvent_ArrayList_success() {
		addEvents();
		this.account.removeEvent(bpm1);
		assertArrayEquals(new BrowsingPageEvent[]{}, this.account.getSavedEvent().toArray());
	}
	
	@Test
	void testRemoveEvent_Return_null() {
		addEvents();
		assertEquals(false, this.account.removeEvent(bpm3));
	}
	
	@Test
	void testRemoveEvent_ArrayList_null() {
		addEvents();
		this.account.removeEvent(bpm3);
		assertArrayEquals(new BrowsingPageEvent[]{bpm1}, this.account.getSavedEvent().toArray());
	}
	
	@Test
	void testRemoveEvent_Return_notExistInList() {
		addEvents();
		assertEquals(false, this.account.removeEvent(bpm2));
	}

	@Test
	void testRemoveEvent_ArrayList_notExistInList() {
		addEvents();
		this.account.removeEvent(bpm2);
		assertArrayEquals(new BrowsingPageEvent[]{bpm1}, this.account.getSavedEvent().toArray());
	}
	
	@Test
	void testRemoveEvent_Return_listIsEmpty() {
		assertEquals(false, this.account.removeEvent(bpm1));
	}
	
	@Test
	void testRemoveEvent_ArrayList_listIsEmpty() {
		this.account.removeEvent(bpm1);
		assertArrayEquals(new BrowsingPageEvent[]{}, this.account.getSavedEvent().toArray());
	}
	
}
