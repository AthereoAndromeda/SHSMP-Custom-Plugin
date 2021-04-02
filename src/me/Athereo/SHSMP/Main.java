package me.Athereo.SHSMP;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

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
         
        if (label.equalsIgnoreCase("hello")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (player.hasPermission("hello.use")) {
					player.sendMessage("Ayy look at dis bewk");
                    // Bukkit.broadcastMessage("broadcastMessage");
                    // Bukkit.getServer().broadcastMessage("getserver Broadcastmsg");

                    player.getInventory().setItemInMainHand(recipes.new RevivalBook().getItem());
					return true;
				}

				player.sendMessage("Heyy friendo. sorry but you hab no perms :(");
				return true;
			} else {
				sender.sendMessage("Hey console");
				return true;
			}
		}

        if (label.equalsIgnoreCase("shsmp:test")) {
            System.out.println(config.get("Discord Webhook"));

            if (sender instanceof Player) {
                Player player = Bukkit.getPlayer(UUID.fromString(args[0]));
                player.setGameMode(GameMode.SURVIVAL);
            }
        }

        if (label.equalsIgnoreCase("shsmp:refresh")) {
            System.out.println(config.getString("Password"));
            if (args[0] == null && args[0] != config.getString("Password")) {
                sender.sendMessage("Unauthorized");
                return false;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.getInventory().setItemInMainHand(recipes.new RevivalBook().getItem());
            }

        }

        return false;
    }

    private void configFileHandler() {
        config.addDefault("Discord Webhook", "Insert Webhook here");
        config.addDefault("Password", "password");
        config.options().copyDefaults(true);
        saveConfig();
    }

    private ItemStack createBook() {
        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();

        for (Player onlplayer : Bukkit.getOnlinePlayers()) {
            BaseComponent[] page = new ComponentBuilder(onlplayer.getDisplayName())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/test " + onlplayer.getUniqueId()))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Revive this player?")))
                .create();

            //add the page to the meta
            bookMeta.spigot().addPage(page);
        }

        BaseComponent[] refreshPage = new ComponentBuilder("Refresh")
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shsmp:refresh"))
            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Refresh the book")))
            .create();

        bookMeta.spigot().addPage(refreshPage);

        //set the title and author of this book
        bookMeta.setTitle("Bruh");
        bookMeta.setAuthor("ree");

        //update the ItemStack with this new meta
        writtenBook.setItemMeta(bookMeta);

        return writtenBook;
    }

}
