package frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import game.engine.Entity;
import game.engine.GameTimer;
import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.controllers.AIController;
import game.engine.components.controllers.PlayerController;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.Sprite;
import game.engine.components.rendering.Viewport;
import game.engine.systems.InputSystem;
import game.engine.systems.RenderingSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

		setAppScene(AppScene.LOGIN);

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

		InputSystem inputSystem = gameWorld.getInputSystem();
		scene.setOnKeyPressed(inputSystem.getInputHandler());
		scene.setOnKeyReleased(inputSystem.getInputHandler());

		RenderingSystem renderingSystem = gameWorld.getRenderingSystem();
		Viewport viewport = renderingSystem.getViewport();
		renderingSystem.setCanvas(canvas);
		
		Entity player = new Entity();
		
		player.setBehavior(new PlayerController());
		player.setRender(new Render(Sprite.PALLET_TOWN, 10, 10));
		player.setCollider(new RectangleCollider(10, 10, false));
		player.setAttributeMap(new AttributeMap());
		gameWorld.addEntity(player);
		
		viewport.setTarget(player);;

		Entity enemy1 = new Entity();
		enemy1.move(-30, -20);
		enemy1.setAttributeMap(new AttributeMap());
		enemy1.setRender(new Render(Sprite.CHARMANDER, 10, 10));
		enemy1.setCollider(new RectangleCollider(10, 10, true));
		gameWorld.addEntity(enemy1);

		Entity enemy2 = new Entity();
		enemy2.move(-10, -20);
		enemy2.setAttributeMap(new AttributeMap());
		enemy2.setRender(new Render(Sprite.SQUIRTLE, 10, 10));
		enemy2.setCollider(new RectangleCollider(10, 10, false));
		gameWorld.addEntity(enemy2);

		Entity enemy3 = new Entity();
		enemy3.move(10, -20);
		enemy3.setAttributeMap(new AttributeMap());
		enemy3.setRender(new Render(Sprite.BULBASAUR, 10, 10));
		enemy3.setCollider(new CircleCollider(5, true));
		gameWorld.addEntity(enemy3);

		Entity enemy4 = new Entity();
		enemy4.move(30, -20);
		enemy4.setAttributeMap(new AttributeMap());
		enemy4.setRender(new Render(Sprite.PIKACHU, 10, 10));
		enemy4.setCollider(new CircleCollider(5, false));
		gameWorld.addEntity(enemy4);
				
		// int numEnemies = 1;
		// for (int i = 0; i < numEnemies; i++) {
		// Entity enemy = new Entity();
		// enemy.setBehavior(new AIController());
		// enemy.setAttributeMap(new AttributeMap());
		// enemy.setRender(new Render(Sprite.CHARMANDER, 10, 10));
		// enemy.setCollider(new RectangleCollider(10, 10) {
		//
		// private Map<Collider, Long> recentCollisions = new HashMap<Collider, Long>();
		// private double cooldown = 0.5;
		//
		// @Override
		// public void handleCollision(Collider other) {
		// super.handleCollision(other);
		// long nanoTime = System.nanoTime();
		// if(recentCollisions.containsKey(other)) {
		// Long lastTime = recentCollisions.get(other);
		// boolean isReady = (nanoTime - lastTime) / GameTimer.NANO_CONVERSION >
		// cooldown;
		// if (!isReady) {
		// return;
		// }
		// }
		//
		// if (other.equals(player.getCollider())) {
		// player.getAttributeMap().get(AttributeType.HEALTH).add(-10);
		// recentCollisions.put(other, nanoTime);
		// }
		// }
		// });
		// gameWorld.addEntity(enemy);
		// }
		Entity background = new Entity();
		Render render = new Render(Sprite.PALLET_TOWN);
		render.setzIndex(-1);
		background.setRender(render);
	}
}
