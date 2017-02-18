package com.kaniha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;




public class WhitespaceFilter implements Filter {

	
	
	// Constants ----------------------------------------------------------------------------------
	
	
    // Specify here where you'd like to start/stop the trimming.
    // You may want to replace this by init-param and initialize in init() instead.
	static final String[] START_TRIM_AFTER = {"<!DOCTYPE"};
	static final String[] STOP_TRIM_AFTER = {"</html>"};
    // Actions ------------------------------------------------------------------------------------

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        //
    	
    	
    	
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpres = (HttpServletResponse) response;
            chain.doFilter(request, wrapResponse(httpres, createTrimWriter(httpres)));
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        //
    }

    // Utility (may be refactored to public utility class) ----------------------------------------

    /**
     * Create a new PrintWriter for the given HttpServletResponse which trims all whitespace.
     * @param response The involved HttpServletResponse.
     * @return A PrintWriter which trims all whitespace.
     * @throws IOException If something fails at I/O level.
     */
    private static PrintWriter createTrimWriter(final HttpServletResponse response)
        throws IOException
    {
        return new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), true) {
            private StringBuilder builder = new StringBuilder();
            private boolean trim = false;

            public void write(int c) {
                builder.append((char) c); // It is actually a char, not an int.
            }

            public void write(char[] chars, int offset, int length) {
                builder.append(chars, offset, length);
                this.flush(); // Preflush it.
            }

            public void write(String string, int offset, int length) {
                builder.append(string, offset, length);
                this.flush(); // Preflush it.
            }

            // Finally override the flush method so that it trims whitespace.
            public void flush() {
                synchronized (builder) {
                    BufferedReader reader = new BufferedReader(new StringReader(builder.toString()));
                    String line = null;

                    try {
                        while ((line = reader.readLine()) != null) {
                           
                        
                        	if (startTrim(line)) {
                                trim = true;
                                out.write(line);
                            } 
                            
                            else if (trim) {
                            	if (line.endsWith(" ")) {
                            	out.write(line.trim() + " ");
                            	} else if (line.startsWith(" ")) {
                            	out.write(" " + line.trim());
                            	} else {
                            	out.write(line.trim());
                            	}
                                
                            	
                                if (stopTrim(line)) {
                                    trim = false;
                                    println();
                                }
                            } else {
                                out.write(line);
                                println();
                            }
                        	
                        }
                    } catch (IOException e) {
                        setError();
                        // Log e or do e.printStackTrace() if necessary.
                    }

                    // Reset the local StringBuilder and issue real flush.
                    builder = new StringBuilder();
                    super.flush();
                }
            }

            private boolean startTrim(String line) {
                for (String match : START_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean stopTrim(String line) {
                for (String match : STOP_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * Wrap the given HttpServletResponse with the given PrintWriter.
     * @param response The HttpServletResponse of which the given PrintWriter have to be wrapped in.
     * @param writer The PrintWriter to be wrapped in the given HttpServletResponse.
     * @return The HttpServletResponse with the PrintWriter wrapped in.
     */
    private static HttpServletResponse wrapResponse(
        final HttpServletResponse response, final PrintWriter writer)
    {
        return new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() throws IOException {
                return writer;
            }
        };
    }

}
