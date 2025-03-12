package tests;

import base.BaseTest;
import org.testng.annotations.BeforeClass;
import pages.DeleteFavoriteListPage;

public class DeleteFavoriteListTest extends BaseTest {
    private String loginURL = baseURL + "/user/signin";
    private DeleteFavoriteListPage DeleteFLPage;
    @BeforeClass
    public void setupTest() {
        driver.get(loginURL);
        driver.manage().window().maximize();
    }
}
