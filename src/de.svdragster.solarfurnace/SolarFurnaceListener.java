package de.svdragster.solarfurnace;

import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.Furnace;
import net.canarymod.api.world.blocks.TileEntity;
import net.canarymod.chat.Colors;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockPlaceHook;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.canarymod.hook.world.SmeltBeginHook;
import net.canarymod.plugin.PluginListener;

public class SolarFurnaceListener implements PluginListener {
	
	@HookHandler
	public void onBlockPlace(BlockPlaceHook hook) {
		if (hook.getBlockPlaced().getType().equals(BlockType.DaylightSensor)) {
			if (hook.getPlayer().getWorld().getBlockAt(hook.getBlockClicked().getX(), hook.getBlockPlaced().getY()-1, hook.getBlockPlaced().getZ()) != null) {
				Block block = hook.getPlayer().getWorld().getBlockAt(hook.getBlockClicked().getX(), hook.getBlockPlaced().getY()-1, hook.getBlockPlaced().getZ());
				if (block.getType().equals(BlockType.Furnace)) {
					TileEntity tileentity = block.getTileEntity();
					Furnace furnace = (Furnace) tileentity;
					furnace.setInventoryName("Solar Powered Furnace");
					hook.getPlayer().message(Colors.GREEN + "Connected Solar Panel to Furnace! It will take some time until it charges.");
				}
			}
		}
	}
	
	public void smelt(Furnace furnace) {
		//if (furnace.getWorld().getBlockAt(furnace.getX(), furnace.getY() + 1, furnace.getZ()).getType().equals(BlockType.DaylightSensor)) {
			if (furnace.getWorld().getRelativeTime() > 0 && furnace.getWorld().getRelativeTime() < 13500) {
				/*ItemFactory factory = Canary.factory().getItemFactory();
				Item coal = factory.newItem(ItemType.Coal);
				coal.setSlot(1);
				furnace.setSlot(coal);*/
				furnace.setBurnTime((short) 2000);
			}
			//furnace.setCookTime((short) (furnace.getCookTime() * 1.5)); // This does not work
			furnace.update();
		//}
	}
	
	@HookHandler
	public void onSmeltBegin(SmeltBeginHook hook) {
		Furnace furnace = hook.getFurnace();
		smelt(furnace);
	}
	
	@HookHandler
	public void onRedstoneChange(RedstoneChangeHook hook) {
		if (hook.getSourceBlock().getType().equals(BlockType.DaylightSensor)) {
			Block block = hook.getSourceBlock();
			if (hook.getSourceBlock().getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ()).getType().equals(BlockType.Furnace)) {
				Block furnace = hook.getSourceBlock().getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ());
				if (hook.getNewLevel() > 5) {
					smelt((Furnace) furnace.getTileEntity());
				}
			}
		}
	}
	
	@HookHandler
	public void onBlockRightClick(BlockRightClickHook hook) {
		if (hook.getPlayer().getWorld().getBlockAt(hook.getBlockClicked().getX(), hook.getBlockClicked().getY()+1, hook.getBlockClicked().getZ()) != null) {
			Block block = hook.getPlayer().getWorld().getBlockAt(hook.getBlockClicked().getX(), hook.getBlockClicked().getY()+1, hook.getBlockClicked().getZ());
			if (block.getType().equals(BlockType.DaylightSensor)) {
				Furnace furnace = (Furnace) hook.getBlockClicked().getTileEntity();
				furnace.setCanOpenRemote(true);
			}
		}	
	}
}
