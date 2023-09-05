/*
*
*  Dev: Jannik Aumann
*  Company: Heinrich Büssing Schule - 2FOS233
*
*  BMI-Rechner
*  Version: 0.1
*  Quelle: https://www.tk.de/service/app/2002866/bmirechner/bmirechner.app
*
*/

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    boolean doExit = false;
    float bmi;
    // Quelle: https://www.verival.de/blog/wp-content/uploads/2021/04/bmi-tabelle-frau-mann-768x468.jpg - 04.09.2023 14:33
    
    // Verwendet wird eine TreeMap da diese automatisch nach dem Key sortiert ist
    // Eine Hashmap war falsch sortiert
    TreeMap<Double, String> bmiMapMale = new TreeMap<Double, String>() {{
    put(0.0, "Untergewicht");
    put(20.0, "Normalgewicht");
    put(26.0, "Übergewicht");
    put(30.0, "Adipositas Grad 1");
    put(35.0, "Adipositas Grad 2");
    }};
    
    TreeMap<Double, String> bmiMapFemale = new TreeMap<Double, String>() {{
    put(0.0, "Untergewicht");
    put(17.5, "Normalgewicht");
    put(24.5, "Übergewicht");
    put(29.0, "Adipositas Grad 1");
    put(34.0, "Adipositas Grad 2");
    }};
    
    System.out.printf("\n\n=====\n" + "\tBMI-Rechner\n" + "=====\n");
    
    do {
      // User Input
      
      bmi = calcBMI(scan);
      
      if (bmi != 0) {
        System.out.printf("\nBitte geben Sie ihr Geschlecht ein (m/w): ");
        String gender = scan.next();
        
        
        // BMI verhält sich unterschiedlich bei Männern und Frauen
        TreeMap<Double, String> usingBMIMap = null;
        
        switch (gender.toUpperCase()) {
          case "M":
            usingBMIMap = bmiMapMale;
            break;
          case "F":
            usingBMIMap = bmiMapFemale;
            break;
          default:
            System.out.printf("\nUngültige Geschlechtseingabe\nAls Standard werden die männlichen Werte angewendet\n");
            usingBMIMap = bmiMapMale;
            break;
        }
        
        String bmiCategoryName = "";
        try {
          bmiCategoryName = findBMIName(usingBMIMap, bmi);
        } catch (Exception e) {
          //System.out.println(e);
        }
        
        
        System.out.printf("====\n\nIhr BMI beträgt: %f\nDas entspricht der Kategorie: %s", bmi, bmiCategoryName);
        
        
        
        
        
      } else {
        System.out.printf("\nUnbekannter Fehler\nbitte staren Sie das Programm erneut!");
        scan.nextLine(); // Verwerfen der ungültigen Eingabe
      }
      
      System.out.printf("\n\n====" + "\nWollen Sie einen Weiteren BMI berechnen? (J/N): ");
      String tempAns = scan.next();
      
      if (tempAns.equalsIgnoreCase("N")) {
        doExit = true;
      }
      
    } while (!doExit);
  }

  public static float calcBMI(Scanner scan) {
    float bmi = 0;
    float weight;
    float height;
    
    // Altersunhabhängiger BMI Berechnen
    // BMI = Körpergewicht / Körpergröße^2
    
    
    
    try {
      System.out.printf("\nBitte geben Sie ihr gewicht ein (Bsp: 80,5): ");
      weight = scan.nextFloat();
      System.out.printf("\nBitte geben Sie ihre Körpergröße in CM ein (Bsp: 185): ");
      height = scan.nextFloat();
      
      
      bmi = weight / ((height / 100) * (height / 100));
      
      
    } catch (Exception e) {
      //System.out.println(e);
    }
    
    return bmi;
  }

  public static String findBMIName(TreeMap<Double, String> map, double bmi) {
    Iterator<Entry<Double, String>> iterator = map.entrySet().iterator();
    Entry<Double, String> aktuellerBereich = null;
    
    // Per iterator durch die Treemap gehen um den passenden bereich zu finden
    while (iterator.hasNext()) {
      aktuellerBereich = iterator.next();
      
      // Wenn der BMI größer ist als der Mindestwert der Kategorie wird in die nächste gesprungen
      // Wenn der mindestwert zu klein ist der bereich gefunden.
      if (bmi >= aktuellerBereich.getKey()) {
        continue;
      } else {
        break;
      }
    }
    
    // Überprüfen Sie, ob der Bereich gefunden wurde
    if (aktuellerBereich != null) {
      return aktuellerBereich.getValue();
    } else {
      // Wenn kein passender Bereich gefunden wurde
      return "Kein passender Bereich gefunden";
    }
  }
}
