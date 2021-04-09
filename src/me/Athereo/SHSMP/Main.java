package me.Athereo.SHSMP;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Athereo.SHSMP.DiscordWebhook.EmbedObject;

public class Main extends JavaPlugin {
    public FileConfiguration config;
    public MyRecipes recipes;

    @Override
    public void onEnable() {
        this.config = getConfig();
        this.recipes = new MyRecipes(this);
        configFileHandler();

        // Adds the events
        PluginManager bukkitPluginManager = Bukkit.getPluginManager();
        bukkitPluginManager.registerEvents(new MyListener(this), this);

        // Add Recipes
        Bukkit.addRecipe(recipes.new LightGapple().getRecipe());
        Bukkit.addRecipe(recipes.new RevivalBook().getRecipe());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Revives dead player
        if (label.equalsIgnoreCase("shsmp:revive")) {
            System.out.println(config.get("Discord Webhook"));

            if (sender instanceof Player) {
                // Gets player
                Player player = Bukkit.getPlayer(UUID.fromString(args[0]));
                Player sendingPlayer = (Player) sender;
                player.setGameMode(GameMode.SURVIVAL);

                DiscordWebhook webhook = new DiscordWebhook(getConfig().getString("Discord Webhook"));
                EmbedObject embed = new EmbedObject()
                    .setTitle(player.getDisplayName() + " Has been Resurrected!")
                    .setDescription("Revived by " + sendingPlayer.getDisplayName());

                webhook.addEmbed(embed);

                try {
                    webhook.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }

        // Refreshes and updates book
        if (label.equalsIgnoreCase("shsmp:refresh")) {
            String necroOnly = "You can only use this command through the Necronomicon!";

            // If sender is a console or non-player
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only a Player can use this command");
                return true;
            }

            Player player = (Player) sender;
            ItemStack necronomicon = recipes.new RevivalBook().getItem();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            String itemInHandName = itemInMainHand.hasItemMeta() 
                ? itemInMainHand.getItemMeta().getDisplayName()
                : null;
            
            // Checks if player is already holding Necronomicon
            if (itemInHandName == null || !itemInHandName.equals(necronomicon.getItemMeta().getDisplayName())) {
                System.out.println("Not Necro");
                sender.sendMessage(necroOnly);
                return true;
            }
            
            // Refreshes the Necronomicon by giving the player a new one
            player.getInventory().setItemInMainHand(necronomicon);
            return true;
        }

        return false;
    }

    /**
     * Handles The config.yml setup
     */
    private void configFileHandler() {
        config.addDefault("Discord Webhook", "Insert Webhook here");
        config.addDefault("Light Necronomicon", false);

        config.options().copyDefaults(true);
        saveConfig();
    }

}