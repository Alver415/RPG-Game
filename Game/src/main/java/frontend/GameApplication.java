package frontend;

import java.io.IOException;

import frontend.settings.Setting;
import game.engine.GameObject;
import game.engine.GameWorld;
import game.engine.PlayerControlledBehavior;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.behaviors.FollowPlayerBehavior;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.rendering.AnimatedSprite;
import game.engine.components.rendering.Camera;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.StaticSprite;
import game.engine.components.rendering.render.ColliderRender;
import game.engine.components.rendering.render.HealthBarRender;
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

		scene.setOnMousePressed(gameWorld.getInputHandler());
		scene.setOnKeyPressed(gameWorld.getInputHandler());
		scene.setOnKeyReleased(gameWorld.getInputHandler());

		RenderingSystem renderingSystem = gameWorld.getRenderingSystem();
		renderingSystem.setCanvas(canvas);
		
		GameObject player = new GameObject();
		player.addComponent(new PlayerControlledBehavior());
		player.addComponent(new SpriteRender(AnimatedSprite.DOWN, 1d));
		player.addComponent(new HealthBarRender());
		player.addComponent(new ColliderRender());
		player.addComponent(new CircleCollider(0.5d, true));
		player.addComponent(new AttributeMap());
		player.addComponent(new Camera());
		gameWorld.addChild(player);
		
		createMap(gameWorld);
//		 spawnEnemies(gameWorld);
	}

	private void spawnEnemies(GameWorld gameWorld) {
		GameObject enemy1 = new GameObject();
		enemy1.move(-3, -2);
		enemy1.addComponent(new FollowPlayerBehavior());
		enemy1.addComponent(new AttributeMap());
		enemy1.addComponent(new SpriteRender(StaticSprite.BULBASAUR, 1d));
		enemy1.addComponent(new RectangleCollider(1, 1, true));
		gameWorld.addChild(enemy1);

		GameObject enemy2 = new GameObject();
		enemy2.move(-1, -2);
		enemy2.addComponent(new FollowPlayerBehavior());
		enemy2.addComponent(new AttributeMap());
		enemy2.addComponent(new SpriteRender(StaticSprite.SQUIRTLE, 1d));
		enemy2.addComponent(new RectangleCollider(1, 1, false));
		gameWorld.addChild(enemy2);

		GameObject enemy3 = new GameObject();
		enemy3.move(1, -2);
		enemy3.addComponent(new FollowPlayerBehavior());
		enemy3.addComponent(new AttributeMap());
		enemy3.addComponent(new SpriteRender(StaticSprite.CHARMANDER, 1d));
		enemy3.addComponent(new CircleCollider(0.5, true));
		gameWorld.addChild(enemy3);

		GameObject enemy4 = new GameObject();
		enemy4.move(3, -2);
		enemy4.addComponent(new FollowPlayerBehavior());
		enemy4.addComponent(new AttributeMap());
		enemy4.addComponent(new SpriteRender(StaticSprite.PIKACHU, 1d));
		enemy4.addComponent(new CircleCollider(0.5, false));
		gameWorld.addChild(enemy4);
	}

	private void createMap(GameWorld gameWorld) {
		GameObject background = new GameObject();
		Render render = new SpriteRender(StaticSprite.PALLET_TOWN, 20d, 18d);
		render.setzIndex(-1);
		background.addComponent(render);
		gameWorld.addChild(background);

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
		Color color = Color.rgb(0, 0, 50, 0.5d);
		GameObject right = new GameObject();
		right.move(-10 + w / 2, -9 + h / 2);
		right.move(x, y);
		right.addComponent(new RectangleRender(color, w, h));
		right.addComponent(new RectangleCollider(w, h, true));
		gameWorld.addChild(right);
	}
}
