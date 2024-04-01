# JSecureAPI - Simple API for work with a JWT Token

-> Example: https://github.com/Ingur-5967/ExampleSecureAPI

=NEED A SPRING BOOT=
| Request              | Address (URL)                               | Answer		    |
| -------------------- | ------------------------------------------- | -------------------- |
| GET                  | http://localhost:8080/api/token             | Optional[Any value]  |
| POST                 | http://localhost:8080/api/token             | Optional[Any value]  |


<h1>Features</h1>

- Get value from properties file:

```java
        ConfigurationImpl configuration = new ConfigurationImpl(Main.class);

        String parameter = String.valueOf(configuration.getValue("application.properties", "jwt.secret"));

        // ...
```

- Generate a JWT token:

```java
	ConfigurationImpl configuration = new ConfigurationImpl(Main.class);

        String parameter = String.valueOf(configuration.getValue("application.properties", "jwt.secret"));

        SecureManager secureManager = new SecureManager(parameter);

        String key = secureManager.generateSecureKey(new SecureEntity() {
            @Override
            public String getId() {
                return "TestId";
            }

            @Override
            public Map<String, Object> getParameters() {
                return Map.of("name", "Plotter", "email", "Plotter@mail.ru");
            }
        }, 900000);

        // ...
```

- Validate JWT Token
```java
        ConfigurationImpl configuration = new ConfigurationImpl(Main.class);
        String parameter = String.valueOf(configuration.getValue("application.properties", "jwt.secret"));

        SecureManager secureManager = new SecureManager(parameter);

        Optional<Jws<Claims>> claims = secureManager.validator().validateKey("joined_jwt_token");

        // ...
```

<h3>HTTP/HTTPS Requests</h3>

- Get and Post (HTTP)
```java
        ServerConnection serverConnection = new ServerConnection("http://localhost:8080/.../...");
        ServerHandler serverHandler = new ServerHandler(serverConnection);

        Optional<Object> answerGet = serverHandler.http().getOfNullBody();
	Optional<Object> answerPost = serverHandler.http().sendWithBody("any_value");

        // ...
```

- Get and Post (HTTPS)
```java
        ServerConnection serverConnection = new ServerConnection("https://localhost:8080/.../...");
        ServerHandler serverHandler = new ServerHandler(serverConnection);

        Optional<Object> answerSecureGet = serverHandler.https("your_ssl_path").getWithSecure();
	Optional<Object> answerSecurePost = serverHandler.https("your_ssl_path").sendWithSecure("any_value");

        // ...
```


<h1>Maven/Gradle</h1>

### Maven

```xml

<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
	    <groupId>com.github.Ingur-5967</groupId>
	    <artifactId>JSecureAPI</artifactId>
	    <version>1.0.5</version>
	</dependency>
</dependencies>
```

### Gradle

```groovy
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
	implementation 'com.github.Ingur-5967:JSecureAPI:1.0.5'
}
```


