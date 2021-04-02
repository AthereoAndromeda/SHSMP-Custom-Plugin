package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
    private JavaPlugin plugin;

    public MyRecipes(JavaPlugin plugin) {
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
            
            // G is Gold ingot, S = Stick
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

    public class RevivalBook {
        private ShapedRecipe recipe;
        private ItemStack item;

        public RevivalBook() {
            // ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            // BookMeta bookMeta = (BookMeta) book.getItemMeta();

            ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
    
            for (Player onlplayer : Bukkit.getOnlinePlayers()) {
                String content = ChatColor.translateAlternateColorCodes('&', "&b" + onlplayer.getDisplayName() + "&r\n\n Revive This person");

                BaseComponent[] page = new ComponentBuilder(content)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shsmp:test " + onlplayer.getUniqueId()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Revive this player?")))
                    .create();
    
                //add the page to the meta
                bookMeta.spigot().addPage(page);
            }
            
            BaseComponent[] refreshPage = new ComponentBuilder("Refresh")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shsmp:refresh " + plugin.getConfig().getString("Password")))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Refresh the book")))
                .create();
    
            bookMeta.spigot().addPage(refreshPage);
    
            //set the title and author of this book
            bookMeta.setAuthor("SHSMP");
            bookMeta.setTitle("Necronomicon");
    
            //update the ItemStack with this new meta
            writtenBook.setItemMeta(bookMeta);

            NamespacedKey key = new NamespacedKey(plugin, "necronomicon");
            ShapedRecipe recipe = new ShapedRecipe(key, writtenBook);
            // G = ench gapple, B = book
            recipe.shape(
                "GGG",
                "GBG",
                "GGG"
            );
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

}
