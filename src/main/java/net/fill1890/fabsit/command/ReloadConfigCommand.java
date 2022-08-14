package net.fill1890.fabsit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fill1890.fabsit.FabSit;
import net.fill1890.fabsit.config.ConfigManager;
import net.fill1890.fabsit.error.LoadConfigException;
import net.fill1890.fabsit.util.Messages;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public abstract class ReloadConfigCommand {
    // reload config command
    // `/fabsit reload`
    // requires node fabsit.reload or op level 2
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean ignored) {
        dispatcher.register(literal(FabSit.MOD_ID)
                .then(literal("reload")
                        .requires(Permissions.require(FabSit.MOD_ID + ".reload", 2))
                        .executes(ReloadConfigCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // attempt to reload config
        try {
            ConfigManager.loadConfig();
        } catch (LoadConfigException e) {
            // failure message
            e.printStackTrace();
            return -1;
        }
        // success message
        context.getSource().sendFeedback(Messages.configLoadSuccess(context.getSource().getPlayer()), true);
        return 0;
    }
}
