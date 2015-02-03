import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class uram_graph extends PApplet {

int screen_width = 1000;//\u30b9\u30af\u30ea\u30fc\u30f3\u6a2a\u5e45
int screen_height = 1000;//\u30b9\u30af\u30ea\u30fc\u30f3\u7e26\u5e45
int dot_width = 4;//\u30c9\u30c3\u30c8\u306e\u5927\u304d\u3055

int center_width = screen_width/2;//\u30b9\u30af\u30ea\u30fc\u30f3\u306e\u4e2d\u5fc3\u4f4d\u7f6e\uff08\uff58\uff09
int center_height = screen_height/2;//\u30b9\u30af\u30ea\u30fc\u30f3\u306e\u4e2d\u5fc3\u4f4d\u7f6e(\uff59)

ArrayList<Dot> Dots;//\u30c9\u30c3\u30c8\u304c\u683c\u7d0d\u3055\u308c\u308b\u30ea\u30b9\u30c8

boolean right = false;//\u87ba\u65cb\u3092\u63cf\u304f\u306e\u306b\u4f7f\u3046
boolean down = false;//\u87ba\u65cb\u3092\u63cf\u304f\u306e\u306b\u4f7f\u3046
boolean left = false;//\u87ba\u65cb\u3092\u63cf\u304f\u306e\u306b\u4f7f\u3046
boolean up = false;//\u87ba\u65cb\u3092\u63cf\u304f\u306e\u306b\u4f7f\u3046

Dot Standard;//\u57fa\u6e96\u30c9\u30c3\u30c8 \u89d2\u306e\u30c9\u30c3\u30c9\u304c\u57fa\u6e96\u30c9\u30c3\u30c9\u3068\u306a\u308b

int number = 2;//\u73fe\u5728\u306e\u756a\u53f7
int i = 1;//\u87ba\u65cb\u3092\u63cf\u304f\u306e\u306b\u4f7f\u3046

ArrayList<Integer> prime_list;//\u7d20\u6570\u30ea\u30b9\u30c8

public void setup(){//processing\u306e\u521d\u671f\u5316\u51e6\u7406
  println("start");
  CreatePrimeList();//\u7d20\u6570\u30ea\u30b9\u30c8\u306e\u4f5c\u6210
  println("PrimeList is Created");
  Dots = new ArrayList<Dot>();//\u30c9\u30c3\u30c8\u30ea\u30b9\u30c8\u306e\u5b9a\u7fa9
  Dots.add(new Dot(center_width,center_height,1));//\u30b9\u30af\u30ea\u30fc\u30f3\u306e\u4e2d\u5fc3\u306b1\u756a\u76ee\u306e\u30c9\u30c3\u30c8\u3092\u5b9a\u7fa9
  size(screen_width,screen_height);//\u30b9\u30af\u30ea\u30fc\u30f3\u306e\u5927\u304d\u3055\u5b9a\u7fa9
  Dots.get(0).Draw();//1\u756a\u76ee\u306e\u30c9\u30c3\u30c8\u3092\u63cf\u753b
  Standard = Dots.get(0);//\u57fa\u6e96\u30c9\u30c3\u30c8\u30921\u756a\u76ee\u306e\u30c9\u30c3\u30c8\u306b\u3059\u308b
  right = true;//\u53f3\u5411\u304d\u306b\u6b21\u306e\u30c9\u30c3\u30c9\u3092\u8a2d\u7f6e\u3057\u305f\u3044

  for(int x = 0;x<(screen_width/dot_width) * (screen_height/dot_width);x++){//\u30c9\u30c3\u30c9\u306e\u8a2d\u7f6e\u3092\u5148\u306b\u3057\u3066\u304a\u304f
    AddDot();//\u30c9\u30c3\u30c9\u306e\u8ffd\u52a0
  }
}

public void CreatePrimeList(){//\u7d20\u6570\u30ea\u30b9\u30c8\u4f5c\u6210(\u5f15\u7528)
  prime_list = new ArrayList<Integer>();//\u7d20\u6570\u30ea\u30b9\u30c8\u306e\u5b9a\u7fa9
  prime_list.add(1);//1\u8ffd\u52a0
  prime_list.add(2);//2\u8ffd\u52a0
  prime_list.add(3);//3\u8ffd\u52a0
  println("prime is createing");
  for(int n = 5;n <= (screen_width/dot_width) * (screen_height/dot_width);n+=2){//5\u301c\u30b9\u30af\u30ea\u30fc\u30f3\u306b\u53ce\u307e\u308b\u7bc4\u56f2\u306e\u6570\u3060\u3051\u7d20\u6570\u3092\u767b\u9332
    boolean is_prime = true;//n\u3092\u7d20\u6570\u3068\u4eee\u5b9a\u3059\u308b
    for(int i=1; prime_list.get(i) * prime_list.get(i) <= n;i++){
      if(n % prime_list.get(i) == 0){
        is_prime = false;
        break ;
      }
    }
    if(is_prime){
      prime_list.add(n);
      print(" "+n);
    }
  }
}

public void draw(){
  for(Dot dot: Dots){
    dot.Draw();
  }
  //AddDot();
}

public void AddDot(){
  int x=0,y=0;
  //println("now number = " + number + " current standard = " +Standard.number);
  boolean  only_flag = true;
  if(right&&only_flag){
    only_flag = false;
    x = Standard.x + dot_width * i;
    y = Standard.y;
    if(is_corner(number)){
      //println("right - down");
      Standard = new Dot(x,y,number);
      right = false;
      down = true;
    }
  }
  if(down&&only_flag){
    only_flag = false;
    x = Standard.x ;
    y = Standard.y + dot_width * i;
    if(is_corner(number)){
      //println("down - left");
      Standard = new Dot(x,y,number);
      down = false;
      left = true;
    }
  }
  if(left&&only_flag){
    only_flag = false;
    x = Standard.x - dot_width * i;
    y = Standard.y;
    if(is_corner(number)){
      //println("left - up");
      Standard = new Dot(x,y,number);
      left = false;
      up = true;
    }
  }
  if(up&&only_flag){
    only_flag = false;
    x = Standard.x ;
    y = Standard.y - dot_width * i;
    if(is_corner(number)){
      //println("up - right");
      Standard = new Dot(x,y,number);
      up = false;
      right = true;
    }
  }  
  Dots.add(new Dot(x,y,number));

  if(is_corner(number)){
    i = 1;
  }else{
    i++;
  }
  number++;
}
int count_rgb = 0;
class Dot{
  int x,y;
  int number;
  boolean is_prime;
  int rgb;

  Dot(int x,int y,int n){
    this.x = x;
    this.y = y;
    number = n;

    int prime_list_index = prime_list.indexOf(n);
    is_prime = (prime_list_index != -1);
    count_rgb++;
    if(count_rgb >= 255) count_rgb = 0;
    rgb = count_rgb = 0;

  }

  public void Draw(){
    int left = x + dot_width/2;
    int top = y + dot_width/2;
    if(is_prime){
      fill(0,255,255);
    }else{
      fill(this.rgb,this.rgb,this.rgb);
    }
    rect(left,top,dot_width,dot_width);
  }
};

public boolean is_corner(int number){
  int count = 1;
  int is_munber = 1;
  int add_number = 0;
  while(number >= is_munber){
    //print("  "+ is_munber+" "+number+"  "); 
    if(is_munber == number)return true;
    if(count %2 == 0){
      add_number++;
    }
    is_munber += add_number;
    count++;
  }
  return false;
}
 


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "uram_graph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
