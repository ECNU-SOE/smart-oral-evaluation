package net.ecnu.iflytek;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.commons.codec.Resources;
import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
/*评测示例默认为中文句子题型的示例,
其他试题示例请到Demo中查看试题示例与音频示例并注意修改相关评测参数值,
到平台文档下方进行音频试题示例下载也可以*/
public class ReadSentence extends WebSocketListener {
    private static final String hostUrl ="https://ise-api.xfyun.cn/v2/open-ise";//开放评测地址
    private static final String appid = "9864a388";//控制台获取
    private static final String apiSecret = "NmJlNDQ3MWE3YmZlMDkwM2RkMWVkYjVj";//控制台获取
    private static final String apiKey = "09aa945a23895f8696051179ed31e471";//控制台获取

    private static final String sub="ise";//服务类型sub,开放评测值为ise
    private static final String ent="cn_vip";//语言标记参数 ent(cn_vip中文,en_vip英文)

    //题型、文本、音频要请注意做同步变更(如果是英文评测,请注意变更ent参数的值)
    private static final String category="read_sentence";//题型
    private String text="今天天气怎么样";//评测试题,英文试题:[content]\nthere was a gentleman live near my house.

    //private static  String file;

    private static final String file = "C:\\Users\\Lenovo\\Desktop\\smart-oral-evaluation-main\\soe-evaluate\\src\\main\\resources\\static\\iseAudio\\sentence\\read_sentence_cn.mp3";//评测音频,如传mp3格式请改变参数aue的值为lame
    public static final int StatusFirstFrame = 0;//第一帧
    public static final int StatusContinueFrame = 1;//中间帧
    public static final int StatusLastFrame = 2;//最后一帧

    final Base64.Encoder encoder = Base64.getEncoder();//编码
    final Base64.Decoder decoder = Base64.getDecoder();//解码

    public static final Gson json = new Gson();
    /*private int aus = 1;
    private static Date dateBegin = new Date();// 开始时间
    private static Date dateEnd = new Date();// 结束时间
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");*/
    private static long beginTime=(new Date()).getTime();
    private static long endTime=(new Date()).getTime();

    public ReadSentence() throws IOException {
    }


    public static void main(String[] args) throws Exception {

    }
    //WebSocket握手连接并上传音频数据

    public void statTest() throws Exception{
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);// 构建鉴权url
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");//将url中的 schema http://和https://分别替换为ws:// 和 wss://
        Request request = new Request.Builder().url(url).build();//url
        WebSocket webSocket = client.newWebSocket(request, new ReadSentence());
    }
    public String getText(){
        return this.text;
    }
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        new Thread(() -> {
            //连接成功，开始发送数据
            int frameSize = 1280; //每一帧音频的大小,建议每 40ms 发送 1280B，大小可调整，但是不要超过19200B，即base64压缩后能超过26000B，否则会报错10163数据过长错误
            int intervel = 40;
            int status = 0;  // 音频的状态
            //FileInputStream fs = new FileInputStream("0.pcm");
            ssb(webSocket);
            //ttp(webSocket);
            beginTime=(new Date()).getTime();

            /*Resource res = new ClassPathResource("static/iseAudio/sentence/read_sentence_cn.pcm");
            file = res.toString();*/
            /*ClassPathResource classPathResource = new ClassPathResource("static/iseAudio/sentence/" + "/read_sentence_cn.pcm");
            try {
                InputStream is = classPathResource.getInputStream();
                file = is.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

            try (FileInputStream fs = new FileInputStream(file)) {
                byte[] buffer = new byte[frameSize];
                // 发送音频
                end:
                while (true) {
                    int len = fs.read(buffer);
                    if (len == -1) {
                        status = StatusLastFrame;  //文件读完，改变status 为 2
                    }

                    switch (status) {
                        case StatusFirstFrame:   // 第一帧音频status = 0
                            send(webSocket,1,1,Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            status=StatusContinueFrame;//中间帧数
                            break;

                        case StatusContinueFrame:  //中间帧status = 1
                            send(webSocket,2,1,Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            break;

                        case StatusLastFrame:    // 最后一帧音频status = 2 ，标志音频发送结束
                            send(webSocket,4,2,"");
                            System.out.println("sendlast");
                            endTime=(new Date()).getTime();
                            System.out.println("总耗时："+(endTime-beginTime)+"ms");
                            break end;
                    }
                    Thread.sleep(intervel); //模拟音频采样延时
                }
                System.out.println("all data is send");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    //上传参数添加与发送
    private void ssb(WebSocket webSocket) {
        ParamBuilder p = new ParamBuilder();
        p.add("common", new ParamBuilder()
                        .add("app_id", appid)
                )
                .add("business", new ParamBuilder()
                        .add("category", category)
                        .add("rstcd", "utf8")

                        //群体：adult成人 、youth（中学，效果与设置pupil参数一致）、pupil小学
                        //仅中文字、词、句题型支持
                        //.add("group", "pupil")

                        //打分门限值：hard、common、easy
                        //仅英文引擎支持
                        //.add("check_type","common")

                        //学段：junior(1,2年级) middle(3,4年级) senior(5,6年级)
                        //仅中文题型：中小学的句子、篇章题型支持
                        //.add("grade","junior")

                        //extra_ability生效条件：ise_unite=1,rst=entirety
                        //.add("ise_unite","1")
                        //.add("rst","entirety")
                        /*1.全维度(准确度分、流畅度分、完整度打分) ,extra_ability值为multi_dimension
                          2.支持因素错误信息显示(声韵、调型是否正确),extra_ability值为syll_phone_err_msg
                          3.单词基频信息显示（基频开始值、结束值）,extra_ability值为pitch ，仅适用于单词和句子题型
                          4.(字词句篇均适用,如选多个能力，用分号;隔开如:syll_phone_err_msg;pitch;multi_dimension)*/
                        //.add("extra_ability","multi_dimension")

                        //试卷部分添加拼音,限制条件：添加拼音的汉字个数不超过整个试卷中汉字个数的三分之一。
                        //jin1|tian1|天气怎么样支持

                        //分制转换，rst=entirety是默认值，请根据文档推荐选择使用百分制


                        .add("sub",sub)
                        .add("ent",ent)
                        .add("tte", "utf-8")
                        .add("cmd", "ssb")
                        .add("auf", "audio/L16;rate=16000")
                        .add("aue", "lame")
                        //评测文本(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF })+text)
                        .add("text",'\uFEFF'+text)//Base64.getEncoder().encodeToString(text.getBytes())
                ).add("data", new ParamBuilder()
                        .add("status", 0)
                        .add("data", ""));
        //System.err.println(p.toString());
        webSocket.send(p.toString());
    }
    //客户端给服务端发送数据
    public void send(WebSocket webSocket, int aus,int status, String data) {
        ParamBuilder p = new ParamBuilder();
        p.add("business", new ParamBuilder()
                .add("cmd", "auw")
                .add("aus", aus)
                .add("aue", "lame")
        ).add("data",new ParamBuilder()
                .add("status",status)
                .add("data",data)
                .add("data_type",1)
                .add("encoding","raw")
        );
        //System.out.println("发送的数据"+p.toString());
        webSocket.send(p.toString());
    }
    //客户端接收服务端消息

    @SuppressWarnings("unchecked")
    public static void iterateNodes(Element node, JSONObject json) {
        //获取当前元素的名称
        String nodeName = node.getName();
        //判断已遍历的JSON中是否已经有了该元素的名称
        if (json.containsKey(nodeName)) {
            //该元素在同级下有多个
            Object Object = json.get(nodeName);
            JSONArray array = null;
            if (Object instanceof JSONArray) {
                array = (JSONArray) Object;
            } else {
                array = new JSONArray();
                array.add(Object);
            }
            //获取该元素下所有子元素
            List<Element> listElement = node.elements();
            if (listElement.isEmpty()) {
                //该元素无子元素，获取元素的值
                String nodeValue = node.getTextTrim();
                array.add(nodeValue);
                json.put(nodeName, array);
                return;
            }
            //有子元素
            JSONObject newJson = new JSONObject();
            //遍历所有子元素
            for (Element e : listElement) {
                //递归
                iterateNodes(e, newJson);
            }
            array.add(newJson);
            json.put(nodeName, array);
            return;
        }
        //该元素同级下第一次遍历
        //获取该元素下所有子元素
        List<Element> listElement = node.elements();
        if (listElement.isEmpty()) {
            //该元素无子元素，获取元素的值
            String nodeValue = node.getTextTrim();
            json.put(nodeName, nodeValue);
            return;
        }
        //有子节点，新建一个JSONObject来存储该节点下子节点的值
        JSONObject object = new JSONObject();
        //遍历所有一级子节点
        for (Element e : listElement) {
            //递归
            iterateNodes(e, object);
        }
        json.put(nodeName, object);
        return;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        //System.out.println(text);
        IseNewResponseData resp = json.fromJson(text, IseNewResponseData.class);
        if (resp != null) {
            if (resp.getCode() != 0) {
                System.out.println( "code=>" + resp.getCode() + " error=>" + resp.getMessage() + " sid=" + resp.getSid());
                System.out.println( "错误码查询链接：https://www.xfyun.cn/document/error-code");
                return;
            }
            if (resp.getData() != null) {
                if (resp.getData().getData() != null) {
                    //中间结果处理
                }
                if (resp.getData().getStatus() == 2) {
                    try {

                        String result_XML = new String(decoder.decode(resp.getData().getData()), "UTF-8");
                        //XmltoJson xmltoJson = new XmltoJson();
                        //JSONObject result_JSON = xmltoJson.xmltoJson(result_XML);


                        InputStream in = new ByteArrayInputStream(result_XML.getBytes("UTF-8"));
                        SAXReader reader = new SAXReader();
                        Document read = reader.read(in);
                        //读取文件返回文档类

                        //getRootElement();获得根标签
                        Element root = read.getRootElement();

                        Element read_sentence = root.element("read_sentence").element("rec_paper").element("read_sentence");
                        Attribute fluency_score = read_sentence.attribute("fluency_score");
                        System.out.println(fluency_score.getValue());
                        System.out.println(read_sentence.asXML());





                        //System.out.println("sid:"+resp.getSid()+" 最终识别结果"+result_XML);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    webSocket.close(1000, "");
                } else {
                    // todo 根据返回的数据处理
                }
            }
        }
        //System.out.println("response==>"+text);
    }
    //鉴权
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);//开放评测地址
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        //String date = format.format(new Date());
        //System.err.println(date);
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("GET ").append(url.getPath()).append(" HTTP/1.1");//signature_orgin
        //System.err.println(builder);
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));//signature_sha=hmac-sha256(signature_origin,$apiSecret)
        String sha = Base64.getEncoder().encodeToString(hexDigits);//这里的sha其实就是最终signature
        //System.err.println(sha);
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        //System.err.println(authorization);
        HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();
        return httpUrl.toString();
    }
    //JSON解析
    private static class IseNewResponseData{
        private int code;
        private String message;
        private String sid;
        private Data data;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getSid() {
            return sid;
        }
        public void setSid(String sid) {
            this.sid = sid;
        }
        public Data getData() {
            return data;
        }
        public void setData(Data data) {
            this.data = data;
        }
    }
    private static class Data{
        private int status;
        private String data;
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public String getData() {
            return data;
        }
        public void setData(String data) {
            this.data = data;
        }
    }
    //传参构建
    public static class ParamBuilder {
        private JsonObject jsonObject = new JsonObject();
        public ParamBuilder add(String key, String val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }
        public ParamBuilder add(String key, int val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }
        public ParamBuilder add(String key, boolean val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }
        public ParamBuilder add(String key, float val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }
        public ParamBuilder add(String key, JsonObject val) {
            this.jsonObject.add(key, val);
            return this;
        }
        public ParamBuilder add(String key, ParamBuilder val) {
            this.jsonObject.add(key, val.jsonObject);
            return this;
        }
        @Override
        public String toString() {
            return this.jsonObject.toString();
        }
    }
}


