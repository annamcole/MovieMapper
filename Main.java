// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * This class instantiates and runs the backend and frontend of the MovieMapper application
 */
public class Main {
    
    public static void main(String[] args) {
      // check for correct number of commandline arguments
      if(args.length == 0) {
          System.out.println("<<< Please include a data filepath >>>");
          return;
      } else if (args.length != 1) {
          System.out.println("<<< Incorrect number of arguments >>>");
      }

      String csvFilepath = args[0];
      Backend backend;
      try {

        // instantiate and run the backend and front end
        backend = new Backend(csvFilepath);
        Frontend frontend = new Frontend();
        frontend.run(backend);  // run the application
      } catch (IOException | DataFormatException e) {
        e.printStackTrace();
      } // pass csv filepath to backend in constructor
	}
}
