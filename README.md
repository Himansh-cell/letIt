# Letit

Letit is a full-stack social media web application built with Spring Boot and React. It supports user authentication, user profiles, posts, media uploads, likes, comments, replies, follow requests, public/private accounts, and a personalized feed.

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- MongoDB
- Maven

### Frontend
- React
- Vite
- CSS

## Features

- User registration and login
- JWT-based authentication
- User profile management
- Create posts with captions and media
- Personalized feed
- Like and unlike posts
- Comment and reply on posts
- Like and unlike comments
- Send and accept follow requests
- View pending follow requests
- Public and private account support
- Media storage using MongoDB

## Prerequisites

Install the following:

- Java 17
- Maven
- PostgreSQL
- MongoDB
- Node.js and npm

## Environment Variables

Create a `.env` file or configure `application.yml` with:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/letit
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/letit

JWT_SECRET=your_secret_key
