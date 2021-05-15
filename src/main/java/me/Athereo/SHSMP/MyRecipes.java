package me.Athereo.SHSMP;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

/**
 * Some Recipes cuz hell yeah
 */
public class MyRecipes {
    private Main plugin;

    public MyRecipes(Main plugin) {
        this.plugin = plugin;    
    }

    /**
     * Light Gapple Reicpe
     */
    public class LightGapple {
        private ShapedRecipe recipe;

        public LightGapple() {
            ItemStack lightGapple = new ItemStack(Material.GOLDEN_APPLE);
            ItemMeta lightGappleMeta = lightGapple.getItemMeta();
            lightGappleMeta.setDisplayName("Light Golden Apple");
            lightGapple.setItemMeta(lightGappleMeta);

            // Recipe
            NamespacedKey key = new NamespacedKey(plugin, "light_golden_apple");
            ShapedRecipe lightGappleRecipe = new ShapedRecipe(key, lightGapple);
            
            // G = Gold ingot, A = Apple
            lightGappleRecipe.shape(
                " G ",
                "GAG",
                " G "
            );
    
            lightGappleRecipe.setIngredient('G', Material.GOLD_INGOT);
            lightGappleRecipe.setIngredient('A', Material.APPLE);
            
            this.recipe = lightGappleRecipe;
        }

        public ShapedRecipe getRecipe() {
            return this.recipe;
        }
    }

    /**
     * Necronomicon to Resurrect and Revive players
     */
    public class Necronomicon {
        private ShapedRecipe recipe;
        private ItemStack item;

        public Necronomicon() {
            ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();

            BaseComponent[] firstPage = new ComponentBuilder("The Necronomicon")
                .create();

            bookMeta.spigot().addPage(firstPage);
    
            for (Player onlplayer : Bukkit.getOnlinePlayers()) {
                // TODO store time person died
                String content = "Time died to be set soon";
                String coloredContent = ChatColor.translateAlternateColorCodes('&', onlplayer.getDisplayName() + "\n\n" + content);

                // Only adds dead players
                if (onlplayer.isDead()) {
                    BaseComponent[] page = new ComponentBuilder(coloredContent)
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shsmp:revive " + onlplayer.getUniqueId()))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Revive this player?")))
                        .create();

                    // add the page to the meta
                    bookMeta.spigot().addPage(page);
                }
    
            }

            BaseComponent[] refreshPage = new ComponentBuilder("Refresh")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shsmp:refresh"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Refresh the book")))
                .create();
    
            bookMeta.spigot().addPage(refreshPage);
    
            //set the title and author of this book
            bookMeta.setAuthor("SHSMP");
            bookMeta.setTitle("Necronomicon");
            bookMeta.setDisplayName("The Necronomicon");
    
            //update the ItemStack with this new meta
            writtenBook.setItemMeta(bookMeta);

            NamespacedKey key = new NamespacedKey(plugin, "necronomicon");
            ShapedRecipe recipe = new ShapedRecipe(key, writtenBook);

            // G = ench gapple, B = book
            if (plugin.getConfig().getBoolean("LightNecronomicon")) {
                recipe.shape(
                    " G ",
                    "GBG",
                    " G "
                );
            } else {
                recipe.shape(
                    "GGG",
                    "GBG",
                    "GGG"
                );
            }

            recipe.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
            recipe.setIngredient('B', Material.BOOK);

            this.item = writtenBook;
            this.recipe = recipe;
        }

        public ItemStack getItem() {
            return this.item;
        }

        public ShapedRecipe getRecipe() {
            return this.recipe;
        }
    }

    /**
     * Represents a used or deactivated Necronomicon
     */
    public class UsedNecronomicon {
        private ItemStack item;

        public UsedNecronomicon(Player revivedPlayer, Player revivingPlayer) {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

            BaseComponent[] firstPage = new ComponentBuilder("The Necronomicon")
                .create();

            String mainPageContent = revivedPlayer.getDisplayName() + " has been revived by " + revivingPlayer.getDisplayName() +
                "\n\nRevived on " + dateFormatter.format(now);
            
            BaseComponent[] mainPage = new ComponentBuilder(mainPageContent)
                .create();

            bookMeta.spigot().addPage(firstPage);
            bookMeta.spigot().addPage(mainPage);

            //set the title and author of this book
            bookMeta.setAuthor("SHSMP");
            bookMeta.setTitle("Used Necronomicon");
    
            //update the ItemStack with this new meta
            book.setItemMeta(bookMeta);

            this.item = book;
        }

        public ItemStack getItem() {
            return this.item;
        }
    }

}