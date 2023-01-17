package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import model.Account;
import model.BrowsingPageEvent;

class AccountSaveEvent_JUnit_Test1 {

	private Account account = new Account("UserName","Password");
	private static BrowsingPageEvent bpm1 = 
			new BrowsingPageEvent("Six-Sixty",100.00,"Auckland","5","2022-11-11","Concert");
	private static BrowsingPageEvent bpm2 = 
			new BrowsingPageEvent("Bullet-Train",30.00,"Auckland","1","2022-11-10","Movies");
	private static BrowsingPageEvent bpm3;
	
	public Object[] addAll(BrowsingPageEvent[] events) {
		for(BrowsingPageEvent e : events) {
			this.account.saveEvent(e);
		}
		return this.account.getSavedEvent().toArray();
	}
	
	public static Collection events_wrongExpectation() {
		return Arrays.asList(new Object[][] {
			//success
			{new BrowsingPageEvent[]{bpm1,bpm2}, new BrowsingPageEvent[]{bpm2,bpm1}},
			//duplication
			{new BrowsingPageEvent[]{bpm1,bpm1,bpm2}, new BrowsingPageEvent[]{bpm1,bpm1,bpm2}},
			//null
			{new BrowsingPageEvent[]{bpm1,bpm3,bpm2}, new BrowsingPageEvent[]{bpm1,bpm3,bpm2}},
		});
	}
	
	@ParameterizedTest(name="{index}:event({0})={1}")
	@DisplayName("Check saved events_wrong expectation")
	@MethodSource("events_wrongExpectation")
	void testSavedEvents_wrongExpectation(BrowsingPageEvent[] value, BrowsingPageEvent[] result) {
		assertArrayEquals(result, addAll(value));
	}
	
	public static Collection events() {
		return Arrays.asList(new Object[][] {
			//success
			{new BrowsingPageEvent[]{bpm1,bpm2}, new BrowsingPageEvent[]{bpm1,bpm2}},
			//duplication
			{new BrowsingPageEvent[]{bpm1,bpm1,bpm2}, new BrowsingPageEvent[]{bpm1,bpm2}},
			//null
			{new BrowsingPageEvent[]{bpm1,bpm3,bpm2}, new BrowsingPageEvent[]{bpm1,bpm2}},
		});
	}
	
	@ParameterizedTest(name="{index}:event({0})={1}")
	@DisplayName("Check saved events")
	@MethodSource("events")
	void testSavedEvents(BrowsingPageEvent[] value, BrowsingPageEvent[] result) {
		assertArrayEquals(result, addAll(value));
	}
	
//	@Test
//	void testSaveEvent_ArrayList_null() {
//		this.account.saveEvent(bpm1);
//		this.account.saveEvent(bpm3);
//		this.account.saveEvent(bpm2);
//		assertArrayEquals(new BrowsingPageEvent[]{bpm1,bpm2}, this.account.getSavedEvent().toArray());
//	}
//	
//	@Test
//	void testSaveEvent_ArrayList_null_wrongExpectation() {
//		this.account.saveEvent(bpm1);
//		this.account.saveEvent(bpm3);
//		this.account.saveEvent(bpm2);
//		assertArrayEquals(new BrowsingPageEvent[] {bpm1,bpm3,bpm2}, this.account.getSavedEvent().toArray());
//	}
	
	@Test
	void testSaveEvent_Message_Success() {
		assertEquals("Event added to favourite", this.account.saveEvent(bpm1));
	}
	
	@Test
	void testSaveEvent_Message_Duplication() {
		this.account.saveEvent(bpm1);
		assertEquals("This event is already added to favourite", this.account.saveEvent(bpm1));
	}
	
	@Test
	void testSaveEvent_Message_Null() {
		assertEquals("No event to save", this.account.saveEvent(bpm3));
	}
	
}
