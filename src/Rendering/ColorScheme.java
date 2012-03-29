package Rendering;

import GameObjects.Hostility;
import org.lwjgl.util.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Joe Laptop
 * Date: 3/27/12
 * Time: 9:15 AM
 */
public class ColorScheme {
    static final Color defaultDefaultColor = new Color(0, 0, 0, 0);
    public Color DefaultColor;

    Map<Hostility, Map<RenderLayer, Color>> scheme;

    public ColorScheme() {
        this(defaultDefaultColor);
    }

    public ColorScheme(Color defaultColor) {
        scheme = new HashMap<Hostility, Map<RenderLayer, Color>>();
        this.DefaultColor = defaultColor;
    }

    public Color GetColor(Hostility hostility, RenderLayer layer) {
        if (!scheme.containsKey(hostility))
            CreateLayerColorMap(hostility);
        if (!scheme.get(hostility).containsKey(layer))
            return DefaultColor;
        else
            return scheme.get(hostility).get(layer);
    }

    public void SetColor(Hostility hostility, RenderLayer layer, Color color) {
        if (!scheme.containsKey(hostility))
            CreateLayerColorMap(hostility);
        scheme.get(hostility).put(layer, color);
    }

    public void LoadFromFile(String filename, String profilename) {
    }

    public void SaveToFile(String filename, String profilename) {
    }

    private void CreateLayerColorMap(Hostility hostility) {
        scheme.put(hostility, new HashMap<RenderLayer, Color>());
    }
}
