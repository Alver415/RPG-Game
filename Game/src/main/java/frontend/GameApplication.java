package frontend;

import java.io.IOException;

import frontend.settings.Setting;
import game.engine.Entity;
import game.engine.GameWorld;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.controllers.FollowPlayerBehavior;
import game.engine.components.controllers.PlayerController;
import game.engine.components.rendering.AnimatedSprite;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.Sprite;
import game.engine.components.rendering.Viewport;
import game.engine.components.rendering.render.RectangleRender;
import game.engine.components.rendering.render.SpriteRender;
import game.engine.systems.RenderingSystem;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameApplication extends Application {

	public static void main(String... strings) {
		GameApplication.launch(strings);
	}

	private Stage primaryStage;

	public GameApplication() {
		Facade.setGameApplication(this);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setOnCloseRequest(e -> System.exit(0));

		setupStage();

		setAppScene(AppScene.GAME);

		this.primaryStage.show();
	}

	private void setupStage() {
		Integer screenIndex = Setting.SCREEN.get();
		screenIndex -= 1;
		Screen screen;
		if (screenIndex > -1 && screenIndex < Screen.getScreens().size()) {
			screen = Screen.getScreens().get(screenIndex);
		} else {
			screen = Screen.getPrimary();
		}
        Rectangle2D bounds = screen.getVisualBounds();

        Boolean fullScreen = Setting.FULL_SCREEN.get();
		if (fullScreen) {
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth());
			primaryStage.setHeight(bounds.getHeight());
        } else {
            Double width = Setting.SCREEN_WIDTH.get();
            Double height = Setting.SCREEN_HEIGHT.get();
			primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - width) / 2);
			primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - height) / 2);
	        primaryStage.setWidth(width);
	        primaryStage.setHeight(height);
        }
	}

	public void setAppScene(AppScene appScene) {
		Scene scene = appScene.getScene();
		switch (appScene) {
		case GAME:
			setupGame(scene);
			break;
		case LOGIN:
			break;
		case SETTINGS:
			break;
		default:
			break;
		}

		primaryStage.setTitle(appScene.getTitle());
		primaryStage.setScene(scene);
	}

	private void setupGame(Scene scene) {
		GameWorld gameWorld = GameWorld.INSTANCE;
		
		Pane root = new Pane();
		scene.setRoot(root);
		Canvas canvas = new Canvas();
		root.getChildren().add(canvas);
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());

		scene.setOnKeyPressed(gameWorld.getInputHandler());
		scene.setOnKeyReleased(gameWorld.getInputHandler());

		RenderingSystem renderingSystem = gameWorld.getRenderingSystem();
		Viewport viewport = renderingSystem.getViewport();
		renderingSystem.setCanvas(canvas);
		
		Entity player = new Entity();
		player.setBehavior(new PlayerController());
		player.setRender(new SpriteRender(Sprite.TRAINER, 1d));
		player.setCollider(new RectangleCollider(1, 1, true));
		player.setAttributeMap(new AttributeMap());
		gameWorld.addEntity(player);
		
		viewport.setTarget(player);;
		
		createMap(gameWorld);

		Entity enemy1 = new Entity();
		enemy1.move(-3, -2);
		enemy1.setBehavior(new FollowPlayerBehavior());
		enemy1.setAttributeMap(new AttributeMap());
		enemy1.setRender(new SpriteRender(AnimatedSprite.TEST, 1d));
		enemy1.setCollider(new RectangleCollider(1, 1, true));
		gameWorld.addEntity(enemy1);
//
//		Entity enemy2 = new Entity();
//		enemy2.move(-1, -2);
//		enemy2.setBehavior(new FollowPlayerBehavior());
//		enemy2.setAttributeMap(new AttributeMap());
//		enemy2.setRender(new SpriteRender(Sprite.SQUIRTLE, 1d));
//		enemy2.setCollider(new RectangleCollider(1, 1, false));
//		gameWorld.addEntity(enemy2);
//
//		Entity enemy3 = new Entity();
//		enemy3.move(1, -2);
//		enemy3.setBehavior(new FollowPlayerBehavior());
//		enemy3.setAttributeMap(new AttributeMap());
//		enemy3.setRender(new SpriteRender(Sprite.BULBASAUR, 1d));
//		enemy3.setCollider(new CircleCollider(0.5, true));
//		gameWorld.addEntity(enemy3);
//
//		Entity enemy4 = new Entity();
//		enemy4.move(3, -2);
//		enemy4.setBehavior(new FollowPlayerBehavior());
//		enemy4.setAttributeMap(new AttributeMap());
//		enemy4.setRender(new SpriteRender(Sprite.PIKACHU, 1d));
//		enemy4.setCollider(new CircleCollider(0.5, false));
//		gameWorld.addEntity(enemy4);
	}
	
	private void createMap(GameWorld gameWorld) {
		Entity background = new Entity();
		Render render = new SpriteRender(Sprite.PALLET_TOWN, 20d, 18d);
		render.setzIndex(-1);
		background.setRender(render);

		//Walls
		createWorldObject(gameWorld, 0, 0, 1, 17);
		createWorldObject(gameWorld, 1, 0, 1, 1);
		createWorldObject(gameWorld, 1, 16, 9, 1);
		createWorldObject(gameWorld, 3, 17, 1, 1);
		createWorldObject(gameWorld, 12, 16, 7, 1);
		createWorldObject(gameWorld, 9, 17, 1, 1);
		createWorldObject(gameWorld, 12, 17, 1, 1);
		createWorldObject(gameWorld, 18, 17, 1, 1);
		createWorldObject(gameWorld, 19, 0, 1, 17);
		createWorldObject(gameWorld, 8, 0, 11, 1);

		//Water
		createWorldObject(gameWorld, 4, 0, 4, 4);
		
		//Lab
		createWorldObject(gameWorld, 10, 6, 6, 4);

		//House1
		createWorldObject(gameWorld, 4, 12, 4, 3);
		createWorldObject(gameWorld, 3, 12, 1, 1);
		
		//House2
		createWorldObject(gameWorld, 12, 12, 4, 3);
		createWorldObject(gameWorld, 11, 12, 1, 1);
		
		//Fences
		createWorldObject(gameWorld, 10, 4, 6, 1);
		createWorldObject(gameWorld, 4, 8, 4, 1);
		
		
	}
	
	private void createWorldObject(GameWorld gameWorld, double x, double y, double w, double h) {
		Color color = Color.rgb(0, 0, 0, 0);
		Entity right = new Entity();
		right.move(-10 + w / 2, -9 + h / 2);
		right.move(x, y);
		right.setRender(new RectangleRender(color, w, h));
		right.setCollider(new RectangleCollider(w, h, true));
		gameWorld.addEntity(right);
	}
}
