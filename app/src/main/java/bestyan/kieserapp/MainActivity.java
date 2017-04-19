package bestyan.kieserapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bestyan.data.Geraet;
import bestyan.utils.GuiUtils;

@SuppressWarnings("unused")
public class MainActivity extends Activity {
    
    protected static final String SHAREDPREFS_NAME = "KieserAppPrefs";
    protected static final String ROWS_VALUE = "rows";
    public static StopwatchHandler stopwatch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("BANANAS/LIFECYCLE", "onCreate()");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
    
    @Override
    protected void onStart(){
        Log.d("BANANAS/LIFECYCLE", "onStart()");
        super.onStart();
        //restore saved settings
        SharedPreferences prefs = this.getSharedPreferences(SHAREDPREFS_NAME, 0);
        String rowValues = prefs.getString(ROWS_VALUE, null);
        if(rowValues != null){
            Gson gson = new Gson();
            List<Geraet> geraete = gson.fromJson(rowValues, new TypeToken<ArrayList<Geraet>>(){}.getType());
        
        
            TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
            masterTable.removeAllViews();
            
            for(Geraet geraet : geraete){
                TableRow row = this.addNeueZeile(null);
                geraet.restoreTableRow(row);
            }
        }
    }

    @Override
    protected void onStop(){
        Log.d("BANANAS/LIFECYCLE", "onStop()");
        super.onStop();
        
        //save current rows
        List<Geraet> rows = new ArrayList<>();
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        for(int i = 0; i < masterTable.getChildCount(); i++){
            TableRow row = (TableRow) masterTable.getChildAt(i);
            rows.add(Geraet.createFromRow(row));
        }
    
        Type type = new TypeToken<ArrayList<Geraet>>(){}.getType();
        Gson gson = new Gson();
        String rowString = gson.toJson(rows, type);
        
        SharedPreferences prefs = this.getSharedPreferences(SHAREDPREFS_NAME, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(ROWS_VALUE, rowString);
        prefsEditor.commit();
    }
    
    public TableRow addNeueZeile(View button){
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        View newRow = this.getLayoutInflater().inflate(R.layout.mastertable_tablerow, null);
        masterTable.addView(newRow);
        return (TableRow) newRow;
    }
    
    public void resetProgress(View button){
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        for(int i = 0; i < masterTable.getChildCount(); i++){
            TableRow row = (TableRow) masterTable.getChildAt(i);
            CheckBox checkbox = (CheckBox)row.findViewById(R.id.cbErledigt);
            EditText uhr = (EditText) row.findViewById(R.id.tfUhr);
            checkbox.setChecked(false);
            uhr.getBackground().setTint(Color.TRANSPARENT);
        }
    }
    
    public void deleteRow(View button){
        TableRow row = (TableRow)button.getParent();
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        masterTable.removeView(row);
    }
    
    public void startTimer(View button){
        this.setControlsEnabled(false);
        ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.master);
        final TextView timerView = (TextView) this.getLayoutInflater().inflate(R.layout.timer_textview, null);
        root.addView(timerView);
        ConstraintSet con = new ConstraintSet();
        con.clone(root);
        con.addToHorizontalChain(timerView.getId(), root.getId(), root.getId());
        con.addToVerticalChain(timerView.getId(), root.getId(), root.getId());
        con.applyTo(root);
        
        TableRow parent = (TableRow) button.getParent();
        EditText tfUhr = (EditText) parent.findViewById(R.id.tfUhr);
        
        stopwatch = new StopwatchHandler(timerView, tfUhr);
    }
    
    protected void setControlsEnabled(boolean enabled){
        ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.master);
        GuiUtils.disableEnableControls(enabled, root);
    }

    public void toggleTimer(View timerView){
        if(stopwatch.isRunning()){
            stopwatch.sendEmptyMessage(StopwatchHandler.STOP_TIMER);
            ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.master);
            root.removeView(timerView);
            this.setControlsEnabled(true);
        } else{
            stopwatch.sendEmptyMessage(StopwatchHandler.START_TIMER);
        }
    }
    
    @Override
    protected void onResume(){
        Log.d("BANANAS/LIFECYCLE", "onResume()");
        super.onResume();
    }
    
    @Override
    protected void onPause(){
        Log.d("BANANAS/LIFECYCLE", "onPause()");
        super.onPause();
    }
    
    @Override
    protected void onDestroy(){
        Log.d("BANANAS/LIFECYCLE", "onDestroy()");
        super.onDestroy();
    }
}
