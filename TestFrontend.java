// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Role: FrontEnd Developer
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.zip.DataFormatException;

/**
 * This class contains a set of tests for the front end of the Movie Mapper project.
 */
public class TestFrontend {
	
	public static void main(String[] args) {
		(new TestFrontend()).runTests();
	}
	
	/**
	 * This method calls all of the test methods in the class and ouputs pass / fail
	 * for each test.
	 */
	public void runTests() {
		System.out.println("Testing if 'x' exits application");
		System.out.println("Passed: " + this.enterXToExit());

		System.out.println("-------");
		System.out.println("Testing if initial output is empty");
		System.out.println("Passed: " + this.testFrontendInitialOutputNoMovie());

		System.out.println("-------");
		System.out.println("Testing if Genre Mode displays the correct genres");
		System.out.println("NOTE: This test will not pass until a backend is integrated");
		System.out.println("Passed: " + this.testFrontendGForGenres());	// won't pass until integrated

		System.out.println("-------");
		System.out.println("Testing if non-valid commands in base mode create error messages");
		System.out.println("Passed: " + this.testInvalidInputBase());

		System.out.println("-------");
		System.out.println("Testing if more non-valid commands in base mode create error messages");
		System.out.println("Passed: " + this.testInvalidInputBase2());

    	System.out.println("-------");
		System.out.println("Testing if non-valid commands in genre selection mode create error messages");
		System.out.println("Passed: " + this.testInvalidInputGenre());

		System.out.println("-------");
		System.out.println("Testing if non-valid commands in rating selection mode create error messages");
		System.out.println("Passed: " + this.testInvalidInputRating());

		System.out.println("-------");
		System.out.println("Testing if deselecting all ratings correctly displays no movies");
		System.out.println("Passed: " + this.testDeslectAllRatings());

		System.out.println("-------");
		System.out.println("Testing if deselecting some ratings correctly displays the correct movies");
		System.out.println("Passed: " + this.testSelectFewRatings());
		
	}
	
    /**
	 * This test runs the front end and redirects its output to a string. It then
	 * passes in 'x' as a command. When the front end exists, the tests succeeds.
	 * If 'x' does not exist the app, the test will not terminate (it won't fail
	 * explicitely in this case). The test fails explicitely if the front end is
	 * not instantiated or if an exception occurs.
	 * @return true if the test passed, false if it failed
	 */
	public boolean enterXToExit() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			// set the input stream to our input (with an x to test of the program exists)
			String input = "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Object frontend = new Frontend();
			((Frontend)frontend).run(new Backend(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			)));
			// set the output back to standard out for running the test
			System.setOut(standardOut);
			// same for standard in
			System.setIn(standardIn);
			if (frontend == null) {
				// test fails
				return false;
			} else {
				// test passed
				return true;
			}
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			// test failed
			return false;
		}
	}

	/**
	 * This test runs the front end and redirects its output to a string. It then
	 * passes in 'x' as a command to exit the app. The test succeeds if the front
	 * end does not contain any of the three movies (the movie titles are not in
	 * the string captured from the front end) by default. It fails if any of
	 * those three titles are present in the string or an exception occurs.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testFrontendInitialOutputNoMovie() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			// set the input stream to our input (with an x to test of the program exists)
			String input = "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Object frontend = new Frontend();
			((Frontend)frontend).run(new Backend(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			)));
			// set the output back to standard out for running the test
			System.setOut(standardOut);
			// same for standard in
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			if (frontend == null || appOutput.contains("The Source of Shadows")
							|| appOutput.contains("The Insurrection")
							|| appOutput.contains("Valley Girl")) {
				// test failed
				return false;
			} else {
				// test passed
				return true;
			}
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			// test failed
			return false;
		}
	}

	/**
	 * This test runs the front end and redirects its output to a string. It then
	 * passes in 'g' as a command to go to the genre selection mode. It then exists
	 * the app by pressing 'x' to go back to the main mode and another 'x' to exit.
	 * The test succeeds if the genre selectio screen contains all five genres
	 * from the data. It fails if any of them are missing, the front end has not
	 * been instantiated (is null), or there is an exception.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testFrontendGForGenres() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			// set the input stream to our input (with an g to test of the program lists genres)
			String input = "g" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Object frontend = new Frontend();
			((Frontend)frontend).run(new Backend(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			)));
			// set the output back to standard out for running the test
			System.setOut(standardOut);
			// same for standard in
			System.setIn(standardIn);
			// add all tests to this method
			String appOutput = outputStreamCaptor.toString();
			if (frontend != null && appOutput.contains("Horror")
							&& appOutput.contains("Action")
							&& appOutput.contains("Comedy")
							&& appOutput.contains("Musical")
							&& appOutput.contains("Romance")) {
				// test passes if all genres from the data are displayed on the screen
				return true;
			} else {
				// test failed
				return false;
			}
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			// test failed
			return false;
		}
	}
	
	/**
	 * This test runs the front end and redirects its output to a string. It then
	 * passes in non-valid commands. Then exits the app by pressing 'x' to go 
	 * back to the main mode and another 'x' to exit.
	 * The test succeeds if the rating selection screen contains all from the data. 
 	 * It fails if any of them are not present, the front end has not
	 * been instantiated (is null), or there is an exception.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testInvalidInputBase() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input 
				String input = "7" + System.lineSeparator() // movies should be empty initially so any number should print an error message
					+ "n" + System.lineSeparator() // should print an error because 'n' isn't a valid command
					+ "y" + System.lineSeparator() // enter random key to acknowledge error message
					+ "x" + System.lineSeparator() 
					+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				if (frontend != null && appOutput.contains("<<<< Incorrect Input >>>>")
												&& appOutput.contains("<<<< There are no movies selected >>>>")) {
						// test passes
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}

	/**
	 * This test runs the front end and redirects its output to a string. It then
	 * passes in non-valid commands. Then exits the app by pressing 'x' to go 
	 * back to the main mode and another 'x' to exit.
	 * The test succeeds if the rating selection screen contains all from the data. 
 	 * It fails if any of them are not present, the front end has not
	 * been instantiated (is null), or there is an exception.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testInvalidInputBase2() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input 
				String input = "3x" + System.lineSeparator() // movies should be empty initially so any number should print an error message
					+ "-4" + System.lineSeparator() // should print an error because 'n' isn't a valid command
					+ "y" + System.lineSeparator() // enter random key to acknowledge error message
					+ "x" + System.lineSeparator() 
					+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				if (frontend != null && appOutput.contains("<<<< Incorrect Input >>>>")) {
						// test passes
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}

	/**
	 * This test runs the front end and redirects its output to a string. 
	 * It then goes to genre selection mode and inputs incorrect commands
	 * The test succeeds if the rating selection screen does not contain the one selection from the data.
	 * It fails if none or the wrong selections are present, the front end has not
	 * been instantiated (is null), or there is an excpetion.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testInvalidInputGenre() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input 
				String input =  "g" + System.lineSeparator()
				+ "1a"+ System.lineSeparator() // should print out error message
				+ "y" + System.lineSeparator() // enter random key to acknowledge error message
				+ "x" + System.lineSeparator()
				+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				if (frontend != null && appOutput.contains("<<<< Incorrect Input >>>>")) {
						// test passes 
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}	

	/**
	 * This test runs the front end and redirects its output to a string. 	 
	 * It then goes to genre selection mode and inputs incorrect commands
	 * The test succeeds if the rating selection screen does not contain the one selection from the data.
	 * It fails if none or the wrong selections are present, the front end has not
	 * been instantiated (is null), or there is an excpetion.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testInvalidInputRating() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input 
				String input =  "r" + System.lineSeparator()
				+ "1a"+ System.lineSeparator() // should result in error message
				+ "y" + System.lineSeparator() // enter random key to acknowledge error message
				+ "x" + System.lineSeparator()
				+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				if (frontend != null && !appOutput.contains("3.5")
										&& !appOutput.contains("2.9")
										&& !appOutput.contains("5.4")) {
						// test passes 
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}	

	/**
	 * This test runs the front end and redirects its output to a string. 	 
	 * It then goes to deselects all ratings and selects all genres to check
	 * correctness of rating dselection.
	 * The test succeeds if the base mode does not contain any movies.
	 * It fails if none or the wrong selections are present, the front end has not
	 * been instantiated (is null), or there is an excpetion.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testDeslectAllRatings() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input
				String input =  "r" + System.lineSeparator()
				+ "0"+ System.lineSeparator()
				+ "1"+ System.lineSeparator()
				+ "2"+ System.lineSeparator()
				+ "3"+ System.lineSeparator()
				+ "4"+ System.lineSeparator()
				+ "5"+ System.lineSeparator()
				+ "6"+ System.lineSeparator()
				+ "7"+ System.lineSeparator()
				+ "8"+ System.lineSeparator()
				+ "9"+ System.lineSeparator()
				+ "10"+ System.lineSeparator()
				+ "g"+ System.lineSeparator()
				+ "0"+ System.lineSeparator()
				+ "1"+ System.lineSeparator()
				+ "2"+ System.lineSeparator()
				+ "3"+ System.lineSeparator()
				+ "4"+ System.lineSeparator()
				+ "x" + System.lineSeparator()
				+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				if (frontend != null && !appOutput.contains("3.5")
										&& !appOutput.contains("2.9")
										&& !appOutput.contains("5.4")
										&& appOutput.contains("Filter Results:  0 movies")) {
						// test passes 
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}

	/**
	 * This test runs the front end and redirects its output to a string. 	 
	 * It then goes to deselects some ratings and selects all genres to check
	 * correctness of rating selection.
	 * The test succeeds if the base mode does not contain any movies.
	 * It fails if none or the wrong selections are present, the front end has not
	 * been instantiated (is null), or there is an excpetion.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testSelectFewRatings() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
				// set the input stream to our input 
				String input =  "r" + System.lineSeparator()
				+ "0"+ System.lineSeparator()
				+ "1"+ System.lineSeparator()
				+ "2"+ System.lineSeparator()
				+ "3"+ System.lineSeparator()
				+ "4"+ System.lineSeparator()
				// leave 5 selected
				+ "6"+ System.lineSeparator()
				+ "7"+ System.lineSeparator()
				+ "8"+ System.lineSeparator()
				+ "9"+ System.lineSeparator()
				+ "10"+ System.lineSeparator()
				+ "x" + System.lineSeparator()
				+ "g"+ System.lineSeparator()
				+ "3"+ System.lineSeparator()
				+ "x" + System.lineSeparator()
				+ "x";
				InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
				System.setIn(inputStreamSimulator);
				ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
				// set the output to the stream captor to read the output of the front end
				System.setOut(new PrintStream(outputStreamCaptor));
				// instantiate when front end is implemented
				Object frontend = new Frontend();
				((Frontend)frontend).run(new Backend(new StringReader(
								"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
								+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
								+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael ParÃ©, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
								+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
				)));
				// set the output back to standard out for running the test
				System.setOut(standardOut);
				// same for standard in
				System.setIn(standardIn);
				// add all tests to this method
				String appOutput = outputStreamCaptor.toString();
				//System.out.println(appOutput);
				if (frontend != null && !appOutput.contains("3.5")
										&& !appOutput.contains("2.9")
										&& appOutput.contains("5.4")) {
						// test passes 
						return true;
				} else {
						// test failed
						return false;
				}
		} catch (Exception e) {
				// make sure stdin and stdout are set correctly after we get exception in test
				System.setOut(standardOut);
				System.setIn(standardIn);
				e.printStackTrace();
				// test failed
				return false;
		}

	}
		
}
