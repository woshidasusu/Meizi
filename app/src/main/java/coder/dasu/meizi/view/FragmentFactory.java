package coder.dasu.meizi.view;

import coder.dasu.meizi.view.base.BaseFragment;
import coder.dasu.meizi.view.fragment.AndroidDataFragment;
import coder.dasu.meizi.view.fragment.AppDataFragment;
import coder.dasu.meizi.view.fragment.DayDataFragment;
import coder.dasu.meizi.view.fragment.ExpandDataFragment;
import coder.dasu.meizi.view.fragment.IosDataFragment;
import coder.dasu.meizi.view.fragment.MeiziDataFragment;
import coder.dasu.meizi.view.fragment.PushDataFragment;
import coder.dasu.meizi.view.fragment.VideoDataFragment;
import coder.dasu.meizi.view.fragment.WebDataFragment;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class FragmentFactory {

    private static final String TAG = FragmentFactory.class.getSimpleName();

    /**
     * Fragment key
     */
    public enum FragmentKey {
        Day("每日干货"), Android("Android"), Ios("iOS"), Video("休息视频"), Meizi("福利"),
        Expand("拓展资源"), Web("前端"), Push("瞎推荐"), App("App");

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

    public BaseFragment newFragment(String value) {
        FragmentKey key = FragmentKey.getKey(value);
        if (key == null) {
            throw new UnsupportedOperationException("FragmentKey 无法识别");
        }
        switch (key) {
            case Day:
                return new DayDataFragment(key.getValue());
            case Android:
                return new AndroidDataFragment(key.getValue());
            case Ios:
                return new IosDataFragment(key.getValue());
            case Video:
                return new VideoDataFragment(key.getValue());
            case Meizi:
                return new MeiziDataFragment(key.getValue());
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
