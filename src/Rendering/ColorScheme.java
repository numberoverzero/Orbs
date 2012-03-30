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
// ------------------------------ FIELDS ------------------------------

    static final Color defaultDefaultColor = new Color(0, 0, 0, 0);
    public Color DefaultColor;

    Map<Hostility, Map<RenderLayer, Color>> scheme;

// --------------------------- CONSTRUCTORS ---------------------------

    public ColorScheme() {
        this(defaultDefaultColor);
    }

    public ColorScheme(Color defaultColor) {
        scheme = new HashMap<Hostility, Map<RenderLayer, Color>>();
        this.DefaultColor = defaultColor;
    }

// -------------------------- OTHER METHODS --------------------------

    public Color GetColor(Hostility hostility, RenderLayer layer) {
        if (!scheme.containsKey(hostility))
            CreateLayerColorMap(hostility);
        if (!scheme.get(hostility).containsKey(layer))
            return DefaultColor;
        else
            return scheme.get(hostility).get(layer);
    }

    private void CreateLayerColorMap(Hostility hostility) {
        scheme.put(hostility, new HashMap<RenderLayer, Color>());
    }

    public void LoadFromFile(String filename, String profilename) {
    }

    public void SaveToFile(String filename, String profilename) {
    }

    public void SetColor(Hostility hostility, RenderLayer layer, Color color) {
        if (!scheme.containsKey(hostility))
            CreateLayerColorMap(hostility);
        scheme.get(hostility).put(layer, color);
    }
}
