package bestyan.kieserapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("unused")
class MainActivity extends Activity {
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    public void addNeueZeile(View button){
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        View newRow = this.getLayoutInflater().inflate(R.layout.mastertable_tablerow, null);
        masterTable.addView(newRow);
    }
    
    public void resetProgress(View button){
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        for(int i = 0; i < masterTable.getChildCount(); i++){
            TableRow row = (TableRow) masterTable.getChildAt(i);
            CheckBox checkbox = (CheckBox)row.findViewById(R.id.cbErledigt);
            EditText uhr = (EditText) row.findViewById(R.id.textUhr);
            checkbox.setChecked(false);
            uhr.setText("");
            //uhr.getBackground().setTintMode(PorterDuff.Mode.LIGHTEN);
            uhr.getBackground().setTint(Color.WHITE);
        }
    }
    
    public void deleteRow(View button){
        TableRow row = (TableRow)button.getParent();
        TableLayout masterTable = (TableLayout) this.findViewById(R.id.tableRows);
        masterTable.removeView(row);
    }
    
    public void startTimer(View button){
        ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.master);
        final TextView timerView = (TextView) this.getLayoutInflater().inflate(R.layout.timer_textview, null);
        root.addView(timerView);
        ConstraintSet con = new ConstraintSet();
        con.clone(root);
        con.addToHorizontalChain(timerView.getId(), root.getId(), root.getId());
        con.addToVerticalChain(timerView.getId(), root.getId(), root.getId());
        con.applyTo(root);
        
        TableRow parent = (TableRow) button.getParent();
        EditText smallTimerView = (EditText) parent.findViewById(R.id.textUhr);
        
        stopwatch = new StopwatchHandler(timerView, smallTimerView);
    }
    
    public static StopwatchHandler stopwatch;
    public void toggleTimer(View timerView){
        if(stopwatch.isRunning()){
            stopwatch.sendEmptyMessage(StopwatchHandler.STOP_TIMER);
            ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.master);
            root.removeView(timerView);
        } else{
            stopwatch.sendEmptyMessage(StopwatchHandler.START_TIMER);
        }
    }
}
