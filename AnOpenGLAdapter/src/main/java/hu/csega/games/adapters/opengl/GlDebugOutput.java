/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;

/**
 *
 * @author GBarbieri
 */
public class GlDebugOutput implements GLDebugListener {

    public int source;
    public int type;
    public int id;
    public int severity;
    public int length;
    public String message;
    public boolean received = false;

    public GlDebugOutput() {

    }

    public GlDebugOutput(final int source, final int type, final int severity) {
        this.source = source;
        this.type = type;
        this.severity = severity;
        this.message = null;
        this.id = -1;

    }

    public GlDebugOutput(final String message, final int id) {
        this.source = -1;
        this.type = -1;
        this.severity = -1;
        this.message = message;
        this.id = id;
    }

    @Override
    public void messageSent(GLDebugMessage event) {

        System.err.println("GlDebugOutput.messageSent(): " + event);
        if (null != message && message.equals(event.getDbgMsg()) && id == event.getDbgId()) {
            received = true;
        } else if (0 <= source && source == event.getDbgSource()
                && type == event.getDbgType()
                && severity == event.getDbgSeverity()) {
            received = true;
        }
    }

}
