package rtrtk.pnrs1.ra54_2014;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_statistic);

        StatisticView statisticView = new StatisticView(getApplicationContext());
        setContentView(statisticView);
    }
}
