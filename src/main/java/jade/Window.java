package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private int width, height;
	private String title;
	private long glfwWindow;

	private static Window window = null;

	private Window() {
		this.width = 1120;
		this.height = 595;
		this.title = "Mario";
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

		glfwMakeContextCurrent(glfwWindow);
		glfwSwapInterval(1);

		glfwShowWindow(glfwWindow);

		GL.createCapabilities();

	}

	private void loop() {
		while (!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();

			glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT);

			glfwSwapBuffers(glfwWindow);
		}
	}
}
