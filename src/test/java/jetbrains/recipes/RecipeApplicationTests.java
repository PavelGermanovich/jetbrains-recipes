package jetbrains.recipes;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jetbrains.recipes.model.User;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static jetbrains.recipes.Constants.RecipeEndpoints.REGISTER_API;


@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeApplicationTests extends BaseTest{

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

	@Test
	void testRestAssuredEmptyBody() {
		when().post(REGISTER_API)
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testUserRegistrationWithEmptyPassword() {
		User user = new User();
		user.setEmail("test123ewf@mail.ru");
		user.setPassword("");
		given().body(user)
				.when().post(REGISTER_API).then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testUserRegisterSuccess() {
		User user = new User();
		user.setEmail("test123ewf@mail.ru");
		user.setPassword("qwerty123");
		given().body(user).when().post(REGISTER_API)
				.then().statusCode(400).body("message",
						Matchers.equalTo("User already exists"));
	}



}
