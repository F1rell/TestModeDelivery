import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;


class AuthTes {
    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void validUser() {
        RegistrationDto user = DataGenerator.user("active");
        $x("//span[@data-test-id='login']//input[@type='text']").val(user.getLogin());
        $x("//span[@data-test-id='password']//input[@type='password']").val(user.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div//h2").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void blockedUser() {
        RegistrationDto user = DataGenerator.user("blocked");
        $x("//span[@data-test-id='login']//input[@type='text']").val(user.getLogin());
        $x("//span[@data-test-id='password']//input[@type='password']").val(user.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    void notCorrectLogin() {
        RegistrationDto user = DataGenerator.newUserNoValidLogin();
        $x("//span[@data-test-id='login']//input[@type='text']").val(user.getLogin());
        $x("//span[@data-test-id='password']//input[@type='password']").val(user.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void notCorrectPassword() {
        RegistrationDto user = DataGenerator.newUserNoValidPassword();
        $x("//span[@data-test-id='login']//input[@type='text']").val(user.getLogin());
        $x("//span[@data-test-id='password']//input[@type='password']").val(user.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }
}