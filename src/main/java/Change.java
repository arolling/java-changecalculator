import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class Change {
  public static void main(String[] args) {}

  public Integer[] changeCalculator(Integer pennies) {
    Integer[] changeArray = {0, 0, 0, 0};
    while (pennies >= 25) {
      changeArray[0] += 1;
      pennies -= 25;
    }
    return changeArray;
  }
}
