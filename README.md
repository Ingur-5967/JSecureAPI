# SecureAPI
Simple API for work with a JWT Token

- Generate a JWT Token

```java

public class Main implements SecureBoot {

    public void onEnable() {
        SecureManager secureManager = new SecureManager(this);

        String cryptJWT = secureManager.generateSecureKey(new SecureEntity() {
            @Override
            public String getId() {
                return "Anatoliy";
            }

            @Override
            public Map<String, Object> getParameters() {
                return Map.of("name", "Local", "email", "local@mail.ru");
            }
        }, 8600000);

        System.out.println(cryptJWT);
    }

    @Override
    public String getSpecificKey() {
        return "uhasdyuigxcsihjwertyui237912t34678UIGYUFDAS78TY78678t3rghuasdsjgurhjndftdyuig"; // (length * 8) >= 256 or (length * 8) >= 384 or (length * 8) >= 512
    }

```

- Send POST request

```java
        ServerHandler serverHandler = new ServerHandler("http://localhost:8080/api/token?jwt=...");
        System.out.println(serverHandler.send(jwt));
```

- Send GET request

```java
        ServerHandler serverHandler = new ServerHandler("http://localhost:8080/api/token?jwt=...");
        System.out.println(serverHandler.get());
```

- Decrypt a JWT Token

```java
        Claims decryptJWT = EncryptTool.getDecryptKey(getSpecificKey(), cryptJWT);
        
        Object name = decryptJWT.get("name"); // Local
        Object email = decryptJWT.get("email"); // local@mail.ru
```

- Check a JWT Token:

```java
        SecureManager secureManager = new SecureManager(this); // Or SecureManager secureManager = new SecureManager(Main.class);

        String cryptJWT = secureManager.generateSecureKey(new SecureEntity() {
            @Override
            public String getId() {
                return "Anatoliy";
            }

            @Override
            public Map<String, Object> getParameters() {
                return Map.of("name", "Local", "email", "local@mail.ru");
            }
        }, 8600000);

        if(!secureManager.validator().validateKey(cryptJWT))  // Valid!
            return;
        
        // ...
```

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
	    <version>1.0.2</version>
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
	implementation 'com.github.Ingur-5967:JSecureAPI:1.0.2'
}
```


