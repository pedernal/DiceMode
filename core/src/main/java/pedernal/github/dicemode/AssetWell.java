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

public class AssetWell implements Disposable {
    private AssetManager manager;
    private final AssetDescriptor<TextureAtlas> atlasDesc =
        new AssetDescriptor<>("skin/clean-crispy-ui.atlas", TextureAtlas.class);
    private final AssetDescriptor<Skin> skinDesc =
        new AssetDescriptor<>("crispySkin", Skin.class, new SkinLoader.SkinParameter("skin/clean-crispy-ui.json"));
    private FreeTypeFontLoaderParameter notoMonoParam, bigNotoMonoParam, notoConsoleParam;
    private Skin skin;

    public AssetWell() {
        manager = new AssetManager();
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        //setting freetype font parameters
        String fontFile = "NotoSansMono-Bold.ttf";
        notoMonoParam = generateFontParam(fontFile, 12, Color.DARK_GRAY, true);
        bigNotoMonoParam = generateFontParam(fontFile, 40, Color.DARK_GRAY, true);
        notoConsoleParam = generateFontParam(fontFile, 40, Color.DARK_GRAY, true);
    }

    /**Loads the assets via the {@link AssetManager}*/
    public void load() {
        //loading fonts:
        //FIXME: assets cannot be loaded, find out why
        manager.load("NotoMono", BitmapFont.class, notoMonoParam);
        manager.load("BigNotoMono", BitmapFont.class, bigNotoMonoParam);
        manager.load("NotoConsole", BitmapFont.class, notoConsoleParam);

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

    /**@return the {@link Skin} instance with all extra fonts and {@linkplain com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion}s added, lazy loading.*/
    public Skin getSkin() {
        if (skin == null) {
            skin = manager.get("crispySkin", Skin.class);
            skin.addRegions(manager.get("./skin/clean-crispy-ui.atlas", TextureAtlas.class));
            skin.add("NotoMono", manager.get("NotoMono", BitmapFont.class));
            skin.add("BigNotoMono", manager.get("BigNotoMono", BitmapFont.class));
            skin.add("NotoConsole", manager.get("NotoConsole", BitmapFont.class));
        }
        return skin;
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
