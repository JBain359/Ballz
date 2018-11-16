import java.awt.*;
import java.awt.Color;

public class Bullet
{
   protected double x,y,dirX,dirY;
   protected int  r = 20;
   protected Color bg;
   protected int maxSpeed;
   protected int minSpeed;
   protected double canvasH = 600;
   protected boolean locked = false;
  
   public Bullet(double x, double y, double xVelocity, double yVelocity, Color bg, boolean lock)
   {
      this.x = x;
      this.y = y;
      this.dirX = xVelocity;
      this.dirY = yVelocity;
      this.bg = bg;
      this.maxSpeed = 5 ;
      this.minSpeed = 4 ;
      this.locked = lock;
   }
   
   public void render(Graphics g)
   {
      g.setColor(this.bg);
      g.fillOval((int)x,(int)y,r,r);
      
      while(Math.sqrt(Math.pow(dirX,2)+ Math.pow(dirY,2))>this.maxSpeed)
      {
         
         dirX/=1.15;
         dirY/=1.15;
      }
      
      while(Math.sqrt(Math.pow(dirX,2)+ Math.pow(dirY,2))<this.minSpeed && y!=canvasH-30)
      {
         if(dirX == 0 ){dirX=1;}
         if(dirY == 0 ){dirY=1;}
         dirX*=1.15;
         dirY*=1.15;
      }
      
      x+=dirX;
      y+=dirY;
      
  
   }
   public boolean locked(){ return locked;}
   public void unlock(){locked = false;}
   public double x(){return x;}
   public double y(){return y;}
   public double r(){return r;}
   public double dirX(){return dirX;}
   public double dirY(){return dirY;}
   
   
   public void setVelocity(double vX, double vY)
   {
      dirX = vX;
      dirY = vY;
   }
   
   public void setCoord(int pX, int pY)
   {
      this.x = pX;
      this.y = pY;
   }
   
   
   
   
   

}