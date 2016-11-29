# Testing with Spring Boot
### Purpose
This article will walk through a quick overview of what Spring Boot is, the latest testing facilities that Spring Boot provides and a contrived example that uses the constituent parts of these facilities in order to compelte an end to end test client to mocked back end services.
### What is Spring Boot
1. **Opinionated**: Spring Boot is opinionated in that if you just want the defaults it’s great. Spring Boot will autoconfigure everything for you and you’re all set. If you want to pick and choose things it’s still possible but you may have to pierce the veil quite a bit to facilitate that. This is very similar to the XML style namespace config that Spring supports, the same thing happens there, if you can do defaults your great, otherwise you’ll need to understand the XML configuration a lot more, and usually the underlying implementation much more.
2. **Auto Configuration via Classpath Scanning**: Spring Boot will start bootstrapping configuration for things if it recognizes it on the classpath. What this means is you should only bring the jars that actually matter to your application. This will break down, for instance, if someone declares Mockito as a runtime dependency and it has Jetty so Spring Boot will bootstrap Spring MVC configuration for Jetty just because it’s there. This can be turned off, but of course you are losing the win of Spring Boot
3. **Documentation and Appendices**: Because a lot of things are auto configured and exposed to your application you will need to know what they are, and often common properties that can be used to do some minimal configuration.  The appendices have all that documented and it’s really a must for any production grade configuration IMO.
4. **Conventions**: Failing using the documentation you can also “Go fish” quite a bit inside the IDE, the naming conventions are pretty guessable and the names are usually very good.  @Enable* is the top level bootstrapping that you can use by hand as opposed to classpath scanning, @AutoConfigure* are the “atoms” of @Enable*, gives you even more granular control if you’d like it.  *Customizer beans, a callback for a given common thing, think Jackson ObjectMapper, Spring RestTemplate that are created as part of standard bootstrapping of Spring Boot that you don’t need to create on your own.
### New Spring Boot Annotations
[@DataJpaTest](#datajpatest)
[@RestClientTest](#restclienttest)
[@JsonTest](#jsontest)
#### @DataJpaTest
#### @RestClientTest
#### @JsonTest