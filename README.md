## Fleetio Vehicle Explorer
This is a small project that uses the [Fleetio Developer API](https://developer.fleetio.com/) to display a list of
fleet vehicles based on selected filters and sorting options. The user can browse through the vehicles and view detailed information
about each vehicle.

### Screens

- **Vehicles**: Displays a list of vehicles based on selected filters and sorting.
- **Vehicle Detail**: Displays detailed information about the selected vehicle.

### Technologies

This app uses a fairly modern Android stack consisting of:
- [Kotlin](https://kotlinlang.org/) (Language)
- [Compose/Material3](https://m3.material.io/develop/android/jetpack-compose) (UI)
- [Hilt](https://dagger.dev/hilt/) (Dependency Injection)
- [Coil](https://coil-kt.github.io/coil/) (Image loading)
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) (Pagination)
- [Retrofit](https://square.github.io/retrofit/) (Networking)

### Setup

To run this project, you need to create a `local.properties` file in the root directory of the project and add the following line:

```properties
AUTH_TOKEN="Bearer your_auth_token_here"
ACCOUNT_TOKEN="your_account_token_here"
```

You can generate this yourself by [creating an account](https://secure.fleetio.com/register) and navigating to [your api keys](https://secure.fleetio.com/api_keys).
You can also email me and I can provide my own keys for testing.

After that, you can build either debug or release versions of the app.

### Testing

You can either run the tests from Android Studio or run the following command in the terminal from the root directory of the project:

- Unit Tests
```shell
./gradlew :app:testDebugUnitTest
```