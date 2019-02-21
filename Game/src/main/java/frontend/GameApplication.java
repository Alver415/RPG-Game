package frontend;

import java.io.IOException;

import game.engine.Entity;
import game.engine.GameWorld;
import game.engine.components.AIController;
import game.engine.components.Controller;
import game.engine.components.Render;
import game.engine.components.RigidBody;
import game.engine.systems.InputSystem;
import game.engine.systems.PhysicsSystem;
import game.engine.systems.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
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
		player.addComponent(new Controller());
		player.addComponent(new Render());
		player.addComponent(new RigidBody(1));

		Entity enemy = new Entity();
		enemy.addComponent(new AIController());
		enemy.addComponent(new Render());
		enemy.addComponent(new RigidBody(1));

		gameWorld.addEntity(player);
		gameWorld.addEntity(enemy);

		gameWorld.getTimer().start();
	}


}
