package com.lifeknight.forcekill.mod;

import com.lifeknight.forcekill.gui.hud.HudText;
import com.lifeknight.forcekill.variables.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lifeknight.forcekill.gui.hud.HudTextRenderer.doRender;
import static com.lifeknight.forcekill.mod.Mod.*;
import static net.minecraft.util.EnumChatFormatting.*;

@net.minecraftforge.fml.common.Mod(modid = modID, name = modName, version = modVersion, clientSideOnly = true)
public class Mod {
	public static final String modName = "ForceKill",
			modVersion = "1.0",
			modID = "forcekill";
	public static final EnumChatFormatting modColor = RED;
	public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(new LifeKnightThreadFactory());
	public static boolean onHypixel = false, openGui = false;
	public static final ArrayList<LifeKnightVariable> variables = new ArrayList<>();
	public static final LifeKnightBoolean runMod = new LifeKnightBoolean("Mod", "Main", false);
	public static final LifeKnightInteger timeToKill = new LifeKnightInteger("TimeToKill", "Settings", 60, 10, 300);
	private static com.lifeknight.forcekill.mod.Timer killTimer = new com.lifeknight.forcekill.mod.Timer();
	public static GuiScreen guiToOpen;
	public static Config config;
	public static HudText timerDisplay;

	@EventHandler
	void init(FMLInitializationEvent initEvent) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientCommandHandler.instance.registerCommand(new ModCommand());
		config = new Config();

		timerDisplay = new HudText(0, 0, YELLOW + "PAUSED") {
			@Override
			public void render() {
				if (!this.getText().equals(YELLOW + "PAUSED") && killTimer != null) {
					if (killTimer.getMinutes() > 0) {
						this.setText(GREEN + killTimer.getFormattedTime());
					} else if (killTimer.getSeconds() >= 30) {
						this.setText(YELLOW + killTimer.getFormattedTime());
					} else {
						this.setText(RED + killTimer.getFormattedTime());
					}
				}
				if (runMod.getValue() && onHypixel) {
					Minecraft.getMinecraft().fontRendererObj.drawString(this.getText(), Utils.getGameWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getText()) - 10,
							5,
							0xffffffff, this.isDropShadow());
				}
			}
		};
	}
	
	@SubscribeEvent
    void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Minecraft.getMinecraft().theWorld != null) {
                	for (String msg: Utils.queuedMessages) {
                		Utils.addChatMessage(msg);
                	}
                }
                try {
					onHypixel = Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
				} catch (Exception ignored) {}
            }
        }, 2000L);
    }

	@SubscribeEvent
	public void onChatMessageReceived(ClientChatReceivedEvent e) {
		String message = Utils.removeFormattingCodes(e.message.getUnformattedText());
		if (runMod.getValue() && onHypixel && !message.contains(":")) {
			if (message.equals("Cages opened! FIGHT!")) {
				killTimer = new com.lifeknight.forcekill.mod.Timer(timeToKill.getValue());
				killTimer.start();
				Utils.addChatMessage(YELLOW + "You have " + RED + "" + BOLD + timeToKill.getValue() +
						RESET + "" + YELLOW + " seconds to kill a player.");
				playDing();
				timerDisplay.setText(killTimer.getFormattedTime());
			} else if (message.endsWith(" " + Utils.getUsername() + ".") || message.endsWith(" " + Utils.getUsername())) {
				killTimer.setSeconds(timeToKill.getValue());
				killTimer.setMilliseconds(0);
				Utils.addChatMessage(YELLOW + "You have " + RED + "" + BOLD + timeToKill.getValue() +
						RESET + "" + YELLOW + " seconds to kill a player.");
				playDing();
			} else if (killTimer.isRunning() && (message.startsWith(Utils.getUsername() + " ") || (message.contains("(Win)") || message.contains(" Win ")))) {
				killTimer.toggle();
				Utils.addChatMessage(YELLOW + "KillTimer paused.");
				timerDisplay.setText(YELLOW + "PAUSED");
			}
		}
	}

	public static void playDing() {
		Minecraft.getMinecraft().thePlayer.playSound("lifeknight:ding", 5.0f, 1.0f);
	}

	@SubscribeEvent
	public void onWorldSwitch(WorldEvent.Load event) {
		if (runMod.getValue() && killTimer.isRunning() && onHypixel) {
			killTimer.toggle();
			Utils.addChatMessage(YELLOW + "KillTimer paused.");
			timerDisplay.setText(YELLOW + "PAUSED");
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (openGui) {
			Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
			openGui = false;
		}

		if (runMod.getValue() && killTimer.hasEnded()) {
			FMLCommonHandler.instance().exitJava(0, true);
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			doRender();
		}
	}

	public static void openGui(GuiScreen guiScreen) {
		guiToOpen = guiScreen;
		openGui = true;
	}
}