// Nathin W.
// Section A
// Final project
// May 29th, 2020
// COVIDSim
// Runs a simulation for a virus spreading throughout a community, and gives stats about the results

import java.awt.*;
import java.util.*;
public class COVIDSim
{
   /* The rate the DrawingPanel frames change at */
   public static final int FRAMERATE = 10;
        
   /* A variable to determine how much the population observes social distancing */
   public static int DISTANCINGFACTOR = 1;
        
   /* How many individuals are created for the simulation */
   public static int POPULATIONSIZE = 500;
   
   /* A variable for the relative time in the simulation*/
   public static int TIME = 0;
   
   /**
   * Runs a simulation of how a virus spreads throughout a community of people 
   */
   public static void main(String [] args)
   {
      Scanner input = new Scanner(System.in);
            
      DISTANCINGFACTOR = getNumber(input, 
        "Enter a number representing the level of social distancing: 1, 2 or 3: ", 1, 3);
      POPULATIONSIZE = getNumber(input, "Enter a population size (between 1 and 1000): ", 1, 1000);
      TIME = getNumber(input, "How many seconds do you want to run the simulation for? ", 0, 100);
      
      DrawingPanel panel = new DrawingPanel(1000, 600);
      Graphics g = panel.getGraphics();
      
      PopulationManager manager = new PopulationManager(DISTANCINGFACTOR);
      manager.createPopulation(POPULATIONSIZE);
      ArrayList<IndividualPerson> population = manager.getPopulation();
                
      for (int i = 0; i < TIME * 10; i ++)
      {
                        
         for (int ii = 0; ii < POPULATIONSIZE; ii ++)
         {
            IndividualPerson person = population.get(ii);
            person.move(g, person.getX(), person.getY());
            person.treatment();
            for (int x = 0; x < POPULATIONSIZE; x ++)
            {
               person.transmission(population.get(x));
            }
         }
         manager.drawAll(g);
         panel.sleep(1000 / FRAMERATE);
      }
      
      statistics(population);
                
   }
   
   /**
   * Prints statistics about how the community fared
   * @param population the population of individuals that interacted in the simulation 
   */
   public static void statistics(ArrayList<IndividualPerson> population)
   {
      int numberInfections = 0;
      int numberDead = 0;
      int numberRecovered = 0;
                
      for (int i = 0; i < POPULATIONSIZE; i ++)
      {
         IndividualPerson person = population.get(i);
         if (person.getTimeInfected() > 0)
         {
            numberInfections += 1;
         }
         if (person.getState().equals(IndividualPerson.State.Dead))
         {
            numberDead += 1;
         }
         if (person.getState().equals(IndividualPerson.State.Recovered))
         {
            numberRecovered += 1;
         }
      }
      System.out.println();
      System.out.println("Statistics:");
      System.out.print("Social distancing level " + DISTANCINGFACTOR + ": ");
      if (DISTANCINGFACTOR == 1)
      {
         System.out.println("No distancing measures");
      }
      else if (DISTANCINGFACTOR == 2)
      {
         System.out.println("50% quarantine");
      }
      else if (DISTANCINGFACTOR == 3)
      {
         System.out.println("Essential workers moving only");
      }
      else
      {
         System.out.println("TOTAL ISOLATION");
      }
      System.out.println("Population size: " + POPULATIONSIZE);
      System.out.println("Total number of infections: " + numberInfections);
      System.out.println("Deaths: " + numberDead);
      System.out.println("Recovered: " + numberRecovered);
   }
        
    /**
    * Gets a user input of an integer, prints an error if they don't input a number or it's too big
      or small (from MyUtils.java)
    * @param s a scanner to gather user inputs
    * @param prompt the prompt for the user
    * @param min the minimum number range
    * @param max the maximum number range
    * @return the number 
   */
   public static int getNumber(Scanner s, String prompt, int min, int max)
   {
      while (true)
      {
         System.out.print(prompt);
      
         if (s.hasNextInt() == false)
         {
            System.out.println("You must enter an *integer* between " + min + " and " + max + ".");
            s.nextLine();
         }
         else
         {
            int number = s.nextInt();
            if (number > max || number < min)
            {
               System.out.println("Your number needs to be between " + min + " and " + max + ".");
               s.nextLine();
            }
            else
            {
               s.nextLine();
               return number;
            }
         }
      }
   }

}
