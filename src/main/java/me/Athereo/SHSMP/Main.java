package me.Athereo.SHSMP;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
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
    public MyListener listener;

    @Override
    public void onEnable() {
        this.config = getConfig();
        this.recipes = new MyRecipes(this);
        this.listener = new MyListener(this);
        configFileHandler();

        // Adds the event handlers
        PluginManager bukkitPluginManager = Bukkit.getPluginManager();
        bukkitPluginManager.registerEvents(listener, this);

        // Add Recipes
        Bukkit.addRecipe(recipes.new LightGapple().getRecipe());
        Bukkit.addRecipe(recipes.new Necronomicon().getRecipe());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                listener.updateScoreboard();
            }
        }, 0, 10000);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Revives dead player
        if (label.equalsIgnoreCase("shsmp:revive")) {
            if (sender instanceof Player) {
                // Gets player
                Player revivedPlayer = Bukkit.getPlayer(UUID.fromString(args[0]));
                Player revivingPlayer = (Player) sender;

                // Teleport revivedPlayer to revivingPlayer
                String tpCommand = "teleport " + revivedPlayer.getDisplayName() + " " + revivingPlayer.getDisplayName();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tpCommand);

                // Changes gamemode which "revives" the player. Also sets them to max health and
                // hunger
                revivedPlayer.setGameMode(GameMode.SURVIVAL);
                revivedPlayer.setHealth(revivedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                revivedPlayer.setFoodLevel(20);

                // Replace Necronomicon with Used Necronomicon
                revivingPlayer.getInventory()
                        .setItemInMainHand(recipes.new UsedNecronomicon(revivedPlayer, revivingPlayer).getItem());

                // Broadcast Message that someone has been revived
                String revivedMessage = ChatColor.translateAlternateColorCodes('&',
                        "&b" + revivedPlayer.getDisplayName() + " was revived by " + revivingPlayer.getDisplayName());
                Bukkit.broadcastMessage(revivedMessage);

                if (this.getConfig().getBoolean("EnableDiscordWebhook")) {
                    DiscordWebhook webhook = new DiscordWebhook(getConfig().getString("DiscordWebhook"));
                    EmbedObject embed = new EmbedObject()
                            .setTitle(revivedPlayer.getDisplayName() + " Has been Resurrected!")
                            .setDescription("Revived by " + revivingPlayer.getDisplayName());

                    webhook.addEmbed(embed);

                    // Send Discord Webhook that someone has been revived
                    try {
                        webhook.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }
        }

        // Refreshes and updates book
        if (label.equalsIgnoreCase("shsmp:refresh")) {
            // If sender is a console or non-player
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only a Player can use this command");
                return true;
            }

            Player player = (Player) sender;
            String necroOnly = "You can only use this command through the Necronomicon!";
            ItemStack necronomicon = recipes.new Necronomicon().getItem();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            // Checks if Item in hand has ItemMeta. If true, returns display name, else it
            // returns null
            String itemInHandName = itemInMainHand.hasItemMeta() ? itemInMainHand.getItemMeta().getDisplayName() : null;

            // Checks if player is already holding Necronomicon
            if (itemInHandName == null || !itemInHandName.equals(necronomicon.getItemMeta().getDisplayName())) {
                sender.sendMessage(necroOnly);
                return true;
            }

            // Refreshes the Necronomicon by giving the player a new updated one.
            // pretty stupid but i dunno how to dynamically update an existing book.
            player.getInventory().setItemInMainHand(necronomicon);
            return true;
        }

        return false;
    }

    /**
     * Handles The config.yml setup
     */
    private void configFileHandler() {
        config.addDefault("EnableDiscordWebhook", false);
        config.addDefault("DiscordWebhookLink", "Insert Webhook here");
        config.addDefault("LightNecronomicon", false);

        config.options().copyDefaults(true);
        saveConfig();
    }
}