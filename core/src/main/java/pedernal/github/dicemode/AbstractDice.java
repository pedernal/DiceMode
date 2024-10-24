package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class AbstractDice extends Actor implements Disposable {
    private int total;
    private Resterizer resterizer;

    public AbstractDice(float x, float y, float width, float height) {
        this.total = 0;
        super.setX(x);
        super.setY(y);
        super.setWidth(width);
        super.setHeight(height);
        this.resterizer = new Resterizer(Math.round(width), Math.round(height));
    }

    public void roll(Batch batch) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Resterizer getResterizer() {
        return this.resterizer;
    }
}

class Resterizer {
    private FrameBuffer frameBuffer;

    public Resterizer(int width, int height) {
        this.frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    public TextureRegion resterize(Runnable lambda) {
        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lambda.run();

        frameBuffer.end();

        return generateTextureRegion();
    }

    private TextureRegion generateTextureRegion() {
        TextureRegion toReturn = new TextureRegion(frameBuffer.getColorBufferTexture());
        toReturn.flip(false, true);
        return toReturn;
    }
}
