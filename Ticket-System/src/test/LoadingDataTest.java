package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import model.BrowsingPageEvent;

/**
 *Testing the loadEventData() and loadDetailData().
 *TestEventDatabase is used instead of EventDatabase as minor changes was made
 *to the EventDatabase code to make it easier to test for me
 */
class LoadingDataTest {
	
	TestEventDatabase eventDataBase = new TestEventDatabase();


	//Test existing files. Hoping it passes.
	//Results - Pass -> I'm happy
	@Test
	void testLoadEventData() {
		File eventDataFile = new File("testEventInfo.txt");
		File eventDescriptionFile = new File("testDescription.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertEquals("Auckland", array.get(0).getLocation());
		assertEquals(327, array.get(9).getDescription().length());
	}
	
	//Empty Event File? 
	//Hoping there to be a red error as I don't want 
	//an arrayList to be made in the first place if the eventData is null.
	//I don't want assertNull to work because that'd mean a null arrayList was created
	//Results - Red Error. -> I'm happy
	@Test
	void testEmptyFile() {
		File eventDataFile = new File("empty.txt");
		File eventDescriptionFile = new File("testDescription.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertNull(array.get(0).getName());
	}
	
	//Empty Description File?
	//Hoping this passes
	//Events should be made here but their descriptions should be null.
	//Results - Pass -> I'm happy. Placeholders need to be put in listviews to accomodate this
	@Test
	void testEmptyDescription() {
		File eventDataFile = new File("testEventInfo.txt");
		File eventDescriptionFile = new File("empty.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertNull(array.get(9).getDescription());
	}
	
	//File where number is where a string was originally (Instead of Six-sixty, it's 660)
	//Hoping this passes. Events can be called numbers if that's the event name.
	//Results - Pass -> I'm happy.
	@Test
	void testIntforString() {
		File intForStringTest = new File("IntForString.txt");
		File eventDescriptionFile = new File("testDescription.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(intForStringTest, eventDescriptionFile);
		assertEquals("660", array.get(0).getName());
	}
	
	//File where string is where a number was originally 
	//Hoping for a red error. I don't want the arraylist to be made in the first place if
	//there's a problem like this in the textfile.
	//Results - Red Error -> Happy-ish. These methods are very sensitive to the textfile having
	//the exact right format. We need to make a system which guarantees that the information
	//given from event organizers match the format we have.
	@Test
	void testStringforInt() {
		File stringForIntTest = new File("StringForInt.txt");
		File eventDescriptionFile = new File("testDescription.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(stringForIntTest, eventDescriptionFile);
		assertEquals("Twenty", array.get(2).getPrice());
	}
	
	//TextFile where there is a blank line in the middle of it.
	//Hoping for a pass. Blank lines are hopefully ignored.
	//Results - Pass -> I'm happy.
	@Test
	void testLineSkip() {
		File lineSkipTest = new File("lineSkip.txt");
		File eventDescriptionFile = new File("testDescription.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(lineSkipTest, eventDescriptionFile);
		assertEquals("Blonde", array.get(4).getName());
	}
	
	
	
	//Checking with 1,000 events and descriptions. Hoping for passes
	//Results - Passes
	
	@ParameterizedTest (name = "{index}: Thousand Events: index {2} is at location {3}")
	@DisplayName ("Checking if 1000 Event Data has loaded correctly")
	@MethodSource ("generateEventTestCases")
	void testLoadData(File eventDataFile, File eventDescriptionFile, int index, String result) {
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertEquals(result, array.get(index).getLocation());
	}
	
	@ParameterizedTest (name = "{index}: Thousand Events: index {2} with length {3}")
	@DisplayName ("Checking if 1000 Event Descriptions have loaded correctly")
	@MethodSource ("generateDescriptionTestCases")
	void testLoadData(File eventDataFile, File eventDescriptionFile, int index, int result) {
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertEquals(result, array.get(index).getDescription().length());
	}
	
	
	//Hoping this is doesn't take like a minute to finish?
	//Results: Under 10 seconds. Sure.
	@Test
	void testStressTest() {
		File eventDataFile = new File("stressEvents.txt");
		File eventDescriptionFile = new File("stressDescriptionFile.txt");
		ArrayList<BrowsingPageEvent> array = eventDataBase.loadEventData(eventDataFile, eventDescriptionFile);
		assertEquals("TestDate983452", array.get(983452).getDate());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static Collection generateEventTestCases() {
		File eventDataFile = generateEventsForTest();
		File eventDescriptionFile = generateEventDescriptionsForTest();
		
		return Arrays.asList(new Object[][] {
			{eventDataFile, eventDescriptionFile, 672, "TestLocation672"},
			{eventDataFile, eventDescriptionFile, 310, "TestLocation310"},

		});
	}
	
	//Even with an empty description, I'm hoping for index 0 event to have the description
	//which is 0 long and not for it to be skipped and end up getting index 1's description instead.
	public static Collection generateDescriptionTestCases() {
		File eventDataFile = generateEventsForTest();
		File eventDescriptionFile = generateEventDescriptionsForTest();
		
		return Arrays.asList(new Object[][] {
			{eventDataFile, eventDescriptionFile, 432, 432},
			{eventDataFile, eventDescriptionFile, 0, 0},
		});
	}
	
	/**
	 * Generating 1000 events for test
	 * @return
	 */
	public static File generateEventsForTest() {
		
		File thousandTestFile = new File("thousandTestFile.txt");
		try {
			PrintStream ps = new PrintStream(thousandTestFile);
			for(int i = 0; i<1000; i++) {
				
				//Prints line in format TestName0 0(price) TestLocation0 0(popularity rank) TestDate0 TestType0
				ps.println("TestName" + i + " " + i + " TestLocation" + i + 
						" " + i + " TestDate" + i + " TestType" + i);
			}
			ps.close();
			
		}catch(IOException e) {System.out.println("File not written");}
		
		return thousandTestFile;
	}
	
	/**
	 * Generating 1000 descriptions for test
	 * @return
	 */
	public static File generateEventDescriptionsForTest() {
		
		File thousandDescriptionFile = new File("thousandDescriptionFile.txt");
		try {
			PrintStream ps = new PrintStream(thousandDescriptionFile);
			
			StringBuilder stringBuilder = new StringBuilder();
			for(int i = 0; i<1000; i++) {
				//Make descriptions as long as the index
				if(i!=0)
					stringBuilder.append("x");
				
				ps.println(stringBuilder.toString());
			}
			ps.close();
			
		}catch(IOException e) {System.out.println("File not written");}
		
		return thousandDescriptionFile;
	}
}
