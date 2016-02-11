import java.util.HashMap;
import java.util.ArrayList;

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
    while (pennies >= 10) {
      changeArray[1] += 1;
      pennies -= 10;
    }
    while (pennies >= 5) {
      changeArray[2] += 1;
      pennies -= 5;
    }
    while (pennies > 0) {
      changeArray[3] += 1;
      pennies -= 1;
    }
    return changeArray;
  }

  public String prettyOutput(Integer[] changeArray) { // changeArray = {2, 2, 1, 1}
    String[] coinArray = {"quarters", "dimes", "nickels", "pennies", "quarter", "dime", "nickel", "penny"};
    ArrayList<String> coinStrings = new ArrayList<String>();
    String sentence = "will be %s, %s, %s, and %s.";
    for (int i = 0; i < changeArray.length; i++) {
      String numberWord = Change.getWord(changeArray[i]);
      if (changeArray[i] == 1) {
        coinStrings.add(numberWord + " " + coinArray[i + 4]);
      } else {
        coinStrings.add(numberWord + " " + coinArray[i]);
      }
    }
    return String.format(sentence, coinStrings.get(0), coinStrings.get(1), coinStrings.get(2), coinStrings.get(3));
  }

  public static String getWord(Integer number) {
    String finalWord = "";
    HashMap<Integer, String> findSmallWords = new HashMap<Integer, String>();
    HashMap<Integer, String> findTensWords = new HashMap<Integer, String>();
    HashMap<Integer, String> findHundredsWords = new HashMap<Integer, String>();
    String[] smallNumberWords = {"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
    String[] tensWords = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    findHundredsWords.put(0, "");
    for(Integer i = 1; i <= 19; i++) {
      findSmallWords.put(i, smallNumberWords[i-1]);
    }
    for(Integer i = 2; i <= 9; i++) {
      findTensWords.put(i, tensWords[i-2]);
    }
    for(Integer i = 1; i <= 9; i++) {
      findHundredsWords.put(i, (smallNumberWords[i-1] + " hundred"));
    } //END HASHMAP SETUP

    if (number == 0) {
      return "zero";
    }
    String stringNumber = Integer.toString(number);
    Integer numberScale = stringNumber.length();
    Integer numberGroups = numberScale / 3;
    Integer numberGroupsRemainder = numberScale % 3;
    if(numberGroupsRemainder > 0) {
      numberGroups += 1;
    }
    ArrayList<String> stringNumberArray = new ArrayList<String>();
    for(Integer i = 1; i <= numberGroups; i++) {
      if(stringNumber.length() - 3 < 0) {
        stringNumberArray.add(stringNumber);
      } else {
        stringNumberArray.add(stringNumber.substring(stringNumber.length()-3,stringNumber.length()));
        stringNumber = stringNumber.substring(0,stringNumber.length()-3);
      }
    }

    for(Integer i = numberGroups; i > 0; i--) {
      String tempStringNumber = stringNumberArray.get(i-1);
      Integer tempNumber = Integer.parseInt(tempStringNumber);
      Integer hundreds = tempNumber / 100;
      tempNumber = tempNumber % 100;
      Integer tens = tempNumber / 10;
      Integer remainder = tempNumber % 10;

      finalWord += findHundredsWords.get(hundreds);
      if (tempNumber > 0 && hundreds > 0) {
        finalWord += " and ";
      }
      if(tens >= 2) {
        finalWord = finalWord + findTensWords.get(tens);
        if(remainder > 0) {
          finalWord = finalWord + " " + findSmallWords.get(remainder);
        }
      } else if (tempNumber > 0){
        finalWord += findSmallWords.get(tempNumber);
      }

      if(i == 5) {
        finalWord += " trillion ";
      }
      if(i == 4) {
        finalWord += " billion ";
      }
      if(i == 3) {
        finalWord += " million ";
      }
      if(i == 2) {
        finalWord += " thousand ";
      }

    }
    return finalWord;
  }
}
