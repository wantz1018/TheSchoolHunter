package functions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GetALYJson {
    private String AccessKeyID;
    private String AccessKeySecret;
    private String bucketName;
    private String endpoint;
    private String folder;

    public String getAccessKeyID() {
        return AccessKeyID;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getFolder() {
        return folder;
    }

    public GetALYJson(){
        JSONObject jsonObject = ReadJson();
        this.AccessKeyID = jsonObject.getString("AccessKeyID");
        this.AccessKeySecret = jsonObject.getString("AccessKeySecret");
        this.bucketName = jsonObject.getString("bucketName");
        this.endpoint = jsonObject.getString("endpoint");
        this.folder = jsonObject.getString("folder");
    }

    public JSONObject ReadJson(){
        String JSONStr = "";
        try{
            String p = Objects.requireNonNull(this.getClass().getResource("/aly.json")).getPath();
            File jsonFile = new File(p);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch=0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1){
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            JSONStr = sb.toString();
            return JSON.parseObject(JSONStr);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
