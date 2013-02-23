package naterich2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BTracker_Executor implements CommandExecutor {
	private final BTracker plugin;
	public BTracker_Executor(BTracker p) {
		plugin = p;
	}
	public boolean onCommand(CommandSender s, Command cmd, String label, String [] args){
		if (cmd.getName().equalsIgnoreCase("holding")){
			if(s.hasPermission("BTracker.holding")){
				if(args[0] != null){
					if(Material.getMaterial(args[0])!=null){
						String messageToSend = "List of players and how many blocks they are holding for typeID "+args[0]+":\n";
						HashMap<String, Integer> toList= plugin.list(Material.getMaterial(args[0]).getId());
						for(Iterator<String> i = toList.keySet().iterator(); i.hasNext();){
							messageToSend = messageToSend+ i.next()+": "+toList.get(i.next())+"\n";
						}
						s.sendMessage(messageToSend);
						return true;
					} else
						s.sendMessage("That is not a proper Material, try again");
				} else
					return false;
			} else
				s.sendMessage("You dont have permission to do this command");
		}
		else if(cmd.getName().equals("NaturalItems")){
			if(s.hasPermission("BTracker.NaturalItems")){
				if(args[0] != null && args[1] != null && args[2] != null && args[3] != null && args[4]!= null && args[5] != null){
					if(Material.getMaterial(args[0]) != null){
						int x, y, z, radius;
						try{
							radius = Integer.parseInt(args[1]);
							x = Integer.parseInt(args[2]);
							y = Integer.parseInt(args[3]);
							z = Integer.parseInt(args[4]);
						} catch (NumberFormatException e){
							s.sendMessage("Please enter integer values for radius, x, y and z");
							return false;
						}
						String messageToSend = "List of all natural "+args[0]+" blocks: ";
						Location l;
						if(Bukkit.getServer().getWorld(args[5])!= null){
							l = new Location(Bukkit.getServer().getWorld(args[5]), x, y, z);
							ArrayList<Location> locations = plugin.naturalItemsNear(l, radius, Material.getMaterial(args[0]).getId());
							for(int i = 0; i<locations.size(); i++){
								messageToSend = messageToSend +"X: "+locations.get(i).getX()+"\tY: "+locations.get(i).getY()+"\tZ: "+locations.get(i).getZ()+"\n";
							}
							s.sendMessage(messageToSend);
							return true;
						} else s.sendMessage("The world you entered does not exist, try again");
					} else {
						s.sendMessage("That is not a proper Material, try again");
					}
				} else {
					return false;
				}
			} else
				s.sendMessage("You dont have permission to do this command");
		}
		else if(cmd.getName().equals("NaturalAroundMe")){
			if(s.hasPermission("BTracker.NaturalAroundMe")){
				if(s instanceof Player){
					Player player = (Player) s;
					if(args[0] != null && args[1] != null){
						int radius;
						try{
							radius = Integer.parseInt(args[1]);
						} catch (NumberFormatException e){
							s.sendMessage("Please enter integer values for radius, x, y and z");
							return false;
						}
						if(Material.getMaterial(args[0]) != null){
							String messageToSend = "List of all natural "+args[0]+" blocks within "+radius+" of you: ";
							ArrayList<Location> locations = plugin.naturalItemsNear(player.getLocation(), radius, Material.getMaterial(args[0]).getId());
							for(int i = 0; i<locations.size(); i++){
								messageToSend = messageToSend +"X: "+locations.get(i).getX()+"\tY: "+locations.get(i).getY()+"\tZ: "+locations.get(i).getZ()+"\n";
							}
							s.sendMessage(messageToSend);
							return true;
						}
					} else return false;	
				} else 
					s.sendMessage("This command can only be run by a player");
			} else
				s.sendMessage("You dont have permission to do this command");
		}
		return false;
	}
}
