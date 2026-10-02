package com.livingwilds.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.content.*;
import java.util.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setNavigationBarColor(Color.rgb(11,18,13));
        setContentView(new WildsView(this));
    }

    static class WildsView extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        Random rng = new Random();
        Handler handler = new Handler();
        int level=8, xp=420, xpMax=600, hp=86, energy=71, camp=3, day=4;
        int wood=32, berries=8, discoveries=3;
        boolean rare=false, running=false;
        ArrayList<String> feed = new ArrayList<>();

        WildsView(Context c) {
            super(c);
            setBackgroundColor(Color.rgb(11,18,13));
            feed.add("🌲 Lumi is exploring the forest.");
            feed.add("⭐ Lumi reached Level 8.");
            feed.add("🪵 Wood ×32 and Berry ×8 stored.");
        }

        void txt(Canvas c, String s, float x, float y, float size, int color, boolean bold) {
            p.setColor(color); p.setTextSize(size);
            p.setTypeface(Typeface.create("sans", bold ? Typeface.BOLD : Typeface.NORMAL));
            c.drawText(s,x,y,p);
        }
        void round(Canvas c,float l,float t,float r,float b,float rad,int color){
            p.setColor(color); c.drawRoundRect(l,t,r,b,rad,rad,p);
        }

        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            float w=getWidth(), h=getHeight();
            // Scene
            p.setShader(new LinearGradient(0,0,0,h*.58f,Color.rgb(124,164,143),Color.rgb(209,202,151),Shader.TileMode.CLAMP));
            c.drawRect(0,0,w,h*.58f,p); p.setShader(null);
            p.setColor(Color.rgb(73,99,72)); c.drawOval(-w*.15f,h*.25f,w*.62f,h*.68f,p);
            p.setColor(Color.rgb(47,69,49)); c.drawOval(w*.38f,h*.23f,w*1.18f,h*.69f,p);
            p.setColor(Color.rgb(37,55,39)); c.drawRect(0,h*.55f,w,h*.7f,p);
            p.setColor(Color.rgb(48,72,48)); c.drawRect(0,h*.62f,w,h*.73f,p);

            // trees
            for(int i=0;i<9;i++){
                float x=(i*137+31)%((int)w);
                float base=h*.58f;
                p.setColor(Color.rgb(84,61,42)); c.drawRect(x-7,base-90,x+7,base,p);
                p.setColor(Color.rgb(31,61,44)); c.drawCircle(x,base-112,38,p);
                p.setColor(Color.rgb(43,81,54)); c.drawCircle(x+22,base-92,30,p);
            }

            // river/path
            p.setColor(Color.rgb(107,161,165));
            Path river=new Path(); river.moveTo(w*.64f,h*.7f); river.cubicTo(w*.58f,h*.62f,w*.78f,h*.52f,w*.72f,h*.4f);
            river.lineTo(w*.91f,h*.4f); river.cubicTo(w*.94f,h*.55f,w*.75f,h*.67f,w*.79f,h*.73f); river.close(); c.drawPath(river,p);

            // camp
            float cx=w*.72f, cy=h*.43f;
            p.setColor(Color.rgb(107,75,52)); c.drawRect(cx-68,cy,cx+68,cy+62,p);
            Path roof=new Path(); roof.moveTo(cx-84,cy+3); roof.lineTo(cx,cy-66); roof.lineTo(cx+84,cy+3); roof.close();
            p.setColor(Color.rgb(58,43,35)); c.drawPath(roof,p);
            p.setColor(Color.rgb(55,39,30)); c.drawRoundRect(cx-14,cy+28,cx+14,cy+62,7,7,p);

            // Lumi
            float lx=w*.39f, ly=h*.51f;
            p.setColor(Color.rgb(201,216,145)); c.drawOval(lx-48,ly-37,lx+45,ly+39,p);
            Path ear1=new Path(); ear1.moveTo(lx-38,ly-27); ear1.lineTo(lx-55,ly-68); ear1.lineTo(lx-8,ly-38); ear1.close(); c.drawPath(ear1,p);
            Path ear2=new Path(); ear2.moveTo(lx+28,ly-32); ear2.lineTo(lx+56,ly-67); ear2.lineTo(lx+50,ly-12); ear2.close(); c.drawPath(ear2,p);
            p.setColor(Color.rgb(24,32,25)); c.drawCircle(lx-20,ly-5,6,p); c.drawCircle(lx+21,ly-5,6,p);
            p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(5); c.drawArc(lx-13,ly+3,lx+20,ly+28,15,110,false,p); p.setStyle(Paint.Style.FILL);
            p.setColor(Color.rgb(126,150,89)); c.drawOval(lx+34,ly+3,lx+93,ly+30,p);

            // top HUD
            round(c,14,14,w-14,62,20,Color.argb(170,7,12,8));
            txt(c,"🌲  WHISPERING FOREST",28,43,14,Color.WHITE,true);
            txt(c,"Lv "+level+"   ⭐ "+xp+"/"+xpMax+"   ⛺ "+camp,w-205,43,12,Color.WHITE,true);

            // bottom cards
            float top=h*.72f;
            round(c,14,top,w-14,h-14,22,Color.rgb(20,29,22));
            txt(c,"LUMI",30,top+31,20,Color.rgb(238,242,232),true);
            txt(c,"Forestling  •  Auto-survivor",30,top+52,12,Color.rgb(170,181,164),false);
            txt(c,"❤️ "+hp+"%",30,top+82,12,Color.rgb(210,220,205),true);
            round(c,75,top+73,w*.47f,top+81,6,Color.rgb(42,54,44));
            round(c,75,top+73,75+(w*.47f-75)*hp/100f,top+81,6,Color.rgb(183,217,139));
            txt(c,"⚡ "+energy+"%",w*.50f,top+82,12,Color.rgb(210,220,205),true);
            round(c,w*.59f,top+65,w-28,top+108,14,Color.rgb(183,217,139));
            txt(c,"LET LUMI LIVE",w*.59f+17,top+92,12,Color.rgb(16,23,15),true);
            txt(c,"While you were away",30,top+132,14,Color.rgb(238,242,232),true);
            String latest=feed.size()>0?feed.get(0):"The forest is quiet.";
            txt(c,latest,30,top+155,12,Color.rgb(170,181,164),false);
            txt(c,"🪵 "+wood+"   🍓 "+berries+"   📖 "+discoveries+"/20",30,top+180,12,Color.rgb(201,216,145),true);
            if(rare){
                round(c,w-190,top+124,w-28,top+164,13,Color.rgb(52,50,33));
                txt(c,"✨ RARE DISCOVERY",w-176,top+149,11,Color.rgb(241,226,151),true);
            }
        }

        @Override public boolean onTouchEvent(android.view.MotionEvent e) {
            if(e.getAction()!=MotionEvent.ACTION_UP) return true;
            float w=getWidth(), h=getHeight(), top=h*.72f;
            if(e.getY()>top+55 && e.getY()<top+115 && e.getX()>w*.56f){
                simulate();
            }
            invalidate(); return true;
        }

        void simulate(){
            if(running)return; running=true;
            int hours=2+rng.nextInt(6);
            energy=Math.max(18,energy-(8+rng.nextInt(16)));
            hp=Math.max(25,hp-rng.nextInt(12));
            int gain=70+rng.nextInt(180); xp+=gain;
            wood+=20+rng.nextInt(36); berries+=4+rng.nextInt(9);
            if(xp>=xpMax){
                xp-=xpMax; level++; xpMax+=120; camp=Math.min(9,level/4+1);
                feed.add(0,"⭐ Level "+level+" reached — Lumi grew stronger.");
            } else {
                String[] events={
                    "🌲 Expedition finished — Lumi brought back useful materials.",
                    "🐾 New encounter — a Mosskin watched Lumi from the trees.",
                    "⛏️ Resource found — an iron vein was uncovered.",
                    "🌧️ Weather changed — rain moved through the forest.",
                    "🏕️ Camp improved itself using gathered materials."
                };
                feed.add(0,events[rng.nextInt(events.length)]);
            }
            if(rng.nextFloat()<.16f){
                rare=true; discoveries++;
                feed.add(0,"✨ RARE DISCOVERY — something unknown was spotted in the forest.");
            }
            while(feed.size()>4)feed.remove(feed.size()-1);
            energy=Math.min(100,energy+6);
            running=false; invalidate();
        }
    }
}
