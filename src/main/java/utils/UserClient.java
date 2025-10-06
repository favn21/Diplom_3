package utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class UserClient {

    static {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    @Step("Регистрируем пользователя: {user.email}")
    public static ValidatableResponse registerUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/auth/register")
                .then()
                .log().ifError();
    }

    @Step("Удаляем пользователя с токеном: {accessToken}")
    public static ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete("/auth/user")
                .then()
                .log().ifError();
    }
}
