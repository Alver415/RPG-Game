package frontend;

import java.io.IOException;

import game.engine.Entity;
import game.engine.GameWorld;
import game.engine.Sprite;
import game.engine.components.Position;
import game.engine.components.Render;
import game.engine.components.RigidBody;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.controllers.AIController;
import game.engine.components.controllers.Controller;
import game.engine.components.controllers.PlayerController;
import game.engine.systems.InputSystem;
import game.engine.systems.PhysicsSystem;
import game.engine.systems.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameApplication extends Application {

	public static void main(String...strings) {
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
		switch(appScene) {
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
		Renderer renderer = new Renderer(canvas);

		PhysicsSystem physics = new PhysicsSystem();

		GameWorld gameWorld = GameWorld.get();
		gameWorld.addSystem(inputSystem);
		gameWorld.addSystem(renderer);
		gameWorld.addSystem(physics);

		Entity player = new Entity();
		player.setPosition(new Position());
		player.setController(new PlayerController());
		player.setRender(new Render(Color.BLUE, 10, 10));
		player.setRigidBody(new RigidBody(1));
		player.setCollider(new CircleCollider(5));
		gameWorld.addEntity(player);
		
		renderer.setTarget(player);

		for (int i = 0; i < 1; i++) {
		Entity enemy = new Entity();
			enemy.setPosition(new Position());
			enemy.setController(new AIController());
			enemy.setRender(new Render(Sprite.CHARMANDER, 10, 10));
			enemy.setRigidBody(new RigidBody(1));
			enemy.setCollider(new RectangleCollider(10, 10));
			gameWorld.addEntity(enemy);
		}
		
//		Entity background = new Entity();
//		background.setPosition(new Position());
//		background.setRender(new Render(Sprite.PALLET_TOWN, -1));
//		gameWorld.addEntity(background);
	}
}
