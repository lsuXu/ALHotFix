package xp.com.alhotfix;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    //申请权限
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            String [] permissions = new String[]{
                    "android.permission.INTERNET",
                    "android.permission.ACCESS_NETWORK_STATE",
                    "android.permission.ACCESS_WIFI_STATE",
                    "android.permission.READ_EXTERNAL_STORAGE"
            };
            this.requestPermissions(permissions,1);
        }
    }
}
