package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.util.Objects.requireNonNull;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private int width, height;
	private String title;
	private long glfwWindow;

	private float r, g, b, a;
	private boolean fadeToBlack = false;

	private static Window window = null;

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

	private void init() {
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

	}

	private void loop() {
		while (!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();

			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT);
			if (fadeToBlack) {
				r = Math.max(r - 0.01f, 0);
				g = Math.max(g - 0.01f, 0);
				b = Math.max(b - 0.01f, 0);
			}

			if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
				fadeToBlack = true;
			}
			glfwSwapBuffers(glfwWindow);
		}
	}
}
