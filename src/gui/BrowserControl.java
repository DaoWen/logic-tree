/*
 * BrowserControl.java
 *
 * Created on March 21, 2006, 6:05 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 * 
 */

package gui;

import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

/**
 * Simple utility for opening a URL in the system default browser.
 *
 * @author Nick Vrvilo
 */
public class BrowserControl {

    /**
     * Open a URI with the browser.
     *
     * @param url the file's URI
     */
    public static void displayURL(String url) {
        try {
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
            else if (hasXdg()) {
                Runtime.getRuntime().exec(new String[] { "xdg-open", url });
            }
        }
        catch(Exception ex) {
            System.err.println("Could not invoke browser, URI=" + url);
            System.err.println("Caught: "  + ex);
        }
    }

    /**
     * Check if the current platform supports xdg-open
     * (generic app for launching things in X-Windows environment)
     *
     * @return true if xdg-open command is available
     */
    public static boolean hasXdg() throws IOException {
        return Runtime.getRuntime().exec("type xdg-open").exitValue() == 0;
    }

}
