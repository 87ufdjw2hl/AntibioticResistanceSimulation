<div align=center><h1> Antibiotic Resistance Simulation in Java</h1></div>

<p align="center">
  <img src="https://github.com/jaeihn/AntibioticResistanceSimulation/blob/master/preview.png" width="500">
</p>
<div align=center>Computational Biology Spring 2019</div>
<div align=center>Jae Ihn</div>


## Table of Contents
  * [How to use this app](#how-to-use-this-app)
  * [Concepts](#concepts)
  * [Simulation](#simulation)
  * [Resources](#resources)

<br/>

## How to use this app

<p align="center">
  <img src="https://github.com/jaeihn/AntibioticResistanceSimulation/blob/master/how_to.png" width="500">
</p>

- Upon launching, the simulation will begin automatically.
- The ![](https://via.placeholder.com/15/174FCF/000000?text=+)25x25 grid shows the bacteria colony. When there is no live cell in a grid’s position, the position is left white. Otherwise, the color of the position corresponds to the bacteria cell’s gene expression level of an arbitrary gene. The darker the color, the higher the gene expression level.
- The ![](https://via.placeholder.com/15/E09A1A/000000?text=+)“DOSE ANTIBIOTICS ①” button applies a dosage of the first antibiotic solution onto the colony. This solution targets bacteria with low expression of the arbitrary gene. In other words, bacteria cells with higher expression (darker color) are more likely to survive.
- The ![](https://via.placeholder.com/15/78CE12/000000?text=+)“DOSE ANTIBIOTICS ②” button applies a dose of the second antibiotic solution onto the colony. This solution targets bacteria with high expression of the arbitrary gene. In other words, bacteria cells with lower expression (lighter color) are more likely to survive.
- The ![](https://via.placeholder.com/15/E01A1A/000000?text=+) “RESTART” button restarts the simulation. You can press on this any time to start over. It is useful when you have accidently killed off all your bacteria population.
- The average gene expression level among all the live cells of the bacteria colony is printed ![](https://via.placeholder.com/15/11C5FB/000000?text=+)next to the buttons.
- Exiting the window will terminate the application.
<br/>


## Concepts

This simulation is based on the concept of antibiotic resistance. Antibiotic resistance is a consequence of natural selection. When it is time for a cell to divide, the cell’s genetic material is replicated to be distributed among the two daughter cells. This process of DNA replication is not perfect, leading to occasional errors, called mutations.

DNA is a recipe coding for amino acids sequences that fold into proteins. The mutation in the DNA may cause a different amino acid to be called in place of another one, and this would in turn affect the protein that is built. Sometimes, the mutation may have occurred but the amino acid it codes for would remain the same, as some amino acids have several recipes associated with them. In that case, the effect of the mutation is negligible. Sometimes, the effects are fatal— an important protein may become incapacitated or absent, and the organism would die. Other times, the effects are not fatal, but the organism survives with a slightly different trait. This gives rise to variability among the individuals of a population.

Although there are no traits that are inherently “better” than others, some traits may allow an individual a better chance of survival under certain environmental conditions. Such individuals are more likely to survive to reproduce, passing on their genes to the next generation, while others who do not have the trait may not survive to produce offspring as much. Over time, a greater proportion of the population would have the advantageous trait.

In the case of antibiotic resistance, the environmental factor would be the presence of antibiotics. Variability within the bacteria population might allow some of the bacteria a higher chance of survival. These bacteria are ones that are more likely to survive to reproduce or share their genes with surrounding neighbors via horizontal gene transfer. Over time, with continuous exposure to the antibiotics that kill bacteria with a lower exhibition of the advantageous (antibiotic resistant) trait, bacteria with stronger exhibition of the trait will be naturally selected, becoming common within the colony. The bacteria colony evolves to become more difficult to kill with the same antibiotics—in other words, they develop antibiotic resistance.
<br/>


## Simulation

The concepts described above are simplified within the simulation. Instead of storing information about the DNA sequence or the amino acid sequences, the simulation works with the general effect of the mutation. Each bacteria cell has a number between 0.1 and 1 (0 was reserved to indicate a dead or nonexistent cell) that indicates how strongly it portrays a trait. This may represent the expression level of a gene that controls the concentration of a chemical substance, the abundance of a certain protein, or the strength of an observable, phenotypic characteristic, that somehow affect the bacteria’s survivability when exposed to antibiotics.

The simulation begins with a single bacteria cell with expression level of 0.1. Over time, some of the bacteria mutate, and show varied values of expression level. When left alone, the colony is faced with no particular environmental pressure. This means that there is nothing advantageous or disadvantageous about having a high or low expression level of the gene, and all bacteria have a similar chance of survival and reproduction. Since the lower and upper bound of the expression level are 0.1 and 1, average expression level across the bacteria colony is expected to be around 0.55.

The simulation has two antibiotic solutions. The first antibiotics are designed to kill bacteria with low expression level of the arbitrary gene. In other words, the higher the expression level of the bacteria, the more likely it is to survive the dose of antibiotics. Dosing the population will increase the average expression level, as it kills many of the bacteria with low expression levels. When dosing the colony continuously, if there is even one bacterium with sufficiently high enough resistance to survive the continuous dosage, the bacteria will slowly repopulate, the colony will have a high average expression level, and will become harder and harder to exterminate using the same antibiotics. This demonstrates the antibiotic resistance crisis, where the overuse antibiotics causes bacteria to become resistant to the only available drugs.

A similar effect happens with the second type of antibiotics, which is designed to kill bacteria with high expression level of the arbitrary gene. The only difference is that this time around, the average expression level will decrease each time the colony is dosed with the second type. This shows how there are no inherently ‘better’ traits—it all depends on how the trait affects an individual’s survivability under that specific environmental context. In fact, a trait that seemed to be advantageous may quickly turn into a handicap under the change in environment (for example, first dose the colony with antibiotics (1) to harbor a colony of high gene expression, then dose the colony with antibiotics (2)).

When we stop interacting with the simulation, over time, the average resistance level will return to around 0.55, even after harboring a bacteria colony that has very high or low average expression level. When there is no longer the environmental pressure (when a lot of time passes without dosing the colony with any antibiotics) that naturally selected the bacteria exhibiting the trait, the trait less important in terms of its influence in survivability, and hence the variance in trait expression increases.
<br/>


## Resources

- An article on antibiotic resistance crisis: Ventola C. L. (2015), “The Antibiotic Resistance Crisis”. https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4378521/.

- Example code by MadProgrammer on how to paint individual JTable cells based on table value: https://stackoverflow.com/questions/30552644/how-do-i-color-individual-cells-of-a-jtable-based-on-the-value-in-the-cell
