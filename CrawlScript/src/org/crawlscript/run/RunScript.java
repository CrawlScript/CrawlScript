package org.crawlscript.run;

/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

import org.crawlscript.robot.JSDocument;
import org.crawlscript.robot.Mysql;

import org.mozilla.javascript.*;

/**
 * RunScript: simplest example of controlling execution of Rhino.
 * 
 * Collects its arguments from the command line, executes the script, and prints
 * the result.
 * 
 * @author Norris Boyd
 */
public class RunScript extends ScriptableObject {
	private static final long serialVersionUID = -5638074146250193112L;

	@Override
	public String getClassName() {
		return "global";
	}

	Context cx;
	Scriptable scope;

	public RunScript() {

	}

	public void execute(String jsstr) {
		// Collect the arguments into a single string.
		String s = "";

		s = "print('123')";
		// Now evaluate the string we've colected.
		processCmd(cx,jsstr);

		
	}
	
	public void setOut(PrintStream out)
	{
		System.setOut(out);
	}

	public void init() {
		// Creates and enters a Context. The Context stores information
		// about the execution environment of a script.
		cx = Context.enter();
		
		try {
			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed. Returns
			// a scope object that we use in later calls.

			RunScript runscript = new RunScript();
			scope = cx.initStandardObjects(runscript);
			try {
				
				ScriptableObject.defineClass(runscript, JSDocument.class);
				ScriptableObject.defineClass(runscript, Mysql.class);
			} catch (Exception e) {

			}

			String[] names = { "print", "quit", "version", "load", "help",
					"write" };
			// shell.defineFunctionProperties(names, RunScript.class,
			// ScriptableObject.DONTENUM);
			runscript.defineFunctionProperties(names, RunScript.class,
					ScriptableObject.DONTENUM);
			runscript.processSource(cx, "myjs/mylib.js");
			runscript.processSource(cx, "myjs/crawl.js");
			runscript.processSource(cx, "myjs/user.js");

		} finally {
			// Exit from the context.
			// Context.exit();
		}
	}

	public static void main(String[] args) {
		RunScript r = new RunScript();
		r.init();
		r.execute("print('234234234');");
	}

	public static void main1(String args[]) {
		// Creates and enters a Context. The Context stores information
		// about the execution environment of a script.
		Context cx = Context.enter();
		try {
			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed. Returns
			// a scope object that we use in later calls.

			RunScript runscript = new RunScript();
			Scriptable scope = cx.initStandardObjects(runscript);
			try {
			
			} catch (Exception e) {

			}

			String[] names = { "print", "quit", "version", "load", "help",
					"write" };
			// shell.defineFunctionProperties(names, RunScript.class,
			// ScriptableObject.DONTENUM);
			runscript.defineFunctionProperties(names, RunScript.class,
					ScriptableObject.DONTENUM);
			runscript.processSource(cx, "myjs/mylib.js");
			runscript.processSource(cx, "myjs/crawl.js");
			runscript.processSource(cx, "myjs/user.js");

			// Collect the arguments into a single string.
			String s = "";
			for (int i = 0; i < args.length; i++) {
				s += args[i];
			}
			s = "print('123')";
			// Now evaluate the string we've colected.
			Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

			// Convert the result to a string and print it.
			System.err.println(Context.toString(result));

		} finally {
			// Exit from the context.
			Context.exit();
		}
	}

	private boolean quitting;

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
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
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
					we.printStackTrace();
				} catch (EvaluatorException ee) {
					// Some form of JavaScript error.
					System.err.println("js: " + ee.getMessage());
				} catch (JavaScriptException jse) {
					// Some form of JavaScript error.
					System.err.println("js: " + jse.getMessage());
				} catch (IOException ioe) {
					System.err.println(ioe.toString());
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

	public static void write(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		String result = "";
		if (args.length < 2) {
			return;
		}
		String filename = Context.toString(args[0]);
		for (int i = 1; i < args.length; i++) {

			String s = Context.toString(args[i]);

			result += s;
		}
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
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
	
	public static Printer printer=new Printer();
	public static void print(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		for (int i = 0; i < args.length; i++) {
			if (i > 0)
				printer.print(" ");

			// Convert the arbitrary JavaScript value into a string form.
			String s = Context.toString(args[i]);

			printer.print(s);
		}
		printer.println();
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

	/**
	 * Load and execute a set of JavaScript source files.
	 * 
	 * This method is defined as a JavaScript function.
	 * 
	 */
	public static void load(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		RunScript shell = (RunScript) getTopLevelScope(thisObj);
		for (int i = 0; i < args.length; i++) {
			shell.processSource(cx, Context.toString(args[i]));
		}
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

	private static void p(String s) {
		System.out.println(s);
	}

	/**
	 * Evaluate JavaScript source.
	 * 
	 * @param cx
	 *            the current context
	 * @param filename
	 *            the name of the file to compile, or null for interactive mode.
	 */
	private void processCmd(Context cx, String cmd) {

		StringReader in = new StringReader(cmd);

		try {
			// Here we evalute the entire contents of the file as
			// a script. Text is printed only if the print() function
			// is called.
			
			cx.evaluateString(scope, cmd, "<cmd>", 1, null);
		} catch (WrappedException we) {
			System.err.println(we.getWrappedException().toString());
			we.printStackTrace();
		} catch (EvaluatorException ee) {
			System.err.println("js: " + ee.getMessage());
		} catch (JavaScriptException jse) {
			System.err.println("js: " + jse.getMessage());
		} finally {

			in.close();

		}
	}
}
