package bestyan.kieserapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bastian on 04.03.2017.
 */
@SuppressWarnings("unused")
public class StopwatchHandler extends Handler {
    private final Stopwatch timer = new Stopwatch();
    private final DateFormat formatter = new SimpleDateFormat("mm:ss");
    public static final int START_TIMER = 0;
    public static final int UPDATE_TIMER = 1;
    public static final int STOP_TIMER = 2;
    private final int REFRESH_RATE = 100;
    
    private TextView timerView;
    private EditText smallView;
    private boolean running = false;
    private long goal;
    private boolean goalReached = false;
    
    public StopwatchHandler(TextView timerView, EditText smallView){
        super();
        this.timerView = timerView;
        this.smallView = smallView;
        goal = Long.parseLong(smallView.getText().toString())*1000;
    }
    
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case START_TIMER:
                running = true;
                timer.start(); //start timer
                this.sendEmptyMessage(UPDATE_TIMER);
                break;
            
            case UPDATE_TIMER:
                long elapsedTime = timer.getElapsedTime();
                if(!goalReached){
                    if(elapsedTime >= goal){
                        goalReached = true;
                        timerView.getBackground().setTint(Color.rgb(0x54, 0xa6, 0x63));
                        timerView.getBackground().setTintMode(PorterDuff.Mode.DARKEN);
                        smallView.setBackgroundColor(Color.rgb(0x54, 0xa6, 0x63));
                    }
                }
                timerView.setText(formatter.format(elapsedTime));
                this.sendEmptyMessageDelayed(UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                break;                                  //though the timer is still running
            
            case STOP_TIMER:
                this.removeMessages(UPDATE_TIMER); // no more updates.
                timer.stop();//stop timer
                running = false;
                if(!goalReached){
                    smallView.setBackgroundColor(Color.rgb(0xff, 0xb6, 0x73));
                }
                break;
            
            default:
                break;
        }
    }
    
    public boolean isRunning(){
        return running;
    }
}
