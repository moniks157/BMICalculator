package pl.edu.pwr.maczkowiak.lab1.bmicalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ShareActionProvider;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_MASS_EDIT = "mass";
    private static final String PREFERENCES_HEIGHT_EDIT = "height";
    private static final String PREFERENCES_SWITCH = "switch";

    @BindView(R.id.mass_text) TextView mass_text;
    @BindView(R.id.height_text) TextView height_text;
    @BindView(R.id.button_count) Button button_count;
    @BindView(R.id.switch_units) Switch switch_units;
    @BindView(R.id.height_edit) EditText height_edit;
    @BindView(R.id.mass_edit) EditText mass_edit;
    @BindView(R.id.value_text) TextView value_text;
    @BindView(R.id.bmi_text) TextView bmi_text;

   // @BindView(R.id.save) MenuItem save;
    @BindView(R.id.share) ImageButton share_button;

    @BindColor(R.color.red) int red;
    @BindColor(R.color.burgundy) int burgundy;
    @BindColor(R.color.orange) int orange;
    @BindColor(R.color.yellow) int yellow;
    @BindColor(R.color.green) int green;
    @BindColor(R.color.violet) int violet;

    @BindString(R.string.share_message) String share_message;
    @BindString(R.string.smile) String smile;

    PopupWindow pw;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

        SwitchChange();

        restore();

        //enableSave();

        changeShare();


    }

    public void save()
    {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        String massSave = mass_edit.getText().toString();
        preferencesEditor.putString(PREFERENCES_MASS_EDIT, massSave);
        String heightSave = height_edit.getText().toString();
        preferencesEditor.putString(PREFERENCES_HEIGHT_EDIT, heightSave);
        preferencesEditor.putBoolean(PREFERENCES_SWITCH,switch_units.isChecked());
        preferencesEditor.commit();
    }

    public void restore(){
        String textFromPreferences = preferences.getString(PREFERENCES_MASS_EDIT, "");
        mass_edit.setText(textFromPreferences);
        textFromPreferences = preferences.getString(PREFERENCES_HEIGHT_EDIT, "");
        switch_units.setChecked(preferences.getBoolean(PREFERENCES_SWITCH, false));
        height_edit.setText(textFromPreferences);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @OnClick(R.id.share)
    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = share_message + bmi_text.getText().toString()+ smile;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(height_edit.getText().toString().matches(("")) || mass_edit.getText().toString().matches("")){
            menu.getItem(1).setEnabled(false);
        }
        else{
            menu.getItem(1).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //enableSave();
        switch (item.getItemId()) {
            case R.id.author:
                Intent intent = new Intent(this, AboutAuthorActivity.class);
                startActivity(intent);
                return true;
            case R.id.save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnTouch
    public boolean hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @OnClick(R.id.button_count)
    public void ButtonClick() {

        if(switch_units.isChecked()){
            Count(new CountBMIforImperial());
        }
        else{
            Count(new CountBMIforMetric());
        }

        changeShare();

    }

    public void enableSave(){
        MenuItem save = (MenuItem) findViewById(R.id.save);
        if((height_edit.getText().toString().matches(("")) || mass_edit.getText().toString().matches(""))
                && bmi_text.getText().toString().matches("")){
            save.setEnabled(false);
        }
        else{
            save.setEnabled(true);
        }
    }

    public void changeShare(){
        if(bmi_text.getText().toString().matches("")){
            share_button.setEnabled(false);
            share_button.setVisibility(View.INVISIBLE);
        }
        else{
            share_button.setEnabled(true);
            share_button.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        bmi_text.setText(savedInstanceState.getString("bmi_text"));
        bmi_text.setTextColor(savedInstanceState.getInt("bmi_color"));
        value_text.setTextColor(savedInstanceState.getInt("value_color"));
        value_text.setText(savedInstanceState.getString("value_text"));
        height_edit.setText(savedInstanceState.getString("height"));
        mass_edit.setText(savedInstanceState.getString("weight"));
        switch_units.setChecked(savedInstanceState.getBoolean("switch"));
        SwitchChange();
        changeShare();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("bmi_color",bmi_text.getCurrentTextColor());
        outState.putString("bmi_text", String.valueOf(bmi_text.getText()));
        outState.putInt("value_color",value_text.getCurrentTextColor());
        outState.putString("value_text",String.valueOf(value_text.getText()));
        outState.putString("height",String.valueOf(height_edit.getText()));
        outState.putString("weight",String.valueOf(mass_edit.getText()));
        outState.putBoolean("switch",switch_units.isChecked());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.switch_units)
    public void SwitchChange(){
        if(switch_units.isChecked()){
            mass_edit.setHint(R.string.pound);
            height_edit.setHint(R.string.inch);
        }
        else{
            mass_edit.setHint(R.string.kilograms);
            height_edit.setHint(R.string.meter);
        }
    }

    public void popup(){
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.screen_popup,
                (ViewGroup) findViewById(R.id.popup));
        pw = new PopupWindow(layout,500,150);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pw.setElevation(10);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    public void setColor(int color){
        value_text.setTextColor(color);
        bmi_text.setTextColor(color);
    }

    private void Count(CountBMI calculator){
        try{
            float bmi = calculator.countBMI(Float.parseFloat(mass_edit.getText().toString())
                    ,Float.parseFloat(height_edit.getText().toString()));
            if(bmi < 18.5) {
                value_text.setText(R.string.underweight_message);
                if (bmi < 16) {
                    setColor(red);
                } else if (bmi < 17) {
                    setColor(orange);
                } else {
                    setColor(yellow);
                }
            }
            else if(bmi < 25) {
                value_text.setText(R.string.normal_message);
                setColor(green);
            }
            else if(bmi < 30) {
                value_text.setText(R.string.overweight_message);
                setColor(yellow);}
            else{
                value_text.setText(R.string.obesity_message);
                if (bmi < 35) {
                    setColor(orange);
                } else if (bmi < 40) {
                    setColor(red);
                } else {
                    setColor(burgundy);
                }
            }
            bmi_text.setText(String.format("%.2f",bmi));
        } catch(IllegalArgumentException e){
            bmi_text.setText(null);
            value_text.setText(null);
            popup();
        }
    }

}
