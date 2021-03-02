// --== CS400 File Header Information ==--
// Name: Akshat Bansal
// Email: akshat.bansal@wisc.edu
// Team: Red
// Role: Data Wrangler
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class MovieDataReader implements MovieDataReaderInterface {
  private BufferedReader reader; 

  public MovieDataReader() {
    // Constructor
    reader = null; 
  }
  
  /**
   * Reads each line of the file of the specified FileReader 
   * and initializes Movie objects
   * 
   * @param Reader inputFileReader
   * @return List<Movie> toReturn a list of Movie objects
   */
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader)
      throws FileNotFoundException, IOException, DataFormatException {
    List<MovieInterface> movieList = new ArrayList<MovieInterface>();  
    String row;
    boolean firstLine = true;
    int numCol = 0;
    try {
      reader = new BufferedReader(inputFileReader);
      while ((row = reader.readLine()) != null) {
        if (firstLine) {
          String[] header = row.split(",");
          numCol = header.length;
          firstLine = false;
        } else {
          try {
            Movie toAdd = new Movie(row.trim());
            if (toAdd.getNumColumns() != numCol) {
              throw new DataFormatException();
            }
            movieList.add(toAdd);
          } catch (DataFormatException dfe) {
            // One of the lines in the csv file is the wrong format
          }
        }
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println(fnfe.toString());
      return null;
      
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return null;
      
    } finally {
      reader.close();
    }
    return movieList;
  }

}
