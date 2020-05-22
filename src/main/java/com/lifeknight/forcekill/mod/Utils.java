package com.lifeknight.forcekill.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.lifeknight.forcekill.mod.Mod.modColor;
import static com.lifeknight.forcekill.mod.Mod.modName;

public class Utils {
    public static final ArrayList<String> queuedMessages = new ArrayList<>();
    public static int height = 0;
    public static int width = 0;

    public static void addChatMessage(String msg) {
        if (Minecraft.getMinecraft().theWorld != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(modColor + "" + EnumChatFormatting.BOLD + modName + " > " + EnumChatFormatting.RESET + msg));
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    addChatMessage(msg);
                }
            }, 100L);
        }
    }

    public static void queueChatMessageForConnection(String msg) {
        queuedMessages.add(msg);
    }

    public static int scale(int toScale) {
        switch (Minecraft.getMinecraft().gameSettings.guiScale) {
            case 1: {
                return toScale * 2;
            }
            case 2: {
                return toScale;
            }
            default: {
                return (int) (toScale / 1.5);
            }
        }
    }

    public static int get2ndPanelCenter() {
        return getScaledHeight(300) + (width - getScaledWidth(300)) / 2;
    }

    public static int getSupposedWidth() {
        int i = Minecraft.getMinecraft().gameSettings.guiScale == 0 ? 1 : Minecraft.getMinecraft().gameSettings.guiScale;
        return 1920 / i;
    }

    public static int getSupposedHeight() {
        int i = Minecraft.getMinecraft().gameSettings.guiScale == 0 ? 1 : Minecraft.getMinecraft().gameSettings.guiScale;
        return 1080 / i;
    }

    public static int getScaledWidth(int widthIn) {
        return scale((int) (widthIn * ((double) width / getSupposedWidth())));
    }

    public static int getScaledHeight(int heightIn) {
        return scale((int) (heightIn * ((double) height / getSupposedHeight())));
    }

    public static int getGameWidth() {
        int i = Minecraft.getMinecraft().gameSettings.guiScale == 0 ? 1 : Minecraft.getMinecraft().gameSettings.guiScale;
        return Minecraft.getMinecraft().displayWidth / i;
    }

    public static String removeAll(String msg, String rmv) {
        msg = msg.replaceAll(rmv, "");
        return msg;
    }

    public static String removeFormattingCodes(String input) {
        String formattingSymbol = "";
        formattingSymbol += '\u00A7';

        input = removeAll(input, formattingSymbol + "0");
        input = removeAll(input, formattingSymbol + "1");
        input = removeAll(input, formattingSymbol + "2");
        input = removeAll(input, formattingSymbol + "3");
        input = removeAll(input, formattingSymbol + "4");
        input = removeAll(input, formattingSymbol + "5");
        input = removeAll(input, formattingSymbol + "6");
        input = removeAll(input, formattingSymbol + "7");
        input = removeAll(input, formattingSymbol + "8");
        input = removeAll(input, formattingSymbol + "9");
        input = removeAll(input, formattingSymbol + "a");
        input = removeAll(input, formattingSymbol + "b");
        input = removeAll(input, formattingSymbol + "c");
        input = removeAll(input, formattingSymbol + "d");
        input = removeAll(input, formattingSymbol + "e");
        input = removeAll(input, formattingSymbol + "f");
        input = removeAll(input, formattingSymbol + "k");
        input = removeAll(input, formattingSymbol + "l");
        input = removeAll(input, formattingSymbol + "m");
        input = removeAll(input, formattingSymbol + "n");
        input = removeAll(input, formattingSymbol + "o");
        input = removeAll(input, formattingSymbol + "r");

        return input;
    }

    public static String getUsername() {
        return Minecraft.getMinecraft().thePlayer.getName();
    }
}
