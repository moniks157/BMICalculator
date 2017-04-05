package pl.edu.pwr.maczkowiak.lab1.bmicalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Monika Maczkowiak on 2017-04-03.
 */

public class AboutAuthorActivity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_author);
        ButterKnife.bind(this);
    }
}
