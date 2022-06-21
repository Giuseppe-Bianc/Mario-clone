package jade;

public class Window {
	private int width, height;
	private String title;

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

	private void loop() {
		//
	}

	private void init() {
		//
	}
}
