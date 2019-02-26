package ru.dimakaplin143.samogon;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dimakaplin143.samogon.R;


public class MainActivity extends AppCompatActivity {

    public static boolean firstStart = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.to_note_list:
                Intent intent = new Intent(MainActivity.this, NoteList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Калькулятор самогонщика");

        final TextView waterView = (TextView) findViewById(R.id.water);
        final TextView outVolumeView = (TextView) findViewById(R.id.volume);
        final TextView buttleView  = (TextView) findViewById(R.id.buttle);
        final EditText volEdit = (EditText) findViewById(R.id.vol);
        final EditText grInputEdit = (EditText) findViewById(R.id.grInput);
        final EditText grOutputEdit = (EditText) findViewById(R.id.grOutput);

        if(firstStart) {
            waterView.setText("0 л");
            outVolumeView.setText("0 л");
            buttleView.setText("0");
        }
        else {
            waterView.setText(DataStorage.getCalcState("water"));
            outVolumeView.setText(DataStorage.getCalcState("outVolume"));
            buttleView.setText(DataStorage.getCalcState("buttle"));
            volEdit.setText(DataStorage.getCalcState("vol"));
            grInputEdit.setText(DataStorage.getCalcState("grInput"));
            grOutputEdit.setText(DataStorage.getCalcState("grOutput"));
        }

        Button calcButton  = (Button) findViewById(R.id.calc);
        calcButton.setOnClickListener(onClickCalc);


        grOutputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    @Override
    protected void onPause() {
        super.onPause();
        firstStart = false;
        TextView waterView = (TextView) findViewById(R.id.water);
        TextView outVolumeView = (TextView) findViewById(R.id.volume);
        TextView buttleView  = (TextView) findViewById(R.id.buttle);
        EditText volEdit = (EditText) findViewById(R.id.vol);
        EditText grInputEdit = (EditText) findViewById(R.id.grInput);
        EditText grOutputEdit = (EditText) findViewById(R.id.grOutput);
        String water = waterView.getText().toString();
        String outVolume = outVolumeView.getText().toString();
        String buttle = buttleView.getText().toString();
        String vol = volEdit.getText().toString();
        String grInput = grInputEdit.getText().toString();
        String grOutput = grOutputEdit.getText().toString();

        DataStorage.addCalcState("water", water);
        DataStorage.addCalcState("outVolume", outVolume);
        DataStorage.addCalcState("buttle", buttle);
        DataStorage.addCalcState("vol", vol);
        DataStorage.addCalcState("grInput", grInput);
        DataStorage.addCalcState("grOutput", grOutput);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView waterView = (TextView) findViewById(R.id.water);
        String water = waterView.getText().toString();
        TextView outVolumeView = (TextView) findViewById(R.id.volume);
        String outVolume = outVolumeView.getText().toString();
        TextView buttleView  = (TextView) findViewById(R.id.buttle);
        String buttle = buttleView.getText().toString();
        outState.putString("water", water);
        outState.putString("outVolume", outVolume);
        outState.putString("buttle", buttle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView waterView = (TextView) findViewById(R.id.water);
        TextView outVolumeView = (TextView) findViewById(R.id.volume);
        TextView buttleView  = (TextView) findViewById(R.id.buttle);
        waterView.setText(savedInstanceState.getString("water"));
        outVolumeView.setText(savedInstanceState.getString("outVolume"));
        buttleView.setText(savedInstanceState.getString("buttle"));
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
            DataStorage.addCalcState("water", AlcoCalc.getWaterVol() + " л");
            water.setText(DataStorage.getCalcState("water"));
            TextView outVolume = (TextView) findViewById(R.id.volume);
            DataStorage.addCalcState("outVolume", AlcoCalc.getNextVol() + " л");
            outVolume.setText(DataStorage.getCalcState("outVolume"));
            TextView buttle  = (TextView) findViewById(R.id.buttle);
            DataStorage.addCalcState("buttle", AlcoCalc.getAmountBottle());
            buttle.setText(DataStorage.getCalcState("buttle"));

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
