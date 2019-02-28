package frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import frontend.settings.Setting;
import game.engine.Entity;
import game.engine.GameTimer;
import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.controllers.FollowPlayerBehavior;
import game.engine.components.controllers.PlayerController;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.Sprite;
import game.engine.components.rendering.Viewport;
import game.engine.components.rendering.render.RectangleRender;
import game.engine.components.rendering.render.SpriteRender;
import game.engine.systems.BehaviorSystem;
import game.engine.systems.RenderingSystem;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

		Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Boolean fullScreen = Setting.FULL_SCREEN.get();
		if (fullScreen) {
			primaryStage.initStyle(StageStyle.UNDECORATED);
	        primaryStage.setFullScreen(true);
        } else {
            Double width = Setting.SCREEN_WIDTH.get();
            Double height = Setting.SCREEN_HEIGHT.get();
	        primaryStage.setX((bounds.getWidth() - width) / 2);
	        primaryStage.setY((bounds.getHeight() - height) / 2);
	        primaryStage.setWidth(width);
	        primaryStage.setHeight(height);
        }

		setAppScene(AppScene.GAME);

		this.primaryStage.show();
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
		player.setCollider(new RectangleCollider(1, 1, false));
		player.setAttributeMap(new AttributeMap());
		gameWorld.addEntity(player);
		
		viewport.setTarget(player);;
		
		createMap(gameWorld);

//		Entity enemy1 = new Entity();
//		enemy1.move(-30, -20);
//		enemy1.setAttributeMap(new AttributeMap());
//		enemy1.setRender(new SpriteRender(Sprite.CHARMANDER, 10d));
//		enemy1.setCollider(new RectangleCollider(10, 10, true));
//		gameWorld.addEntity(enemy1);
//
//		Entity enemy2 = new Entity();
//		enemy2.move(-10, -20);
//		enemy2.setAttributeMap(new AttributeMap());
//		enemy2.setRender(new SpriteRender(Sprite.SQUIRTLE, 10d));
//		enemy2.setCollider(new RectangleCollider(10, 10, false));
//		gameWorld.addEntity(enemy2);
//
//		Entity enemy3 = new Entity();
//		enemy3.move(10, -20);
//		enemy3.setAttributeMap(new AttributeMap());
//		enemy3.setRender(new SpriteRender(Sprite.BULBASAUR, 10d));
//		enemy3.setCollider(new CircleCollider(5, true));
//		gameWorld.addEntity(enemy3);
//
//		Entity enemy4 = new Entity();
//		enemy4.move(30, -20);
//		enemy4.setAttributeMap(new AttributeMap());
//		enemy4.setRender(new SpriteRender(Sprite.PIKACHU, 10d));
//		enemy4.setCollider(new CircleCollider(5, false));
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
