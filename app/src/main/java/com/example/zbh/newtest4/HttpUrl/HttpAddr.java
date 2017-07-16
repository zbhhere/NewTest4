package com.example.zbh.newtest4.HttpUrl;

/**
 * Created by zbh on 2017/6/26.
 */

public class HttpAddr {
    static String http="http://211.159.189.219/";//我的服务器

    static String httpselftitle=http+"News/News/title.php";//获得新闻的标题
    static String httpProcess=http+"News/News/process.php";//登陆
    static String httpRegister=http+"News/News/register.php";//注册
    static String httpsearhistoryandsave=http+"News/News/searhistoryandsave.php";//历史和收藏的获取
    static String save=http+"News/News/save.php";//收藏
    static String save_delete=http+"News/News/save_delete.php";//取消收藏
    static String content=http+"News/News/content.php";//获取新闻内容
    static String history=http+"News/News/history.php";//获取历史纪录
    static String photoall=http+"News/News/photo.php";//获取图片新闻
    static String photocontent=http+"News/News/photocontent.php";//获取图片新闻的内容
    static String videocontent=http+"News/News/video.php";//获取视频
    static String videoadmin=http+"News/News/videoupdate.php";//视频更新

    public static String getVideoadmin() {
        return videoadmin;
    }

    public static void setVideoadmin(String videoadmin) {
        HttpAddr.videoadmin = videoadmin;
    }

    public static String getVideocontent() {
        return videocontent;
    }

    public static void setVideocontent(String videocontent) {
        HttpAddr.videocontent = videocontent;
    }

    public static String getPhotocontent() {
        return photocontent;
    }

    public static void setPhotocontent(String photocontent) {
        HttpAddr.photocontent = photocontent;
    }

    public static String getPhotoall() {
        return photoall;
    }

    public static void setPhotoall(String photoall) {
        HttpAddr.photoall = photoall;
    }

    public static String getSave() {
        return save;
    }

    public static void setSave(String save) {
        HttpAddr.save = save;
    }

    public static String getSave_delete() {
        return save_delete;
    }

    public static void setSave_delete(String save_delete) {
        HttpAddr.save_delete = save_delete;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        HttpAddr.content = content;
    }

    public static String getHistory() {
        return history;
    }

    public static void setHistory(String history) {
        HttpAddr.history = history;
    }

    public static String getHttpsearhistoryandsave() {
        return httpsearhistoryandsave;
    }

    public static void setHttpsearhistoryandsave(String httpsearhistoryandsave) {
        HttpAddr.httpsearhistoryandsave = httpsearhistoryandsave;
    }

    static String httpDeletehistoryorsave=http+" News/deletehistoryorsave.php";

    public static String getHttpDeletehistoryorsave() {
        return httpDeletehistoryorsave;
    }

    public static void setHttpDeletehistoryorsave(String httpDeletehistoryorsave) {
        HttpAddr.httpDeletehistoryorsave = httpDeletehistoryorsave;
    }

    public static String getHttpRegister() {
        return httpRegister;
    }

    public static void setHttpRegister(String httpRegister) {
        HttpAddr.httpRegister = httpRegister;
    }

    public static String getHttpProcess() {
        return httpProcess;
    }

    public static void setHttpProcess(String httpProcess) {
        HttpAddr.httpProcess = httpProcess;
    }

    public static String getHttpselftitle() {
        return httpselftitle;
    }

    public static void setHttpselftitle(String httpselftitle) {
        HttpAddr.httpselftitle = httpselftitle;
    }

    public static String getHttp() {
        return http;
    }

    public static void setHttp(String http) {
        HttpAddr.http = http;
    }






}
