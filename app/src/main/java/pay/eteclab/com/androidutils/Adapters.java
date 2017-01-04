package pay.eteclab.com.androidutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.xndroid.cn.annotation.Layout;
import org.xndroid.cn.annotation.ViewIn;
import org.xndroid.cn.widget.HolderAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
@Layout(R.layout.layout_adapter)
public class Adapters extends HolderAdapter<String> {

    public Adapters(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void bindView(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
        ViewHolder holder = (ViewHolder) paramViewHolder;
        holder.text.setText(mData.get(paramInt) + "---" + paramInt);
    }

    class ViewHolder extends BaseViewHolder {
        @ViewIn(R.id.text)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
