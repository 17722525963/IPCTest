package com.zsrun.anotheractivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TestIPCActivity extends AppCompatActivity {

    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ipc);

        show = findViewById(R.id.show);

        findViewById(R.id.getToast)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TestIPCActivity.this, show.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        /**
         * 从Intent传递中获取数据
         */
        huoqushuju();
    }

    private void huoqushuju() {
        if (getIntent().getData() != null) {
            String host = getIntent().getData().getHost();
            show.setText("host==" + host);

            String bundleValue = getIntent().getExtras().getString("value");

            show.append("\n 传递的数据==" + bundleValue);

        }
    }
}
