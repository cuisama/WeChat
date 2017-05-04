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
     * http请求方式：POST（请使用https协议）
     * 自定义菜单创建接口
     */
    public static String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    /**
     * http请求方式：GET
     * 自定义菜单删除接口
     */
    public static String DELETE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * http请求方式：GET
     * 自定义菜单查询接口
     */
    public static String GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

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
     */
    public static String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * http请求方式: POST
     * 批量获取用户基本信息
     */
    public static String USER_INFO_LIST="https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    /**
     * 获取用户列表
     * {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
     */
    public static String USER_LIST="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

    /**
     * 获取微信服务器IP地址
     * {
     "ip_list": [
     "127.0.0.1",
     "127.0.0.2",
     "101.226.103.0/25"
     ]
     }
     */
    public static String WEIXIN_IP= "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

    /**
     * 用户标签管理 创建标签
     * http请求方式：POST
     * {
     "tag" : {
     "name" : "广东"//标签名
     }
     }
     */
    public static String CREATE_TAG="https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";

    /**
     * 获取公众号已创建的标签
     * http请求方式：GET（请使用https协议）
     */
    public static String GET_TAGS="https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";

    /**
     * post!!!!
     * 获取标签下粉丝列表
     * {
     "tagid" : 134,
     "next_openid":""//第一个拉取的OPENID，不填默认从头开始拉取
     }
     */
    public static String TAG_USERS="https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";

    /**
     * 批量为用户打标签
     * {
     "openid_list" : [//粉丝列表
     "ocYxcuAEy30bX0NXmGn4ypqx3tI0",
     "ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"
     ],
     "tagid" : 134
     }
     */
    public static String BATCH_TAGGING="https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";

    /**
     * 获取用户身上的标签列表
     * "openid" : "ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"
     */
    public static String LIST_USER_TAGS="https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";

    /**
     * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
     */
    public static String MASS_SEND_BY_OPENID = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";


    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     * http请求方式: POST
     * {
     "msg_id": "201053012"
     }
     */
    public static String MASS_SEND_GET_STATE="https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";

    /**
     * 添加客服帐号
     * http请求方式: POST
     * {
     "kf_account" : "test1@test",
     "nickname" : "客服1",
     "password" : "pswmd5",
     }
     */
    public static String ADD_KFACCOUNT="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";

    public static String GET_KFLIST="https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";

    /**
     * 客服接口发消息
     * http请求方式: POST
     * 发送文本消息
     * {
     "touser":"OPENID",
     "msgtype":"text",
     "text":
     {
     "content":"Hello World"
     }
     }
     */
    public static String KF_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";


    /**
     * http请求方式：POST/FORM，使用https
     */
    public static String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
}
