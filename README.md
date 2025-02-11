# AndroidIntegrator
This project shows how to use REST, SOAP, WebSockets and GraphQL in an app written using Clean Architecture with a "Package by Component" modularization strategy.

## Key Concepts

### Dependencies
- **Jetpack Compose** - UI
- **Ktor** - REST API, SOAP and WebSockets
- **Apollo GraphQL** - GraphQL
- **Koin** - dependency injection
- **LeakCanary** - memory leaks detection 
- **Chucker** - inspection of HTTP(S) requests/responses (requires `android.permission.POST_NOTIFICATIONS` permission)
- **JUnit** and **Mockito** - unit testing
- **Firebase** - remote config, A/B testing
- **Kover** - code coverage

### Screens
- **Rick and Morty** - data about all the characters from the adult cartoon series **Rick and Morty** using GraphQL (REST API also available)
- **The Movie Database (TMDB)** - various info about all the movies that are currently now in cinemas using REST API from TMDB
- **Currency info** - The daily exchange rate to RON (Romanian leu) for common currencies using SOAP from **infovalutar.ro** 
- **Binance** - realtime cryptocurrency values using Binance Websockets

## Project setup instructions

1. **Firebase** - go to Firebase and create a new project and use the app's namespace to configure `google-services.json`, which needs to be downloaded and added to the root of `app` folder
2. Create a new **Remote config parameter** called `binance_stablecoin` and set it with the default **USDT** or **USDC**
3. **TMDB** - go to TMDB and create an account and aquire an API KEY
4. **secrets** - in the `secrets` directory (use Project view) create a new file `secrets.properties`; add in it the following line `TMDB_API_KEY = "..."` where **...** is your key from TMDB
