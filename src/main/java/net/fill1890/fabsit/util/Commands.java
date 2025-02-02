package net.fill1890.fabsit.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fill1890.fabsit.FabSit;
import net.fill1890.fabsit.command.GenericSitBasedCommand;
import net.fill1890.fabsit.command.PoseCommands;
import net.fill1890.fabsit.command.ReloadConfigCommand;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public abstract class Commands {
    // permission node for commands
    public static String PERMISSION_NODE = FabSit.MOD_ID + ".commands.";

    public static void register() {
        // register pose commands
        PoseCommands.stream().forEach(Commands::registerByCommand);
        // register reload config command
        CommandRegistrationCallback.EVENT.register(ReloadConfigCommand::register);
    }

    // register command for a specific pose command
    private static void registerByCommand(PoseCommands command) {
        CommandRegistrationCallback.EVENT.register((CommandDispatcher<ServerCommandSource> dispatcher, boolean ignored) ->
                dispatcher.register(literal(command.name)
                        .requires(Permissions.require(PERMISSION_NODE + command.name, command.enable))
                        .executes((CommandContext<ServerCommandSource> context) -> GenericSitBasedCommand.run(context, command.pose))));
    }
}
