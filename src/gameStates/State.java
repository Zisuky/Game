/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates;

import audio.AudioPlayer;
import main.Game;
import java.awt.event.MouseEvent;
import ui.MenuButton;

/**
 *
 * @author loisu
 */
public class State {
    protected Game game;
    
    public State(Game game){
        this.game = game;
    }
    public boolean isIn(MouseEvent e, MenuButton mb) {
	return mb.getBounds().contains(e.getX(), e.getY());
    }
    public Game getGame(){
        return game;
    }
    public void setGamestate(GameState state) {
        switch (state) {
            case MENU ->
                game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING ->
                game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
        }

        GameState.state = state;
    }
}
