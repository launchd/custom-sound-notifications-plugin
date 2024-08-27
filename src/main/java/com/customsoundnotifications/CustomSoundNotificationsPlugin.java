package com.customsoundnotifications;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
		name = "Custom Sound Notifications",
		description = "Plays a sound when a message that contains a preset value is detected.",
		tags = {"sounds", "notification"}
)
public class CustomSoundNotificationsPlugin extends Plugin
{
	@Inject
	private CustomSoundNotificationsConfig config;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Notifier notifier;

	@Override
	protected void startUp() throws Exception {
		// Init stuff here
	}

	@Override
	protected void shutDown() throws Exception {
		// Clean up here
	}

	@Subscribe
	public void onChatMessage(ChatMessage event){
		//Get the message type
		ChatMessageType messageType = event.getType();

		// Don't alert on messages for public, private, and friends chat
		if(messageType == ChatMessageType.PUBLICCHAT ||
				messageType == ChatMessageType.PRIVATECHAT ||
				messageType == ChatMessageType.FRIENDSCHAT
		){
			return;
		}

		// For game messages, check if they match a given string from the user and alert if they do match
		if (messageType == ChatMessageType.GAMEMESSAGE){
			String message = event.getMessage();
			String[] keywords = config.stringList().split("\\s*,\\s*");

			for (String keyword : keywords){
				if (!keyword.isEmpty() && message.contains(keyword)){
					notifier.notify("Alert triggered on key: " + keyword);
				}
			}

		}
	}

	@Provides
	CustomSoundNotificationsConfig provideConfig(ConfigManager configManager){
		return configManager.getConfig(CustomSoundNotificationsConfig.class);
	}

}
