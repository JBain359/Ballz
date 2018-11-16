import java.awt.*;
import java.awt.Color;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Player
{
   protected int x, y,r,mouseX,mouseY;
   protected double  launchX = 0, launchY = 0;
   protected ArrayList<Bullet> bullets = new ArrayList<Bullet>();
   protected Color bg;
   protected boolean isClicked = false;
   protected boolean disabled = false;
   protected Timer clock = new Timer();
   protected boolean finished = true;
   protected double canvasH = 600.00;
   protected boolean newAmmo = false;
   
   public Player(int x,int y)
   {
       
      this.x = x;
      this.y = y;
      this.r = 20;
      bullets.add(new Bullet(this.x,this.y,0,0,Color.red,false));
      //for(int i = 0; i< ; i++) 
      	bullets.add(new Bullet(this.x,this.y,0,0,Color.gray,false));
      //bullets.add(new Bullet(this.x,this.y,0,0,Color.green));
      
   }
   
   public void render(Graphics g)
   {
      
      if(newAmmo)
      {
         getAmmo();
         newAmmo = false;
      }
      for(int i = bullets.size()-1;i>=0;i--)
      {  
         if(!bullets.get(i).locked())
            bullets.get(i).render(g);
      }
      for(int i = 1; i<bullets.size();i++)
      {
      	 if(bullets.get(bullets.size()-1).y()+bullets.get(bullets.size()-1).r()>0){}
         
         if(Math.sqrt(Math.pow(this.x-bullets.get(i-1).x(),2)+Math.pow(this.y-bullets.get(i-1).y(),2))>40 && bullets.get(i).x()==this.x && bullets.get(i).y()==this.y && !bullets.get(i).locked() )
         {
            bullets.get(i).setVelocity(launchX,launchY);
         }
         //else{bullets.get(i).setVelocity(0,0);}
         if(allLanded() && !Ballz.gameOver)
         {
            
         	disabled = false;
         	
         }
         else
         {
         	disabled = true;
         }
         
         
      }
     
      
   }
   
   public ArrayList<Bullet> bullets()
   {
      return bullets;
   }
   
   public void getAmmo()
   {
      bullets.add(new Bullet(this.x,this.y,0,0,Color.gray,true));
   }
   
   public void hitDetect( ArrayList<Blockz> bl, ArrayList<Ammunition> ammo)
   {
      
      for(Bullet b: bullets)
      {
      

         if(b.x()<=50.0)
         {
            b.setCoord(51,(int)b.y());
            b.setVelocity(b.dirX()*-1,b.dirY());
         }
         
         if(b.x() + b.r()>=550.0)
         {
            b.setCoord(549-(int)b.r(),(int)b.y());
            b.setVelocity(b.dirX()*-1,b.dirY());
         }
         
      
         
         
         if(b.y()<=50.0)
         {
            b.setCoord((int)b.x(),50);
            b.setVelocity(b.dirX(),b.dirY()*-1);
         
         }
         
         if(b.y()+b.r()>canvasH-20 && b.dirY()>0)
         {
            
            b.setCoord((int)(b.x()+b.dirX()),(int)canvasH-30);
            
            if(b == bullets.get(0))
            	this.x = (int)b.x();
            	
            if( allLanded())
            {
               
            	for(Bullet bu: bullets)
            	{
            		bu.setCoord((int)bullets.get(0).x(),(int)canvasH-30);
                  bu.unlock();
                  
            	}
               Ballz.nextLevel();
               
            }
            b.setVelocity(0,0);
         }
         
         
         for(Blockz t: bl)
         {
         	/*
            int tLeft = t.x();
            int tRight = t.x()+t.w();
            int tTop = t.y();
            int tBottom = t.y()+t.h();
            */
            
            
            int bLeft = (int)(b.x());
            int bXMid = (int)(b.x()+b.r()/2);
            int bRight = (int)(b.x()+b.r());
            
            int bTop = (int)(b.y());
            int bYMid = (int)(b.y()+b.r()/2);
            int bBottom = (int)(b.y()+b.r());
            
            
            if(t.detect(bXMid,bBottom) || t.detect(bXMid,bTop) || t.detect(bRight,bYMid) || t.detect(bLeft,bYMid))
            {
	            if(t.detect(bXMid,bBottom) || t.detect(bXMid,bTop) )
	            {
	               t.getHit();
	               b.setCoord((int)(b.x()-b.dirX()),(int)(b.y()-b.dirY()));
	            	b.setVelocity(b.dirX(),b.dirY()*-1);
	            }
	            else if(t.detect(bRight,bYMid) || t.detect(bLeft,bYMid))
	            {
	               t.getHit();
	               b.setCoord((int)(b.x()-b.dirX()),(int)(b.y()-b.dirY()));
	               b.setVelocity(b.dirX()*-1,b.dirY());
	            }
	            else
	            {
	            	
	               for(double c = 0; c<= 2*Math.PI;c+=Math.PI/30)
	               {
	                  if(t.detect(bXMid+(1*Math.cos(c)),bYMid+(10*Math.sin(c))))
	                  {
	                     
	                     //b.setCoord((int)(b.x()-b.dirX()),(int)(b.y()-b.dirY()));
	                     b.setVelocity(b.dirX()+12*Math.cos(c),b.dirY()+12*Math.sin(c));
	                     
	                  }
	               }
	            }      
            }
         }
         
         for(int i =0 ; i<ammo.size();i++)
         {
            Ammunition am = ammo.get(i);
            int bLeft = (int)(b.x());
            int bXMid = (int)(b.x()+b.r()/2);
            int bRight = (int)(b.x()+b.r());
            
            int bTop = (int)(b.y());
            int bYMid = (int)(b.y()+b.r()/2);
            int bBottom = (int)(b.y()+b.r());
            
            if(am.detect(bXMid,bBottom) || am.detect(bXMid,bTop) || am.detect(bRight,bYMid) || am.detect(bLeft,bYMid))
            {
               newAmmo = true;
               ammo.remove(am);
            	
            }
            
            
         }
         
      }
   }
   
   public boolean allLanded()
   {
   	int numStopped = 0;
   		for(Bullet b: bullets)
   		{
   			if(b.y() == canvasH-30 )
   				numStopped++;
   		}
  		return numStopped == bullets.size();
   }
   
   public void launch()
   {
      //this.bg = Color.white;
      bullets.get(0).setVelocity(launchX,launchY);
      disabled = true;
      
      
   }
   
   public void disable()
   {
      disabled = true;
   }
   
   public boolean isDisabled()
   {
      return disabled;
   }
   
   public boolean inBounds(int cX, int cY)   
   {
      if(cX>=x && cX<=x+r && cY>=y && cY<=y+r)
      {
       return true;
      }
      return false;
   }
   
   public void determineTraj(int cX, int cY, Graphics g)
   {
      double tX = x+r/2-cX;
      double tY = y+r/2-cY;
      double tH = (int)Math.sqrt((Math.pow(tX,2)+Math.pow(tY,2)));
      
      launchX = (tX/tH)*5;
      launchY = (tY/tH)*5;
      
      g.drawLine(x+r/2,y+r/2,x+(int)Math.round(tX),y+(int)Math.round(tY));
      
   }
   
   public void clicked()
   {
      isClicked = true;
   }
   
   public void unClicked()
   {
      isClicked = false;
   }
   
   public int getX(){return x;}
   public int getY(){return y;}
   public int getR(){return r;}
   
   public boolean isClicked(){return isClicked;}
   
   public void setMouseCoord(int cX, int cY)
   {
      mouseX = cX;
      mouseY = cY;
   }
   
   public int clickedX(){return mouseX;}
   public int clickedY(){return mouseY;}
   public void setBG(Color bg)
   {
   		this.bg = bg;
   }
   
   public static void delay(long n)
	{
		n *= 1000;
		long startDelay = System.nanoTime();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.nanoTime();
	}
}