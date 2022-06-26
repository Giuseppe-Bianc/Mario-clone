package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.*;

import static java.util.Objects.requireNonNull;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private int width, height;
	private String title;
	private long glfwWindow;

	public float r, g, b, a;
	private boolean fadeToBlack = false;

	private static Window window = null;

	private static int currentSceneIndex = -1;
	private static Scene currentScene;

	public static void changeScene(int newScene){
		switch (newScene){
			case 0 ->{
				currentScene = new LevelEditorScene();
				currentScene.init();
			}
			case 1 ->{
				currentScene = new LevelScene();
				currentScene.init();
			}
			default -> {
				assert false : "unknown scene '" + newScene + "'";
			}
		}
	}

	private Window() {
		this.width = 1120;
		this.height = 595;
		this.title = "Mario";
		r = g = b = a =1;
	}

	public static Window get(){
		if (Window.window == null){
			Window.window = new Window();
		}
		return Window.window;
	}

	public void run(){
		init();
		loop();
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		glfwTerminate();
		requireNonNull(glfwSetErrorCallback(null)).free();
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()){
			throw new IllegalStateException("unable to initialize GLFW");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if (glfwWindow == NULL) {
			throw new IllegalStateException("Failed to create the GLFW window.");
		}

		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
		glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

		glfwMakeContextCurrent(glfwWindow);
		glfwSwapInterval(1);

		glfwShowWindow(glfwWindow);

		GL.createCapabilities();

		Window.changeScene(0);
	}

	public void loop() {
		float beginTime = Time.getTime(),endTime, dt = -1.0f;

		while (!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();

			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT);

			if (dt >= 0) {
				currentScene.update(dt);
			}

			glfwSwapBuffers(glfwWindow);

			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
	}
}
