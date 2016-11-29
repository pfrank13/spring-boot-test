# Testing with Spring Boot

### Purpose
This article will walk through a quick overview of what Spring Boot is, the latest testing facilities that Spring Boot provides and a contrived example that uses the constituent parts of these facilities in order to compelte an end to end test client to mocked back end services.

### What is Spring Boot
1. **Opinionated**: Spring Boot is opinionated in that if you just want the defaults it’s great. Spring Boot will autoconfigure everything for you and you’re all set. If you want to pick and choose things it’s still possible but you may have to pierce the veil quite a bit to facilitate that. This is very similar to the XML style namespace config that Spring supports, the same thing happens there, if you can do defaults your great, otherwise you’ll need to understand the XML configuration a lot more, and usually the underlying implementation much more.
2. **Auto Configuration via Classpath Scanning**: Spring Boot will start bootstrapping configuration for things if it recognizes it on the classpath. What this means is you should only bring the jars that actually matter to your application. This will break down, for instance, if someone declares Mockito as a runtime dependency and it has Jetty so Spring Boot will bootstrap Spring MVC configuration for Jetty just because it’s there. This can be turned off, but of course you are losing the win of Spring Boot
3. **Documentation and Appendices**: Because a lot of things are auto configured and exposed to your application you will need to know what they are, and often common properties that can be used to do some minimal configuration.  The appendices have all that documented and it’s really a must for any production grade configuration IMO.
4. **Conventions**: Failing using the documentation you can also “Go fish” quite a bit inside the IDE, the naming conventions are pretty guessable and the names are usually very good.  @Enable* is the annotation equivalent of XML style namespace configuration that you can use by hand as opposed to classpath scanning, this really isn’t a Spring Boot-ism as it predates that.  @AutoConfigure* are the test specific to bootstrap testing without using full blown autoconfiguration, basically the most important thing to handle the “slice” of beans started for testing.  *AutoConfiguration classes are the @Configuration “magic” that configures basically everything in Spring Boot, e.g. @EnableJpaRepositories == (under spring boot auto configuration or import) JpaRepositoriesAutoConfiguration.  *Customizer beans, a callback for a given common thing, think Jackson ObjectMapper, Spring RestTemplate that are created as part of standard bootstrapping of Spring Boot that you don’t need to create on your own.

### New Spring Boot Annotations
Typically these are just composite annotations around a bunch of @AutoConfigure* and coupled with a TypeExcludeFilter in order to limit beans created for a test.  They all use @ImportAutoConfiguration which is the test version of @EnableAutoConfiguration that would be used in a runtime application as opposed to a test.   @ImportAutoConfiguration applies the @AutoConfigure* classes in the same way as @EnableAutoConfiguration would.

1. [@DataJpaTest](#datajpatest)
2. [@RestClientTest](#restclienttest)
3. [@JsonTest](#jsontest)

#### @DataJpaTest
This annotation is the aggregate of {@Transactional @AutoConfigureCache @AutoConfigureDataJpa @AutoConfigureTestDatabase @AutoConfigureTestEntityManager}

1. **AutoConfigureTestDatabase**: This annotation will automatically create an in memory database and will use a DDL script if you configure the application on where to find that or if you follow the convention you can put them at schema-{platform}.sql
2. **AutoConfigureTestEntityManager**: This annotation will automatically create a TestEntityManager for use in your tests, has convenience methods for your Tests

```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"00000000000000000");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void findByUsernameShouldReturnUser() throws Exception {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("sboot");
		assertThat(user.getUsername()).isEqualTo("sboot");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

	@Test
	public void findByUsernameWhenNoUserShouldReturnNull() throws Exception {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("mmouse");
		assertThat(user).isNull();
	}

}
```

#### @RestClientTest
#### @JsonTest