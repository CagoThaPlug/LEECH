package plugins;

import com.plugins.EthanApiPlugin;
import com.plugins.ExamplePlugin;
import com.plugins.PacketUtilsPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest {

    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(ExamplePlugin.class, EthanApiPlugin.class, PacketUtilsPlugin.class);
        RuneLite.main(args);
    }
}
