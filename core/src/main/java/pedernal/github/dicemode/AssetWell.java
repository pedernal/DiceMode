/**Class implements {@link com.badlogic.gdx.assets.AssetManager} to aid the load of assets  for the application.
 * Handles Everything to make atlases, skin, fonts, etc. and provies methods for UI elements to have access to them.
 * Implements a {@link java.util.EnumMap} for an ID solution for a consistent usage of file paths as names for the assets.
 * Implements {@link com.badlogic.gdx.utils.Disposable}.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import java.util.EnumMap;

public class AssetWell implements Disposable {
    //relevant file paths
    private final String skinPath = "skin/clean-crispy-ui.json";
    private final String skinAtlasPath = "skin/clean-crispy-ui.atlas";
    private final String fontPath = "NotoSansMono-Bold.ttf";
    //Enum to correspond to file paths
    public enum AssetID {
        SKIN, FONT, SKIN_ATLAS, FONT_MONO, FONT_BIG, FONT_CONSOLE
    }
    private final EnumMap<AssetID, String> assetPaths; // Dictionary to map enum to file paths and file names for assets

    private AssetManager manager;

    //Asset descriptions for the loader
    private final AssetDescriptor<TextureAtlas> atlasDesc =
        new AssetDescriptor<>(skinAtlasPath, TextureAtlas.class);
    private final AssetDescriptor<Skin> skinDesc =
        new AssetDescriptor<>(skinPath, Skin.class, new SkinLoader.SkinParameter(skinAtlasPath));

    private FreeTypeFontLoaderParameter notoMonoParam, bigNotoMonoParam, notoConsoleParam;

    public AssetWell() {
        manager = new AssetManager();
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, new FreetypeFontLoader(resolver));
        assetPaths = new EnumMap<AssetID, String>(AssetID.class);
        //mapping Enum to file names/paths
        assetPaths.put(AssetID.SKIN, skinPath);
        assetPaths.put(AssetID.SKIN_ATLAS, skinAtlasPath);
        assetPaths.put(AssetID.FONT, fontPath);
        assetPaths.put(AssetID.FONT_MONO, "NotoMono");
        assetPaths.put(AssetID.FONT_BIG, "BigNotoMono");
        assetPaths.put(AssetID.FONT_CONSOLE, "NotoConsole");

        //setting freetype font parameters
        notoMonoParam = generateFontParam(fontPath, 12, Color.DARK_GRAY, true);
        bigNotoMonoParam = generateFontParam(fontPath, 40, Color.DARK_GRAY, true);
        notoConsoleParam = generateFontParam(fontPath, 10, Color.WHITE, false);
    }

    /**Loads the assets via the {@link AssetManager}.*/
    public void load() {
        //loading fonts:
        manager.load(assetPaths.get(AssetID.FONT_MONO), BitmapFont.class, notoMonoParam);
        manager.load(assetPaths.get(AssetID.FONT_BIG), BitmapFont.class, bigNotoMonoParam);
        manager.load(assetPaths.get(AssetID.FONT_CONSOLE), BitmapFont.class, notoConsoleParam);
        //loading assets
        manager.load(atlasDesc);
        manager.load(skinDesc);
    }

    /**Helper method to generate font parameters with arbitrary size, color and bevel effect.
     * @param fontFile file path of the .ttf.
     * @param size the size that the font will be.
     * @param color the color the font will be.
     * @param addBevel if true, will add a bevel like effect to the font.*/
    private FreeTypeFontLoaderParameter generateFontParam(String fontFile, int size, Color color, boolean addBevel) {
        FreeTypeFontLoaderParameter parameter = new FreeTypeFontLoaderParameter();
        parameter.fontFileName = fontFile;
        parameter.fontParameters.size = size;
        parameter.fontParameters.color = color;
        if (addBevel) {
            parameter.fontParameters.shadowColor = Color.WHITE;
            parameter.fontParameters.shadowOffsetY = 2;
        }
        return parameter;
    }

    /**@return the {@link AssetManager} instance.*/
    public AssetManager getManager() { return manager; }

    /**@param type the {@link Class} type of the asset object to be returned.
     * @param id {@link AssetID} enum type that corresponds to the file path/name.
     * @return asset object of given {@param type}.*/
    public <T> T get(AssetID id, Class<T> type) {
        return manager.get(assetPaths.get(id), type);
    }
    /*public <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }*/

    /**@param id {@link AssetID} enum value.
     * @return the file name/path that corresponds to {@link AssetID} value.*/
    public String getPath(AssetID id) {
        return assetPaths.get(id);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
