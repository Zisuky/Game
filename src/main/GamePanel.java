/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;



import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.*;





/**
 *
 * @author loisu
 */
public class GamePanel extends JPanel{
    private MouseInputs moutsInputs; 
    private Game game;
    
    public GamePanel(Game game) {
        
        moutsInputs = new MouseInputs(this);
        this.game = game;
        
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); 
        addMouseListener(moutsInputs);
        addMouseMotionListener(moutsInputs);
        
    }
    
    private void setPanelSize() {
         Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
         
         setPreferredSize(size);
         System.out.println("SIZE: " + Game.GAME_WIDTH+ "|" + Game.GAME_HEIGHT);
     }
    
    void updateGame() {
       
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
      
    }   

    public Game getGame(){
        return game;
    }
}
    

    

    


   

    

   

    
    
    
    

