package naterich2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BTracker extends JavaPlugin {
	
	public void onEnable(){
		this.getLogger().info("BTracker enabled");
		this.getCommand("holding").setExecutor(new BTracker_Executor(this));
	}
	public void onDisable(){
		this.getLogger().info("BTracker disabled");
	}
	public HashMap<String, Integer> list(int itemID){
		HashMap<String, Integer> items = new HashMap<String, Integer>();
		Player [] players = Bukkit.getServer().getOnlinePlayers();
		int totalForPlayer = 0;
		for(int i = 0; i<players.length; i++){
			Collection<ItemStack> itemStacks = (Collection<ItemStack>) players[i].getInventory().all(itemID).values();
			for(Iterator<ItemStack> j= itemStacks.iterator(); j.hasNext();){
				totalForPlayer += j.next().getAmount();
			}
			items.put(players[i].getName(), totalForPlayer);
			totalForPlayer = 0;
		}
		return items;
	}
	public ArrayList<Location> naturalItemsNear(Location l, int radius, int ID){
		ArrayList<Location> naturalBlocks = new ArrayList<Location>();
		int xStart = l.getBlockX(), yStart = l.getBlockY(), zStart = l.getBlockZ();
		int xLength = xStart+ radius, yLength = yStart+radius, zLength = zStart+radius;
		for(int x = xStart; x<xLength; x++){
			for(int y = yStart; y<yLength; y++){
				for(int z = zStart; z<zLength; z++){
					Block currentBlock = l.getWorld().getBlockAt(x, y, z);
					if(currentBlock.getTypeId() == ID)
						naturalBlocks.add(currentBlock.getLocation());
				}
			}
		}
		return naturalBlocks;
	}

}
