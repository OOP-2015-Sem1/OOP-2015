# Redstone Simulator

###### Platform: PC
###### (Will be developed in Java)

### What is Redstone?

Redstone is a boolean (with few analog aplications) logic system implemented in Minecraft that can interact with the world.
Using limited simple operators (NOT and OR) one can build complex [circuits](http://minecraft.gamepedia.com/Redstone_circuit) performing various tasks.

##### Design implications
What are not so limited are the objets that redstone can iterract with.
Many bugs and glitches have been proven to have very useful implication (such as [this](http://minecraft.gamepedia.com/Tutorials/Block_update_detector)) and thus have become features.
It is also important to state that Minecraft is a **voxel based 3D** game.

Because of that, this project will include only a subset of the functionality of Minecraft redstone (only what you'd think makes sense, maybe I'll include a couple of them such as the [redstone ladder](http://minecraft.gamepedia.com/Transmission_circuit#Vertical_transmission)).

It will be **top down grid based** and will implement verticality the same way *Drawf Frotress* did, which is you see a **height-width** section on the interface and you can manually change the **height**.
You will also see the grid below with a bit of tint in the empty grid cells.


# TODO:

- [ ] add GUI design
- [ ] implement the view and basic cell infrastructure

More to go...
