README.txt

This project runs a simulation of how a virus spreads throughout a community, using a DrawingPanel window.

Open and run the file COVIDSim.java
The user will be prompted to choose population size, a number of seconds to run the
simulation for, and a social distancing level (representing these three levels):
        1. No social distancing, everyone moves around
        2. 50% quarantine, half the individuals stay in place
        3. Essential workers only, 90% of the population stays in place
It will run the simulation, then print the statistics to the console.

In the simulation, green dots represent healthy people, red dots represent infected people,
blue dots represent recovered people, and x's represent people who died from the virus. The virus transmits when an individual contacts someone who is infected. Infected individuals remain infected for 10 seconds, before either dying or recovering, which is based on the survival rate in their predetermined age group.
