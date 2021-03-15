package me.Athereo.SHSMP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
            
            // G is Gapple, S = Stick
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
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();

            List<String> pages = new ArrayList<String>();
            pages.add("hehe peepee poopoo");
            pages.add("Im high as a kite");

            bookMeta.setAuthor("SHSMP");
            bookMeta.setTitle("Necronomicon");
            bookMeta.setPages(pages);
            book.setItemMeta(bookMeta);

            NamespacedKey key = new NamespacedKey(plugin, "necronomicon");
            ShapedRecipe recipe = new ShapedRecipe(key, book);
            // G = ench gapple, B = book
            recipe.shape(
                "GGG",
                "GBG",
                "GGG"
            );
            recipe.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
            recipe.setIngredient('B', Material.BOOK);

            this.item = book;
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
