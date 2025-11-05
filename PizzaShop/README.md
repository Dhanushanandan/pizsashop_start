PizzaShop - Modern Kotlin Firebase project (minimal)
-------------------------------------
How to run:
1. Open this folder in Android Studio.
2. Let Android Studio download Gradle and sync (may ask to update Gradle wrapper).
3. If prompted, update Gradle wrapper to 8.9.
4. Build and run on an emulator or device.
Notes:
- The included google-services.json is already embedded.
- The Realtime Database should contain a `pizzas` node with pizza objects:
  Example:
  {
    "pizzas": {
      "p1": { "id":"p1","name":"Margherita","price":7.5 },
      "p2": { "id":"p2","name":"Pepperoni","price":8.5 }
    }
  }
