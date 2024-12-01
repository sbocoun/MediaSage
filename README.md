<img src="resources/MediaSage_Icon.jpeg" alt="icon" width="200" style="float: right; margin-left: 10px;"/>

# MediaSage

## Table of contents
- [Introduction](#mediasage)
- [Project Details](#project-details)
- [Project Features](#project-features)
- [Installation Instructions](#installation-instructions)
- [Contributors](#contributors)
- [Feedback](#feedback)
- [Acknowledgements](#acknowledgments)
- [Contributing to the project](#contributing)
- [Assigned Use Cases](#assigned-use-cases)
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

## Project Features
- **User Authentication**: Users can create an account, log in, log out, and change their password.

<video width="640" height="360" controls>
<source src="resources/MediaSage_User_Functionality_Demo.mp4" type="video/mp4">
Your browser does not support the video tag.
</video>

- **Media Search**: Users can search for movies and tv shows by title.
- **Media Filtering**: Users can filter media by genre, rating, and release year.
- **Media Recommendations**: Users can get recommendations based on their preferences.
- **Media Rating**: Users can rate media they have watched.
[<img src="resources/main-view.png">](https://link-to-your-URL/)

## Installation Instructions
Navigate to `src/main/resources/input_apikeys.yaml`, make a copy of the file as `apikeys.yaml`,
then input the respective API keys.

## Feedback
// TODO

## Contributing
If you have any code contributions, feel free to fork the project in the top right corner of the page, 
commit the relevant changes, and submit a pull request. 
The pull request should contain reasons for the pull request, what features it adds, and any potential issues not addressed
in the pull request.

We will review the code for functionality and style, provide specific feedback and eventually merge it if it's 
evaluated to fit our project's scope and goals.

## Contributors (Group #74)
- Sophie Miki Erenberg ([sophie-erenberg](https://github.com/sophie-erenberg/))
- Simon Bocoun ([sbocoun](https://github.com/sbocoun))
- Xiao Lan (Judy) Chen ([Leo081](https://github.com/Leo081))
- Yizhe (Sera) Zhao ([papertots](https://github.com/papertots))

## Acknowledgments

This project incorporates concepts and code from the following repositories, both licensed under the [CC0 1.0 Universal (Public Domain Dedication)](https://creativecommons.org/publicdomain/zero/1.0/):

- **[Lab 5 Repository](https://github.com/CSC207-2024F-UofT/lab-5)**
  - Adapted for specific functionality, such as demonstrating design patterns or algorithm implementation.

- **[Note Application](https://github.com/CSC207-2024F-UofT/NoteApplication?tab=readme-ov-file)**  
  - Referenced for features like user data handling and application structure.

## Assigned Use Cases:
- Sophie - filter list by criteria
- Simon - search by criteria
- Judy - Move media to another list
- Sera - Change movie user rating in the user list.

## Software License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
