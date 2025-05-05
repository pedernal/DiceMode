package pedernal.github.dicemode;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
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
    private final String skinPath = "skin/clean-crispy-ui.json";
    private final String skinAtlasPath = "skin/clean-crispy-ui.atlas";
    private final String fontPath = "NotoSansMono-Bold.ttf";
    public enum AssetID {
        SKIN, FONT, SKIN_ATLAS, FONT_MONO, FONT_BIG, FONT_CONSOLE
    }
    private final EnumMap<AssetID, String> assetPaths;

    private AssetManager manager;

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

    /**Loads the assets via the {@link AssetManager}*/
    public void load() {
        //loading fonts:
        //FIXME: assets cannot be loaded, find out why
        manager.load(assetPaths.get(AssetID.FONT_MONO), BitmapFont.class, notoMonoParam);
        manager.load(assetPaths.get(AssetID.FONT_BIG), BitmapFont.class, bigNotoMonoParam);
        manager.load(assetPaths.get(AssetID.FONT_CONSOLE), BitmapFont.class, notoConsoleParam);

        manager.load(atlasDesc);
        manager.load(skinDesc);
    }

    /**Helper method to generate font parameters with arbitrary size, color and bevel effect
     * @param size the size that the font will be
     * @param color the color the font will be
     * @param addBevel if true, will add a bevel like effect to the font*/
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

    public <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }
    public <T> T get(AssetID id, Class<T> type) {
        return manager.get(assetPaths.get(id), type);
    }

    public String getPath(AssetID id) {
        return assetPaths.get(id);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
