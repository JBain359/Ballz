import java.awt.*;
import java.awt.Color;

class Blockz extends Rectangle
{
   protected int x, y, w, h, hp, maxHp, level;
   protected Color bg;
   
   public Blockz()
   {
      this.x = 60;
      this.y = 60;
      this.w = 10;
      this.h = 10;
      this.hp = 5;
      this.maxHp = this.hp;
      this.level = 1;
   }
   
   public Blockz(int x, int y, int w, int h, int hp, Color bg)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      this.hp = hp;
      this.bg = bg;
      this.maxHp = this.hp;
      this.level = 1;
   }
   
   public int x(){return x;}
   public int y(){return y;}
   public int w(){return w;}
   public int h(){return h;}
      
   public void render(Graphics g)
   {
      //draws the block
      int hpInc = hp*w/2/maxHp ;
      g.setColor(bg);
      g.fillRect(x,y,w,h);
      
      g.setColor(bg.brighter());
      Polygon NE = new Polygon();
      NE.addPoint(x,y);
      NE.addPoint(x+10,y);
      NE.addPoint(x,y+10);
      
      Polygon SE = new Polygon();
      SE.addPoint(x,y+h);
      SE.addPoint(x+10,y+h);
      SE.addPoint(x,y+h-10);
      
      Polygon NW = new Polygon();
      NW.addPoint(x+w,y);
      NW.addPoint(x+w-10,y);
      NW.addPoint(x+w,y+10);
      
      Polygon SW = new Polygon();
      SW.addPoint(x+w,y+h);
      SW.addPoint(x+w-10,y+h);
      SW.addPoint(x+w,y+h-10);
      
      g.fillPolygon(NE);
      g.fillPolygon(SE);
      g.fillPolygon(NW);
      g.fillPolygon(SW);
      
      Color hpBG = bg;
         
      for(int c = 0; c< hp/4;c++)
      {
         hpBG = hpBG.darker();
         g.setColor(hpBG);
      }
      if(hp!=0)
      g.fillRect(x+w/2 -(hpInc),y+h/2-10,w-w +  2*(hpInc),20);
      
      //g.drawString(""+hp,x+w/2 -3,y+h/2+3);
      //shoves the blocks down 
      if(this.y<70*level + 60)
      {
         this.y+=5;
      }
   }
   
   public void getHit()
   {
      this.hp--;
      //shift the color by a little
   }
   
   public void lower()
   {
      level++;
   }
   
   public int getHP(){return this.hp;}
   public int getMaxHp(){return this.maxHp;}
   public int getLevel(){return level;}   
   public boolean detect(double px, double py)
   {
   		if(this.x<px && px<this.x+this.w  && this.y<py && py<this.y+this.h)
   		{
   			return true;
   		}
   		return false;
   }
}