package xp.com.alhotfix;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by 12852 on 2018/10/10.
 */

public class InitApplication extends SophixApplication{

    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。RealApplicationStub类名不能修改
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "1.0.2";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25103022-1", "db425bc161130b87e8be94976bd3959b", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrssSSNYa8cnP6j7UxE/+22BCHQ4OnjwJrGqWoaTa3Fl74aveAbhKFJd1nxypATuWGQYqp+jB5Js4IKP/E+Cw99Da/g8gjC/DxJhOJH3uEUwqKWUbegbnFe24DVzfdd/gUyg5XLSH+ulniyLeszjgO6uiK5P/bzPdG7TAb+HNcT3t8s3qlSCK7cGRs3ZqtBdPH13NQpz7qBZV32SU1PvtFK2dfVLL93zqfuJW2pDSDObW3IDDs4VClVkwDchnzWumOuhdbeixaQs/SUK6w/o3l42REvL2PSsLVCuEhzWXsNVC1YlS5TWnm1NRiuqfiOuugwg+xwKk5ozigGwp9p5C5AgMBAAECggEAcSxOgYkinIhchiW/avAht6XHj0EjQIj1MR/JXa6sUBZA10G6jj7/miARRlxzLxxSEiKVgeDVnqXDXIGZxX1HvDaC982nV+KBGJLegZ3szpXl9rdODVA44UCwjScqPaLcNOfSeAfZxouSv/dJinALZ1nTmenD9F+wBmJFOcqUT51FpvyNbNm1XGJx5qjXSL8pvWQ4heLpVoQdwVQNEAN6GWUYptXDTYTvHN0L63wwlXyuJC3ShHuipZLDL0nVHj6aMYJ3qo5X731aofeDFhO7m7gwSnwvQf57G0vs4MAVrf+PiZ032wOHUEqWrqAQP3wDEJmFAX7TwbR2tmsPvKhHAQKBgQDvAcrnVJZ+J/18KFcNoY/m0dmoeMmjqV9s7SUfPESrTvEkuQHacveYp9EBHNZL7xGShDQSTyXdekJxBiF+2jjyvzdaBIln9tJM47Hu4F1jsirgQdBE5eaNP4SL1VZSlwRcFlC7VL3MXAskq/GaurFhpW5N0qGrANGAvNBLlw3QaQKBgQC35+DVWTWF9e59lutI62vD5qGClBjTXX06/Mih4PAbVP4Gs17jGHHdDcV28WSWcYpQKW3E1RgIvQgR1IxmGAIjR7a9sGoJudkDaok+JvIbVJzE5jnqnA8dSbj3Ms+TKRG6K5MQ7ENFq2T1e11nF9fYNDcZvT92Ef1MD7RKWI+z0QKBgQDAqYRDV9d8WIYoeGg8L7lNSmoXOU7po9Wte7iJ5k5AfYE7pfsNedGG9/gt2m4QheV3YySKgcy3LR+z9ko39HcljuhyoueUq804d+9t7OS7Qdr4LXVSpLRltgcFpXJRXzT/emmXqKbavnOpGwXGQapNWUqCZ0E1FJLNIOcTl+9lyQKBgASrXooMOc+s7uFBc7fmQlY3BOLL39V+Ci/1OyLhEzeecNS6uS4K8NksAFeEyYkIWPqwEplsz69pHUtWmjn9YhCDQaCPWiHlFh2AFvv9ASiwfUqCjfXnAHs94/Ri1eJmqIyRjqpfVVXvhCC3gSec814J9Vwx8eqNimDkBS/eD8gBAoGAXn4IvV2wHZy/vmzlGIRFWgfoudJj2Dg+DgfmLJf9TM3fVcOPG9z3vVng30aJS9iFT4gR3uAlqQbjWgX+HtSh5k7mF2C6/bEch8vnyxKq3rzR0XLLT5DoJYDM09oheSF6mH3MVfF+D5HiLwmI/t57TPG6k1uqADbEciq9hT7/bmM=")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}
