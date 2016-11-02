package com.vstack.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility Class for the Aegis Toolkit
 */

public class VStackUtils {

	private static ServletContext servletContext = null;

	private static String contextPath = null;

	/**
	 * File Separator
	 */
	public static String FS = System.getProperty("file.separator");

	/**
	 * Temporary Directory for putting Logs and Scripts
	 */
	public static String SYSTEM_TMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Log Directory for putting Logs and Scripts
	 */
	public static String LOG_DIR = SYSTEM_TMP_DIR + FS + "logs";
	
	/**
	 * Temporary Directory for Charts
	 */
	public static String CHARTS_DIR = "charts";
	/**
	 * Throw exception
	 * 
	 * @param ex
	 * @return
	 */
	public static String returnExceptionTrace(Exception ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		return writer.toString();
	}

	/**
	 * Return the ServletContext from the Controllers
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * Sets the ServletContext
	 * 
	 * @param servletContext
	 */
	public static void setServletContext(ServletContext servletContext) {
		VStackUtils.servletContext = servletContext;
	}

	/**
	 * Get the servlet context PAth
	 * 
	 * @return
	 */
	public static String getContextPath() {
		return contextPath;
	}

	/**
	 * Set the servlet context path
	 * 
	 * @param contextPath
	 */
	public static void setContextPath(String contextPath) {
		VStackUtils.contextPath = contextPath;
	}


	/**
	 * Handle RuntimeException
	 * 
	 * @param ex
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void handleRuntimeException(Exception ex, HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(message + " " + ex.getMessage());
		response.flushBuffer();
	}

	/**
	 * Handle Runtime Error
	 * 
	 * @param ex
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void handleRuntimeError(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		if (message != null) {
			response.getWriter().write(message);
		} else {
			response.getWriter().write("");
		}
		response.flushBuffer();
	}

	/**
	 * Handle Runtime Error
	 * 
	 * @param ex
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void handleResponse(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(message);
		response.flushBuffer();
	}

}
