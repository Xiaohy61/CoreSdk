package com.skyward.android.sdk.network.update.app;

import com.skyward.android.sdk.R;
import com.skyward.android.sdk.base.BaseRecyclerAdapter;
import com.skyward.android.sdk.base.BaseRecyclerViewHolder;

import java.util.ArrayList;

/**
 *
 * date: 2018/4/27
 * desc:
 * @author skyward
 */
public class UpdateAppLogsAdapter extends BaseRecyclerAdapter<String,BaseRecyclerViewHolder> {


    protected UpdateAppLogsAdapter(int layoutResId, ArrayList<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void bindTheData(BaseRecyclerViewHolder holder,String data, int position) {
        holder.setText(R.id.logs,data);
    }
}
