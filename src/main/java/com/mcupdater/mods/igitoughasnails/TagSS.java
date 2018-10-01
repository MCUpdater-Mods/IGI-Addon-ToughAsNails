package com.mcupdater.mods.igitoughasnails;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

public abstract class TagSS extends Tag {

	@Override
	public String getCategory() {
		return "sereneseasons";
	}


	public static class Season extends TagSS {

		@Override
		public String getValue() {
			try {
				ISeasonState seasonData = SeasonHelper.getSeasonState(world);
				return getSeasonName(seasonData.getSubSeason());
			} catch (Throwable e) {
				log(this, e);
			}
			return "Unknown";
		}

		private String getSeasonName(sereneseasons.api.season.Season.SubSeason season) {
			return I18n.format("toughasnails.subseason." + season.name().replace("_","").toLowerCase() + ".desc");
		}
	}

	static void register() {
		TagRegistry.INSTANCE.register(new TagSS.Season().setName("tanseason"));
	}

	void log(Tag tag, Throwable ex) {
		FMLCommonHandler.instance().getFMLLogger().warn(IGIToughAsNails.metadata.modId + ":" + tag.getName(), ex);
	}

}
