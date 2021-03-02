// --== CS400 File Header Information ==--
// Name: Akshat Bansal
// Email: akshat.bansal@wisc.edu
// Team: Red
// Role: Data Wrangler
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.util.ArrayList;
import java.util.List;

public class Movie implements MovieInterface {

  // Variables that hold all the information in a movie
  private ArrayList<String> movieInfo = new ArrayList<>();
  private String title;
  private Integer year;
  private List<String> genres = new ArrayList<String>();
  private String director;
  private String description;
  private Float avgVote;

  public Movie(String line) {
    // Breaking up the information by topic
    String currTopic = "";
    boolean isParsingQuotes = false;

    for (int i = 0; i < line.length(); i++) {
      char currChar = line.charAt(i);
      char nextChar = 0;

      if (i != line.length() - 1) {
        nextChar = line.charAt(i + 1);
      }

      if (currChar == '\"') {
        if (nextChar == currChar) {
          i++; // Skip next quote
          currTopic += currChar; // Add quote to the String
        } else if (isParsingQuotes) {
          movieInfo.add(currTopic);
          currTopic = "";
          isParsingQuotes = false;
        } else {
          isParsingQuotes = true;
        }
      } else if (!isParsingQuotes && currChar == ',') {
        if (currTopic.length() > 0 || line.charAt(i - 1) == ',') {
          movieInfo.add(currTopic);
          currTopic = "";
        }
      } else {
        currTopic += currChar;
      }
    }

    // Adds the last piece of information
    if (!currTopic.isEmpty()) {
      movieInfo.add(currTopic);
    }

    String[] g = movieInfo.get(3).replaceAll("^(\")+|(\")+$", "").split(",");
    for (String s : g) {
      this.genres.add(s.trim());
    }

  }

  /**
   * Method that initializes the title variable
   * 
   * @return this.title
   */
  @Override
  public String getTitle() {
    this.title = movieInfo.get(0);
    return this.title;
  }

  /**
   * Method that initializes the year variable
   * 
   * @return this.year
   */
  @Override
  public Integer getYear() {
    this.year = Integer.parseInt(movieInfo.get(2));
    return this.year;
  }

  /**
   * Method that initializes the genres variable
   * 
   * @return this.genres
   */
  @Override
  public List<String> getGenres() {
    return this.genres;
  }

  /**
   * Method that initializes the director variable
   * 
   * @return this.director
   */
  @Override
  public String getDirector() {
    this.director = movieInfo.get(7);
    return this.director;
  }

  @Override
  public String getDescription() {
    this.description = movieInfo.get(11);
    return this.description;
  }

  /**
   * Method that initializes avgVote variable
   * 
   * @return this.avgVote
   */
  @Override
  public Float getAvgVote() {
    this.avgVote = Float.parseFloat(movieInfo.get(12));
    return this.avgVote;
  }

  /**
   * Sorts movie by rating in descending order
   * 
   * @return int -1 if this average vote is less than otherMovie average vote, 1 if this average
   *         vote is more than otherMovie average vote, 0 if this average vote is equal to
   *         otherMovie average vote
   */
  @Override
  public int compareTo(MovieInterface otherMovie) {
    if (this.getAvgVote() > otherMovie.getAvgVote()) {
      return 1;
    } else if (this.getAvgVote() < otherMovie.getAvgVote()) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Method to get the number of columns of the list of movies
   * 
   * @return int representing the number of columns in list of movies
   */
  public int getNumColumns() {
    return movieInfo.size();
  }
}
