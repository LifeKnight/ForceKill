package com.lifeknight.forcekill.mod;

import com.lifeknight.forcekill.gui.LifeKnightGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;


import static com.lifeknight.forcekill.mod.Mod.*;

public class ModCommand extends CommandBase {
	private final List<String> aliases = Collections.singletonList("fk");

	public String getCommandName() {
		return modID;
	}

	public String getCommandUsage(ICommandSender arg0) {
		return modID;
	}

	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return true;
	}

	public List<String> getCommandAliases() {
		return aliases;
	}

	public boolean isUsernameIndex(String[] arg0, int arg1) {
		return false;
	}

	public int compareTo(ICommand o) {
		return 0;
	}

	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		openGui(new LifeKnightGui(modName, variables));
	}
}
