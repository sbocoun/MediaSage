# Project Specification for Group # 74 - Project MediaSage


## Team Name: Team Watch-a-holics


## Project Domain:

*[Establish what the domain is and what the broad purpose of your proposed software will be — this can be quite brief]*

Primarily movie (and eventually general media) recommendations


## Software Specification:

*[In plain English, **what** should the program be able to do (not **how** it should do it)]*

*[think in terms of nouns and verbs, which will map onto variables and methods in the program]*

Users can store movies they’ve watched in the program, rate their previously-watched movies, view their list of previously-watched movies, and ask the program for a movie recommendation. Each recommendation generates multiple options, and each option has the title of the movie along with additional information such as a description, the runtime, and an external rating. The program should be extendable to accommodate different media types such as TV shows, music, and books.


## User Stories: 

*[statements of interactions between the user and the system]*

*[aim for at least one user story per group member + 1 extra; in the table below, each group member must be assigned to one user story + mark one user story as being a team user story — this one should be the one that is most central to the basic functionality of your system. That is, the one you would probably want to implement first.]*

Team User Story:



* Beatrice wants to get a recommendation similar to a movie she watched. She inputs the movie name to the program, picks the correct one from a list of movies, and then asks the program to give her a similar movie. She then saves both the new and old movies to her “watched” list, and later gives both of them a rating.

Simon’s User Story:



* Trinity wants to find a new movie to watch. She knows she wants it to be Science Fiction, and thinks it would be cool if it included both mechs and kaiju. She opens the movie recommendation program and inputs the Science Fiction genre. She then inputs the kaiju and mech keywords, hits search, and chooses from a list of movies that include those keywords in their description. She decides to order the list by ratings before choosing Pacific Rim and saving it to her watchlist.

___ User Story:



* Neo remembers watching a historical drama that he loved, but can’t remember what it was called. He remembers that it featured Liam Neeson and Ralf Fiennes and that it was directed by Steven Spielberg. He opens the movie recommendation program and adds these criteria to a movie search. The number one result is Schindler’s List, which Neo realizes was what he watched. He marks the film as watched and gives it a rating out of five.

___ User Story:



* Casey is looking for something to watch, so she logs onto the movie recommendation program. She realizes she wants to watch a Nicholas Cage movie, so she adds him as a cast member. First, she orders the search results by her ratings (of previously watched movies featuring Nicholas Cage), then by external ratings. She decides she wants to see one of Cage’s worst-rated movies, so she reverses the sorting order and chooses The Wicker Man, adding it to her “watched” list.

Sera’s User Story:



* Sakura has an extensive list of 


## Proposed Entities for the Domain:

*[based on your specification, indicate a few potential entities for your domain — including their names and instance variables]*



* Rating (abstract class)
    * int rating
* UserRating
    * int rating
* ExternalRating
    * int rating
* Media (abstract class)
    * String name
    * List&lt;Genre> genres
    * Bool viewed
    * UserRating userRating
    * ExternalRating othersRating
* Movie (extends Media)
    * String name
    * String description
    * List[String] cast
    * int minuteRuntime
* Show (extends Media)
    * String name
    * String description
    * List[String] cast
    * int episodeCount
* User
    * String username
    * String password
    * List&lt;MediaCollection> viewedMedia
* MediaCollection (abstract class)
    * List&lt;Media>
* MovieCollection (extends MediaCollection)
    * List&lt;Movie>


## Proposed APIs for the project:

*[links to one or more APIs your team plans to make use of; include brief notes about what services the API provides and whether you have successfully tried calling the API]*



* the TMDB API; [https://developer.themoviedb.org/docs/faq](https://developer.themoviedb.org/docs/faq) 
    * Provides a rating, a description, as well as genres (as IDs) and probably cast info too (for both movies and TV shows).
* The Grade API; [CSC207-Grade-APIs-Demo | Postman API Network](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/overview)
    * For verifying/differentiating users and storing user-specific information (viewed media, ratings).
* the Google Books API; [Google Books APIs  |  Google for Developers](https://developers.google.com/books)
    * Provides a description, author, and genre, as well as page count and rating (0 to 5) if available.
* Spotify Web API; [Web API | Spotify for Developers](https://developer.spotify.com/documentation/web-api)
    * Provides song name, length, contributors, and popularity (on a scale of 100).


## Scheduled Meeting Times + Mode of Communication:

*[when will your team meet each week — you MUST meet during the weekly tutorial timeslot and we strongly recommend scheduling one more regular meeting time]*

**Meeting time outside of lab:** Each Friday at 

**Mode of Communication:** Via a project Discord server and video/voice call
