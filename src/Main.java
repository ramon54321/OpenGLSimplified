import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import method2.Game;

public class Main {

	public static String loadFileToString(String filename){
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
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Main() throws Exception{
		
		// Method 1
		//start();
		
		// Method 2
		new Game();
		
	}
	
	
	// ------------------------ Start ---------------------------
	
	
	private void start() throws Exception{
		
		// ------------------------ Set Up OpenGL ---------------------------
		
		GLFWErrorCallback errorCallback;
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		glfwInit();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_STENCIL_BITS, 4);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        long glfwWindow = glfwCreateWindow(1280, 720, "Proxy Engine", NULL, NULL);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Disable VSync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(glfwWindow);
        
        // Enable OpenGL
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.5f);
        
        // Depth Test
        glEnable(GL_DEPTH_TEST);
        
        // Blending for Alpha
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        // Draw Style
        glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
		
        
        // ------------------------ Shader Program ---------------------------
        
        int programId = glCreateProgram();
        
        int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderId, loadFileToString("src/shader.vert"));
        glCompileShader(vertexShaderId);
        glAttachShader(programId, vertexShaderId);
        
        int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderId, loadFileToString("src/shader.frag"));
        glCompileShader(fragmentShaderId);
        glAttachShader(programId, fragmentShaderId);
        
        glLinkProgram(programId);
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
        
        int uniformProjection = glGetUniformLocation(programId, "uniformProjection");
        int uniformModelView = glGetUniformLocation(programId, "uniformModelView");
        
        
        // ------------------------ Mesh ---------------------------
        
        float[] vertexPositions = new float[]{
                -0.5f,  0.5f,
                -0.5f, -0.5f,
                 0.5f, -0.5f,
                 0.5f,  0.5f,
        };
        float[] vertexTextureCoordinates = new float[]{
         		0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f,
        };
        int[] vertexIndexArray = new int[]{
         		0, 1, 3, 3, 1, 2,
        };
        
        
        // ------------------------ VAO ---------------------------
        
        int indexCount = vertexIndexArray.length;
        
        // Vertex Array Object
        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        
        // Position VBO
        int vboIdVertexPositions = glGenBuffers();
        FloatBuffer vertexPositionsBuffer = BufferUtils.createFloatBuffer(vertexPositions.length);
        vertexPositionsBuffer.put(vertexPositions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboIdVertexPositions);
        glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        
        // Texture VBO
        int vboIdVertexTextureCoords = glGenBuffers();
        FloatBuffer textureCoordinatesBuffer = BufferUtils.createFloatBuffer(vertexTextureCoordinates.length);
        textureCoordinatesBuffer.put(vertexTextureCoordinates).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboIdVertexTextureCoords);
        glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        
        // Index VBO
        int vboIdIndexArray = glGenBuffers();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(vertexIndexArray.length);
        indicesBuffer.put(vertexIndexArray).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIdIndexArray);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        
        
        // ------------------------ Texture ---------------------------
        
        int textureId = glGenTextures();
        
        glBindTexture(GL_TEXTURE_2D, textureId);
        
        float pixels[] = {
        		1.0f, 0.0f, 0.0f,
        	    1.0f, 1.0f, 1.0f,
        	    0.0f, 0.0f, 1.0f,
        	    0.0f, 1.0f, 0.0f,
        	};
        
        // GL_NEAREST
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        
        FloatBuffer pixelData = BufferUtils.createFloatBuffer(4 * 3);
    	pixelData.put(pixels);
    	pixelData.flip();
    	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 2, 2, 0, GL_RGB, GL_FLOAT, pixelData);
    	
    	glGenerateMipmap(GL_TEXTURE_2D);
        
        
        // ------------------------ Transformation ---------------------------
        
        float aspectRatio = 1280f / 720f;
        
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.ortho(-1f * aspectRatio, 1f * aspectRatio, -1f, 1f, 0.01f, 100f);
        
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.translate(-0, -0, -0);
        
        Matrix4f modelViewMatrix = new Matrix4f();
        modelViewMatrix.identity();
        modelViewMatrix.translate(1, 0, 0);
        
        
        // ------------------------ Render ---------------------------
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        glUseProgram(programId);
     	
     	// Use First Texture
     	glActiveTexture(GL_TEXTURE0);
     	glBindTexture(GL_TEXTURE_2D, textureId);
     	
     	// Set Matrix
     	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
     	
     	projectionMatrix.get(matrixBuffer);
        glUniformMatrix4fv(uniformProjection, false, matrixBuffer);
        
        FloatBuffer matrixBuffer2 = BufferUtils.createFloatBuffer(16);
        
        modelViewMatrix.get(matrixBuffer2);
        glUniformMatrix4fv(uniformModelView, false, matrixBuffer2);
         
	    // Draw the mesh
	    glBindVertexArray(vaoId);
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);
	    
	    glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
	    
	    // Restore state        
	    glDisableVertexAttribArray(0);
	    glDisableVertexAttribArray(1);
	    glBindVertexArray(0);
	    glUseProgram(0);
        
	    glfwSwapBuffers(glfwWindow);
        glfwPollEvents();
        
        
        
        
        
        
        
        Thread.sleep(2000);
	}
}
