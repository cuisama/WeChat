package com.iss;

/**
 * Created by yxcui on 2017/5/2.
 */
public class WXAPIUrl {

    /**
     * 获取access_token
     */
    public static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取用户基本信息(UnionID机制)
     * {
     "subscribe": 1,
     "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
     "nickname": "Band",
     "sex": 1,
     "language": "zh_CN",
     "city": "广州",
     "province": "广东",
     "country": "中国",
     "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
     eMsv84eavHiaiceqxibJxCfHe/0",
     "subscribe_time": 1382694957,
     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     "remark": "",
     "groupid": 0,
     "tagid_list":[128,2]
     }
     {"errcode":40013,"errmsg":"invalid appid"}
     */
    public static String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";

    /**
     * 批量获取用户基本信息
     */
    public static String USER_INFO_LIST="https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    /**
     * 获取用户列表
     * {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
     * {"errcode":40013,"errmsg":"invalid appid"}
     */
    public static String USER_LIST="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
}
