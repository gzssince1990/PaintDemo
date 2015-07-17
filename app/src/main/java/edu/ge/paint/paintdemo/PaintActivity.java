package edu.ge.paint.paintdemo;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class PaintActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener{

    MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);
        myView = (MyView) findViewById(R.id.my_view);
    }

    final static int PEN_COLOR = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, PEN_COLOR, 0, "Pen Color");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case PEN_COLOR:
                new ColorPickerDialog(this,this,"Pen Color", Color.BLACK, Color.WHITE).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(String key, int color) {
        myView.setPenColor(color);
    }
}
