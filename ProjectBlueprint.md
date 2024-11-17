# Project Specification for Group \# 74

## Team Name: Team Watch-a-holics

## Project Domain:

Primarily movie (and hopefully general media) recommendations.

## Software Specification:

Users can store and rate previously watched movies, ask the program for movie recommendations, and add interesting movies to a plan-to-watch list. Each recommendation generates multiple options, each of which has the movie's title and additional information such as a description, the runtime, and an external rating. Users can choose to generate recommendations based on their previously watched movies. Users can also search for movies by keywords and/or specifying criteria such as cast members or ratings. The program should be extendable to accommodate different media types, such as TV shows, music, and books.

## User Stories:

Team User Story:

- Beatrice wants to get a movie recommendation. The list in the program already has 3 movies. She clicks the “generate recommendation” button and sees a list of movie recommendations.
  - Use case: movie recommendation feature

Simon’s User Story:

- Trinity wants to find a new movie to watch. She opens the search tab, chooses the movie category, then chooses from multiple criteria (e.g. the Science Fiction genre, the kaiju and mecha keywords), hits search, and browses a list of relevant movies that she’s able to save to her watchlist.
  - Use case: search by criteria

Sophie’s User Story:

- Neo is feeling down and wants to re-watch a comfort movie, but he can’t remember any good ones. He logs in to his account and goes to his list of watched movies. He filters the genre in his list to “animated” and chooses “Castle in the Sky.”
  - Use case: filter list by criteria

Judy’s User Story:

- Casey just finished watching a movie on her plan-to-watch list, so she navigates to her plan-to-watch list in the program, selects the movie she just watched, and moves it to her list of viewed movies.
  - Use case: Move media to another list.

Sera’s User Story:

- Sakura has an extensive list of previously watched movies and wants to check and update her ratings for those movies. She navigates to the movie list, selecting movies she wants to update one at a time, and updates each of their ratings.
  - Use case: Change movie user rating in the user list.

## Proposed Entities for the Domain:

*\[based on your specification, indicate a few potential entities for your domain — including their names and instance variables\]*

* Rating
  * int Rating
* Media (abstract class)
  * String name
  * List\<String\> genres
  * Rating userRating
  * Rating externalRating
* Movie (extends Media)
  * String description
  * List\[String\] cast
  * int minuteRuntime
* Show (extends Media)
  * String name
  * String description
  * List\[String\] cast
  * int seasonCount
  * int episodeCount
* User
  * String username
  * String password
  * List\<MediaCollection\> mediaCollections
* MediaCollection\<T\>
  * String name
  * Enum collectionType
  * List\<T\>

## Proposed APIs for the project:

* the TMDB API; [https://developer.themoviedb.org/docs/faq](https://developer.themoviedb.org/docs/faq)
  * Provides an implementation for pretty much all the possible use cases related to movies, including a simple recommendation feature. Also, has two Java wrapper implementations.
    * [api-themoviedb](https://github.com/Omertron/api-themoviedb/) by Omertron
    * [themoviedbapi](https://github.com/holgerbrandl/themoviedbapi/) by holgerbrandl
  * Token obtained and database successfully called. *Has a guest access interface.*
* The Grade API; [CSC207-Grade-APIs-Demo | Postman API Network](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/overview)
  * For verifying/differentiating users and storing user-specific data (viewed media, watchlisted media, ratings) in a JSON format.
  * Lab 5 API (successfully called)
* the Google Books API; [Google Books APIs  |  Google for Developers](https://developers.google.com/books)
  * Provides a description, author, and genre, as well as page count and rating (0 to 5\) if available.
  * Obtained an API key and successfully called.
* Spotify Web API; [Web API | Spotify for Developers](https://developer.spotify.com/documentation/web-api)
  * Provides song name, length, contributors, and popularity (on a scale of 100).
  * Obtained an API key and successfully called.
* TasteDive API; [API | TasteDive](https://tastedive.com/read/api)
  * Provides 1-20 media recommendations based on any number of media inputs. *Allows mixed media inputs spanning music, movies, shows, podcasts, books, games etc.*
  * Obtained an API key and successfully called.
* AniList API: [Introduction | AniList API Docs](https://docs.anilist.co/guide/introduction)
  * A database for Japanese anime/manga/light novels.
  * It could be more difficult to implement since it requires GraphQL queries, but has powerful search capabilities, such as search by season/genre/tags/staff.
  * Have not executed any API calls yet.

## Scheduled Meeting Times \+ Mode of Communication:

**Meeting time outside of the lab:** Each Friday at 2 pm.

**Mode of Communication:** Via a project Discord server and video/voice call