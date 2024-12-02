# MediaSage

<img src="resources/MediaSage_Icon.jpeg" alt="icon" width="200"/>

## Table of contents
- [Introduction](#mediasage)
- [Project Details](#project-details)
- [Project Features / Usage Guide](#project-features--usage-guide)
- [Installation Instructions](#installation-instructions)
- [How to Contribute](#how-to-contribute)
- [Feedback](#feedback)
- [Contributors (Group #74)](#contributors-group-74)
- [Assigned Use Cases](#assigned-use-cases)
- [Acknowledgments](#acknowledgments)
- [Software License](#software-license)


## Introduction
MediaSage is a project developed as part of an assignment for CSC207; a course on Software Design offered by the University of Toronto.
It is a tool designed to help users find, rate, and save different pieces of media, such as movies and television shows. It also provides recommendations based on the user's preferences.

## Project Details
This project allows users to add, rate, and get recommendations for media they enjoy.
After creating an account, users are able to search for pieces of media, add media to user-specific lists, rate each piece of media, 
and get new media recommendations based on their preferences.

Compared to other media-management software, MediaSage is unique as it allows users keep track of different types of 
media content (e.g. books, movies, tv shows) in one single place.
Also, regardless of the media type, MediaSage allows users to discover more content they might like based on their interests.
Currently, only movies and tv shows are supported as media types.

## Project Features / Usage Guide

### User Authentication

- Users can create an account, log in, log out, and change their password. 
- Relies on the [Grade API](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/documentation/fg3zkjm/5-password-protected-user).
- **Create Account**:
    - Users can create an account by providing a username and password.
    - The password must be repeated twice to ensure it is entered correctly.
- **Log In**:
  - Once signed up, users can log in by providing their username and password.
- **Log Out**:
  - Users can log out of their account.
- **Change Password**:
  - Users can change their password (once logged in) by providing a new password.

https://github.com/user-attachments/assets/85d28a46-6da6-4cce-baf8-10d7d631a7b7

### Searching for Media
- Users can search for media according to keywords, genres, and/or actors.
- Relies on the TMDB API for movie and tv show data.
- **Search by Keyword**:
  - Users can search for movies and tv shows by from their descriptions.
  - The search results will display the title, release date, and ratings of the media.
  - Users can add media to their lists from the search results.
- **Add Genre Filter**:
  - Users can add genres to filter the search results by.
- **Add Actor Filter**:
  - Users can add actors' names to filter the search results by.

https://github.com/user-attachments/assets/f4719a97-558a-4e8f-8661-be9fb0f278ff

### Media Filtering
- Users can filter media by genre, rating, and release year, 
and sort media by any of the displayed list criteria (e.g. name or rating).
- **Filter by Keywords**:
  - Users can filter media by inputting keywords from the media's description.
- **Filter by Genre**:
  - Users can filter media by inputting the desired genres.
- **Filter by Actors**:
  - Users can filter media by inputting the names of actors in the media.
- **Sorting Media**:
  - Users can sort media by any of the displayed criteria (e.g. name, rating, or runtime) by clicking
    on the corresponding column header.

https://github.com/user-attachments/assets/7fc550d3-53f1-44b6-b9ad-3ed727260c14

### Media Recommendations
- Users can get recommendations based on their preferences.
- Relies on the TasteDive API to generate recommendations.
- **Get Recommendations**:
  - Users can get media recommendations based on any input they provide in the
  lower section of the list tab and clicking the "Get Recommendations" button. 
  - Currently, however, only movie recommendations are generated.

https://github.com/user-attachments/assets/1c5aaa09-2689-4b75-b8a0-eadb2d7aa4f2

### Media Ratings
- Users can rate media they have watched.
- **Rate Media**:
  - Users can rate media they have watched by clicking on a media entry's user-rating box and 
    inputting a rating from 1 to 100. 
  - The rating will be saved and displayed in the user's list of media.
  - Users can change their rating by inputting a new rating.

https://github.com/user-attachments/assets/fe6d19f3-0cf3-462d-b54e-fc74ee5439a4

## Installation Instructions

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/sbocoun/MediaSage.git
   cd MediaSage
    ```
2. **Ensure you have Java Development Kit (JDK) 16 or higher installed.**
3. **Set Up the API Keys:**
   - Navigate to src/main/resources/input_apikeys.yaml. 
   - Make a copy of the file and name it apikeys.yaml. 
   - Open apikeys.yaml and input the respective API keys required for the project.
     - For a TMDB API key, visit [TMDB API](https://developer.themoviedb.org/docs/getting-started) and create an account to request an API key.
     - For a TasteDive API key, visit [TasteDive API](https://tastedive.com/account/api_access) and create an account to request an API key.

4. **Build the Project:**
   - Use Maven to install dependencies and build the project.
       ```sh
       mvn clean install
       ```
   - Ensure your pom.xml includes the following dependencies:

       ```xml
       <dependencies>
           <dependency>
               <groupId>org.json</groupId>
               <artifactId>json</artifactId>
               <version>20240303</version>
           </dependency>
           <dependency>
               <groupId>com.squareup.okhttp3</groupId>
               <artifactId>okhttp</artifactId>
               <version>4.12.0</version>
           </dependency>
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.13.1</version>
               <scope>test</scope>
           </dependency>
           <dependency>
               <groupId>org.yaml</groupId>
               <artifactId>snakeyaml</artifactId>
               <version>2.3</version>
           </dependency>
       </dependencies>
       ```  
5. **Run the Application:**
   - After the build is complete, you can run the application from the command line using:
       ```sh
       mvn exec:java -Dexec.mainClass="app.Main"
      ```
    - Alternatively, you can run the application from your IDE by running the Main class (from the src.main.java.app package).

6. **Use the Application:**
   - Create an account, log in, search for media, filter media, rate media, and get recommendations. 
   - Refer to the [Project Features / Usage Guide](#project-features--usage-guide) section for more information.

## How to Contribute
- If you have any code contributions, feel free to fork the project, commit the relevant changes, and submit a pull request. 
- The pull request should contain your justifications for the pull request, what features it adds, and any potential issues not addressed
by the pull request.
- We will review the code for functionality and style, provide specific feedback and merge the pull request if it fits our project's scope and goals.

## Feedback
- If you have any feedback, suggestions, or bugs to report, please use the Issues tab or create a pull request.
- Please be constructive and mindful of our project's scope when providing feedback.
- Ensure your feedback is clear and specific, providing examples where possible.
- Respect the contribution guidelines and code of conduct when submitting feedback.

## Contributors (Group #74)
- Sophie Miki Erenberg ([sophie-erenberg](https://github.com/sophie-erenberg/))
- Simon Bocoun ([sbocoun](https://github.com/sbocoun))
- Xiao Lan (Judy) Chen ([Leo081](https://github.com/Leo081))
- Yizhe (Sera) Zhao ([papertots](https://github.com/papertots))

## Assigned Use Cases:
- Sophie - filter list by criteria
- Simon - search by criteria
- Judy - Move media to another list
- Sera - Change movie user rating in the user list.

## Acknowledgments

This project incorporates concepts and code from the following repositories, both licensed under the [CC0 1.0 Universal (Public Domain Dedication)](https://creativecommons.org/publicdomain/zero/1.0/):

- **[Lab 5 Repository](https://github.com/CSC207-2024F-UofT/lab-5)**
  - Used as a foundation for the project structure and provided the user interface.

- **[Note Application](https://github.com/CSC207-2024F-UofT/NoteApplication?tab=readme-ov-file)**  
  - Referenced for features like user data handling and user authentication.

## Software License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
