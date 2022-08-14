package net.fill1890.fabsit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fill1890.fabsit.entity.Pose;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * /lay command implementation
 * <br>
 * Requires <code>fabsit.commands.lay</code> permission node, granted to all players by default
 */
public abstract class LayCommand {
    private static final Pose POSE = Pose.LAYING;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean ignored) {
        dispatcher.register(literal("lay")
                .requires(Permissions.require("fabsit.commands.lay", true))
                .executes((CommandContext<ServerCommandSource> context) -> GenericSitBasedCommand.run(context, POSE)));
    }
}
