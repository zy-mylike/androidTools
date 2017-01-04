package com.enetic.push.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.enetic.esale.R;

/**
 * Created by json on 2016/6/3.
 */
public class InputNumberView extends PopupWindow {

    public InputNumberView show(Context context, View parent, final EditText mEditMoney) {
        LinearLayout layout = (LinearLayout) View.inflate(context, R.layout.layout_num_input, null);
        for (int index = 0; index < layout.getChildCount();
             index++) {
            View child = layout.getChildAt(index);

            if (child instanceof LinearLayout) {
                for (int ch = 0; ch < ((LinearLayout) child).getChildCount();
                     ch++) {
                    ((LinearLayout) child).getChildAt(ch).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           if (v.getId() != R.id.num_click) {
                               TextView tv = (TextView) v;
                               mEditMoney.append(tv.getText().toString());
                            } else {
                               String s = mEditMoney.getText().toString();
                               if(TextUtils.isEmpty(s)){
                                   return;
                               }
                               mEditMoney.setText(s.substring(0,s.length()-1));
                               Editable etext = mEditMoney.getText();
                               Selection.setSelection(etext, etext.length());
                            }
                        }
                    });
                }
            }

        }
        setWidth(-1);
        setHeight(-2);
        setOutsideTouchable(true);
        setContentView(layout);
        showAtLocation(parent, Gravity.BOTTOM,0,0);
        return this;
    }

}
