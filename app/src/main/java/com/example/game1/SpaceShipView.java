package com.example.game1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SpaceShipView extends View {

    private  Bitmap ship[] = new Bitmap[2];
    private int shipX = 10;
    private  int shipY;
    private  int shipSpeed;

    private int canvasWidth, canvasHeight;
    private  int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private  int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private  int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    private int score, lifeCounter;

    private boolean touch = false;

    private  Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private  Bitmap life[] = new Bitmap[2];

    public SpaceShipView(Context context) {
        super(context);
        ship[0] = BitmapFactory.decodeResource(getResources(),R.drawable.ship);
        ship[1] = BitmapFactory.decodeResource(getResources(),R.drawable.ship1);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.stars);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        shipY = 550;
        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minShipY = ship[0].getHeight();
        int maxShipY = canvasHeight - ship[0].getHeight() * 3;
        shipY = shipY + shipSpeed;

        if(shipY < minShipY){
            shipY = minShipY;
        }
        if(shipY > maxShipY){
            shipY = maxShipY;
        }
        shipSpeed = shipSpeed + 2;

        if(touch){
            canvas.drawBitmap(ship[1], shipX, shipY, null);
            touch = false;
        }
        else{
            canvas.drawBitmap(ship[0], shipX, shipY,null);
        }



        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX, yellowY)){
            score = score +10;
            yellowX = - 100;
        }

        if(yellowX < 0){
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() *(maxShipY - minShipY)) + minShipY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);



        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX, greenY)){
            score = score +20;
            greenX = - 100;
        }

        if(greenX < 0){
            greenX = canvasWidth +21;
            greenY = (int) Math.floor(Math.random() *(maxShipY - minShipY)) + minShipY;
        }
        canvas.drawCircle(greenX, greenY, 30, greenPaint);



        redX = redX - redSpeed;

        if(hitBallChecker(redX, redY)){
            redX = - 100;
            lifeCounter--;

            if(lifeCounter == 0){
                Toast.makeText(getContext(), "Game over", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if(redX < 0){
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random() *(maxShipY - minShipY)) + minShipY;
        }
        canvas.drawCircle(redX, redY, 30, redPaint);



        canvas.drawText("Score :  " + score, 20,80, scorePaint);

        for(int i = 0; i < 3; i++){
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
             if(i< lifeCounter){
                 canvas.drawBitmap(life[0], x, y, null);
             }else {
                 canvas.drawBitmap(life[1], x, y, null);
             }
        }
    }

    public boolean hitBallChecker(int x, int y)
    {
        if(shipX < x && x < (shipX + ship[0].getWidth()) && shipY < y  &&  y < (shipY +ship[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            shipSpeed = -22;
        }
        return  true;
    }
}
