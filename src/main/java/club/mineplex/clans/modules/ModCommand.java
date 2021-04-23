package club.mineplex.clans.modules;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.utils.UtilText;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public abstract class ModCommand extends CommandBase {

    private final String name, usage;
    private boolean mineplexOnly = false;

    public ModCommand(String name, String usage) {
        this.name =  name;
        this.usage = usage;
    }

    protected abstract void run(ICommandSender sender, String[] args);

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    protected void setMineplexOnly(boolean b) {
        mineplexOnly = b;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (mineplexOnly && !ClansMod.getInstance().getClientData().isMineplex) {
            UtilText.sendPlayerMessageWithPrefix("Error", "This command only works in Mineplex!");
            return;
        }

        run(sender, args);

    }

}
