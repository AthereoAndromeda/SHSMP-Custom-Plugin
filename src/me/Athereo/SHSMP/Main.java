package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

// import javafx.scene.effect.Light;
// import net.md_5.bungee.api.chat.BaseComponent;
// import net.md_5.bungee.api.chat.ClickEvent;
// import net.md_5.bungee.api.chat.ComponentBuilder;
// import net.md_5.bungee.api.chat.HoverEvent;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Adds the events
        PluginManager bukkitPluginManager = Bukkit.getServer().getPluginManager();
        bukkitPluginManager.registerEvents(new MyListener(this), this);

        // Add Recipes
        MyRecipes recipes = new MyRecipes(this);
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
                    Bukkit.broadcastMessage("broadcastMessage");
                    Bukkit.getServer().broadcastMessage("getserver Broadcastmsg");

                    // ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
                    // BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
                    
                    // BaseComponent[] page = new ComponentBuilder("Click me")
                    //     .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://spigotmc.org"))
                    //     .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the spigot website!").create()))
                    //     .create();

                    // //add the page to the meta
                    // bookMeta.spigot().addPage(page);

                    // //set the title and author of this book
                    // bookMeta.setTitle("Interactive Book");
                    // bookMeta.setAuthor("gigosaurus");

                    // //update the ItemStack with this new meta
                    // writtenBook.setItemMeta(bookMeta);

					return true;
				}
				
				player.sendMessage("Heyy friendo. sorry but you hab no perms :(");
				return true;
			} else {
				sender.sendMessage("Hey console");
				return true;
			}
		}
        
        return false;
    }

}
