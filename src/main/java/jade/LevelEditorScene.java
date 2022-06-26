package jade;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderi;

public class LevelEditorScene extends Scene {

	private static final String vertexShaderSrc = "#version 330 core\n" +
			"layout (location = 0) in vec3 aPos;\n" +
			"layout (location = 0) in vec4 aColor;\n\n" +
			"out vec4 fColor;\n\n" +
			"void main() {\n" +
			"\tfColor = aColor;\n" +
			"\tgl_Position = vec4(aPos, 1.0);\n" +
			"}";
	private static final String fragmentShaderSrc = "#version 330 core\n\n" +
			"in vec4 fColor;\n" +
			"out vec4 color;\n\n" +
			"void main() {\n" +
			"\tcolor = fColor;\n" +
			"}";
	private int vertexID, fragmentID, shaderProgram;
	public LevelEditorScene() {
	}

	@Override
	public void init() {
		vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, vertexShaderSrc);
		glCompileShader(vertexID);
		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if (success == GL_FALSE){
			int len = glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
			System.out.println("defaultShader.glsl vertex compilation failed");
			System.out.println(glGetShaderInfoLog(vertexID, len));
			assert false : "";
		}

		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, fragmentShaderSrc);
		glCompileShader(fragmentID);
		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if (success == GL_FALSE){
			int len = glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
			System.out.println("defaultShader.glsl vertex compilation failed");
			System.out.println(glGetShaderInfoLog(fragmentID, len));
			assert false : "";
		}

	}

	@Override
	public void update(float dt) {

	}
}
