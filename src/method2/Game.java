package method2;

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
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Game {

	public Game(){
		
		initGL();
		initElements();
		renderLoop();
		
	}
	
	long glfwWindow;
	
	private void initGL(){
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
	    glfwWindow = glfwCreateWindow(1280, 720, "Proxy Engine", NULL, NULL);
	
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
	}
	
	private ShaderProgram shaderProgram;
	
	private Mesh mesh;
	private Texture texture;
	private Transformation transformation;
	private GameObject gameObject;
	
	private VAO vao;
	
	Matrix4f projectionMatrix;
	
	private void initElements(){
		shaderProgram = new ShaderProgram();
		
		mesh = new Mesh();
		texture = new Texture();
		transformation = new Transformation();
		gameObject = new GameObject(mesh, texture, transformation);
		
		vao = new VAO(mesh);
		
		float aspectRatio = 1280f / 720f;
	    
	    projectionMatrix = new Matrix4f();
	    projectionMatrix.identity();
	    projectionMatrix.ortho(-1f * aspectRatio, 1f * aspectRatio, -1f, 1f, 0.01f, 100f);
	}
	
	private void renderLoop(){
		
		while(true){
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	        
	        glUseProgram(shaderProgram.programId);
	     	
	     	// Use First Texture
	     	glActiveTexture(GL_TEXTURE0);
	     	glBindTexture(GL_TEXTURE_2D, texture.textureId);
	     	
	     	// Set Matrix
	     	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	     	
	     	projectionMatrix.get(matrixBuffer);
	        glUniformMatrix4fv(shaderProgram.uniformProjectionId, false, matrixBuffer);
	        
	        FloatBuffer matrixBuffer2 = BufferUtils.createFloatBuffer(16);
	        
	        transformation.modelViewMatrix.get(matrixBuffer2);
	        glUniformMatrix4fv(shaderProgram.uniformModelViewId, false, matrixBuffer2);
	         
		    // Draw the mesh
		    glBindVertexArray(vao.vaoId);
		    glEnableVertexAttribArray(0);
		    glEnableVertexAttribArray(1);
		    
		    glDrawElements(GL_TRIANGLES, vao.indexCount, GL_UNSIGNED_INT, 0);
		    
		    // Restore state        
		    glDisableVertexAttribArray(0);
		    glDisableVertexAttribArray(1);
		    glBindVertexArray(0);
		    glUseProgram(0);
	        
		    glfwSwapBuffers(glfwWindow);
	        glfwPollEvents();
	        
	        try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
