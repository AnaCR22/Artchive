# Artchive

Artchive is an Android application designed to encourage users to discover and explore art on a daily basis.

Each day, the application presents a different artwork from **The Metropolitan Museum of Art (The Met)** collection, together with information about its author, description and genre. Users can save artworks and artists as favourites, browse their history and discover museums near their location.

## Features

### Daily artwork

* Displays one artwork per day, consistently for all users.
* Provides information about the artwork and its author.
* Allows the artwork to be shared using Android's native sharing system.
* Supports full-screen image viewing and zoom.

### Art history

* Keeps a history of previously displayed artworks.
* Allows users to revisit artworks discovered on previous days.

### Favourites

* Save and remove artworks and artists from favourites.
* Access favourite content from a dedicated section.
* Persist favourites through the user's account.

### Artist information

* View artist biographies and selected artworks.
* Combine artwork information from The Met with additional biographical information from Wikipedia.

### Museum discovery

* Locate museums near the user's current position.
* Display nearby museums using Google Places and map-based visualisation.

### User management

* Email/password registration and authentication.
* Password change functionality.
* User data and favourites synchronised through Firebase.

## Architecture

The application follows **MVVM (Model-View-ViewModel)** together with the **Repository Pattern**, separating presentation, domain and data responsibilities.

```text
ui/
├── Activities
└── Fragments

presentation/
├── ViewModels
├── UiStates
└── Factories

domain/
└── Domain models

data/
├── remote/
│   ├── Met
│   ├── Wikipedia
│   └── Google Places
├── firebase/
└── repository/
```

The ViewModels expose application state through `StateFlow`, while repositories abstract the different data sources used by the application.

This architecture allows the UI layer to remain independent from the underlying APIs and persistence services.

## External Services

### The Metropolitan Museum of Art API

Provides artwork information, including object details and collection data.

### Wikipedia REST API

Provides additional biographical information about artists.

### Google Places API

Provides nearby museum information based on the user's location.

### Firebase

* **Firebase Authentication** for user registration and login.
* **Cloud Firestore** for persistent user data and favourites.

## Technology Stack

* **Kotlin**
* **Android SDK**
* **MVVM**
* **Repository Pattern**
* **Kotlin Coroutines**
* **StateFlow**
* **Retrofit / OkHttp**
* **Firebase Authentication**
* **Cloud Firestore**
* **Google Places API**
* **Glide**
* **Kotlin Serialization**

## Application Design

The application supports both **Spanish and English**, automatically adapting to the language configured on the device.

The UI handles loading, empty and error states to provide feedback during asynchronous operations and external service requests.

API keys are kept outside version control through local configuration.

## Project Structure

```text
app/src/main/java/com/uo300568/artchive/

├── data/
│   ├── firebase/
│   ├── remote/
│   └── repository/
│
├── domain/
│
├── presentation/
│   ├── auth/
│   ├── artista/
│   ├── cuadro/
│   ├── favoritos/
│   ├── historial/
│   └── museo/
│
└── ui/
    ├── auth/
    ├── artista/
    ├── cuadro/
    ├── favoritos/
    ├── historial/
    └── museo/
```

## Getting Started

### Requirements

* Android Studio
* Android SDK
* A Firebase project
* Google Places API credentials

### Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Configure Firebase for the Android application.
4. Add the required API keys to the local configuration.
5. Build and run the application on an Android device or emulator.

## Screenshots

*Add application screenshots here to showcase the main screens and user flow.*

## Project Context

Academic project developed as part of the **Software for Mobile Devices** course at the University of Oviedo.

**Author:** Ana Calleja Ramón
