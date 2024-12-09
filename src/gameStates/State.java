/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates;

import main.Game;
import java.awt.event.MouseEvent;
import ui.menuButton;

/**
 *
 * @author loisu
 */
public class State {
    protected Game game;
    
    public State(Game game){
        this.game = game;
    }
    public boolean isIn(MouseEvent e, menuButton mb) {
	return mb.getBounds().contains(e.getX(), e.getY());
    }
    public Game getGame(){
        return game;
    }
}
