package com.example.myhp.hitthebasket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

public class Hitit extends Activity implements View.OnTouchListener{
    MyBringBackSurface ourSurfaceView;
    float x,y,sX,sY,fX,fY,dX,dY,aniX,aniY,scaledX,scaledY;

    Bitmap test,plus,hit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourSurfaceView=new MyBringBackSurface(this);
        ourSurfaceView.setOnTouchListener(this);
       // d=0;
        x=0;
        y=0;
        sX=0;
        sY=0;
        fX=0;
        dX=dY=fY=0;
        aniX=0;
        aniY=0;
        scaledX=0;
        scaledY=0;
        test= BitmapFactory.decodeResource(getResources(),R.drawable.greenball);
        plus= BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        hit=BitmapFactory.decodeResource(getResources(),R.drawable.hit);
        setContentView(ourSurfaceView);


    }


    @Override
    protected void onPause() {
        super.onPause();
        ourSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourSurfaceView.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x=event.getX();
        y=event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sX=event.getX();
                sY=event.getY();
                fX=fY=aniY=aniX=scaledX=scaledY=dX=dY=0;
                break;
            case MotionEvent.ACTION_UP:
                fX=event.getX();
                fY=event.getY();
                dX=fX-sX;
                dY=fY-sY;
                scaledX=dX/20;
                scaledY=dY/20;
                break;
        }
        return true;
    }

    class MyBringBackSurface extends SurfaceView implements Runnable{
        SurfaceHolder ourHolder;
        SoundPool sound;
        Canvas canvas;
        Thread ourThread=null;
        int d=0,k=0;
        int l,m,ep;
        float f,g;
        String s;
        Paint p;
        boolean isRunning=false;
        public MyBringBackSurface(Context context) {
            super(context);
            ourHolder=getHolder();
         p=new Paint();
            p.setTextSize(50);
            p.setARGB(100, 0, 0, 0);
            p.setTypeface(Typeface.SERIF);
            sound=new SoundPool(5, AudioManager.STREAM_MUSIC,0);
            ep=sound.load(Hitit.this,R.raw.explosion,1);
            ourThread=new Thread(this);
            ourThread.start();

        }

        public void  randomize(){

                double db = Math.random();
                f = (float) (db * ((6*canvas.getWidth())/7));
                db=Math.random();
            m=(int)(db*(1.99));
            if(m==0)
                g=0;
            if(m==1)
                g=canvas.getHeight()-plus.getHeight();

            d=1;
            l=0;

        }
        @Override
        public void run() {

            while(isRunning)
            {
                if(!ourHolder.getSurface().isValid())
                    continue;
                canvas=ourHolder.lockCanvas();
                canvas.drawRGB(2, 2, 150);

if(d==0)
    randomize();

                    //canvas=ourHolder.lockCanvas();
                    canvas.drawBitmap(plus,f,g,null);


              if(x!=0&&y!=0){
                 canvas.drawBitmap(test,x-(test.getWidth()/2),y-(test.getHeight()/2),null);
                }
                if(sX!=0&sY!=0){
                    //      canvas.drawBitmap(plus,sX-(plus.getWidth()/2),sY-(plus.getHeight()/2),null);
                }
               if(fX!=0&&fY!=0){
                    canvas.drawBitmap(test,fX-(test.getWidth()/2)-aniX,fY-(test.getHeight()/2)-aniY,null);
                    //      canvas.drawBitmap(plus,fX-(plus.getWidth()/2),fY-(plus.getHeight()/2),null);
               }

                check();
                aniX=aniX+scaledX;
                aniY=aniY+scaledY;

                s="Score: "+k;
                canvas.drawText(s,canvas.getWidth()/7,canvas.getHeight()/2,p);
                ourHolder.unlockCanvasAndPost(canvas);

            }
        }

        public void check(){
            float z,w,u,v;
            z=fX-(test.getWidth()/2)-aniX;
            w=fY-(test.getHeight()/2)-aniY;
            u=plus.getWidth();
            v=plus.getHeight();

           if((z>=f-u/2&&z<=f+u)&&(w>=g&&w<=g+v)&&(l!=1)){
               d=0;
               k++;
               l=1;
                sound.play(ep,1,1,0,0,1);
               //canvas.drawBitmap(hit, canvas.getWidth() / 2, canvas.getHeight() / 2, null);


           }



        }

        public void pause(){
            isRunning=false;
            while(true)
            {
                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ourThread=null;
        }

        public void resume(){
            isRunning=true;
            ourThread=new Thread(this);
            ourThread.start();
        }
    }

}
