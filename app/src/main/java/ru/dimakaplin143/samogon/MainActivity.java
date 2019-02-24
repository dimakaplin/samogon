package ru.dimakaplin143.samogon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dimakaplin143.samogon.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView water = (TextView) findViewById(R.id.water);
        water.setText(AlcoCalc.getWaterVol() + " л");
        TextView outVolume = (TextView) findViewById(R.id.volume);
        outVolume.setText(AlcoCalc.getNextVol() + " л");
        TextView buttle  = (TextView) findViewById(R.id.buttle);
        buttle.setText(AlcoCalc.getAmountBottle());

        final EditText grOutput = (EditText) findViewById(R.id.grOutput);

        Button calcButton  = (Button) findViewById(R.id.calc);
        calcButton.setOnClickListener(onClickCalc);


        grOutput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calcAlco();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }


    private void calcAlco() {
        double volume = 0;
        double beforeAlco = 0;
        double afterAlco = 0;
        EditText vol = (EditText) findViewById(R.id.vol);
        EditText grInput = (EditText) findViewById(R.id.grInput);
        EditText grOutput = (EditText) findViewById(R.id.grOutput);


        try {
            volume = Double.parseDouble(vol.getText().toString().trim());
            beforeAlco = Double.parseDouble(grInput.getText().toString().trim());
            afterAlco = Double.parseDouble(grOutput.getText().toString().trim());

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (beforeAlco < afterAlco) {

            Toast toast = Toast.makeText(getApplicationContext(), "Нельзя увеличить крепость, разбавляя самогон водой", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (volume > 0 && beforeAlco > 0 && afterAlco > 0) {


            AlcoCalc.addWater(volume, beforeAlco, afterAlco);
            TextView water = (TextView) findViewById(R.id.water);
            water.setText(AlcoCalc.getWaterVol() + " л");
            TextView outVolume = (TextView) findViewById(R.id.volume);
            outVolume.setText(AlcoCalc.getNextVol() + " л");
            TextView buttle  = (TextView) findViewById(R.id.buttle);
            buttle.setText(AlcoCalc.getAmountBottle());

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Одно из полей равно 0", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    private final View.OnClickListener onClickCalc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcAlco();

        }
    };

}
