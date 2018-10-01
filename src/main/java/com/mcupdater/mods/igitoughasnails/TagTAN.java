package com.mcupdater.mods.igitoughasnails;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import toughasnails.api.TANCapabilities;
import toughasnails.temperature.TemperatureDebugger;
import toughasnails.temperature.TemperatureHandler;

public abstract class TagTAN extends Tag
{
	@Override
	public String getCategory() {
		return "toughasnails";
	}

	public static class CurrentTemp extends TagTAN {

		@Override
		public String getValue() {
			try {
				TemperatureHandler tempStats = (TemperatureHandler)player.getCapability(TANCapabilities.TEMPERATURE, player.getHorizontalFacing());
				return String.valueOf(tempStats.getTemperature().getRawValue());
			} catch (Throwable e) {
				log(this, e);
			}
			return "-1";
		}
	}

	public static class TargetTemp extends TagTAN {

		@Override
		public String getValue() {
			try {
				TemperatureHandler tempStats = (TemperatureHandler)player.getCapability(TANCapabilities.TEMPERATURE, player.getHorizontalFacing());
				TemperatureDebugger tempDebug = tempStats.debugger;
				return String.valueOf(Math.min(Math.max(0,tempDebug.targetTemperature),25));
			} catch (Throwable e) {
				log(this, e);
			}
			return "-1";
		}
	}

	static void register() {
		TagRegistry.INSTANCE.register(new TagTAN.CurrentTemp().setName("tancurrtemp"));
		TagRegistry.INSTANCE.register(new TagTAN.TargetTemp().setName("tantargettemp"));
	}

	void log(Tag tag, Throwable ex) {
		FMLCommonHandler.instance().getFMLLogger().warn(IGIToughAsNails.metadata.modId + ":" + tag.getName(), ex);
	}
}
