package de.svdragster.solarfurnace;

import net.canarymod.Canary;
import net.canarymod.plugin.Plugin;

public class SolarFurnace extends Plugin {

	@Override
	public void disable() {
		

	}

	@Override
	public boolean enable() {
		Canary.hooks().registerListener(new SolarFurnaceListener(), this);
		return true;
	}

}
