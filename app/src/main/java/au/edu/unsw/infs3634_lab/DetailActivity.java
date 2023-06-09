package au.edu.unsw.infs3634_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG= "Detail-Activity";
    private TextView detailMessage;
    private Button btnOpenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Set title for the activity
        setTitle("Detail Activity");

        // Get the intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Intent Message = " + message);

            // Capture the layout's TextView and set the string as its text
            detailMessage = findViewById(R.id.tvMessage);
            detailMessage.setText(message);
        }

        // Get the handle to btnUrl Button
        btnOpenUrl = findViewById(R.id.btnUrl);
        // Implement click listener for btnUrl button
        btnOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://youtu.be/M0HqrPDyA6I");
            }
        });
    }

    // Called when user taps on open url button
    public void openUrl(String url){
        // Create an ACTION_VIEW intent
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}