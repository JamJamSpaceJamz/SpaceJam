SpaceJam
==========================================
Coded by David Gilhooley and Blake Lawson


Hello and welcome to the wonderful world of SpaceJam! Named for its obvious space theme and the fact that game coding is typically called a "game jam", this game is being developed strictly by two Princeton students in their spare time. 

How To Run
==========
We typically pull the files and open an eclipse workspace right ontop of it. However, one detail needs to be changed in order for it to work. In eclipse, open configure build path and change the Native library location. The file that you should change it to is lwgl-2.9.1/native/ and then your OS.

Game Vision
===========

Spacejam is a real time stradegy game based on programming. It will be fun and accessible, while still teaching the players the fundamentals of Java programming. The player will have complete access over the AI of his own ships and it will be up to him to make sure that they respond to any threat or reward correctly.

GamePlay
========

In Spacejam the player will be put against another player of his choosing (typically a programmed AI but the game will support 1v1 same screen multiplayer as soon as it is finished). Each player programs AI of their four types of ships (Miners, Fighters, Scouts, and Heavy) initially and they begin with a two miners and a scout. The miners should seek out asteroids to fire at and collect gems which can be brought back to the base (the colored hexagon) for points. The scouts will be able to tag asteroids and send their location to the mining ships, but this behavior will be determined by the player. 

Once the game has started the player still has limited options to control his ships. The player is allowed to use points to buy new ships, heal his/her base, and repair his/her turrets (we are currently undecided about purchasing upgrades). He/she will also be able to send out up to nine different "signals" from the base by pressing the number keys. This signals are interpreted by the ships, but once again, these interpretations will be left up to the player to implement. 

Once enough resources are collected, the fighter ships should begin to attack the other player's base. However, the base is ringed with heavy turrets and will be well defended. Once the enemies base is destroyed the player wins!

Current Version
===============

The game currently supports ships, asteroids, bases, gems, teams, and the basic framework for the ship's api. So far only the miner ship has been implemented, but the framework for the ship is modular enough to allow for the other ships to be made with just a tweaking of constants. Once the api is fully up, the player will be able to code his ships AI and run it from eclipse, but we should eventually have a working in-game terminal.

If you open the game right now you will see one programable ship and one user controlled ship for testing. Move the user ship with "WASD" and space to fire.

Dependencies
============
Java7

Java Lightweight Game Library (lwgl)

TO DO
=====

1) Use a better collision algorithm that runs faster than N^2 (Especially N^2 calls to an unknown collision detector)

2) Comment all of the methods (This goes without saying)

3) Playable skeleton of game (compile it with eclipse and play against friends)

4) Playable, Sharable game (program with in-game terminal)

Possible Future Features
========================

TUTORIALS!

We believe that this game would be able to teach people (especially kids) some fundamental java coding in a fun and interactive environment. If this game was published, the first thing that we would do was have some very basic coding tutorials. Also, the game will always be open source and people will be encouraged to mess with it's inner workings.

GAME TYPES!

Capture the flag, team deathmatch, story mode, custom map builder, etc.

GRAVITY!

Once the normal game has been out, an interesting feature would be a central star in the stage that pulls all of the other objects towards it. Players would then be given a different api that allowed them to know speed/ distance/ gravity ect. to allow them to program their ships to remain in orbit.

POWER UPS!

Some people find this idea cheating, but what is a multiplayer game without some good old fashioned powerups?

KICKSTARTER!

Promote the game once it has a working state and see how it is recieved.
