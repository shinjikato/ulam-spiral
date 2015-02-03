int screen_width = 1000;//スクリーン横幅
int screen_height = 1000;//スクリーン縦幅
int dot_width = 4;//ドットの大きさ

int center_width = screen_width/2;//スクリーンの中心位置（ｘ）
int center_height = screen_height/2;//スクリーンの中心位置(ｙ)

ArrayList<Dot> Dots;//ドットが格納されるリスト

boolean right = false;//螺旋を描くのに使う
boolean down = false;//螺旋を描くのに使う
boolean left = false;//螺旋を描くのに使う
boolean up = false;//螺旋を描くのに使う

Dot Standard;//基準ドット 角のドッドが基準ドッドとなる

int number = 2;//現在の番号
int i = 1;//螺旋を描くのに使う

ArrayList<Integer> prime_list;//素数リスト

void setup(){//processingの初期化処理
  println("start");
  CreatePrimeList();//素数リストの作成
  println("PrimeList is Created");
  Dots = new ArrayList<Dot>();//ドットリストの定義
  Dots.add(new Dot(center_width,center_height,1));//スクリーンの中心に1番目のドットを定義
  size(screen_width,screen_height);//スクリーンの大きさ定義
  Dots.get(0).Draw();//1番目のドットを描画
  Standard = Dots.get(0);//基準ドットを1番目のドットにする
  right = true;//右向きに次のドッドを設置したい

  for(int x = 0;x<(screen_width/dot_width) * (screen_height/dot_width);x++){//ドッドの設置を先にしておく
    AddDot();//ドッドの追加
  }
}
void CreatePrimeList(){//素数リスト作成(引用)
  prime_list = new ArrayList<Integer>();//素数リストの定義
  prime_list.add(1);//1追加
  prime_list.add(2);//2追加
  prime_list.add(3);//3追加
  println("prime is createing");
  for(int n = 5;n <= (screen_width/dot_width) * (screen_height/dot_width);n+=2){//5〜スクリーンに収まる範囲の数だけ素数を登録
    boolean is_prime = true;//nを素数と仮定する
    for(int i=1; prime_list.get(i) * prime_list.get(i) <= n;i++){//n以下の数の二乗までの値で
      if(n % prime_list.get(i) == 0){//nを割った時あまりがないなら
        is_prime = false;//そんな奴素数じゃあらへん!
        break ;//素数じゃないならもうfor文回したくない
      }
    }
    if(is_prime){//もしも素数なら
      prime_list.add(n);//素数リストに追加
      //print(" "+n);//素数の表示
    }
  }
}

void draw(){//描画処理
  for(Dot dot: Dots){//すべてのドッドを取り出す
    dot.Draw();//ドットの描画
  }
  //AddDot();//３３〜35をコメントアウトしてこっちをコメントインすると，随時にどっと追加(遅いけどね)
}

void AddDot(){//ドットの追加
  int x=0,y=0;
  //println("now number = " + number + " current standard = " +Standard.number);
  boolean  only_flag = true;//他の方向に浮気しないで
  if(right&&only_flag){//右方向の時
    only_flag = false;
    x = Standard.x + dot_width * i;
    y = Standard.y;
    if(is_corner(number)){//番号が角の時
      //println("right - down");
      Standard = new Dot(x,y,number);//基準を自分にして
      right = false;//状態変化
      down = true;
    }//以下同じような処理なので省略
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
  Dots.add(new Dot(x,y,number));//設定したx,yにドットを登録

  if(is_corner(number)){//もし角なら
    i = 1;//i=1からスタート
  }else{//もし角じゃないなら
    i++;
  }
  number++;//次の番号へ
}

int count_rgb = 0;//螺旋の方向に色を付けたい

class Dot{
  int x,y;
  int number;
  boolean is_prime;
  int rgb;//螺旋の方向に色を付けたい

  Dot(int x,int y,int n){
    this.x = x;
    this.y = y;
    number = n;

    int prime_list_index = prime_list.indexOf(n);//素数リストの中から番号を調べる
    is_prime = (prime_list_index != -1);//素数リスト内に番号がないと−1が帰ってくる
    count_rgb++;//螺旋の方向色
    if(count_rgb >= 255) count_rgb = 0;//螺旋の方向色のリセット
    rgb = count_rgb;// = 0;=0にしとけば方向色はつかない

  }

  void Draw(){//描画
    int left = x + dot_width/2;//xは左端なので中心に
    int top = y + dot_width/2;//yも上端なので中心に
    if(is_prime){//もしも素数なら
      fill(0,255,255);//色つけよう
    }else{//素数じゃないなら
      fill(this.rgb,this.rgb,this.rgb);//方向色もしくは，色無し
    }
    rect(left,top,dot_width,dot_width);//四角形の描画
  }
};

boolean is_corner(int number){//角判定
  int count = 1;//カウンター
  int is_munber = 1;//比較する番号
  int add_number = 0;//比較する番号にたされる数
  while(number >= is_munber){//比較する番号が番号より低い時ループ
    //print("  "+ is_munber+" "+number+"  "); 
    if(is_munber == number)return true;//もし比較する番号と番号がおなじなら、その番号は角だよん！
    if(count %2 == 0){//２回に１回はたされる数が増加
      add_number++;
    }
    is_munber += add_number;//比較する番号に値をたして次へ
    count++;
  }
  return false;
}