package cloud.cn.applicationtest.activity.smartservice;

import android.util.SparseArray;

import cloud.cn.androidlib.activity.BaseFragment;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public class FragmentFactory {

    // 保存Fragment集合,方便复用
    private static SparseArray<BaseFragment> sFragmentMap = new SparseArray<BaseFragment>();

    // 根据指针位置,生产相应的Fragment
    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = sFragmentMap.get(position);

        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;

                default:
                    break;
            }

            sFragmentMap.put(position, fragment);
        }

        return fragment;
    }
}
