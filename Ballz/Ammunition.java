import java.awt.*;
import java.awt.Color;

public class Ammunition 
{
   protected int x,y,r,level;
   protected Color bg;
   public Ammunition(int x, int y, Color c)
   {
      this.x = x;
      this.y = y;
      this.r = 50;
      this.bg = c;
      this.level = 1;
   }
   
   public void render(Graphics g)
   {
      g.setColor(bg);
      g.fillOval(x,y,r,r);
      g.setColor(bg.darker());
      g.fillOval(x+r/2-10,y+r/2-10,20,20);
      if(this.y<70*level + 60)
      {
         this.y+=5;
      }
   }
   
   public int getLevel(){return level;} 
   
   public void lower()
   {
      level++;
   }
   
   public boolean detect(double px, double py)
   {
   		if(this.x<px && px<this.x+this.r  && this.y<py && py<this.y+this.r)
   		{
   			return true;
   		}
   		return false;
   }
}