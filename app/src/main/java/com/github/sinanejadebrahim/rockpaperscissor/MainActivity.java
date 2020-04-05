package com.github.sinanejadebrahim.rockpaperscissor;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewTop, imageViewBottom;
    TextView result, playerScore, computerScore;
    Button[] player1Buttons = new Button[3];
    Button[] comButtons = new Button[3];
    Button restart;
    String p1Choice, comChoice;
    int comScore, pScore;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        stuff();

        //so the restart button is invisible and disabled when the app starts
        restart.setEnabled(false);
        restart.setAlpha(0);

        player1Buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewBottom.setImageResource(R.drawable.scissor);
                p1Choice = "Scissor";
                vibrator.vibrate(30);
                buttonAnimation(0);
                buttonsOff();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comPick();
                    }
                }, 300);
            }
        });
        player1Buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewBottom.setImageResource(R.drawable.paper);
                p1Choice = "Paper";
                vibrator.vibrate(30);
                buttonAnimation(1);
                buttonsOff();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comPick();
                    }
                }, 300);

            }
        });
        player1Buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewBottom.setImageResource(R.drawable.rock);
                p1Choice = "Rock";
                vibrator.vibrate(30);
                buttonAnimation(2);
                buttonsOff();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comPick();
                    }
                }, 300);

            }
        });

    }

    // simple method that chooses a random card for the Computer.
    public void comPick() {
        Random random = new Random();
        int pick = random.nextInt(3) + 1;

        switch (pick) {
            case 1:
                imageViewTop.setImageResource(R.drawable.scissor);
                comChoice = "Scissor";

                comButtonsOff();
                break;
            case 2:
                imageViewTop.setImageResource(R.drawable.paper);
                comChoice = "Paper";

                comButtonsOff();
                break;
            case 3:
                imageViewTop.setImageResource(R.drawable.rock);
                comChoice = "Rock";

                comButtonsOff();
                break;

        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(p1Choice, comChoice);
            }
        }, 700);
    }

    /* this method checks to see who won that round
     *  after that clears the imageview of any background
     * also keeps score af both cpu (computer) and the player
     */

    public void setResult(String p1choice, String comChoice) {
        buttonsOn();
        comButtonsOn();
        if (p1choice.equals(comChoice))
            result.setText("ای بابا");
        else if (p1choice.equals("Scissor") && comChoice.equals("Paper") ||
                (p1choice.equals("Paper") && comChoice.equals("Rock")) ||
                (p1choice.equals("Rock")) && comChoice.equals("Scissor")) {
            result.setText("تو بردی");
            pScore++;
            playerScore.setText("" + pScore);

        } else {
            result.setText("من بردم");
            comScore++;
            computerScore.setText("" + comScore);

        }
        imageViewTop.setImageBitmap(null);
        imageViewBottom.setImageBitmap(null);


        chooseWinner();
    }

    public void stuff() {

        imageViewTop = findViewById(R.id.imageViewTop);
        imageViewBottom = findViewById(R.id.imageViewBottom);

        result = findViewById(R.id.textViewResult);

        player1Buttons[0] = findViewById(R.id.bottomScissor);
        player1Buttons[1] = findViewById(R.id.bottomPaper);
        player1Buttons[2] = findViewById(R.id.bottomRock);

        comButtons[0] = findViewById(R.id.topScissor);
        comButtons[1] = findViewById(R.id.topPaper);
        comButtons[2] = findViewById(R.id.topRock);

        playerScore = findViewById(R.id.playerScore);
        computerScore = findViewById(R.id.comScore);

        restart = findViewById(R.id.restart);
    }
    /* this is just a simple animation for the chosen card.
     * use YOYO library with the link below (or don't, it's not necessary to use a button animation)
     * https://github.com/daimajia/AndroidViewAnimations
     */
    public void buttonAnimation(int clickedButton) {
        YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(0)
                .playOn(player1Buttons[clickedButton]);

    }

    /* this method chooses the winner ( obviously :D) after 5 rounds
     * and gives you the restart button so you could play again
     */
    public void chooseWinner() {
        buttonsOn();
        comButtonsOn();

        if (comScore >= 5) {
            result.setText("باختی اشکال نداره ایشالا دفعه بعدی");
            afterWin();

            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartClicked();
                }
            });
        } else if (pScore >= 5) {
            result.setText("بازیرو بردی");
            afterWin();

            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartClicked();
                }
            });
        } else {
            buttonsOn();
            comButtonsOn();

        }
    }

    /* simple method to turn buttons off and make them kinda transparent
     * so the user can't change he's/her choice.
     */
    public void buttonsOff() {
        for (int i = 0; i < player1Buttons.length; i++) {
            player1Buttons[i].setEnabled(false);
            player1Buttons[i].setAlpha((float) 0.3);

        }

    }
    // remove transparency and enable the buttons
    public void buttonsOn() {
        for (int i = 0; i < player1Buttons.length; i++) {
            player1Buttons[i].setEnabled(true);
            player1Buttons[i].setAlpha(1);

        }

    }

    /*
     just to show that the computer has picked a card
     and then disable the buttons like we did for the user.
     */
    public void comButtonsOff() {
        for (int i = 0; i < comButtons.length; i++) {
            comButtons[i].setEnabled(false);
            comButtons[i].setAlpha((float) 0.3);

        }
    }
    // turn the buttons back on
    public void comButtonsOn() {
        for (int i = 0; i < comButtons.length; i++) {
            comButtons[i].setEnabled(true);
            comButtons[i].setAlpha(1);
        }
    }
    /* because what we do after someone wins the game is completely equal
     * we use this method so that we don't have to repeat this line twice
     */
    public void restartClicked() {
        buttonsOn();
        comButtonsOn();
        comScore = 0;
        pScore = 0;
        playerScore.setText("0");
        computerScore.setText("0");
        restart.setEnabled(false);
        restart.setAlpha(0);
        result.setText("");
    }
    // again i used this method so i wouldn't have to repeat this lines twice.
    public void afterWin() {
        restart.setEnabled(true);
        restart.setAlpha(1);
        buttonsOff();
        comButtonsOff();
    }
}