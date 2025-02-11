package com.mygdx.game.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SharedLibraryLoader;



public class DesktopControllerManager implements ControllerManager {
    final Array<Controller> controllers = new Array();
    final Array<ControllerListener> listeners = new Array();

    public DesktopControllerManager () {
        new SharedLibraryLoader().load("gdx-controllers-desktop");
        //new OisCo(this);
    }

    public Array<Controller> getControllers () {
        return controllers;
    }

    public void addListener (ControllerListener listener) {
        listeners.add(listener);
    }

    public void removeListener (ControllerListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public void clearListeners () {
        listeners.clear();
    }

    @Override
    public Array<ControllerListener> getListeners () {
        return listeners;
    }
}
