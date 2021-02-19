package me.Athereo.SHSMP;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class onCraftListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event) throws Exception {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		if (event.getRecipe().getResult().getType() == Material.OAK_PLANKS) {
			Player player = (Player) event.getWhoClicked();
			player.sendMessage("Woah, you crafted an Oak Plank. damn son");
			
			sendWebhook(event);
		}
	
		System.out.println("Something got crafted uwu");
	}

    private void sendWebhook(CraftItemEvent event) throws Exception {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/808972536774656010/vrB_4q8YxaRN2KQRwBngOQ38unspjWj5p7Zm4mRbM5flxKeqTnp_4KVvKnT0kTNF-ZQ_");
        webhook.setContent(event.getWhoClicked().getName() + " Crafted some wood plank");
        webhook.execute();
    }
}
