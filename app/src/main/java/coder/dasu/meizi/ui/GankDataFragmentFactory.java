package coder.dasu.meizi.ui;

import coder.dasu.meizi.ui.fragment.AndroidDataFragment;
import coder.dasu.meizi.ui.fragment.AppDataFragment;
import coder.dasu.meizi.ui.fragment.DayGankFragment;
import coder.dasu.meizi.ui.fragment.ExpandDataFragment;
import coder.dasu.meizi.ui.fragment.GankDataFragment;
import coder.dasu.meizi.ui.fragment.IosDataFragment;
import coder.dasu.meizi.ui.fragment.PushDataFragment;
import coder.dasu.meizi.ui.fragment.WebDataFragment;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class GankDataFragmentFactory {

    private static final String TAG = GankDataFragmentFactory.class.getSimpleName();

    /**
     * Gank data Fragment key
     */
    public enum FragmentKey {
        Day("每日干货"), Android("Android"), Ios("iOS"),
        Web("前端"), Expand("拓展资源"),Push("瞎推荐"), App("App");

        String value;

        FragmentKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static FragmentKey getKey(String value) {
            for (FragmentKey k : values()) {
                if (k.getValue().equals(value)) {
                    return k;
                }
            }
            return null;
        }
    }

    public GankDataFragment newFragment(String value) {
        FragmentKey key = FragmentKey.getKey(value);
        if (key == null) {
            throw new UnsupportedOperationException("FragmentKey 无法识别");
        }
        switch (key) {
            case Day:
                return new DayGankFragment(key.getValue());
            case Android:
                return new AndroidDataFragment(key.getValue());
            case Ios:
                return new IosDataFragment(key.getValue());
            case Expand:
                return new ExpandDataFragment(key.getValue());
            case Web:
                return new WebDataFragment(key.getValue());
            case Push:
                return new PushDataFragment(key.getValue());
            case App:
                return new AppDataFragment(key.getValue());
            default:
                throw new UnsupportedOperationException("FragmentKey 无法识别");
        }
    }

}
