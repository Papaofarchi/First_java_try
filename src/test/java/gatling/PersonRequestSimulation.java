package gatling;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class PersonRequestSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:7777")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Performance Test");

    Iterator<Map<String, Object>> feeder = Stream.generate((Supplier<Map<String, Object>>) () -> {
        Faker faker = new Faker(new Locale("ru"));
        Name subName = faker.name();
        String name = subName.firstName();
        String surname = subName.lastName();
        String email = name + surname + "@gmail.com";
        String phone = faker.phoneNumber().phoneNumber();
        return Map.of("name", name, "surname", surname, "email", email, "phone", phone);
    }).iterator();

    ScenarioBuilder scn = CoreDsl.scenario("Load Test Adding Persons")
            .feed(feeder)
            .exec(HttpDsl.http("add-person-request")
                    .post("/api/v1/persons/test-gatling-personForm")
                    .header("Content-Type", "application/json")
                    .body(CoreDsl.StringBody("{ \"name\": \"${name}\", \"surname\": \"${surname}\", \"email\": \"${email}\", \"phone\": \"${phone}\" }"))
                    .check(HttpDsl.status().is(201)));


    public PersonRequestSimulation() {
        this.setUp(scn.injectOpen(CoreDsl.constantUsersPerSec(125).during(Duration.ofSeconds(20))))
                .protocols(httpProtocol);
    }
}
