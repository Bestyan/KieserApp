package bestyan.data;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;

import bestyan.kieserapp.R;

/**
 * entity to store row values in shared preferences
 * Created by Bastian on 16.04.2017.
 */

public class Geraet {
    private String name;
    private String gewicht;
    private String einstellungen;
    private String uhr;
    private boolean erledigt;
    
    public static Geraet createFromRow(TableRow row){
        EditText tfGeraet = (EditText) row.findViewById(R.id.tfGeraet);
        EditText tfGewicht = (EditText) row.findViewById(R.id.tfGewicht);
        EditText tfEinstellungen = (EditText) row.findViewById(R.id.tfEinstellungen);
        EditText tfUhr = (EditText) row.findViewById(R.id.tfUhr);
        CheckBox cbErledigt = (CheckBox) row.findViewById(R.id.cbErledigt);
        
        Geraet result = new Geraet();
        result.setName(tfGeraet.getText().toString());
        result.setGewicht(tfGewicht.getText().toString());
        result.setEinstellungen(tfEinstellungen.getText().toString());
        result.setUhr(tfUhr.getText().toString());
        result.setErledigt(cbErledigt.isChecked());
        
        return result;
    }
    
    public void restoreTableRow(TableRow row){
        EditText tfGeraet = (EditText) row.findViewById(R.id.tfGeraet);
        EditText tfGewicht = (EditText) row.findViewById(R.id.tfGewicht);
        EditText tfEinstellungen = (EditText) row.findViewById(R.id.tfEinstellungen);
        EditText tfUhr = (EditText) row.findViewById(R.id.tfUhr);
        CheckBox cbErledigt = (CheckBox) row.findViewById(R.id.cbErledigt);
        
        tfGeraet.setText(this.getName());
        tfGewicht.setText(this.getGewicht());
        tfEinstellungen.setText(this.getEinstellungen());
        tfUhr.setText(this.getUhr());
        cbErledigt.setSelected(this.isErledigt());
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGewicht() {
        return gewicht;
    }
    
    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }
    
    public String getEinstellungen() {
        return einstellungen;
    }
    
    public void setEinstellungen(String einstellungen) {
        this.einstellungen = einstellungen;
    }
    
    public String getUhr() {
        return uhr;
    }
    
    public void setUhr(String uhr) {
        this.uhr = uhr;
    }
    
    public boolean isErledigt() {
        return erledigt;
    }
    
    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }
    
}
