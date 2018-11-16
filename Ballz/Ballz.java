import java.awt.*;
import java.applet.*;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Object;
import java.awt.Color;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import javax.swing.event.MouseInputAdapter;

public class Ballz extends Applet
{
   
   
   Timer timer ;
   static ArrayList<Blockz> blocks ;
   static ArrayList<Ammunition> ammo;
   static int numLevels;
   int windowW = 600, windowH =700;
   static int canvasW = 500, canvasH =600;
   static Player p1 ;
   public static Boolean gameOver = false;
   Rectangle restart;  
   static int gameLevel; 
   boolean gameStarted = false; 
   Rectangle menuStart = new Rectangle(canvasW/2-75,canvasH/2-50,150,100);
   static int score;
   int highScore = 0; 
   public void init()
   {
      score = -100;
      p1 = new Player(canvasW/2,canvasH-30);
      gameLevel = 0;
      gameOver = false; 
      restart = new Rectangle(0,0,100,50);
      blocks =  new ArrayList<Blockz>();
      ammo = new ArrayList<Ammunition>();
      generateBlockLevel();
      numLevels = 1;
      setSize(windowW,windowH);
      timer = new Timer();
      timer.schedule(new TimerTask()
      {
         public void run()
         {
            
            repaint();
         }
      }, 0, 15);
   }
   
   public void paint(Graphics g)
   {
   		
      if(!gameStarted)
      {
         g.setFont(new Font("Sans-Serif", Font.BOLD, 40));
         
         g.setColor(Color.red);
         g.drawString("Ballz",canvasW/2-45,canvasH/2+100 );
         
         
         
         g.setColor(Color.gray);
         g.fillRect(canvasW/2-75,canvasH/2-50,150,100);
         g.setColor(Color.white);
         g.drawString("Start",canvasW/2-45,canvasH/2+10);
      }
      else
      {
      
      
      g.setColor(Color.gray);
      g.fillRect(0,0,windowW,windowH);
      g.setColor(Color.white);
      g.fillRect(50,50,canvasW,canvasH);
      g.drawString(""+blocks.size(),50,50);
      renderBlocks(g);
      
      g.setColor(Color.blue);
      g.fillRect(0,0,100,50);
      g.setColor(Color.white);
      
      //g.drawString("RESTART",5,35);
      
      //g.drawString("Score: " + (p1.bullets().size()-2),2*canvasW/5,35);
      if(score>highScore)
            highScore = score;
      //g.drawString("HighScore: " + highScore,3*canvasW/4,35);
      
      p1.render(g);
      if(p1.isClicked() && !gameOver)
      {
         p1.determineTraj(p1.clickedX(),p1.clickedY(),g);
      }
      p1.hitDetect(blocks,ammo); 
     
     if(gameOver)
     {
         g.drawString("GAME OVER" ,canvasW/2,canvasH/2); 
         
     }
     
     
     }
   }
   public void renderBlocks(Graphics g)
   {
      for(int i = 0; i<blocks.size(); i++)
      {
         blocks.get(i).render(g);
         if(blocks.get(i).getHP()<=0)
         {
            score+= blocks.get(i).getMaxHp() *100;
         	blocks.remove(i);
         }
      }
      
      for(int j = 0; j<ammo.size(); j++)
      {
         ammo.get(j).render(g);
      }
   }
   public static Color rndColor()
   {
      int r = (int)(Math.random()*(256));
      int g = (int)(Math.random()*(256));
      int b = (int)(Math.random()*(256));
      
      return new Color(r,g,b);
   }
   
   public static int rndNum(int low, int high)
   {
      return (int)(Math.random()*(high-low+1)+low);
   }
   
   public static void generateBlockLevel()
   {
      int topHp = (int)(.1*Math.pow(p1.bullets.size(),2)+ 1.5*p1.bullets.size()+5);
      int [] pos = {0,1,2,3,4,5,6};
      int x = 10;
      int numBlockz = rndNum(1,6);
      numLevels++;    
      for(int i = 0; i < numBlockz; i++)
      {
         int rand = rndNum(0,6);
         if(pos[rand]!=-1)
         {
            x = 70*pos[rand] + 60;
            pos[rand] = -1;
            if(numLevels>1 && rndNum(0,numBlockz)==1)
               ammo.add(new Ammunition(x,60,rndColor()));
            else
               blocks.add(new Blockz(x,60,60,60,rndNum((int)(.5*topHp),topHp),rndColor()));
         }
      }
      score+=100;
   }
   
   public static void nextLevel()
   {
      for(int i =0; i<blocks.size(); i++)
      {
         blocks.get(i).lower();
         if(blocks.get(i).getLevel()>(canvasH/80)-1)
         {
            gameOver = true;
            blocks.remove(blocks.get(i));
         }
      }
      
      for(int i =0; i<ammo.size(); i++)
      {
         ammo.get(i).lower();
         if(ammo.get(i).getLevel()>(canvasH/80)-1)
         {
            ammo.remove(ammo.get(i));
         }
      }
      
      generateBlockLevel();
   }
   
   public boolean mouseDown(Event e, int x, int y)
   {
      
      if(p1.inBounds(x,y)  && !p1.isDisabled())
      {
         p1.clicked();
         
        
      }
      if(restart.inside(x,y))
         init();
      if(menuStart.inside(x,y))
         gameStarted = true;
      return true;
   }
   
   public boolean mouseDrag(Event e, int x, int y)
   {
      if(p1.isClicked() && y>p1.getY()+p1.getR()/2 && !p1.isDisabled() )
      {
         p1.clicked();
         p1.setMouseCoord(x,y);
      }
      return true;
   }
   
   public boolean mouseUp(Event e, int x, int y)
   {
   
      if(p1.isClicked())
      {
         p1.launch();
         p1.disable();
         //nextLevel();
      }  
      p1.unClicked();
      return true;
   }
   
}