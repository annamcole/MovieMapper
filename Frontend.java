// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Role: FrontEnd Developer
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.util.*;

public class Frontend{

    private Backend backend;
    private Scanner scan = new Scanner(System.in);

    private final String APP_WELCOME =    "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                        + "\n          WELCOME TO MOVIE MAPPER!"
                                        + "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
                                        + "\nThis applications allows you to expore and"
                                        + "\nfind new movies to watch!"
                                        + "\nTo scroll through the movies, enter the"
                                        + "\nnumber of the rank of the movie you would"
                                        + "\nlike to display.";
    
    private final String BASE_WELCOME_MESSAGE =   "\n___________________________________________"  
                                                + "\n"
                                                + "\n            MOVIE DISPLAY MODE"
                                                + "\n___________________________________________";
    
    private final String BASE_MENU_DISPLAY =      "\n<<<<<<  MENU  >>>>>>:"
                                                + "\nx - Exit"
                                                + "\ng - Genre Selection"
                                                + "\nr - Rating Selection"
                                                + "\nm - Display Menu"
                                                + "\n";

    private final String GENRE_WELCOME_MESSAGE =      "\n___________________________________________"
                                                    + "\n"
                                                    + "\n          GENRE SELECTION MODE"
                                                    + "\n___________________________________________"
                                                    + "\nEnter the number of the genre you want to"
                                                    + "\nselect/deselect. Selected genres have an"
                                                    + "\n'X' in before them"
                                                    + "\nEnter 'x' to save and exit selection mode"
                                                    + "\nmovie display mode.";

    private static String RATING_WELCOME_MESSAGE =    "\n___________________________________________"
                                                    + "\n"
                                                    + "\n          RATING SELECTION MODE"
                                                    + "\n___________________________________________"
                                                    + "\nEnter the number of the rating you want to"
                                                    + "\nselect/deselect." 
                                                    + "\nEXAMPLE: select '3' to display ratings in"
                                                    + "\nthe range 3.0 - 3.9"
                                                    + "\nEnter 'x' to save and exit selection mode";

    /**
     * This method runs the user interface for Movie Mapper and interacts
     * with the backend interface to provide the correct visuals
     * 
     * @param backend the backend interface
     */
    public void run(Backend backend) {
        this.backend = backend; // define as private global var

        String userInput = ""; // input accepted from user through the commandline
        int movieIndex = 0; // index of movie from selection list to be displayed (plus two additional movies if available)
        int numMovies = 0;  // the total number of movies in the selection set

        // set default rating selections
        for(int i = 0; i < 11; i++) {
            backend.addAvgRating("" + i);
        }

        // display application welcome message
        System.out.println(APP_WELCOME);

        do{
            /**
             * The mode that Movie Mapper will be in when started.
             * Displays a welcome message and then a list of the top
             * 3 (by average selection rating) selected movies.
             * List may be empty or contain less than 3 movies if no
             * 3 movies are in the selection set.
             * 
             * Users will be able to scroll through the list by typing in
             * numbers as commands of the rank (by rating) of the movies
             * to display.
             */
        
            // Display welcome message
            if(userInput.equals("")) {
                System.out.println(BASE_WELCOME_MESSAGE);
                System.out.print(BASE_MENU_DISPLAY);
                movieIndex = 0;
            }

            // clear user input for second+ iterrations of loop 
            // and get updated number of filtered movies
            userInput = "";
            numMovies = this.backend.getNumberOfMovies();

            // display total number of movies in seleciton set
            System.out.println(   "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                + "\nFilter Results:  " + numMovies + " movies\n");

            do{
                // display three movies chosen by user (default index: 0)
                if(numMovies != 0) {
                    System.out.println(getBaseDisplay(movieIndex,numMovies));
                }

                // prompt user for input
                System.out.println("\n[Enter 'x' to exit]");
                System.out.print("> ");
                userInput = scan.nextLine().toLowerCase().trim();

                if(checkIncorrectInput_Base(userInput)) {
                    // user input incorrect input
                    System.out.println("\n\n<<<< Incorrect Input >>>>");
                    System.out.print("[Press any key to continue]");
                    scan.nextLine();
                }

            } while(checkIncorrectInput_Base(userInput));
            
            // perform command
            switch(userInput) {
                case "x":
                    // exit application
                    return;
                case "m":
                    // display actions menu
                    System.out.print(BASE_MENU_DISPLAY);
                    break;
                case "g":
                    // go to Genre Selection Mode
                    genreSelectionMode();
                    userInput = "";
                    break;
                case "r":
                    // go to Rating Selection Mode
                    ratingSelectionMode();
                    userInput = "";
                    break;
            } 
            // convert user input to int and scroll through selection set
            if(userInput.matches("\\d+")) {
                int tempMovieIndex = movieIndex; // save previous movieIndex in case out of bounds index is input by user
                movieIndex = Integer.parseInt(userInput) - 1;
                if(movieIndex < 0 || movieIndex > this.backend.getNumberOfMovies()) {
                    // user input incorrect input
                    if(numMovies != 0) {
                        movieIndex = tempMovieIndex;
                        System.out.println("\n\n<<<< Please enter a number between: 1 " + " and " + numMovies + " >>>>");
                        System.out.print("[Press any key to continue]");
                    scan.nextLine();
                    } else {
                        movieIndex = tempMovieIndex;
                        System.out.println("\n\n<<<< There are no movies selected >>>>");
                        System.out.print("[Press any key to continue]");
                    scan.nextLine();
                    }
                }
            }

        } while (!userInput.equalsIgnoreCase("x"));

        // close scanner
        scan.close();

    }

    /**
     * The genre selection mode that lets users select and deselect
     * genres based on which movies are retrieved from the database.
     * When multiple genres are selected, only movies that have all
     * selected genres are displayed. When no genre is selected,
     * the set of selected movies will be empty (initially).
     * This mode will display a brief introduction for users of how
     * to select or deselect genres and how to interpret the list
     * of selected and unselected genres. This is followed by a list of
     * all genres in which every genre is assigned a number.
     * Users can type in this number as a command to select and deselect
     * each genre. Genres are clearly marked as either selected or
     * unselected in the list. The list of genres is folle by a brief
     * description fo how to return to the base mode and a
     * command prompt.
     */
    private void genreSelectionMode() {
        String userInput; // user input from commandline
        int genreID; // integer corresponding to a genre string
        boolean inSelGenres = false; // true if genre is already selected
        // get all genres
        List<String> allGenres = this.backend.getAllGenres();

        // display genre selection welcome message
        System.out.println(GENRE_WELCOME_MESSAGE);

        do {
            do {
                // display selected genre
                System.out.println(getGenreDisplay(allGenres,backend.getGenres()));
                // prompt user for input
                System.out.println("Enter 'x' to exit Selection Mode");
                System.out.print("> ");
                userInput = scan.nextLine().toLowerCase().trim();

                if(checkIncorrectInput_Genre(allGenres,userInput)) {
                    // user input incorrect input
                    System.out.println("\n<<<< Incorrect Input >>>>");
                    System.out.print("[Press any key to continue]");
                    scan.nextLine();
                }

            } while(checkIncorrectInput_Genre(allGenres,userInput));

            // perform user command
            switch(userInput) {
                case "x":
                    // exit genre selection mode
                    return;
                case "m":
                    // next itteration should display menu again before the next prompt
                    System.out.println(GENRE_WELCOME_MESSAGE);
                    continue;
            }

            // decode userInput to genre
            genreID = Integer.parseInt(userInput);
            userInput = allGenres.get(genreID);
            
            // check if genre is already selected
            inSelGenres = false; // clear variable
            for(String selGenre : this.backend.getGenres()) {            
                if(userInput.equalsIgnoreCase(selGenre)) {
                    inSelGenres = true;
                    break;
                }
            }

            // select/deselect genre
            if(inSelGenres) {
                // deselect
                this.backend.removeGenre(userInput);
            } else {
                // select
                this.backend.addGenre(userInput);
            }

        } while ( !userInput.equals("x") );

    }

    /**
     * The ratings selection mode is structured similarly to the genre
     * selection mode. It allows users to select and deselct ratings.
     * Initially, all ratings are selected.
     * The mode will display a nubmered list of ratings that users can
     * select. The list will display clearly which ratings are selected
     * and which ones are not selected.
     * The move ratings are floats in the range [0-10], where 0 is the
     * lowest possible, and 10 is the highest possible rating.
     * The list will contain all integers (0-10) for users to select.
     * When one of these ratings is selected, all movies that have the
     * rating (ignoring their decimal part) will be retrieved form the
     * database.
     * 
     * @return nothing
     */
    private void ratingSelectionMode() {
        String userInput; // user input from commandline
        boolean inSelRatings = false; // true if rating is already selected

        // display welcome message
        System.out.println(RATING_WELCOME_MESSAGE);

        do {
            do {
                System.out.println(getRatingDisplay(this.backend.getAvgRatings()));
                // prompt user for input
                System.out.println("Enter 'x' to exit Selection Mode");
                System.out.print("> ");
                userInput = scan.nextLine().toLowerCase().trim();

                if(checkIncorrectInput_Rating(userInput)) {
                    // user input incorrect input
                    System.out.println("\n<<<< Incorrect Input >>>>");
                    System.out.print("[Press any key to continue]");
                    scan.nextLine();
                }

            } while(checkIncorrectInput_Rating(userInput));

            // perform user command
            switch(userInput) {
                case "x":
                    // exit rating selection mode
                    return;
                case "m":
                    // next itteration should display menu again before the next prompt
                    System.out.println(RATING_WELCOME_MESSAGE);
                    continue;
            }
            
            inSelRatings = false; // clear rating selected flag

            // check if rating is already selected
            for(String selRating : this.backend.getAvgRatings()) {            
                if(userInput.equalsIgnoreCase(selRating)) {
                    inSelRatings = true;
                    break;
                }
            }

            // select/deselect genre
            if(inSelRatings) {
                // deselect
                backend.removeAvgRating(userInput);
            } else {
                // select
                backend.addAvgRating(userInput);
            }

        } while ( !userInput.equals("x") );

    }

    /**
     * helper method that retreives three movies from the selection set based on
     * user input and creates a string to display in the base mode
     * 
     * @param index - the index of the movie in selection set requested by the user to display
     * @param numMovies - total number of movies in the selection set
     * @return a String representation of three movies requested by the user
     */
    private String getBaseDisplay(int index, int numMovies) {
        List<MovieInterface> movies = this.backend.getThreeMovies(index);

        int rangeStart = index+1;
        int rangeEnd = rangeStart + movies.size() - 1;
        String display = "";

        display +=  "\nCurrently Viewing Movies: " + rangeStart + " - " + rangeEnd + "\n";
                    
        for(MovieInterface movie : movies) {
            display += movieToString(movie) + "\n";
        }

        return display;
    }

    /**
     * Retreives available genres and forms a string for display
     * with selections clearly marked
     * 
     * @param allGenres - all genres available from movie data
     * @param selGenres - selected genres
     * @return a string representation of the available genres to display
     *          in the genre selection mode
     */
    private String getGenreDisplay(List<String> allGenres,List<String> selGenres) {
        String display =  "\nx - Save & Exit Selection Mode"
                        + "\nm - Display Instructions & Menu\n"
                        + "\nKEY\t  GENRE";

        List<String> displayGenres = new ArrayList<String>();
        
        // compare selected genres to all genres and create string list
        // containing indicators for selected items
        boolean select = false; // true if genre is already selected
        for(String genre : allGenres) {
            for(String selGenre : selGenres) {
                if(genre.equals(selGenre)) {
                    select = true;
                    break;
                }
            }
            if(select) {
                displayGenres.add("X " + genre);
                select = false;
            } else {
                displayGenres.add("  " + genre);
            }
        }

        // create display
        int i = 0;
        for(String genre : displayGenres) {
            display += "\n" + i + "\t" + genre;
            i++;
        }
        display += "\n\nSelect one genre at a time:";
        
        return display;
    }

    /**
     * Retreives available ratings and forms a string for display
     * with selections clearly marked
     * 
     * @param selRatings - list of selected ratings
     * @return a string representation of the available and selected ratings
     *          to be displayed in the rating selection mode
     */
    private String getRatingDisplay(List<String> selRatings) {
        String display =  "\nx - Save & Exit Selection Mode"
                        + "\nm - Display Instructions & Menu\n"
                        + "\nRATING";

        List<String> displayRatings = new ArrayList<String>();
        
        // compare selected genres to all genres and create string list
        // containing indicators for selected items
        boolean select = false; // true if rating is already selected and in selection list
        int rating;
        for(int i = 0; i <= 10; i++) {
            for(String selRating : selRatings) {
                rating = Integer.parseInt(selRating);
                if(i == rating) {
                    select = true;
                    break;
                }
            }
            if(select) {
                displayRatings.add("X " + i);
                select = false;
            } else {
                displayRatings.add("  " + i);
            }
        }

        // create display
        for(String r : displayRatings) {
            display += "\n" + r;
        }
        display += "\n\nSelect one rating at a time:";
        
        return display;
    }

    /**
     * checks if the input from the user for selecting a mode
     * is valid or not
     * 
     * @param userInput the action input from the user
     * @return true if input is not valid, false if valid
     */
    private boolean checkIncorrectInput_Base(String userInput) {
        return !userInput.equalsIgnoreCase("x") && !userInput.equalsIgnoreCase("g") 
                                                && !userInput.equalsIgnoreCase("r") 
                                                && !userInput.equalsIgnoreCase("m")
                                                && !userInput.matches("\\d+");
    }

    /**
     * checks if the input from the user for selecting a genre
     * is valid or not 
     * 
     * @param allGenres list of all genres in list of movies
     * @param userInput the genre input from the user
     * @return true if input is NOT valid, false if valid
     */
    private boolean checkIncorrectInput_Genre(List<String> allGenres,String userInput) {
        if(userInput.equalsIgnoreCase("x") || userInput.equalsIgnoreCase("m")) {
            return false;
        }

        // decode userInput to get genre
        int genreID;
        try {
            genreID = Integer.parseInt(userInput);
            if(genreID < allGenres.size() && genreID >= 0) {
                // user has a input a valid genre number
                return false;
            }
            userInput = allGenres.get(genreID);
        } catch (NumberFormatException e) {
            // userInput either contains digits and characters or it doesn't have a valid command
        } catch (IndexOutOfBoundsException obe) {
            // userInput is out of bounds of list
        }

        return true;
    }

    /**
     * checks if the input from the user for selecting rating
     * is valid or not
     * 
     * @param userInput the rating input from the user
     * @return true if input is NOT valid, false if valid
     */
    private boolean checkIncorrectInput_Rating(String userInput) {
        if(userInput.equalsIgnoreCase("x") || userInput.equalsIgnoreCase("m")) {
            return false;
        }

        // decode userInput to get genre
        int rating;
        try {
            rating = Integer.parseInt(userInput);
            if(rating >= 0 && rating <= 10) {
                // user has a input a valid genre number
                return false;
            }
        } catch (NumberFormatException e) {
            // userInput either contains digits and characters or it doesn't have a valid command
        }

        return true;
    }

    /**
     * creates a string representation for one movie
     * 
     * @param movie   the movie to be desplayed
     * @return a string version of the movie
     */
    private String movieToString(MovieInterface movie) {
        String title = movie.getTitle();
        Integer year = movie.getYear();
        List<String> allGenres = movie.getGenres();
        List<String> simpleGenres = new ArrayList<String>();
        String director = movie.getDirector();
        String description = movie.getDescription();
        Float rating = movie.getAvgVote();

        String result =   "\nTITLE: " + title
                        + "\nYEAR: " + year
                        + "\nDIRECTOR: " + director
                        + "\nRATING: " + rating
                        + "\nDESCRIPTION:\n" + description 
                        + "\nGENRES:\n";
        
        boolean inSimpleGenres = false;
        if(allGenres != null) {
            // find and don't include multiple copies of genre string
            for(String aGenre : allGenres) {
                for(String sGenre : simpleGenres) {
                    if (aGenre.equals(sGenre)) {
                        inSimpleGenres = true;
                    }
                }
                if(!inSimpleGenres) {
                    // genre isn't in simple list, so add it
                    simpleGenres.add(aGenre);
                }

                // clear found flag
                inSimpleGenres = false;
            }

            for(String genre : simpleGenres) {
                if(simpleGenres.indexOf(genre) == (simpleGenres.size()-1)) {
                    // add genre without comma if it is the last genre in the list
                    result += genre ;
                } else {
                    result += genre + ", ";
                }
            }
            
        }

        return result;

    }

}
