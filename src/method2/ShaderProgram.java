package method2;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;

public class ShaderProgram {
	
	public int programId;
	public int vertexShaderId;
	public int fragmentShaderId;
	
	public int uniformProjectionId;
	public int uniformModelViewId;
	
	public ShaderProgram(){
		programId = glCreateProgram();
        
        vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderId, loadFileToString("src/shader.vert"));
        glCompileShader(vertexShaderId);
        glAttachShader(programId, vertexShaderId);
        
        fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderId, loadFileToString("src/shader.frag"));
        glCompileShader(fragmentShaderId);
        glAttachShader(programId, fragmentShaderId);
        
        glLinkProgram(programId);
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
        
        uniformProjectionId = glGetUniformLocation(programId, "uniformProjection");
        uniformModelViewId = glGetUniformLocation(programId, "uniformModelView");
	}

	public String loadFileToString(String filename){
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String contentLine = br.readLine();
			while (contentLine != null){
				sb.append(contentLine + "\n");
				contentLine = br.readLine();
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
