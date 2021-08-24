# SHSMP Plugin
A custom Spigot plugin for Semi-Hardcore Survival Multiplayer Worlds.

[![Java CI with Maven](https://github.com/redstripez08/SHSMP-Custom-Plugin/actions/workflows/maven.yml/badge.svg)](https://github.com/redstripez08/SHSMP-Custom-Plugin/actions/workflows/maven.yml)

Pure Hardcore worlds can be hard to sustain and keep alive with a small community, whereas
a Survival world can be too relaxed or casual for more experienced players. So to strike the
balance, I decided to create a custom plugin for a Semi-Hardcore world.

## What is a Semi-Hardcore World?
A Semi-Hardcore world is exactly what it sounds like. A less extreme version of a standard Hardcore world 
wherein **a player can still respawn if specific requirements are met**.

It uses a **Hardcore** world, but this plugin changes some aspects to make it Semi-Hardcore and allow
players to respawn. A Semi-Hardcore SMP would pose a lot of new interesting challenges and scenarios.
An SHSMP emphasizes the importance of surviving, cooperation, and endurance.

**Reviving players is very expensive.**

## How to Revive Players
To revive a player, you need to craft a **Necronomicon**  
You need **8 Ench Gapples and a Book** to craft this.

#### **Recipe:**
<!-- Add Picture of Crafting Recipe here -->

Notice I said *player* instead of *players*. This is because **Necronomicons are single-use only**, 
deactivating after being used. This necessitates the crafting of multiple Necronomicons to 
revive multiple players.  
This equates to **8 Ench Gapples, or 64 Gold Blocks, or 576 Gold Ingots, or 5184 Gold Nuggets for each revival**.

You can change the recipe to 4 Ench Gapples instead though in `config.yml`

This book contains all the names of dead players. Clicking on the name will revive and spawn them
on a specific location or the player who revived them depending on the `config.yml` configuration.

## Prerequisites
* Spigot or Paper Server
* Should work from 1.8+, but only tested for 1.16
* That's really it

## Configuration
There are multiple elements that you can customize or change in `config.yml` once the `SHSMP` folder has
been created:
___
### DiscordWebhook
**Type:** `String`  
Sends a message whenever a Necronomicon has been crafted or used via Discord Webhook

### LightNecronomicon
**Type:** `Boolean`  
**Default:** `false`  
Set to `true` to make Necronomicon recipe use 4 Ench Gapples rather than 8.

### Example
<!-- Pictue of config.yml -->

## Building from source
Building from source is very simple, simply have Maven installed, and you can
package the plugin into a jar
```bash
mvn package
```