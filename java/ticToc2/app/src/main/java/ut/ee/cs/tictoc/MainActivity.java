package ut.ee.cs.tictoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageView[] mBlocks = new ImageView[9];
    private TextView mDisplay;

    private enum TURN {CIRCLE, CROSS}

    private TURN mTurn;
    private int mExitCounter = 0;
    private int mStatusCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeScreen();
        initialize();
    }

    private void makeScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void initialize() {
        ImageView exit = (ImageView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExitCounter == 1) {
                    finish();
                    System.exit(0);
                } else {
                    mExitCounter++;
                    Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDisplay = (TextView) findViewById(R.id.display_board);
        ImageView replay = (ImageView) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starter = getIntent();
                finish();
                starter.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(starter);
            }
        });
        for (int position = 0; position < 9; position++) {
            int resId = getResources().getIdentifier("block_" + (position + 1), "id", getPackageName());
            mBlocks[position] = (ImageView) findViewById(resId);
            final int finalPosition = position;
            mBlocks[position].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTurn(finalPosition);
                }
            });
        }
    }

    private void switchTurn(int position) {
        if (mTurn == TURN.CIRCLE) {
            mBlocks[position].setImageResource(R.drawable.circle);
            mBlocks[position].setId(logic.CIRCLE);
            mTurn = TURN.CROSS;
            mDisplay.setText("CROSS's turn");
        } else {
            mBlocks[position].setImageResource(R.drawable.cross);
            mBlocks[position].setId(logic.CROSS);
            mTurn = TURN.CIRCLE;
            mDisplay.setText("CIRCLE's turn");
        }
        mBlocks[position].setEnabled(false);
        mStatusCounter++;
        if (logic.isCompleted(position + 1, mBlocks)) {
            mDisplay.setText(logic.sWinner + " won");
            displayStick(logic.sSet);
            disableAll();
        }else if (mStatusCounter==9){
            mDisplay.setText("DRAW. Try again");
        }
    }

    private void displayStick(int stick) {
        View view;
        switch (stick) {
            case 1:
                view = findViewById(R.id.top_horizontal);
                break;
            case 2:
                view = findViewById(R.id.center_horizontal);
                break;
            case 3:
                view = findViewById(R.id.bottom_horizontal);
                break;
            case 4:
                view = findViewById(R.id.left_vertical);
                break;
            case 5:
                view = findViewById(R.id.center_vertical);
                break;
            case 6:
                view = findViewById(R.id.right_vertical);
                break;
            case 7:
                view = findViewById(R.id.left_right_diagonal);
                break;
            case 8:
                view = findViewById(R.id.right_left_diagonal);
                break;
            default://which will never happen
                view = findViewById(R.id.top_horizontal);
        }
        view.setVisibility(View.VISIBLE);
    }

    private void disableAll() {
        for (int i = 0; i < 9; i++)
            mBlocks[i].setEnabled(false);
    }

    @Override
    public void onBackPressed() {

    }
}
