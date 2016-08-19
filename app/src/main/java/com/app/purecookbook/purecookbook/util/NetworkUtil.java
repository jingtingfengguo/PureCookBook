package com.app.purecookbook.purecookbook.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.purecookbook.purecookbook.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NetworkUtil {
    public static String KEY="834589fd7f071258932945231d956817";
    /**
     * 检查当前网络状态
     *
     * @param context
     * @return
     */
    public static boolean getNetState(Context context){
        ConnectivityManager connectivity=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
        if(networkinfo !=null || networkinfo.isAvailable()){
            return false;
        }else{
            return true;
        }

    }


    /**
     * pn	数据返回起始下标，默认0
     * rn	数据返回条数，最大30，默认10
     */
    public static String getURL(int id,String menu,int pn,int rn){
        String url;
        if(menu == null || menu.equals("")){
            url = "http://apis.juhe.cn/cook/index?key=" + KEY + "&cid=" + id + "&pn=" + pn + "&rn=" + rn;
        }else{
            try{
                menu = URLEncoder.encode(menu, "utf-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            url = "http://apis.juhe.cn/cook/query?key=" + KEY + "&menu=" + menu + "&pn=" + pn + "&rn=" + rn;
        }
        return url;
    }

    public static String getURL(int id, int pn) {
        String url = "http://apis.juhe.cn/cook/index?key=" + KEY + "&cid=" + id + "&pn=" + pn;
        return url;
    }

    public static String getURL(int id) {
        String url = "http://apis.juhe.cn/cook/index?key=" + KEY + "&cid=" + id;
        return url;
    }

    public static boolean isWrong(Context context,int errorcode,Toast toast){
        String text="获取成功";
        boolean is=false;
        switch (errorcode) {
            case 0:
                text = "获取成功";
                is = true;
                break;
            case 204601:
                text = "菜谱名称不能为空";
                break;
            case 204602:
                text = "查询不到相关信息";
                break;
            case 204603:
                text = "菜谱名过长";
                break;
            case 204604:
                text = "错误的标签ID";
                break;
            case 204605:
                text = "查询不到数据";
                break;
            case 204606:
                text = "错误的菜谱ID";
                break;
            case 10001:
                text = "错误的请求KEY	101";
                break;
            case 10002:
                text = "该KEY无请求权限	102";
                break;
            case 10003:
                text = "KEY过期	103";
                break;
            case 10004:
                text = "错误的OPENID	104";
                break;
            case 10005:
                text = "应用未审核超时，请提交认证	105";
                break;
            case 10007:
                text = "未知的请求源	107";
                break;
            case 10008:
                text = "被禁止的IP	108";
                break;
            case 10009:
                text = "被禁止的KEY	109";
                break;
            case 10011:
                text = "当前IP请求超过限制	111";
                break;
            case 10012:
                text = "请求超过次数限制	112";
                break;
            case 10013:
                text = "测试KEY超过请求限制	113";
                break;
            case 10014:
                text = "系统内部异常，请重试	114";
                break;
            case 10020:
                text = "接口维护	120";
                break;
            case 10021:
                text = "接口停用";
                break;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return is;



    }


}

