package frontend;

import java.io.IOException;

import frontend.control.ComputerController;
import frontend.control.PlayerController;
import frontend.fxml.GameInputHandler;
import gameplay.GameWorld;
import gameplay.entity.Entity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApplication extends Application {

	public static void main(String...strings) {
		GameApplication.launch(strings);
	}
	
	private Stage 				primaryStage;
	
	private AnimationTimer 		timer;
	
	private GameWorld 			gameWorld;
	private GameInputHandler	inputHandler;

	public GameApplication() {
		Facade.setGameApplication(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setOnCloseRequest(e -> System.exit(0));
		this.primaryStage.show();
		
		setAppScene(AppScene.LOGIN);
	}
	
	
	public void setAppScene(AppScene appScene) {
		Scene scene = SceneLoader.load(appScene);
		primaryStage.setTitle(appScene.getTitle());
		primaryStage.setScene(scene);
		
		if (appScene.equals(AppScene.GAME)) {
			setupGame(scene);
		}
	}

	private void setupGame(Scene scene) {
		this.gameWorld = new GameWorld();
		
		this.inputHandler = new GameInputHandler();
		this.gameWorld.addInputHandler(inputHandler);
		scene.setOnMouseMoved(inputHandler);
		scene.setOnMousePressed(inputHandler);
		scene.setOnMouseReleased(inputHandler);
		scene.setOnKeyPressed(inputHandler);
		scene.setOnKeyReleased(inputHandler);

		Pane root = new Pane();
		scene.setRoot(root);
		Canvas canvas = new Canvas();
		root.getChildren().add(canvas);
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());
		Renderer renderer = new Renderer(gameWorld, canvas);
		this.gameWorld.addRenderer(renderer);

		this.gameWorld.start();
		
//		this.timer = new GameTimer() {
//			@Override
//			public void tick(double dt) {
//				
//			}
//		};
	}
	
	public void addHuman(Entity entity) {
		this.inputHandler.addController(new PlayerController(inputHandler, entity));
	}

	public void addComputer(Entity entity) {
		this.inputHandler.addController(new ComputerController(entity));
	}
	
}
