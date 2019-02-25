package frontend;

import java.io.IOException;

import game.engine.Entity;
import game.engine.GameWorld;
import game.engine.Sprite;
import game.engine.components.Render;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.controllers.AIController;
import game.engine.components.controllers.PlayerController;
import game.engine.components.transforms.Acceleration;
import game.engine.components.transforms.Position;
import game.engine.components.transforms.Velocity;
import game.engine.systems.CollisionSystem;
import game.engine.systems.InputSystem;
import game.engine.systems.MovementSystem;
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
		InputSystem inputSystem = new InputSystem();
		scene.setOnKeyPressed(inputSystem.getInputHandler());
		scene.setOnKeyReleased(inputSystem.getInputHandler());

		Pane root = new Pane();
		scene.setRoot(root);
		Canvas canvas = new Canvas();
		root.getChildren().add(canvas);
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());
		RenderingSystem renderingSystem = new RenderingSystem(canvas);

		CollisionSystem collisionSystem = new CollisionSystem();
		MovementSystem movementSystem = new MovementSystem();

		GameWorld gameWorld = new GameWorld(inputSystem, movementSystem, collisionSystem, renderingSystem);

		Entity player = new Entity();
		player.addPosition(new Position(0, 0));
		player.addVelocity(new Velocity(0, 0));
		player.addAcceleration(new Acceleration(0, 0));
		player.addController(new PlayerController(renderingSystem.getViewport()));
		player.addRender(new Render(Color.BLUE, 10, 10));
		player.addCollider(new CircleCollider(5));
		player.addAttributeMap(new AttributeMap());
		gameWorld.addEntity(player);

		renderingSystem.setTarget(player);

		for (int i = 0; i < 1; i++) {
			Entity enemy = new Entity();
			enemy.addPosition(new Position(-100, -100));
			enemy.addVelocity(new Velocity(0, 0));
			enemy.addAcceleration(new Acceleration(0, 0));
			enemy.addController(new AIController());
			enemy.addRender(new Render(Sprite.CHARMANDER, 10, 10));
			enemy.addCollider(new RectangleCollider(10, 10) {
				@Override
				public void handleCollision(Collider other) {
					if (other.equals(player.getCollider())) {
						player.getAttributeMap().get(AttributeType.HEALTH).add(-1);
					}
				}
			});
			gameWorld.addEntity(enemy);
		}

//		 Entity background = new Entity();
//		 background.addPosition(new Position());
//		 background.addRender(new Render(Sprite.PALLET_TOWN, -1));
//		 gameWorld.addEntity(background);
	}
}
