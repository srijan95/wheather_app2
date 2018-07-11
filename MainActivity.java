package internity.srijan.srijanweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity implements LocationListener {

    EditText et1;
    Button btnCuurent, btn2GET, btnSearch;
    ListView ls;
    JSONObject data = null;
    static int flag = 1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    static double lat, lon;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIds();
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        ls.setAdapter(adapter);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
    }


    private void initIds() {
    et1 = findViewById(R.id.cityName);
    btnCuurent = findViewById(R.id.currentLocation);
    btn2GET = findViewById(R.id.getLocation);
    btnSearch = findViewById(R.id.searchLocation);
    ls = findViewById(R.id.detailList);
    }
    public void search(View view) {
        if (!isNetworkAvailable(this))
            Toast.makeText(this, "Not Connected To NetWork", Toast.LENGTH_SHORT).show();
        else
            {
            new AsyncTask<Void , Void , Void>()
            {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(data == null)
                    {
                        Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 0)
                    {
                        Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(MainActivity.this, "Data Found", Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        try {
                            arrayList.add("Weather : " + data.getJSONArray("weather").getJSONObject(0).getString("main"));
                            arrayList.add("Temperature : "+data.getJSONObject("main").getString("temp")+" Kelvin");
                            arrayList.add("Humidty : "+data.getJSONObject("main").getString("humidity"));
                            arrayList.add("Pressure : "+data.getJSONObject("main").getString("pressure"));
                            arrayList.add("City : "+data.getString("name"));
                            arrayList.add("Country : "+data.getJSONObject("sys").getString("country"));
                            adapter.notifyDataSetChanged();

                        }
                        catch (Exception eee)
                        {

                        }
                        }
                    flag = 1;
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try
                    {
                        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?appid=51bd186d81e9633cd022b45a26cdf1f2&q="+et1.getText().toString());
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuffer json = new StringBuffer(1024);
                        String tmp = "";
                        while((tmp = reader.readLine()) != null)
                            json.append(tmp).append("\n");
                        reader.close();

                        data = new JSONObject(json.toString());

                        if(data.getInt("cod") != 200) {
                            System.out.println("Cancelled");
                            flag=0;
                            return null;

                        }
                    }
                    catch (Exception e)
                    {
                       //Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                    return null;
                }
            }.execute();

        }
    }
    public void gett(View view) {
        if (!isNetworkAvailable(this))
            Toast.makeText(this, "Not Connected To NetWork", Toast.LENGTH_SHORT).show();
        else
        {
            new AsyncTask<Void , Void , Void>()
            {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();

                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(data == null)
                    {
                        Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 0)
                    {
                        Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(MainActivity.this, "Data Found", Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        try {
                            arrayList.add("Weather : " + data.getJSONArray("weather").getJSONObject(0).getString("main"));
                            arrayList.add("Temperature : "+data.getJSONObject("main").getString("temp")+" Kelvin");
                            arrayList.add("Humidty : "+data.getJSONObject("main").getString("humidity"));
                            arrayList.add("Pressure : "+data.getJSONObject("main").getString("pressure"));
                            arrayList.add("City : "+data.getString("name"));
                            arrayList.add("Country : "+data.getJSONObject("sys").getString("country"));
                            adapter.notifyDataSetChanged();

                        }
                        catch (Exception eee)
                        {

                        }
                    }
                    flag = 1;
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try
                    {

                        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?appid=51bd186d81e9633cd022b45a26cdf1f2&lat="+lat+"&lon="+lon);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuffer json = new StringBuffer(1024);
                        String tmp = "";
                        while((tmp = reader.readLine()) != null)
                            json.append(tmp).append("\n");
                        reader.close();

                        data = new JSONObject(json.toString());

                        if(data.getInt("cod") != 200) {
                            System.out.println("Cancelled");
                            flag=0;
                            return null;

                        }
                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                    return null;
                }
            }.execute();

        }
    }


    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onLocationChanged(Location location) {
     lat = location.getLatitude();
     lon = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
