
package anthony.coolerdemo.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.github.mikephil.charting.utils.Utils;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;

import anthony.coolerdemo.BarChartPositiveNegative;
import anthony.coolerdemo.DynamicalAddingActivity;
import anthony.coolerdemo.LineChartActivity1;
import anthony.coolerdemo.LineChartTime;
import anthony.coolerdemo.R;
import anthony.coolerdemo.RealtimeLineChartActivity;

import static anthony.coolerdemo.util.Utils.getTotalMemoryCurrent;
import static anthony.coolerdemo.util.Utils.readUsage;

public class MainActivity extends Activity implements OnItemClickListener {

    private ProgressWheel pwCPU;
    private ProgressWheel pwRAM;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        setTitle("MPAndroidChart Example");

//        // initialize the utilities
//        Utils.init(this);
//
//        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();
//
//        objects.add(new ContentItem("Line Chart", "A simple demonstration of the linechart."));
//
//        objects.add(new ContentItem(
//                "Realtime Chart",
//                "This chart is fed with new data in realtime. It also restrains the view on the x-axis."));
//        objects.add(new ContentItem(
//                "Dynamical data adding",
//                "This Activity demonstrates dynamical adding of Entries and DataSets (real time graph)."));
//
//        objects.add(new ContentItem(
//                "BarChart positive / negative",
//                "This demonstrates how to create a BarChart with positive and negative values in different colors."));
//
//        ContentItem time = new ContentItem(
//                "Time Chart",
//                "Simple demonstration of a time-chart. This chart draws one line entry per hour originating from the current time in milliseconds.");
//        time.isNew = true;
//        objects.add(time);
//
//        MyAdapter adapter = new MyAdapter(this, objects);
//
//        ListView lv = (ListView) findViewById(R.id.listView1);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(this);

        pwCPU = (ProgressWheel) findViewById(R.id.progressBar_CPU);
        pwRAM = (ProgressWheel) findViewById(R.id.progressBar_RAM);
        onThreadUpdateStage();
    }


    public void onThreadUpdateStage() {
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                pwCPU.setText("" + readUsage() * 100 );
                pwCPU.setDelayMillis(1000);
                pwCPU.setProgress((int) (readUsage() * 100));
                pwRAM.setText("" + getTotalMemoryCurrent(getApplicationContext()) * 100);
                pwRAM.setDelayMillis(1000);
                pwRAM.setProgress((int) (getTotalMemoryCurrent(getApplicationContext()) * 100));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {

        Intent i;

        switch (pos) {
            case 0:
                i = new Intent(this, LineChartActivity1.class);
                startActivity(i);
                break;
            case 1:
                i = new Intent(this, RealtimeLineChartActivity.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(this, DynamicalAddingActivity.class);
                startActivity(i);
                break;
            case 3:
                i = new Intent(this, BarChartPositiveNegative.class);
                startActivity(i);
                break;
            case 4:
                i = new Intent(this, LineChartTime.class);
                startActivity(i);
                break;

        }

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i = null;

        switch (item.getItemId()) {
            case R.id.viewGithub:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(i);
                break;
            case R.id.report:
                i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "philjay.librarysup@gmail.com", null));
                i.putExtra(Intent.EXTRA_SUBJECT, "MPAndroidChart Issue");
                i.putExtra(Intent.EXTRA_TEXT, "Your error report here...");
                startActivity(Intent.createChooser(i, "Report Problem"));
                break;
            case R.id.blog:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.xxmassdeveloper.com"));
                startActivity(i);
                break;
            case R.id.website:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://at.linkedin.com/in/philippjahoda"));
                startActivity(i);
                break;
        }

        return true;
    }
}
