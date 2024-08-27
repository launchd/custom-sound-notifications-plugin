package com.customsoundnotifications;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("customsoundnotifications")
public interface CustomSoundNotificationsConfig extends Config
{

	@ConfigItem(
			keyName = "stringList",
			name = "String List",
			description = "List of strings to be alerted on"
	)
	default String stringList(){
		return "";
	}
}
