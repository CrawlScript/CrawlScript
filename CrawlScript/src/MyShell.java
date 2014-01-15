/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import org.crawlscript.robot.JSDocument;
import org.crawlscript.robot.JSElement;
import org.crawlscript.robot.JSElements;
import org.crawlscript.robot.JSTextnode;
import org.crawlscript.robot.Mysql;
import org.mozilla.javascript.*;

import java.io.*;
import java.util.UUID;

/**
 * The shell program.
 * 
 * Can execute scripts interactively or in batch mode at the command line. An
 * example of controlling the JavaScript engine.
 * 
 * @author Norris Boyd
 */
public class MyShell extends ScriptableObject {
	private static final long serialVersionUID = -5638074146250193112L;

	@Override
	public String getClassName() {
		return "global";
	}
	
	public static String charset="utf-8";
	
	public static void initConfig()
	{
		File configfile=new File("scriptconfig.txt");
		if(!configfile.exists())
		{
			 charset= new java.io.OutputStreamWriter(new java.io.ByteArrayOutputStream()).getEncoding();
				
		}
		else
		{
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(configfile)));
				String temp;
				while((temp=br.readLine())!=null)
				{
					temp=temp.trim();
					if(temp.startsWith("charset="))
					{
						charset=temp.replace("charset=", "").trim();
						if(charset.equals("default"))
						{
							 charset= new java.io.OutputStreamWriter(new java.io.ByteArrayOutputStream()).getEncoding();							
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("failed to read config file");
				charset= new java.io.OutputStreamWriter(new java.io.ByteArrayOutputStream()).getEncoding();
					
			}
		}
		System.out.println("set charset="+charset);
	}
	
		 

	/**
	 * Main entry point.
	 * 
	 * Process arguments as would a normal Java program. Also create a new
	 * Context and associate it with the current thread. Then set up the
	 * execution environment and begin to execute scripts.
	 */
	public static void main(String args[]) {
		
			initConfig();
		// Associate a new Context with this thread
		Context cx = Context.enter();
		try {
			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed.
			MyShell shell = new MyShell();
			cx.initStandardObjects(shell);
			
			try {
				ScriptableObject.defineClass(shell, JSDocument.class);
				ScriptableObject.defineClass(shell, JSElement.class);
				ScriptableObject.defineClass(shell, JSElements.class);
				ScriptableObject.defineClass(shell, JSTextnode.class);
				ScriptableObject.defineClass(shell, Mysql.class);
			} catch (Exception e) {

			}

			// Define some global functions particular to the shell. Note
			// that these functions are not part of ECMA.
			String[] names = { "print", "uid","quit", "version", "load", "help",
					"write" };

			 shell.defineFunctionProperties(names, MyShell.class,
					 ScriptableObject.DONTENUM);
			 shell.processSource(cx, "myjs/corelib.js");

			
			 
			args = processOptions(cx, args);

			// Set up "arguments" in the global scope to contain the command
			// line arguments after the name of the script to execute
			Object[] array;
			if (args.length == 0) {
				array = new Object[0];
			} else {
				int length = args.length - 1;
				array = new Object[length];
				System.arraycopy(args, 1, array, 0, length);
			}
			Scriptable argsObj = cx.newArray(shell, array);
			shell.defineProperty("arguments", argsObj,
					ScriptableObject.DONTENUM);

			shell.processSource(cx, args.length == 0 ? null : args[0]);
		} finally {
			Context.exit();
		}
	}

	/**
	 * Parse arguments.
	 */
	public static String[] processOptions(Context cx, String args[]) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (!arg.startsWith("-")) {
				String[] result = new String[args.length - i];
				for (int j = i; j < args.length; j++)
					result[j - i] = args[j];
				return result;
			}
			if (arg.equals("-version")) {
				if (++i == args.length)
					usage(arg);
				double d = Context.toNumber(args[i]);
				if (d != d)
					usage(arg);
				cx.setLanguageVersion((int) d);
				continue;
			}
			usage(arg);
		}
		return new String[0];
	}

	/**
	 * Print a usage message.
	 */
	private static void usage(String s) {
		p("Didn't understand \"" + s + "\".");
		p("Valid arguments are:");
		p("-version 100|110|120|130|140|150|160|170");
		System.exit(1);
	}

	/**
	 * Print a help message.
	 * 
	 * This method is defined as a JavaScript function.
	 */
	public void help() {
		p("");
		p("Command                Description");
		p("=======                ===========");
		p("help()                 Display usage and help messages. ");
		p("defineClass(className) Define an extension using the Java class");
		p("                       named with the string argument. ");
		p("                       Uses ScriptableObject.defineClass(). ");
		p("load(['foo.js', ...])  Load JavaScript source files named by ");
		p("                       string arguments. ");
		p("loadClass(className)   Load a class named by a string argument.");
		p("                       The class must be a script compiled to a");
		p("                       class file. ");
		p("print([expr ...])      Evaluate and print expressions. ");
		p("quit()                 Quit the shell. ");
		p("version([number])      Get or set the JavaScript version number.");
		p("");
	}

	
	
	public static void write(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		String result="";
		if(args.length<2)
		{
			return;
		}
		String filename=Context.toString(args[0]);
     	for (int i = 1; i < args.length; i++) {
			
			String s = Context.toString(args[i]);

			result+=s;
		}
     	try {
			FileOutputStream fos=new FileOutputStream(filename, true);
			fos.write(result.getBytes("utf-8"));
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
	}

	/**
	 * Print the string values of its arguments.
	 * 
	 * This method is defined as a JavaScript function. Note that its arguments
	 * are of the "varargs" form, which allows it to handle an arbitrary number
	 * of arguments supplied to the JavaScript function.
	 * 
	 */
	public static void print(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		for (int i = 0; i < args.length; i++) {
			if (i > 0)
				System.out.print(" ");

			// Convert the arbitrary JavaScript value into a string form.
			String s = Context.toString(args[i]);

			System.out.print(s);
		}
		System.out.println();
	}

	/**
	 * Quit the shell.
	 * 
	 * This only affects the interactive mode.
	 * 
	 * This method is defined as a JavaScript function.
	 */
	public void quit() {
		quitting = true;
	}

	/**
	 * Get and set the language version.
	 * 
	 * This method is defined as a JavaScript function.
	 */
	public static double version(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		double result = cx.getLanguageVersion();
		if (args.length > 0) {
			double d = Context.toNumber(args[0]);
			cx.setLanguageVersion((int) d);
		}
		return result;
	}
	
	
	public static String uid(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		return UUID.randomUUID().toString();
	}

	/**
	 * Load and execute a set of JavaScript source files.
	 * 
	 * This method is defined as a JavaScript function.
	 * 
	 */
	public static void load(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		MyShell shell = (MyShell) getTopLevelScope(thisObj);
		for (int i = 0; i < args.length; i++) {
			shell.processSource(cx, Context.toString(args[i]));
		}
	}

	/**
	 * Evaluate JavaScript source.
	 * 
	 * @param cx
	 *            the current context
	 * @param filename
	 *            the name of the file to compile, or null for interactive mode.
	 */
	private void processSource(Context cx, String filename) {
		if (filename == null) {
			 
			BufferedReader in=null;
			try {
				in = new BufferedReader(new InputStreamReader(
						System.in,charset));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
				return;
			}
			String sourceName = "<stdin>";
			int lineno = 1;
			boolean hitEOF = false;
			do {
				int startline = lineno;
				System.err.print("js> ");
				System.err.flush();
				try {
					String source = "";
					// Collect lines of source to compile.
					while (true) {
						String newline;
						newline = in.readLine();
						if (newline == null) {
							hitEOF = true;
							break;
						}
						source = source + newline + "\n";
						lineno++;
						// Continue collecting as long as more lines
						// are needed to complete the current
						// statement. stringIsCompilableUnit is also
						// true if the source statement will result in
						// any error other than one that might be
						// resolved by appending more source.
						if (cx.stringIsCompilableUnit(source))
							break;
					}
					Object result = cx.evaluateString(this, source, sourceName,
							startline, null);
					if (result != Context.getUndefinedValue()) {
						System.err.println(Context.toString(result));
					}
				} catch (WrappedException we) {
					// Some form of exception was caught by JavaScript and
					// propagated up.
					System.err.println(we.getWrappedException().toString());
					//we.printStackTrace();
				} catch (EvaluatorException ee) {
					// Some form of JavaScript error.
					System.err.println("js: " + ee.getMessage());
				} catch (JavaScriptException jse) {
					// Some form of JavaScript error.
					System.err.println("js: " + jse.getMessage());
				} catch (IOException ioe) {
					System.err.println(ioe.toString());
				}catch(Exception e){
					System.err.println(e.getMessage());
				}
				if (quitting) {
					// The user executed the quit() function.
					break;
				}
			} while (!hitEOF);
			System.err.println();
		} else {
			FileReader in = null;
			try {
				in = new FileReader(filename);
			} catch (FileNotFoundException ex) {
				Context.reportError("Couldn't open file \"" + filename + "\".");
				return;
			}

			try {
				// Here we evalute the entire contents of the file as
				// a script. Text is printed only if the print() function
				// is called.
				cx.evaluateReader(this, in, filename, 1, null);
			} catch (WrappedException we) {
				System.err.println(we.getWrappedException().toString());
				we.printStackTrace();
			} catch (EvaluatorException ee) {
				System.err.println("js: " + ee.getMessage());
			} catch (JavaScriptException jse) {
				System.err.println("js: " + jse.getMessage());
			} catch (IOException ioe) {
				System.err.println(ioe.toString());
			} finally {
				try {
					in.close();
				} catch (IOException ioe) {
					System.err.println(ioe.toString());
				}
			}
		}
	}

	private static void p(String s) {
		System.out.println(s);
	}

	private boolean quitting;
}
