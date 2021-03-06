package http.api;

public interface Cookie{

 


    public void setComment(String purpose) ;

    /**
     * Returns the comment describing the purpose of this cookie, or
     * <code>null</code> if the cookie has no comment.
     *
     * @return the comment of the cookie, or <code>null</code> if unspecified
     *
     * @see #setComment
     */ 
    public String getComment() ;
    /**
     *
     * Specifies the domain within which this cookie should be presented.
     *
     * <p>The form of the domain name is specified by RFC 2109. A domain
     * name begins with a dot (<code>.foo.com</code>) and means that
     * the cookie is visible to servers in a specified Domain Name System
     * (DNS) zone (for example, <code>www.foo.com</code>, but not 
     * <code>a.b.foo.com</code>). By default, cookies are only returned
     * to the server that sent them.
     *
     * @param domain the domain name within which this cookie is visible;
     * form is according to RFC 2109
     *
     * @see #getDomain
     */
    public void setDomain(String domain) ;

    /**
     * Gets the domain name of this Cookie.
     *
     * <p>Domain names are formatted according to RFC 2109.
     *
     * @return the domain name of this Cookie
     *
     * @see #setDomain
     */ 
    public String getDomain() ;

    /**
     * Sets the maximum age in seconds for this Cookie.
     *
     * <p>A positive value indicates that the cookie will expire
     * after that many seconds have passed. Note that the value is
     * the <i>maximum</i> age when the cookie will expire, not the cookie's
     * current age.
     *
     * <p>A negative value means
     * that the cookie is not stored persistently and will be deleted
     * when the Web browser exits. A zero value causes the cookie
     * to be deleted.
     *
     * @param expiry		an integer specifying the maximum age of the
     * 				cookie in seconds; if negative, means
     *				the cookie is not stored; if zero, deletes
     *				the cookie
     *
     * @see #getMaxAge
     */
    public void setMaxAge(int expiry);

    /**
     * Gets the maximum age in seconds of this Cookie.
     *
     * <p>By default, <code>-1</code> is returned, which indicates that
     * the cookie will persist until browser shutdown.
     *
     * @return			an integer specifying the maximum age of the
     *				cookie in seconds; if negative, means
     *				the cookie persists until browser shutdown
     *
     * @see #setMaxAge
     */
    public int getMaxAge() ;
    
    /**
     * Specifies a path for the cookie
     * to which the client should return the cookie.
     *
     * <p>The cookie is visible to all the pages in the directory
     * you specify, and all the pages in that directory's subdirectories. 
     * A cookie's path must include the servlet that set the cookie,
     * for example, <i>/catalog</i>, which makes the cookie
     * visible to all directories on the server under <i>/catalog</i>.
     *
     * <p>Consult RFC 2109 (available on the Internet) for more
     * information on setting path names for cookies.
     *
     *
     * @param uri		a <code>String</code> specifying a path
     *
     * @see #getPath
     */
    public void setPath(String uri) ;

    /**
     * Returns the path on the server 
     * to which the browser returns this cookie. The
     * cookie is visible to all subpaths on the server.
     *
     * @return		a <code>String</code> specifying a path that contains
     *			a servlet name, for example, <i>/catalog</i>
     *
     * @see #setPath
     */ 
    public String getPath() ;

    /**
     * Indicates to the browser whether the cookie should only be sent
     * using a secure protocol, such as HTTPS or SSL.
     *
     * <p>The default value is <code>false</code>.
     *
     * @param flag if <code>true</code>, sends the cookie from the browser
     * to the server only when using a secure protocol; if <code>false</code>,
     * sent on any protocol
     *
     * @see #getSecure
     */
    public void setSecure(boolean flag);

    /**
     * Returns <code>true</code> if the browser is sending cookies
     * only over a secure protocol, or <code>false</code> if the
     * browser can send cookies using any protocol.
     *
     * @return <code>true</code> if the browser uses a secure protocol,
     * <code>false</code> otherwise
     *
     * @see #setSecure
     */
    public boolean getSecure() ;

    /**
     * Returns the name of the cookie. The name cannot be changed after
     * creation.
     *
     * @return the name of the cookie
     */
    public String getName() ;

    /**
     * Assigns a new value to this Cookie.
     * 
     * <p>If you use a binary value, you may want to use BASE64 encoding.
     *
     * <p>With Version 0 cookies, values should not contain white 
     * space, brackets, parentheses, equals signs, commas,
     * double quotes, slashes, question marks, at signs, colons,
     * and semicolons. Empty values may not behave the same way
     * on all browsers.
     *
     * @param newValue the new value of the cookie
     *
     * @see #getValue
     */
    public void setValue(String newValue) ;

    /**
     * Gets the current value of this Cookie.
     *
     * @return the current value of this Cookie
     *
     * @see #setValue
     */
    public String getValue() ;

    /**
     * Returns the version of the protocol this cookie complies 
     * with. Version 1 complies with RFC 2109, 
     * and version 0 complies with the original
     * cookie specification drafted by Netscape. Cookies provided
     * by a browser use and identify the browser's cookie version.
     * 
     * @return			0 if the cookie complies with the
     *				original Netscape specification; 1
     *				if the cookie complies with RFC 2109
     *
     * @see #setVersion
     */
    public int getVersion() ;

    /**
     * Sets the version of the cookie protocol that this Cookie complies
     * with.
     *
     * <p>Version 0 complies with the original Netscape cookie
     * specification. Version 1 complies with RFC 2109.
     *
     * <p>Since RFC 2109 is still somewhat new, consider
     * version 1 as experimental; do not use it yet on production sites.
     *
     * @param v	0 if the cookie should comply with the original Netscape
     * specification; 1 if the cookie should comply with RFC 2109
     *
     * @see #getVersion
     */
    public void setVersion(int v) ;


    /**
     * Marks or unmarks this Cookie as <i>HttpOnly</i>.
     *
     * <p>If <tt>isHttpOnly</tt> is set to <tt>true</tt>, this cookie is
     * marked as <i>HttpOnly</i>, by adding the <tt>HttpOnly</tt> attribute
     * to it.
     *
     * <p><i>HttpOnly</i> cookies are not supposed to be exposed to
     * client-side scripting code, and may therefore help mitigate certain
     * kinds of cross-site scripting attacks.
     *
     * @param isHttpOnly true if this cookie is to be marked as
     * <i>HttpOnly</i>, false otherwise
     *
     * @since Servlet 3.0
     */
    public void setHttpOnly(boolean isHttpOnly) ;
 
    /**
     * Checks whether this Cookie has been marked as <i>HttpOnly</i>.
     *
     * @return true if this Cookie has been marked as <i>HttpOnly</i>,
     * false otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isHttpOnly() ;
}

