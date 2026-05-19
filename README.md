# Halli Santhe Digital - Rural Artisan Marketplace

Halli Santhe Digital is a professional Android application designed to bridge the gap between rural artisans and urban consumers. It provides a platform for village craftsmen to showcase and sell their traditional handmade products.

## 🚀 Features
*   **Explore Marketplace**: Browse a variety of authentic products (Pottery, Textiles, Woodwork, etc.).
*   **Real-time Synchronization**: Powered by Firebase Firestore for instant updates.
*   **Wishlist System**: Save your favorite products locally using **Room Database**.
*   **Artisan Profiles**: Learn about the craftsmen and their village heritage.
*   **Search & Discovery**: Real-time filtering and search for specific village crafts.
*   **Product Listing**: Specialized flow for listing new products with image uploads to Firebase Storage.

## 🛠 Tech Stack
*   **Language**: Kotlin
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Database**: Room (Local), Firebase Firestore (Cloud)
*   **Storage**: Firebase Cloud Storage
*   **UI Components**: Material Design 3, ViewBinding, ConstraintLayout
*   **Image Loading**: Glide
*   **Concurrency**: Kotlin Coroutines & StateFlow

## 📂 Project Structure
```text
app/src/main/java/com/hallisanthe/hallisanthe/
├── data/
│   ├── local/            # Room Database, DAOs, Entities
│   ├── Product.kt        # Data Model
│   ├── ProductRepository.kt # Single source of truth for data
│   └── MockDataProvider.kt # Initial data for exploration
├── ui/
│   ├── ProductViewModel.kt # Business logic & State management
│   └── theme/            # Material 3 Theme definitions
├── MainActivity.kt       # Main entry point with Bottom Navigation
├── HomeFragment.kt       # Marketplace browsing
├── WishlistFragment.kt   # Local saved items
├── CategoriesFragment.kt # Categorized browsing
├── ProfileFragment.kt    # User account & settings
├── UploadActivity.kt     # Product listing flow
└── ProductDetailActivity.kt # Detailed craft information
```

## ⚙️ Installation & Setup
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/jaggureddy11/Internship-Project.git
    ```
2.  **Firebase Setup**:
    *   Create a Firebase project at [Firebase Console](https://console.firebase.google.com/).
    *   Enable **Firestore Database** and **Firebase Storage**.
    *   Download `google-services.json` and place it in the `app/` folder.
3.  **Build**: Open the project in **Android Studio** and click "Sync Project with Gradle Files".
4.  **Run**: Select an emulator or physical device and press `Shift + F10`.

## 📈 Impact Goals
*   **Economic Empowerment**: Increasing profit margins for artisans by removing middlemen.
*   **Cultural Preservation**: Making traditional crafts financially sustainable.
*   **Digital Inclusion**: Bringing rural talent into the global digital economy.

## 📝 Success Criteria (Evaluated)
*   **Working Implementation**: Fully functional marketplace with local wishlist persistence.
*   **Code Quality**: Modern Android standards (MVVM, Coroutines, ViewBinding).
*   **Documentation**: Comprehensive README with clear setup instructions.
*   **Security**: Secret keys (google-services.json) managed via `.gitignore`.

---
**Developed for the MindMatrix VTU Internship Program.**
**Developer:** [Jaggu Reddy](https://github.com/jaggureddy11)
