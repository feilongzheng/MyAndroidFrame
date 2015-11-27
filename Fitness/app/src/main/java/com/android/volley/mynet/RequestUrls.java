package com.android.volley.mynet;

import com.lcworld.fitness.framework.util.Const;

public class RequestUrls {

    /**
     * 线上地址前缀
     */
    public static String BASE_URL = Const.BASE_ONLINE_URL;
    public static final String SHARE_URL = "http://api.pengsi.cn/service/appdown/index.html?u=";
    public static final String ABOUTUS_URL = "http://api.pengsi.cn/service/aboutus/index.html";
    public static final String RECHARGE_URL = "http://m.pengsi.cn/pay/index.html?userid=%1$s&paymoney=%2$s";
    public static final String RECHARGE_URL_TEST = "http://test.pengsi.cn/wap/pay/index.html?userid=%1$s&paymoney=%2$s";
    public static final String RECHARGE_URL_ORDER = "http://m.pengsi.cn/wap/pay/theBill.html?userid=%1$s&mdw=%2$s";
    /**
     * 图片地址前缀
     */
    public static String URL_ALLIMAGE = Const.ALLIMAGE_ONLINE_URL;
    public static final String URL_PLSIT = "http://api.pengsi.cn/service/clientprofiles/";

    /**
     * 如果程序发生崩溃，依靠这个方法来重新修改地址前缀，效果如同Act_Splash中的一样 修改线上环境的地址前缀
     */
    static {
        if (Const.ISDEBUG) {
            BASE_URL = DebugConfig.BASE_URL;
            URL_ALLIMAGE = DebugConfig.URL_ALLIMAGE;
        }
    }

    /**
     * url = BASE_URL + UrlPlus and UrlPlus is the unique string in all of urls
     */
    public interface UrlPlus {
        /**
         * 第三方授权后判断是登录或注册 -- 新浪微博
         */
        String v1_sinatoweiboauthsecond = BASE_URL + "a/n/login-sina.json";
        /**
         * 第三方授权后判断是登录或注册 -- QQ
         */
        String v3_qqtoweiboauthsecond = BASE_URL + "a/n/login-qq.json";
        /**
         * 第三方授权后判断是登录或注册 -- Wechat
         */
        String v5_wxtoweiboauthsecond = BASE_URL + "a/n/login-wx.json";
        /**
         * 登录
         */
        String v1_login = BASE_URL + "browser/v1_login.jsp";
        /**
         * 验证用户名是否存在
         */
        String v1_isnickname = BASE_URL + "browser/v1_isnickname.jsp";
        /**
         * 注册
         */
        String register = BASE_URL + "/a/n/register.json";
        /**
         * 获取推荐用户列表
         */
        String v2_getregisterreomuserlist = BASE_URL + "browser/v2_getregisterreomuserlist.jsp";
        /**
         * 为你推荐---关注
         */
        String v2_insertreceives = BASE_URL + "browser/v2_insertreceives.jsp";
        /**
         * 获取排行榜数据
         */
        String rank_list = BASE_URL + "/a/n/rankings/rank-list.json";
        /**
         * 绑定新浪
         */
        String v1_tencenttoweiboauthsecond = BASE_URL + "browser/v1_tencenttoweiboauthsecond.jsp";

        /**
         * 得到活动列表
         */
        String v4_getshowlist = BASE_URL + "browser/v4_getshowlist.jsp";
        /**
         * 获取发现页面的信息
         */
        String v5_getfindindex = BASE_URL + "browser/v5_getfindindex.jsp";
        /**
         * 活动详情
         */
        String v1_getshow = BASE_URL + "browser/v1_getshow.jsp";
        /**
         * 通过showID获取活动详情
         */
        String v4_getshowbyid = BASE_URL + "browser/v4_getshowbyid.jsp";
        /**
         * 查询活动是否存在
         */
        String v4_getisshowbyid = BASE_URL + "browser/v4_getisshowbyid.jsp";
        /**
         * 美图
         */
        String v1_getphotolist = BASE_URL + "browser/v1_getphotolist.jsp";
        /**
         * 获得图集中所有的图片信息
         */
        String v1_getphtotbyid = BASE_URL + "browser/v1_getphtotbyid.jsp";
        /**
         * 获得图集中所有的图片信息
         */
        String v1_getalbumphotolist = BASE_URL + "browser/v1_getalbumphotolist.jsp";
        /**
         * 赞（图片，动态）
         */
        String v1_praise = BASE_URL + "browser/v1_praise.jsp";
        /**
         * 取消动态的点赞
         */
        String v3_deluseractionpraise = BASE_URL + "browser/v3_deluseractionpraise.jsp";
        /**
         * 获取评论列表 * 获取评论数据
         *
         * @param userId
         * @param index
         * @param count
         */
        String v1_getcommentlist = BASE_URL + "browser/v1_getcommentlist.jsp";
        /**
         * 获取礼物列表,获取星星宝贝和花草
         */
        String v1_getgiftlist = BASE_URL + "browser/v1_getgiftlist.jsp";
        /**
         * 举报接口
         */
        String v1_insertcontentreport = BASE_URL + "browser/v1_insertcontentreport.jsp";
        /**
         * 聊天举报接口
         */
        String im_accuse_user = BASE_URL + "a/l/im/accuse-user.json";
        /**
         * 获取赠送记录
         */
        String v5_getgiftinteractlist = BASE_URL + "browser/v5_getgiftinteractlist.jsp";
        /**
         * 获取活动详情列表
         */
        String v5_getinformlist = BASE_URL + "browser/v5_getinformlist.jsp";
        /**
         * 收藏 type=0 活动收藏 type=1 直播间
         */
        String v1_collect = BASE_URL + "browser/v1_collect.jsp";
        /**
         * 删除收藏 type=0 活动收藏 type=1 直播间
         */
        String v1_collectdel = BASE_URL + "browser/v1_collectdel.jsp";
        /**
         * 礼物详情
         */
        String v5_getgiftview = BASE_URL + "browser/v5_getgiftview.jsp";
        /**
         * 用户的捧金数
         */
        String v1_getuserbalance = BASE_URL + "browser/v1_getuserbalance.jsp";
        /**
         * 捧 * @param ruserId 被捧的USERID
         *
         * @param type
         * 1.在捧聊页捧 0.不在捧聊页
         */
        String v1_insertbuygift = BASE_URL + "browser/v1_insertbuygift.jsp";
        /**
         * 求捧 * @param type 2.求捧
         */
        String v1_invitesignupqp = BASE_URL + "browser/v1_invitesignupqp.jsp";
        /**
         * 提交图片评论
         */
        String v1_insertcomment = BASE_URL + "browser/v1_insertcomment.jsp";
        /**
         * 提交动态评论 * @param userId 我的userid
         *
         * @param ruserId
         * 活动作者的id
         * @param objId
         * @param commentType
         * @param huserId
         * 回复人的id
         * @param hNickName
         * @param content
         * @param type
         * 动态评论的时候才需要提交 0为在动态列表页 1为详情页
         */
        String v3_insertcomment = BASE_URL + "browser/v3_insertcomment.jsp";
        /**
         * 删除评论
         */
        String v1_commentdel = BASE_URL + "browser/v1_commentdel.jsp";
        /**
         * 检测活动是否可以报名
         */
        String v1_shwocheckenroll = BASE_URL + "browser/v1_shwocheckenroll.jsp";
        /**
         * 招募报名
         */
        String v4_showsignup = BASE_URL + "browser/v4_showsignup.jsp";
        /**
         * 活动报名用户详情
         */
        String v1_getshowworksbyid = BASE_URL + "browser/v1_getshowworksbyid.jsp";
        /**
         * 获取活动报名用户详情
         */
        String v4_getshowworksbyid = BASE_URL + "browser/v4_getshowworksbyid.jsp";
        /**
         * 活动报名邀请用户列表
         */
        String v1_getshowfollowslist = BASE_URL + "browser/v1_getshowfollowslist.jsp";
        /**
         * 邀请某个用户报名
         */
        String v1_invitesignupyq = BASE_URL + "browser/v1_invitesignupyq.jsp";
        /**
         * 拉票用户列表
         */
        String v1_getshowfanslist = BASE_URL + "browser/v1_getshowfanslist.jsp";
        /**
         * 针对某个人拉票
         */
        String v1_invitesignuplp = BASE_URL + "browser/v1_invitesignuplp.jsp";
        /**
         * 对某个人投票
         */
        String v1_showvote = BASE_URL + "browser/v1_showvote.jsp";
        /**
         * 活动报名激活
         */
        String v1_upshowdateaction = BASE_URL + "browser/v1_upshowdateaction.jsp";
        /**
         * 得到某个人的票值详情
         */
        String v1_getshowvotezhixx = BASE_URL + "browser/v1_getshowvotezhixx.jsp";
        /**
         * 用户的个人信息 * @param type 1：档案 2: 个人主页信息
         */
        String v1_getuserinfo = BASE_URL + "browser/v1_getuserinfo.jsp";
        /**
         * 获取主页信息
         */
        String v5_getuserinfo = BASE_URL + "a/l/user/home.json";
        /**
         * 获取主页信息 * @param type 0 发验证码 1忘记密码时找回
         */
        String v1_sendsms = BASE_URL + "browser/v1_sendsms.jsp";
        /**
         * 绑定(修改)邮箱或手机号码
         */
        String v1_upusermainphone = BASE_URL + "browser/v1_upusermainphone.jsp";
        /**
         * 首页星态圈数据
         */
        String v3_getxxuseractionlist = BASE_URL + "browser/v3_getxxuseractionlist.jsp";
        /**
         * 某一个用户的动态
         */
        String v3_getuseractionlist = BASE_URL + "browser/v3_getuseractionlist.jsp";
        /**
         * 获取捧爷任务列表 悬赏任务
         */
        String v1_gettaskproglist = BASE_URL + "browser/v1_gettaskproglist.jsp";
        /**
         * 获取任务奖励 领取悬赏任务奖励
         */
        String v1_getrenwujl = BASE_URL + "browser/v1_getrenwujl.jsp";
        /**
         * 通知服务器新浪分享成功
         */
        String v1_userlogs = BASE_URL + "browser/v1_userlogs.jsp";
        /**
         * 查询用户的魅魅花和捧爷草信息 * @param type //1:捧爷草 2：魅魅花
         */
        String v5_getpropstatlist = BASE_URL + "browser/v5_getpropstatlist.jsp";
        /**
         * 获取捧爷草或魅力花详情
         */
        String v1_getuserzhixx = BASE_URL + "browser/v1_getuserzhixx.jsp";
        /**
         * 捧丝儿园
         */
        String v1_getfanslist = BASE_URL + "browser/v1_getfanslist.jsp";
        /**
         * 星星园 * @param sex -1 表示不限 用户性别 0、女 1、男
         *
         * @param pxfs
         * 0 时间 1贡献 2特别关注
         */
        String v1_getfollowslist = BASE_URL + "browser/v1_getfollowslist.jsp";
        /**
         * 获取新浪和腾讯微博好友 * @param type type=0 表示新浪 type=1 表示腾讯
         */
        String v1_getloadfens = BASE_URL + "browser/v1_getloadfens.jsp";
        /**
         * * 添加平台好友
         *
         * @param userId
         * @param type
         * =3 星星邀请 =4 捧丝邀请
         * @param wtype
         * =0 是新浪微博 =1 腾讯微博
         * @param sinaname
         * = BASE_URL + 邀请人昵称
         */
        String v1_share = BASE_URL + "browser/v1_share.jsp";
        /**
         * 修改用户信息,功力页面修改确认键,单项修改用户信息
         */
        String v1_upuserinfobyid = BASE_URL + "browser/v1_upuserinfobyid.jsp";
        /**
         * 删除星星
         */
        String v1_delfollow = BASE_URL + "browser/v1_delfollow.jsp";
        /**
         * 删除捧丝
         */
        String v6_deluserrelation = BASE_URL + "browser/v6_deluserrelation.jsp";
        /**
         * * 搜索星星
         *
         * @param userId
         * 用户ID
         * @param selvalue
         * 搜索的内容
         */
        String v1_getselectfollowslist = BASE_URL + "browser/v1_getselectfollowslist.jsp";
        /**
         * 获取用户参加的活动列表
         */
        String v1_getplayerlist = BASE_URL + "browser/v1_getplayerlist.jsp";
        /**
         * 获取收藏的活动列表
         */
        String v1_getcollectlist = BASE_URL + "browser/v1_getcollectlist.jsp";
        /**
         * 获取用户的图集列表
         */
        String v1_getalbumlist = BASE_URL + "browser/v1_getalbumlist.jsp";
        /**
         * 用户的档案
         */
        String v4_getuserinfoxx = BASE_URL + "browser/v4_getuserinfoxx.jsp";
        /**
         * 可用的星星宝贝
         */
        String v3_getsygiftlist = BASE_URL + "/a/l/gift/query-all.json";
        /**
         * 收到的星星宝宝
         */
        String v1_gettagiftlist = BASE_URL + "browser/v1_gettagiftlist.jsp";
        /**
         * 求捧的星星宝贝
         */
        String v1_gettaaskstatlist = BASE_URL + "browser/v1_gettaaskstatlist.jsp";
        /**
         * 星星宝贝详情（可用的星星宝贝，收到的星星宝贝 单个ITEM跳转后的列表页）
         */
        String v1_getusergiftreceivelist = BASE_URL + "browser/v1_getusergiftreceivelist.jsp";
        /**
         * 设置界面数据请求
         */
        String v1_getshezhisj = BASE_URL + "browser/v1_getshezhisj.jsp";
        /**
         * 昵称模糊查询
         */
        String v1_getuserbynickname = BASE_URL + "browser/v1_getuserbynickname.jsp";
        /**
         * 发布动态
         */
        String PublishActionServlet = BASE_URL + "a/l/sns/feed/publish.json";
        /**
         * 上传图集
         */
        String upload_photoalbum = BASE_URL + "a/l/user/upload-photoalbum.json";//upload_photoalbum  a/l/user/upload-photoalbum.json
        /**
         * 新建图集
         */
        String v1_insertalbum = BASE_URL + "browser/v1_insertalbum.jsp";
        /**
         * 删除图集
         */
        String v1_delalbum = BASE_URL + "browser/v1_delalbum.jsp";
        /**
         * 删除图集内的图片
         *
         * @param photosId
         * 需要删除的图片id，多个用，隔开
         * @param indexs
         * 需要删除的图片index(idx)，多个用，隔开
         */
        String v1_delphotolist = BASE_URL + "browser/v1_delphotolist.jsp";
        /**
         * 移动图片到图集
         * <p/>
         * 编辑图集（修改图集名称
         *
         * @param userId
         * @param albumId
         * @param albumName
         * @param albumDes
         */
        String v1_upalbum = BASE_URL + "browser/v1_upalbum.jsp";
        /**
         * 移动图片到图集
         *
         * @param userId
         * @param albumId
         * @param upAlbumId
         * 目标图集的ID
         * @param upAlbumName
         * 目标图集的名字
         * @param upAlbumMs
         * 目标图集的描述
         * @param photosId
         * @param indexs
         */
        String v1_upphotoalbumid = BASE_URL + "browser/v1_upphotoalbumid.jsp";
        /**
         * 删除动态
         *
         * @param userId
         * @param actionId
         */
        String v3_deluseraction = BASE_URL + "browser/v3_deluseraction.jsp";
        /**
         * 捧聊（私信）列表,获取机构消息类表
         */
        String v1_getmessagelist = BASE_URL + "browser/v1_getmessagelist.jsp";
        /**
         * * 捧聊（私信）详情
         *
         * @param userId
         * @param cuserId
         * @param status
         * 0 未读的信息
         * @param index
         * @param count
         */
        String v1_getmessagedialogto = BASE_URL + "browser/v1_getmessagedialogto.jsp";
        /**
         * 发捧聊
         *
         * @param userId
         * @param ruserId
         * @param content
         * @param type
         */
        String v1_invitesignup = BASE_URL + "browser/v1_invitesignup.jsp";
        /**
         * 功力页面
         *
         * @param userId
         */
        String v3_getuserskill = BASE_URL + "browser/v3_getuserskill.jsp";
        /**
         * 添加作品键
         */
        String v2_insertuserworks = BASE_URL + "browser/v2_insertuserworks.jsp";
        /**
         * 删除作品键
         */
        String v2_delworks = BASE_URL + "browser/v2_delworks.jsp";
        /**
         * * 活动申请
         *
         * @param userId
         * @param userName
         * @param tel
         * @param info
         */
        String v1_insertshowapply = BASE_URL + "browser/v1_insertshowapply.jsp";
        /**
         * * 检测图集是否可以认证（检测是否有图集认证令牌）, 图集认证
         *
         * @param userId
         * @param albumId
         */
        String v3_getupalbumstat = BASE_URL + "browser/v3_getupalbumstat.jsp";
        /**
         * * 实名认证
         *
         * @param userId
         * @param userName
         * @param cardNum
         * @param file
         */
        String autonym_approve = BASE_URL + "/a/l/user/autonym-approve.json";
        /**
         * 职业认证
         *
         * @param file
         */
        String IndetityUserApprove = BASE_URL + "IndetityUserApprove";
        /**
         * @param userId
         * @param albumId
         */
        String autonym_identity_approve = BASE_URL + "/a/l/user/autonym-identity-approve.json";
        /**
         * 修改PUSH TOKEN
         */
        String v1_upuserpushtoken = BASE_URL + "browser/v1_upuserpushtoken.jsp";
        /**
         * * 更改用户封面图
         *
         * @param userId
         * @param file
         */
        String upload_background = BASE_URL + "/a/l/user/upload-background.json";
        /**
         * * 更改用户头像
         *
         * @param userId
         * @param file
         */
        String upload_icon = BASE_URL + "/a/l/user/upload-icon.json";
        /**
         * * @param userId 用户ID
         *
         * @param ruserid
         * 用户ID
         * @param type
         * =0 捧爷首页 =1 魅魅花首页
         */
        String v5_getexperindex = BASE_URL + "browser/v5_getexperindex.jsp";

        /**
         * 星星推荐
         */
        String query_tags = BASE_URL + "/a/l/search/query-tags.json";
        /**
         * 搜索标签
         */
        String recommend_list = BASE_URL + "/a/n/rankings/recommend-list.json";
        /**
         * * 未读提示：首页下的活动和星态圈
         *
         * @param userId
         */
        String v3_getuserindexto = BASE_URL + "browser/v3_getuserindexto.jsp";
        /**
         * * 活动类型分页
         *
         * @param params
         */
        String v1_getshowlist = BASE_URL + "browser/v1_getshowlist.jsp";
        /**
         * * 修改密码
         *
         * @param userId
         * @param password
         */
        String v1_uppassword = BASE_URL + "browser/v1_uppassword.jsp";
        /**
         * * 取消绑定SNS
         *
         * @param userId
         * @param type
         * 0=push 1=sina 2=tecent
         * @param typeValue
         * 0= 关闭 1= 打开
         */
        String v1_upsetpush = BASE_URL + "browser/v1_upsetpush.jsp";
        /**
         * * 修改代表作品接口
         */
        String v2_upworks = BASE_URL + "browser/v2_upworks.jsp";
        /**
         * 获得所有作品接口
         */
        String v2_getuserworkslist = BASE_URL + "browser/v2_getuserworkslist.jsp";
        /**
         * * 上传通讯录
         *
         * @param userId
         * @param phoneNums
         */
        String v1_uploaduserphone = BASE_URL + "browser/v1_uploaduserphone.jsp";
        /**
         * * 获取经过服务器比对好的通讯录数据
         *
         * @param userId
         * @param type
         * = BASE_URL + 3 所有用户
         * @param index
         * @param count
         */
        String v1_getusermutuallist = BASE_URL + "browser/v1_getusermutuallist.jsp";
        /**
         * 获取要下载的plist文件名字和MD5
         */
        String version_upgrade = BASE_URL + "/a/n/version-upgrade.json";
        /**
         * 获取用户状态
         */
        String v4_getuserstatuslist = BASE_URL + "browser/v4_getuserstatuslist.jsp";
        /**
         * 获取捧爷的草气泡
         */
        String v5_getreceivebyexperlist = BASE_URL + "browser/v5_getreceivebyexperlist.jsp";
        /**
         * 获取捧爷用户列表
         */
        String v5_getproplist = BASE_URL + "browser/v5_getproplist.jsp";
        /**
         * 收取捧爷草
         */
        String v5_getusertake = BASE_URL + "browser/v5_getusertake.jsp";
        /**
         * 推荐给朋友
         */
        String SHARETOFRIEND = BASE_URL + "/a/h5/inviteUser.htm";

        /**
         * 获取捧爷草信息
         */
        String v5_getreceivegiftdetail = BASE_URL + "browser/v5_getreceivegiftdetail.jsp";

        /**
         */
        String v4_getuserinfo = BASE_URL + "browser/v4_getuserinfo.jsp";

        /**
         */
        String v4_getuserintro = BASE_URL + "browser/v4_getuserintro.jsp";
        /**
         * 检查版本
         */
        String query_android_version = BASE_URL + "/a/l/system/query-android-version.json";

        /**
         */
        String v1_getservicepath = Const.BASE_ONLINE_URL + "browser/v1_getservicepath.jsp";
        /**
         * 删除消息
         */
        String v5_delusermesslist = BASE_URL + "browser/v5_delusermesslist.jsp";
        /**
         * 获取互动请求消息列表
         */
        String v6_getinformlist = BASE_URL + "browser/v6_getinformlist.jsp";
        /**
         * 机构主页-点“更多”进来
         */
        String v4_getusershowlist = BASE_URL + "browser/v4_getusershowlist.jsp";

        /**
         * 获取微信access_token
         */
        String weixinAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        /**
         * 获取用户个人信息（UnionID机制）
         */
        String weixiUserInfonUrl = "https://api.weixin.qq.com/sns/userinfo";
        /**
         * 星星花气泡
         */
        String v1_getgiftreceivebyreceive = BASE_URL + "browser/v1_getgiftreceivebyreceive.jsp";
        /**
         * 星星花赞和投票
         */
        String v3_getuserindex = BASE_URL + "browser/v3_getuserindex.jsp";
        /**
         * 星星认证界面
         */
        String v5_getuserxxtmp = BASE_URL + "browser/v5_getuserxxtmp.jsp";
        /**
         * 星星收取礼物
         */
        String v1_upuserrecrivstat = BASE_URL + "browser/v1_upuserrecrivstat.jsp";
        /**
         * 星星收取礼物（一键收取）
         */
        String v1_upuserrecrivstatyj = BASE_URL + "browser/v1_upuserrecrivstatyj.jsp";
        /**
         * 星星赞列表
         */
        String v3_getuserpraiselist = BASE_URL + "browser/v3_getuserpraiselist.jsp";
        /**
         * 星星投票列表
         */
        String v3_getuservoteslist = BASE_URL + "browser/v3_getuservoteslist.jsp";
        /**
         * 获取气泡信息
         */
        String v1_getgiftview = BASE_URL + "browser/v1_getgiftview.jsp";
        /**
         * 星星宝贝——》宝贝详情
         */
        String v1_getwdgiftxx = BASE_URL + "browser/v1_getwdgiftxx.jsp";

        /**
         * 星星宝贝——》使用宝贝
         */
        String v1_upapplygiftexposure = BASE_URL + "browser/v1_upapplygiftexposure.jsp";

        /**
         * 活动搜索大家都在搜
         */
        String v4_getshowkey = BASE_URL + "browser/v4_getshowkey.jsp";

        /**
         * 活动搜索结果
         */
        String v4_getshowlistbykey = BASE_URL + "browser/v4_getshowlistbykey.jsp";
        /**
         * 活动报名-》查看进度
         */
        String v4_getusershowschedule = BASE_URL + "browser/v4_getusershowschedule.jsp";
        /**
         * 活动报名-》填写地址
         */
        String v4_insertuserway = BASE_URL + "browser/v4_insertuserway.jsp";


        String v4_getshowvotezhi = BASE_URL + "browser/v4_getshowvotezhi.jsp";


        String v4_getshowvotezhixx = BASE_URL + "browser/v4_getshowvotezhixx.jsp";

        /**
         * 绑定环信ID接口
         */
        String bind_emchat = BASE_URL + "/a/l/im/bind-emchat.json";

        /**
         * 环信导入旧系统的聊天记录接口
         */
        String import_history_msg = BASE_URL + "/a/l/im/import-history-msg.json";
        /**
         * 选择不导入聊天记录的接口
         */
        String give_up_history_msg = BASE_URL + "/a/l/im/giveup-history-msg.json";

        /**
         * 搜索捧丝儿
         */
        String v1_getselectfanslist = BASE_URL + "browser/v1_getselectfanslist.jsp";

        /**
         * 通知列表
         */
        String get_list = BASE_URL + "/a/l/notice/get-list.json";
        /**
         * 任务列表
         */
        String task_list = BASE_URL + "/a/l/task/task-list.json";
        /**
         * 领取任务
         */
        String task_toreceive = BASE_URL + "/a/l/task/task-toreceive.json";
        /**
         * 群主获取可邀请进群的用户列表接口
         */
        String query_users = BASE_URL + "/a/l/im/query-users.json";
        /**
         * 群成员获取可邀请进群的用户列表接口
         */
        String query_user_fans = BASE_URL + "/a/l/im/query-user-fans.json";
        /**
         * 批量获取群成员
         */
        String query_user_simple_batch = BASE_URL + "/a/l/user/query-user-simple-batch.json";


        /**
         * 大气泡信息
         */
        String get_userbigbubbles = BASE_URL + "/a/l/user/get-userbigbubbles.json";
        /**
         * 大气泡信息
         */
        String search_list = BASE_URL + "/a/l/search/list.json";
        /**
         * 新人求捧
         */
        String star_news = BASE_URL + "/a/l/search/star-news.json";
        /**
         * 新晋捧爷
         */
        String unstar_news = BASE_URL + "/a/l/search/unstar-news.json";
        /**
         *发现首页接口
         */
        String discover_home = BASE_URL + "/a/l/sns/discover-home.json";


    }

}
