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

package com.naivor.app.data.remote;

import android.content.Context;
import android.text.TextUtils;

import com.naivor.app.extras.utils.ToastUtil;


/**
 * CheckParams api参数检查类
 */
public class CheckParams {

	/**
	 * 验证手机号码
	 * @param phone
	 * @return
	 */
	public  static boolean  checkPhone(Context context,String  phone){
		if (TextUtils.isEmpty(phone)) {
			ToastUtil.showToast(context, "请输入手机号");  //为空
			return  false;
		}

		String reg="[1]\\d{10}";

		if (phone.length()<11 || !phone.matches(reg)) {
			ToastUtil.showToast(context,"手机号码不正确");    //格式错误
			return  false;
		}

		return  true;
	}

	/**
	 * 验证密码
	 * @param passwd
	 * @return
	 */
	public  static boolean  checkPasswd(Context context,String  passwd){
		if (TextUtils.isEmpty(passwd)) {
			ToastUtil.showToast(context,"请输入密码");     //为空
			return  false;
		}
		
		if (passwd.length()<6) {
			ToastUtil.showToast(context,"密码不能小于6位");    //密码太短
			return  false;
		}

		return  true;
	}
	
	/**
	 * 验证验证码
	 * @param code
	 * @return
	 */
	public  static boolean  checkCode(Context context,String  code){
		if (TextUtils.isEmpty(code)) {
			ToastUtil.showToast(context,"请输入验证码");      //为空
			return  false;
		}
		
		if (code.length()!=6) {
			ToastUtil.showToast(context,"验证码不正确");    //错误
			return  false;
		}

		return  true;
	}
}
