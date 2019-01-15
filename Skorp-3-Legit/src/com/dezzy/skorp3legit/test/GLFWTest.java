package com.dezzy.skorp3legit.test;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class GLFWTest {
	private long window;
	private GLFWKeyCallbackI keyCallbackFunc = this::glfwKeyCallbackFunc;
	private GLFWCursorPosCallbackI cursorPosCallbackFunc = this::glfwCursorPosCallbackFunc;
	private GLFWMouseButtonCallbackI mouseButtonCallbackFunc = this::glfwMouseButtonCallbackFunc;
	private GLFWScrollCallbackI scrollCallbackFunc = this::glfwScrollCallbackFunc;
	
	public void run() {
		System.out.println("LWJGL Version: " + Version.getVersion());
		
		init();
		loop();
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(300, 300, "GLFW Test", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW Window");
		} else {
			System.out.println("Window located at 0x" + Long.toHexString(window));
		}
		
		glfwSetKeyCallback(window, keyCallbackFunc);
		glfwSetCursorPosCallback(window, cursorPosCallbackFunc);
		glfwSetMouseButtonCallback(window, mouseButtonCallbackFunc);
		glfwSetScrollCallback(window, scrollCallbackFunc);
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0))/2, (vidmode.height() - pHeight.get(0))/2);
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		
		
	}
	
	private void glfwKeyCallbackFunc(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
			glfwSetWindowShouldClose(window, true);
		}
	}
	
	private void glfwCursorPosCallbackFunc(long window, double xpos, double ypos) {
		System.out.println("mouse: (" + xpos + "," + ypos + ")");
	}
	
	private void glfwMouseButtonCallbackFunc(long window, int button, int action, int mods) {
		
	}
	
	private void glfwScrollCallbackFunc(long window, double xoffset, double yoffset) {
		
	}
	
	private void loop() {
		GL.createCapabilities();
		
		glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
		
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(window);
			
			glfwPollEvents();
		}
	}
}
