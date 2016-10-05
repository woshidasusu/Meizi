package coder.dasu.meizi;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

/**
 * Created by dasu on 2016/9/30.
 * https://github.com/woshidasusu/Meizi
 */
public class DebugApp extends MeiziApp{

    private static final String TAG = DebugApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
//        ButterKnife.setDebug(true);
        Picasso.with(getApplicationContext())
                .setDebugging(true);

    }
}
