/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package karataiev.dmytro.myappportfolio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import karataiev.dmytro.myappportfolio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mContext = getApplicationContext();

        setSupportActionBar(binding.toolbar);

        // Adds red color filter to the button
        binding.contentMain.earthquake
                .getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        binding.contentMain.popMovie.setOnClickListener(mListener);
        binding.contentMain.alexandria.setOnClickListener(mListener);
        binding.contentMain.footballScores.setOnClickListener(mListener);
        binding.contentMain.buildIt.setOnClickListener(mListener);
        binding.contentMain.material.setOnClickListener(mListener);
        binding.contentMain.ubiquitous.setOnClickListener(mListener);
        binding.contentMain.earthquake.setOnClickListener(mListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to show name of the button on the screen
     * @param view to present the Toast on
     */
    public void blankActivity(View view) {
        // Toast shows text on the pressed button
        String textOnButton = ((Button) view).getText().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, textOnButton, duration);
        toast.show();
    }

    /** Open another app intent
     * @param context current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return true if likely successful, false if unsuccessful
     */
    private boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();

        Intent i = manager.getLaunchIntentForPackage(packageName);

        if (i == null) {
            i = new Intent(Intent.ACTION_VIEW);

            if (packageName.contains("karataiev.dmytro")) {
                // Open google play to instal the app
                i.setData(Uri.parse("market://details?id=" + packageName));
            } else {
                // If there is no google play page - open my portfolio site with additional info
                i.setData(Uri.parse("http://adkdevelopment.com"));
            }
        } else {
            // Set the flag to open the app
            i.addCategory(Intent.CATEGORY_LAUNCHER);
        }

        startActivity(i);
        return true;

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.popMovie:
                    openApp(mContext, "karataiev.dmytro.popularmovies");
                    break;
                case R.id.alexandria:
                    openApp(mContext, "it.jaschke.alexandria");
                    break;
                case R.id.football_scores:
                    openApp(mContext, "barqsoft.footballscores");
                    break;
                case R.id.buildIt:
                    openApp(mContext, "com.udacity.gradle.builditbigger.paid");
                    break;
                case R.id.material:
                    openApp(mContext, "com.example.xyzreader");
                    break;
                case R.id.earthquake:
                    openApp(mContext, "com.adkdevelopment.earthquakesurvival");
                    break;
                case R.id.ubiquitous:
                    openApp(mContext, "com.example.android.sunshine.app");
                    break;
                default:
                    break;
            }
        }
    };
}
