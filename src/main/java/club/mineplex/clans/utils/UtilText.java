package club.mineplex.clans.utils;

import club.mineplex.clans.ClansMod;
import club.mineplex.clans.utils.object.ObjectPair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilText {

    private static final Pattern colorPattern = Pattern.compile("&[^\\s]");

    public static void sendPlayerMessage(String text) {
        EntityPlayer player = ClansMod.getInstance().getMinecraft().thePlayer;
        if (player == null) return;

        StringBuilder str = new StringBuilder();
        Matcher matcher = colorPattern.matcher(text);

        List<ObjectPair<ObjectPair<EnumChatFormatting, Integer>, ObjectPair<Integer, Integer>>> indexes = new ArrayList<ObjectPair<ObjectPair<EnumChatFormatting, Integer>, ObjectPair<Integer, Integer>>>();
        while (matcher.find()) {
            final int length = matcher.group().length();

            EnumChatFormatting color;
            try {
                char found = matcher.group().substring(1).charAt(0);

                switch (found) {
                    case '9': color = EnumChatFormatting.BLUE; break;
                    case '8': color = EnumChatFormatting.DARK_GRAY; break;
                    case '7': color = EnumChatFormatting.GRAY; break;
                    case '6': color = EnumChatFormatting.GOLD; break;
                    case '5': color = EnumChatFormatting.DARK_PURPLE; break;
                    case '4': color = EnumChatFormatting.DARK_RED; break;
                    case '3': color = EnumChatFormatting.DARK_AQUA; break;
                    case '2': color = EnumChatFormatting.DARK_GREEN; break;
                    case '1': color = EnumChatFormatting.DARK_BLUE; break;
                    case 'a': color = EnumChatFormatting.GREEN; break;
                    case 'b': color = EnumChatFormatting.AQUA; break;
                    case 'c': color = EnumChatFormatting.RED; break;
                    case 'd': color = EnumChatFormatting.LIGHT_PURPLE; break;
                    case 'e': color = EnumChatFormatting.YELLOW; break;
                    case 'f': color = EnumChatFormatting.WHITE; break;
                    case 'k': color = EnumChatFormatting.OBFUSCATED; break;
                    case 'l': color = EnumChatFormatting.BOLD; break;
                    case 'm': color = EnumChatFormatting.STRIKETHROUGH; break;
                    case 'n': color = EnumChatFormatting.UNDERLINE; break;
                    case 'o': color = EnumChatFormatting.ITALIC; break;

                    default: color = EnumChatFormatting.RESET; break;
                }

            } catch (NumberFormatException e) {
                color = EnumChatFormatting.RESET;
            }

            indexes.add(new ObjectPair<ObjectPair<EnumChatFormatting, Integer>, ObjectPair<Integer, Integer>>(
                    new ObjectPair<EnumChatFormatting, Integer>(color, length),
                    new ObjectPair<Integer, Integer>(matcher.start(), matcher.end())
            ));
        }

        if (indexes.size() > 0) {

            /* Initial color is default */
            if (indexes.get(0).getValue().getKey() > 0) str.append(text, 0, indexes.get(0).getValue().getKey());

            /* Coloring */
            for (int i = 0; i < indexes.size(); i++) {
                ObjectPair<Integer, Integer> bounds = indexes.get(i).getValue();
                int nextBound = indexes.size() > i + 1 ? indexes.get(i+1).getValue().getKey() : text.length();

                String toAppend = text.substring(bounds.getKey(), nextBound).substring(indexes.get(i).getKey().getValue());
                str.append(indexes.get(i).getKey().getKey()).append(toAppend);
            }

        }

        IChatComponent component = new ChatComponentText(str.length() > 0 ? str.toString() : text);
        player.addChatMessage(component);
    }

    /**
     * @param prefix The prefix of the message.
     * @param text The content of the message.
     *
     * Example:
     * Death> Player1 killed by Player2.
     */
    public static void sendPlayerMessageWithPrefix(String prefix, String text) {
        text = text.replaceAll("&r", "&7");
        UtilText.sendPlayerMessage("&9" + prefix + "> &7" + text);
    }

}
