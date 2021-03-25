//package com.example.projekt_android;
//
//import android.app.ActivityManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//
//
//
//import java.util.concurrent.TimeUnit;
//
//public class StartActivity extends AppCompatActivity
//{
//    private static final String mCode = "StartActivity";
//    private BatteryBroadcastReceiver mBatteryBroadcastReceiver;
//
//    /**
//     * Method is invoked when activity is created. Checks if service is running, initializes AndroidLog.
//     *
//     * @param savedInstanceState
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//
//
//        setContentView(R.layout.activity_start);
//
//    }
//
//    /**
//     * Method registers broadcast receiver for battery and wifi state
//     */
//    private void initBatteryAndWiFiBroadcast()
//    {
//        mBatteryBroadcastReceiver = new BatteryBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        registerReceiver(mBatteryBroadcastReceiver, intentFilter);
//
//        WifiManager wifiMan = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        if (wifiMan != null)
//        {
//            wifiMan.startScan();
//        }
//
//
//
//
//
//
//
//
//
//    }
//
//    /**
//     * Method initializes the logger
//     */
//    private void initAndroidLog()
//    {
//        AndroidLog.setContext(this);
//        AndroidLog.changeLevel("INFO");
//        AndroidLog.info(mCode, "Init AndroidLog");
//    }
//
//    /**
//     * Method starts ReconnectFragment, StartFragment, LogInFragment or SettingsErrorFragment depends of state application
//     */
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//
//        initBatteryAndWiFiBroadcast();
//
//        if (checkConfig())
//        {
//            Bundle extras = getIntent().getExtras();
//            if (extras != null && MqttForegroundService.isConnected()) // start Reconnection Fragment
//            {
//                String string = extras.getString(getString(R.string.intent_reconnection_key)); //putExtra from MqttForegroundService
//                if (string != null && string.equals(getString(R.string.intent_reconnection_content)))
//                {
//
//                    addFragment(new ReconnectFragment());
//
//                }
//            }
//            else if (MqttForegroundService.getUsersList() == null) //userList = null means that this is the first time the application is launched
//            {
//
//                addFragment(new StartFragment());
//
//            }
//            else
//            {
//
//                addFragment(new LogInFragment());
//
//            }
//        }
//        else
//        {
//
//            addFragment(new SettingsErrorFragment());
//        }
//
//        setDeviceNameInToolbar();
//
//    }
//
//    /**
//     * Method sets device name in toolbar. The device name gets from shared preferences
//     */
//    private void setDeviceNameInToolbar()
//    {
//        TextView deviceNameTxtView = findViewById(R.id.device_name);
//        String devName = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.device_name_key), "...");
//        deviceNameTxtView.setText(devName);
//    }
//
//    /**
//     * Method checks configuration parameters
//     *
//     * @return boolean
//     */
//    private boolean checkConfig()
//    {
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//
//        String deviceName = sharedPref.getString(getString(R.string.device_name_key), "");
//        if (deviceName.equals(""))
//        {
//            return false;
//        }
//
//        String serverIp = sharedPref.getString(getString(R.string.server_ip_key), "");
//        if (serverIp.equals(""))
//        {
//            return false;
//        }
//
//        String serverPort = sharedPref.getString(getString(R.string.server_port_key), "");
//        if (serverPort.equals(""))
//        {
//            return false;
//        }
//        AndroidLog.debug(mCode, "checkConfig: Device Name:" + deviceName + "  Server IP:" + serverIp + " Server port:" + serverPort);
//        return true;
//    }
//
//    @Override
//    public void openSettingsActivity()
//    {
//        Intent intent = new Intent(this, ConfigActivity.class);
//        startActivity(intent);
//    }
//
//    @Override
//    public void addFragment(Fragment fragment)
//    {
//        if (fragment != null)
//        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.start_container, fragment)
//                    .commit();
//        }
//    }
//
//    /**
//     * Method registers EventBus
//     */
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        EventBus.getDefault().register(this);
//        AndroidLog.debug(mCode, "Registered EventBus");
//    }
//
//    /**
//     * Method unregisters EventBus
//     */
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//        AndroidLog.debug(mCode, "Unregistered EventBus");
//    }
//
//    /**
//     * Method for communication with MqttForegroundService
//     *
//     * @param result Received message
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void receivedNewAlarm(String result)
//    {
//        AndroidLog.debug(mCode, "Received message from EventBus. Authorisation info: " + result);
//        if (result != null)
//        {
//            switch (result)
//            {
//                case "step2_ok":
//                    addFragment(new LogInFragment()); //create LogIn Fragment if step2 of authorization is ok
//                    break;
//                case "Authorization_failed":
//                    addFragment(new StartFragment()); //back to the fragment with connect button
//                    break;
//                case "Wrong ping!":
//                    Toast.makeText(this, getResources().getText(R.string.wrong_pin), Toast.LENGTH_SHORT).show();
//                    break;
//                case "The selected user is already logged in!":
//                    Toast.makeText(this, getResources().getText(R.string.user_already_logged_in), Toast.LENGTH_SHORT).show();
//                    break;
//                case "Users list is empty.":
//                    Toast.makeText(this, getResources().getText(R.string.user_list_empty), Toast.LENGTH_LONG).show();
//                    break;
//
//            }
//        }
//    }
//
//    /**
//     * Method to check if service is running
//     *
//     * @param serviceClass Name of Service
//     * @return True if service is running
//     */
//    protected boolean isServiceRunning(Class<?> serviceClass)
//    {
//        if (serviceClass != null)
//        {
//            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            if (manager != null)
//            {
//                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
//                {
//                    if (serviceClass.getName().equals(service.service.getClassName()))
//                    {
//                        return true;
//                    }
//                }
//            }
//        }
//
//        return false;
//
//    }
//
//    /**
//     * Method sets worker manager for logs
//     */
//    private void setWorkerManagerToLogs()
//    {
//        PeriodicWorkRequest renameLogFilePeriod =
//                new PeriodicWorkRequest.Builder(ChangeFileNameWorker.class, 1, TimeUnit.HOURS)
//                        .build();
//
//        WorkManager.getInstance(this)
//                .enqueueUniquePeriodicWork("changeLogFileName", ExistingPeriodicWorkPolicy.KEEP, renameLogFilePeriod);
//
//        AndroidLog.debug(mCode, "Set worker manager for rename log file");
//    }
//
//    /**
//     * Method unregistered broadcast receiver
//     */
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        unregisterReceiver(mBatteryBroadcastReceiver);
//    }
//
//    private class BatteryBroadcastReceiver extends BroadcastReceiver
//    {
//        private final static String BATTERY_LEVEL = "level";
//        private int mLevelBattery;
//
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()))
//            {
//                mLevelBattery = intent.getIntExtra(BATTERY_LEVEL, -1);
//
//                 updateBatteryLevel();
//            }
//            else if (WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction()))
//            {
//                WifiManager wifiMan = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                if (wifiMan != null)
//                {
//                    wifiMan.startScan();
//                    int newRssi = wifiMan.getConnectionInfo().getRssi();
//                    int levelWifi = WifiManager.calculateSignalLevel(newRssi, 5);
//
//                    updateWiFiLevel(levelWifi);
//                }
//
//            }
//            else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()))
//            {
//                WifiManager wifiMan = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                if (wifiMan != null)
//                {
//                    wifiMan.startScan();
//                    int newRssi = wifiMan.getConnectionInfo().getRssi();
//                    int levelWifi = WifiManager.calculateSignalLevel(newRssi, 5);
//
//                    if (levelWifi == 0)
//                    {
//                        ImageView wifiStatus = findViewById(R.id.wifi_status);
//                        wifiStatus.setImageResource(R.drawable.wifi_0);
//                    }
//                }
//
//            }
//
//
//        }
//
//        /**
//         * Method updates the icon view according to the level
//         */
//        public void updateBatteryLevel()
//        {
//            TextView batteryPercentage = findViewById(R.id.battery_percentage);
//            ImageView imageView = findViewById(R.id.battery);
//
//            batteryPercentage.setText("" + mLevelBattery + "%");
//            if (mLevelBattery > 90)
//            {
//                imageView.setImageResource(R.drawable.battery_5);
//            }
//            else if (mLevelBattery > 70)
//            {
//                imageView.setImageResource(R.drawable.battery_4);
//            }
//            else if (mLevelBattery > 50)
//            {
//                imageView.setImageResource(R.drawable.battery_3);
//            }
//            else if (mLevelBattery > 30)
//            {
//                imageView.setImageResource(R.drawable.battery_2);
//            }
//            else if (mLevelBattery > 10)
//            {
//                imageView.setImageResource(R.drawable.battery_1);
//            }
//            else
//            {
//                imageView.setImageResource(R.drawable.battery_0);
//            }
//
//        }
//
//        /**
//         * Method updates the icon view according to the level
//         *
//         * @param level signal level
//         */
//        private void updateWiFiLevel(int level)
//        {
//            ImageView wifiStatus = findViewById(R.id.wifi_status);
//
//            if (level == 0)
//            {
//                wifiStatus.setImageResource(R.drawable.wifi_0);
//            }
//            else if (level == 1)
//            {
//                wifiStatus.setImageResource(R.drawable.wifi_1);
//            }
//            else if (level == 2)
//            {
//                wifiStatus.setImageResource(R.drawable.wifi_2);
//            }
//            else if (level == 3)
//            {
//                wifiStatus.setImageResource(R.drawable.wifi_3);
//            }
//            else
//            {
//                wifiStatus.setImageResource(R.drawable.wifi_4);
//            }
//
//        }
//    }
//}