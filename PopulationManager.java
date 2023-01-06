// Nathin W.
// Section A
// Final Project
// May 29th
// PopulationManager
//Creates and manages teh array of individuals for the simulation

import java.awt.*;
import java.util.*;
public class PopulationManager
{
   /** The population of the simulation */
   private ArrayList<IndividualPerson> population;
   
   /** A factor of how well the community socially distances */
   private int distancingLevel;
        
   /**
   * Creates a new PopulationManager
   */
   public PopulationManager(int distancingLevel)
   {
      this.population = new ArrayList<IndividualPerson>();
      this.distancingLevel = distancingLevel;
   }
   
   /**
   * Adds a person to the manager
   * @param person the person to be added
   * @return whether the person was added
   */
   public boolean addPerson(IndividualPerson person)
   {
      if (person == null)
      {
         return false;
      }
      population.add(person);
      return true;
   }
   
   /**
   * Creates an array of individuals of a specified size
   * @param size the number of individuals in the population
   */
   public void createPopulation(int size)
   {
        Random random = new Random();
        for (int i = 0; i < size - 1; i ++)
        {
                IndividualPerson newPerson = new IndividualPerson(random.nextInt(100), 
                        random.nextInt(1000), random.nextInt(600), IndividualPerson.State.Healthy,
                        movementFactor());
                addPerson(newPerson);
        }
        IndividualPerson patientZero = new IndividualPerson(15, 500, 300, 
                IndividualPerson.State.Infected, 10);
        addPerson(patientZero);
   }
   
   /**
   * Draws all the people in the manager
   * @param g the Graphics object
   */
   public void drawAll(Graphics g)
   {
      for (int i = 0; i < population.size(); i ++)
      {
         population.get(i).draw(g);
      }
      String time = "Time: " + (population.get(0).getTimeIndex()/10);
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, 65, 10);
      g.setColor(Color.BLACK);
      g.drawString(time, 10, 10);
   }
   
   /**
   * Returns the array with the individuals
   * @return the poplulation
   */
   public ArrayList<IndividualPerson> getPopulation()
   {
        return population;
   }
   
   /**
   * Determines if/how far an individual moves per frame, depending on the level of social
     distancing.
   * @return how far they move
   */
   public int movementFactor()
   {
        Random r = new Random();
        if (distancingLevel == 1)
        {
                return 10;
        }
        else if (distancingLevel == 2)
        {
                return 10 * r.nextInt(2);
        }
        else if (distancingLevel == 3)
        {
                if (r.nextInt(10) == 0)
                {
                        return 10;
                }
                else
                {
                        return 0;
                }
        }
        else
        {
                return 0;
        }
   }
   
}