/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.presentation.ui.helper;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * 验证按钮计时器
 * <p/>
 * Created by tianlai on 16-4-11.
 */
public class TimeCountHelper extends CountDownTimer {
    private TextView view;

    public TimeCountHelper(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() { // 计时结束

        view.setText("获取验证码");
        view.setEnabled(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.setEnabled(false);
        view.setText("重新获取(" + millisUntilFinished / 1000 + ")");
    }


    public View getView() {
        return view;
    }

    public void setView(TextView view) {
        this.view = view;
    }
}
