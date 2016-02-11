import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

// Unit testing
  @Test
  public void changeCalculator_convertsTwentyfivePenniesTo_OneQuarter() {
    Change testChange = new Change();
    Integer[] change = {1, 0, 0, 0};
    Integer[] tillContents = {100, 100, 100, 100};
    assertEquals(change, testChange.changeCalculator(25, tillContents));
  }

  @Test
  public void changeCalculator_convertsSixtyPenniesTo_TwoQuarterOneDime() {
    Change testChange = new Change();
    Integer[] change = {2, 1, 0, 0};
    Integer[] tillContents = {100, 100, 100, 100};
    assertEquals(change, testChange.changeCalculator(60, tillContents));
  }

  @Test
  public void changeCalculator_convertsSixtyFiveTo_TwoQuarterOneDimeOneNickel() {
    Change testChange = new Change();
    Integer[] change = {2, 1, 1, 0};
    Integer[] tillContents = {100, 100, 100, 100};
    assertEquals(change, testChange.changeCalculator(65, tillContents));
  }

  @Test
  public void changeCalculator_convertsSixtyNineTo_TwoQuarterOneDimeOneNickelFourPennies() {
    Change testChange = new Change();
    Integer[] change = {2, 1, 1, 4};
    Integer[] tillContents = {100, 100, 100, 100};
    assertEquals(change, testChange.changeCalculator(69, tillContents));
  }

  @Test
  public void changeCalculator_handlesRunningOutOfQuarters_ThreeDimesFourPennies() {
    Change testChange = new Change();
    Integer[] change = {0, 3, 0, 4};
    Integer[] tillContents = {0, 100, 100, 100};
    assertEquals(change, testChange.changeCalculator(34, tillContents));
  }

  @Test
  public void changeCalculator_handlesRunningOutOfNickelsAndDimes_OneQuarterSixteenPennies() {
    Change testChange = new Change();
    Integer[] change = {1, 0, 0, 16};
    Integer[] tillContents = {100, 0, 0, 100};
    assertEquals(change, testChange.changeCalculator(41, tillContents));
  }

  @Test
  public void changeCalculator_handlesRunningOutOfPennies_OneQuarterOneDimeTwoNickels() {
    Change testChange = new Change();
    Integer[] change = {1, 1, 2, 0};
    Integer[] tillContents = {100, 100, 100, 0};
    assertEquals(change, testChange.changeCalculator(44, tillContents));
  }

  @Test
  public void changeCalculator_handlesRunningOutOfPenniesNickels_OneQuarterTwoDimes() {
    Change testChange = new Change();
    Integer[] change = {1, 2, 0, 0};
    Integer[] tillContents = {100, 100, 0, 0};
    assertEquals(change, testChange.changeCalculator(44, tillContents));
  }

  @Test
  public void prettyOutput_convertArrayTo_DescriptiveString() {
    Change testChange = new Change();
    Integer[] change = {2, 1, 1, 4};
    String prettyTest = "two quarters, one dime, one nickel, and four pennies";
    assertEquals(prettyTest, testChange.prettyOutput(change));
  }

  //Integration testing
    @Test
    public void rootTest() {
        goTo("http://localhost:4567/change");
        assertThat(pageSource()).contains("Input the number of cents");
    }

    // @Test
    // public void fillFormTest() {
    //     goTo("http://localhost:4567/change");
    //     fill("#changeInput").with("33");
    //     submit("#submit");
    //     assertThat(pageSource()).contains("Your change for");
    // }
}
