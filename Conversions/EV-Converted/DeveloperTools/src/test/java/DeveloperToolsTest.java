
import com.plugins.DevToolsPlugin;
import com.plugins.EthanApiPlugin;
import com.plugins.PacketUtilsPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DeveloperToolsTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(DevToolsPlugin.class, EthanApiPlugin.class, PacketUtilsPlugin.class);
        RuneLite.main(args);
    }
}