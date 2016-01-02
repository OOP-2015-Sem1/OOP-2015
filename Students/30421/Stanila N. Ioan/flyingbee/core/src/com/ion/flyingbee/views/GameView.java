package com.ion.flyingbee.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ion.flyingbee.controllers.GameController;
import com.ion.flyingbee.factories.EntityFactory;
import com.ion.flyingbee.models.Bee;
import com.ion.flyingbee.models.Entity;
import com.ion.flyingbee.models.EntityId;
import com.ion.flyingbee.utils.CollisionAnalyzer;
import com.ion.flyingbee.utils.EntityGenerator;
import com.ion.flyingbee.utils.EntityMovement;
import com.ion.flyingbee.utils.PreferencesHandler;

import java.util.ArrayList;

/**
 * Created by Ion on 08.11.2015.
 */
public class GameView extends AbstractView {

    public enum GameState {
        RUNNING, GAME_OVER
    }

    private GameState currentState;

    private int points = 0;

    private static final float REFRESH_TIME = 1/40f;
    private static final float MIN_TIME_OF_GENERATION = .65f;
    private static final float MAX_TIME_OF_GENERATION = 2f;
    private static final float SPEED_RATIO_LOGARITHM_BASE = 1.5f;
    private static final int FONT_SIZE = 128;


    /**
     * The speed ratio at which the entities move towards the bee. It increases logarithmically with
     * time. The base of the logarithm is SPEED_RATIO_LOGARITHM_BASE.
     */
    private float speedRatio = 1f;

    private float timeOfLastMovement = 0f;
    private float timeOfLastGeneration = 0f;
    private float timePassed = 0f;
    private float gameOverWaitingTime = 0f;

    private GameController gameController;
    private MainMenuView mainMenuView;
    private BitmapFont font;
    private Vector2 pointsPosition;
    private ParallaxBackground background;
    private AnimatedButton gameOverAnimation;
    private TextureRegion lifeHeart;

    private Bee bee;
    private ArrayList<Entity> entities;

    private Sound beeTouchesWasp;
    private Sound beeTouchesFlower;

    public GameView(Game game, MainMenuView mainMenuView) {
        super(game);
        this.mainMenuView = mainMenuView;
        gameController = new GameController(game, this);

        entities = new ArrayList<Entity>();
        bee = (Bee) EntityFactory.getEntity(EntityId.BEE_ID);
        bee.setInitialPosition(Gdx.graphics.getWidth() / 9,
                Gdx.graphics.getHeight() / 2 - bee.getKeyFrame(0).getRegionHeight() / 2);
    }

    @Override
    public void show() {
        super.show();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/CFGreenMonster-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.color = Color.GOLD;
        font = generator.generateFont(parameter);
        generator.dispose();

        TextureRegion firstBackgroundLayer = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/clearbluesky.png")));
        TextureRegion secondBackgroundLayer = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/sun.png")));
        TextureRegion thirdBackgroundLayer = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/grass.png")));
        background = new ParallaxBackground(new ParallaxLayer[] {
                new ParallaxLayer(firstBackgroundLayer, new Vector2(0f, 0), new Vector2(0, 0)),
                new ParallaxLayer(secondBackgroundLayer, new Vector2(.05f, 0), new Vector2(0, 0)),
                new ParallaxLayer(thirdBackgroundLayer, new Vector2(1.7f, 0), new Vector2(0, 0))
        }, 1920, 1080, new Vector2(100, 0));

        pointsPosition = new Vector2(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/9,
                Gdx.graphics.getHeight() - 10);
        currentState = GameState.RUNNING;
        gameOverAnimation = new AnimatedButton("buttons/gameovericon.atlas");
        lifeHeart = new TextureRegion((new TextureAtlas("heart.atlas")).findRegion("bigheart"));
        beeTouchesFlower = Gdx.audio.newSound(Gdx.files.internal("sounds/flower_touch.ogg"));
        beeTouchesWasp = Gdx.audio.newSound(Gdx.files.internal("sounds/wasp_touch.ogg"));
        Gdx.input.setInputProcessor(gameController);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        for (Entity e : entities) {
            e.dispose();
        }
        beeTouchesFlower.dispose();
        beeTouchesWasp.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        background.render(delta);

        checkGameState();

        switch (currentState) {
            case RUNNING:
                gameStateRunning(delta);
                break;
            case GAME_OVER:
                gameStateGameOver(delta);
                break;
            default:
                break;
        }
    }

    private void checkGameState() {
        if (bee.getLives() == 0) {
            currentState = GameState.GAME_OVER;
        }
    }

    private void gameStateRunning(float delta) {
        computeSpeedRatio();
        moveAllEntities();
        generateNewEntities();
        checkCollisions();
        batch.begin();
        font.draw(batch, String.valueOf(points), pointsPosition.x, pointsPosition.y);
        batch.draw(bee.getKeyFrame(timePassed), bee.getCoordinateX(), bee.getCoordinateY());
        for (Entity e : entities) {
            batch.draw(e.getKeyFrame(timePassed), e.getCoordinateX(), e.getCoordinateY());
        }
        displayLifeHearts();
        Gdx.app.log("fps: ", String.valueOf(delta));
        timePassed += Gdx.graphics.getDeltaTime();
        batch.end();
    }

    private void gameStateGameOver(float delta) {
        gameOverWaitingTime += delta;
        batch.begin();

        font.setColor(Color.RED);
        font.draw(batch, String.valueOf(points), pointsPosition.x, pointsPosition.y);
        batch.draw(gameOverAnimation.getKeyFrame(timePassed),
                Gdx.graphics.getWidth() / 2 - gameOverAnimation.getDimensions().getRegionWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOverAnimation.getDimensions().getRegionHeight() / 2);

        timePassed += Gdx.graphics.getDeltaTime();
        batch.end();
        if (gameOverWaitingTime > 2f) {
            PreferencesHandler.addHighScore(points);
            game.setScreen(mainMenuView);
            dispose();
        }
    }

    private boolean readyForMovement() {
        return (timePassed - timeOfLastMovement) > REFRESH_TIME;
    }

    private boolean readyForGeneration() {
        float refreshTime = MathUtils.random(MIN_TIME_OF_GENERATION, MAX_TIME_OF_GENERATION);
        return (timePassed - timeOfLastGeneration) > refreshTime;
    }

    private void computeSpeedRatio() {
        speedRatio = MathUtils.log(SPEED_RATIO_LOGARITHM_BASE, timePassed);
        Gdx.app.log("speed ratio: ", String.valueOf(speedRatio));
    }

    private void moveAllEntities() {
        if (readyForMovement()) {
            EntityMovement.moveAll(entities, speedRatio);
            timeOfLastMovement = timePassed;
        }
    }

    private void generateNewEntities() {
        if (readyForGeneration()) {
            Entity newEntity = EntityGenerator.generateNewEntity();
            if (CollisionAnalyzer.collide(newEntity, entities) == -1) {
                entities.add(newEntity);
            }
            timeOfLastGeneration = timePassed;
        }
    }

    private void checkCollisions() {
        int indexOfCollidingEntity = -1;
        // check collisions with the bee
        if ((indexOfCollidingEntity = CollisionAnalyzer.collide(bee, entities)) != -1) {
            updatePoints(indexOfCollidingEntity);
            entities.get(indexOfCollidingEntity).dispose();
            entities.remove(indexOfCollidingEntity);
            playSound(indexOfCollidingEntity);
        }
    }

    private void updatePoints(int indexOfCollidingEntity) {
        switch (entities.get(indexOfCollidingEntity).getId()) {
            case EntityId.WASP_ID:
                bee.subtractLife();
                break;
            case EntityId.FLOWER_ID:
                points += 1;
                break;
            default:
                break;
        }
    }

    private void playSound(int indexOfCollidingEntity) {
        if (PreferencesHandler.isSoundEnabled()) {
            switch (indexOfCollidingEntity) {
                case EntityId.WASP_ID:
                    beeTouchesWasp.play();
                    break;
                case EntityId.FLOWER_ID:
                    beeTouchesFlower.play();
                    break;
                default:
                    break;
            }
        }
    }

    private void displayLifeHearts() {
        for (int i = 0; i < bee.getLives(); i++) {
            batch.draw(lifeHeart,
                    2*Gdx.graphics.getWidth()/5 + i*lifeHeart.getRegionWidth() + 20,
                    Gdx.graphics.getHeight() - lifeHeart.getRegionHeight() - 10);
        }
    }

    /**
     * Get the previous view.
     * @return
     */
    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    /**
     * Get all available entities at a given time.
     * @return
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Bee getBee() {
        return this.bee;
    }
}
