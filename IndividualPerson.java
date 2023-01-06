// Nathin W.
// Section A
// Final Project
// May 29th
// IndividualPerson
// An object that represents a single dot/person in the simulation

import java.awt.*;
import java.util.*;

public class IndividualPerson
{
   /** The health of the person */
   private State state;
   /** The age of the person */
   private int age;
   
   /** The top left x coordinate of the bounding box of the dot  */
   private int x;         
   
   /** The top left y coordinate of the bounding box of the dot */
   private int y;
   
   /* The width of the bounding box of the dot */
   private int width;

   /* The height of the bounding box of the dot */
   private int height;

   /* The fill color of the dot */
   private Color color;  
   
   /* How fast/far the person moves */
   private int movementFactor;
   
   /* Keeps track of relative time in the simulation */
   private int timeIndex;
   
   /* The time that the person was infected */
   private int timeInfected;
   
   /**
   * Constructs a new IndividualPerson object
   * @param age the individual's age
   * @param x the individual's x coordinate
   * @param y the individual's y coordinate
   * @param state the individual's state of health
   * @param movementFactor how fast the individual moves per frame
   */
   public IndividualPerson(int age, int x, int y, State state, int movementFactor)
   {
      this.state = state;
      this.age = age;
      if (state.equals(State.Infected))
      {
         color = Color.RED;
      }
      else
      {
         color = Color.GREEN;
      }
      this.x = x;
      this.y = y;
      this.width = 10;
      this.height = 10;
      this.movementFactor = movementFactor;
      this.timeInfected = 0;
      this.timeIndex = 0;
   }

   enum State
   {
      Healthy,
      Infected,
      Recovered,
      Dead
   }
   
   /**
   * Returns the state of the individual
   * @return the state
   */
   public State getState()
   {
      return this.state;
   }
   
   /**
   * When hit by the centerpoint of an infected individual, the virus is transmitted to the other
   * @param person another IndividualPerson
   */
   public void transmission(IndividualPerson other)
   {
      if (isHit(other.getX(), other.getY()))
      {
         if (state.equals(State.Healthy) && other.getState().equals(State.Infected))
         {
            
            state = State.Infected;
            color = Color.RED;
            timeInfected = timeIndex;
         }
      }
   }
   
   /**
   * Draws the individual (code borrowed from CircleTile.java)
   * @param g Graphics for drawing
   */
   public void draw(Graphics g) 
   {  
      if (state.equals(State.Dead))
      {
         int diameter = Math.min(getWidth(), getHeight()); 
         g.setColor(Color.WHITE);
         g.fillOval(x - 1, y - 1, diameter + 3, diameter + 3);
         g.setColor(Color.BLACK);
         g.drawString("X", x, y);
      }
      else
      {
      // First calculate the smaller dimension: width or height. 
         int diameter = Math.min(getWidth(), getHeight()); 
         int finalx = getX(); 
         int finaly = getY(); 
         int finalwidth = getWidth(); 
         int finalheight = getHeight(); 
      
      // figure out the actual location of the bouncing box of the circle 
      // which means moving either the x or y coordinate of the given bounding box. 
         if ( getWidth() < getHeight() ) 
         {
            finaly = getY() + (getHeight() - diameter) / 2; 
         }
         else 
         {
            finalx = getX() + (getWidth() - diameter) / 2;
         }
         g.setColor(color);
         g.fillOval(finalx, finaly, diameter, diameter);
         g.setColor(Color.BLACK);
         g.drawOval(finalx, finaly, diameter, diameter);
      }
   }

   /**
   * Given a point, determines whether the individual is hit by that point
   * @param x the x coordinate
   * @param y the Y coordinate
   * @return if the individual is hit or not
   */
   public boolean isHit(int x, int y) 
   { 
      int centerx = getX() + getWidth() / 2; 
      int centery = getY() + getHeight() / 2; 
      int diameter = Math.min(getWidth(), getHeight()); 
      
      return distance(x, y, centerx, centery) <= diameter / 2; 
   }
   
   /** 
    * Returns this tile's leftmost x coordinate. 
    * @return This tile's leftmost x coordinate of the bounding box. 
    */
   public int getX() 
   {
      return x;
   }
   
   /** 
    * Returns this tile's topmost y coordinate. 
    * @return This tile's topmost y coordinate of the bounding box. 
    */
   public int getY() 
   {
      return y;
   }
   
   /** 
    * Returns this tile's width
    * @return This tile's width of the bounding box. 
    */
   public int getWidth() 
   {
      return width;
   }
   
   /** 
    * Returns this tile's height. 
    * @return This tile's height of the bounding box. 
    */
   public int getHeight() 
   {
      return height;
   }
        
   /**
    * Return the distance between two points (from tiles.java)
    * @param x1 The x coordinate of the first point
    * @param y1 The y coordinate of the first point
    * @param x2 The x coordinate of the second point
    * @param y2 The y coordinate of the second point 
    * @return The distance between the two points
    */ 
   private double distance(int x1, int y1, int x2, int y2)
   {
      int xdist = x2 - x1; 
      int ydist = y2 - y1; 
      return Math.sqrt(xdist * xdist + ydist * ydist); 
   }
   
   /**
   * Moves the individual a certain spots in a random direction
   * @param g Graphics for drawing
   * @param prevX the current x coordinate
   * @param prevY the previous Y coordinate
   */
   public void move(Graphics g, int prevX, int prevY)
   {
      if (!state.equals(State.Dead))
      {
         int diameter = Math.min(getWidth(), getHeight()); 
         g.setColor(Color.WHITE);
         g.fillOval(prevX - 1, prevY - 1, diameter + 3, diameter + 3);
      
         Random r = new Random();
         x = prevX + movementFactor*(r.nextInt(3) - 1);
         y = prevY + movementFactor*(r.nextInt(3) - 1);
         g.setColor(color);
      }
      timeIndex += 1;
   }
   
   /**
   * returns the time that the individual got infected
   * @return the time infected
   */
   public int getTimeInfected()
   {
      return this.timeInfected;
   }
   
   /**
   * Returns the current time in the simulation
   * @return the time
   */
   public int getTimeIndex()
   {
        return this.timeIndex;
   }
   
   /**
   * When an individual is infected with the virus, they move around for 10 seconds before either
     dying or recovering, depending on their age and the mortality rates for thair age group
   */
   public void treatment()
   {
      Random r = new Random();
      int deathTime = 0;
      if (state.equals(State.Infected))
      {
         deathTime = timeInfected + 100;
      
         if (timeIndex > deathTime)
         {
            deathTime = 0;
            if (age >= 20 && age <= 39) //SOURCE: https://www.doh.wa.gov/Emergencies/NovelCoronavirusOutbreak2020COVID19/DataDashboard
            {
               if (r.nextInt(100) == 0)
               {
                  state = State.Dead;
                  color = Color.WHITE;
               }
               else
               {
                  state = State.Recovered;
                  color = Color.BLUE;
               }
            }
            else if (age >= 40 && age <= 59)
            {
               if (r.nextInt(100) < 9)
               {
                  state = State.Dead;
                  color = Color.WHITE;
               }
               else
               {
                  state = State.Recovered;
                  color = Color.BLUE;
               }
            }
            else if (age >= 60 && age <= 79)
            {
               if (r.nextInt(100) < 38)
               {
                  state = State.Dead;
                  color = Color.WHITE;
               }
               else
               {
                  state = State.Recovered;
                  color = Color.BLUE;
               }
            }
            else if (age >= 80)
            {
               if (r.nextInt(100) < 52)
               {
                  state = State.Dead;
                  color = Color.WHITE;
               }
               else
               {
                  state = State.Recovered;
                  color = Color.BLUE;
               }
            }
            else 
            {
               state = State.Recovered;
               color = Color.BLUE;
            }
         }
      }
   }
}