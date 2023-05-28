package com.example.roomybooky.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.roomybooky.R;

public class WebActivity extends AppCompatActivity {
  private TextView btnBack;
  private Intent intent;
  private WebView webView;
  private String label;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web);

    // init button back
    btnBack = (TextView) findViewById(R.id.backToPrev);
    btnBack.setOnClickListener(v -> {
      setIntent(new Intent(WebActivity.this, ProfileActivity.class));
      finish();
      startActivity(getIntent());
    });

    // init web view
    webView = (WebView) findViewById(R.id.webViewTemplate);
    webView.getSettings().setLoadsImagesAutomatically(true);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setSupportZoom(true);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().setDisplayZoomControls(false);
    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    webView.setWebViewClient(new WebViewClient());

    Intent intentTemp = getIntent();
    if (intentTemp != null) {
      label = intentTemp.getStringExtra("LABEL");
      if (!label.isEmpty()) {
        if (label.equals("PRIVPOL")) {
          btnBack.setText("Privacy Policy");
          webView.loadUrl("https://binus.ac.id/privacy-policy/");
        }
        else if (label.equals("TERMS")) {
          btnBack.setText("Terms and Conditions");
          webView.loadUrl("https://binus.ac.id/2014/03/booking-terms-and-conditions/");
        }
        else if (label.equals("HELP")) {
          btnBack.setText("Help and Support");
          webView.loadUrl("https://support.binus.ac.id/en/");
        }
      }
    }
  }
}