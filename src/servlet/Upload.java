package servlet;

import com.aliyun.oss.*;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import functions.GetALYJson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.List;

@WebServlet(name = "upload", value = "/api/upload")
public class Upload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("utf-8");
            if (!fileUpload.isMultipartContent(request)) {
                //如果是普通表单
                return;
            }
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem item : list){
                if (item.isFormField()){
                    //普通输入
                    continue;
                }
                else{
                    //上传输入
                    String filename = item.getName();
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    InputStream in = item.getInputStream();//得到数据
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    String savePath = this.getServletContext().getRealPath("/");
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    while ((len=in.read(buffer)) > 0){
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                    File file = new File(savePath + "\\" + filename);
                    String url = upload(filename, file);
                    file.delete();
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter res;
                    res = response.getWriter();
                    if (!"FileAlreadyExists".equals(url)) {
                        res.write(
                                "{\"code\":1, \"message\":\"success\", \"url\":\"" + url + "\"}"
                        );
                    }
                    else{
                        res.write(
                                "{\"code\":21, \"message\":\"file already exists\", \"url\":\"null\"}"
                        );
                    }
                    res.close();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private String upload(String filename, File file) throws Exception{
        GetALYJson oss = new GetALYJson();
        String accessKeyId = oss.getAccessKeyID();
        String accessKeySecret = oss.getAccessKeySecret();
        String endpoint = oss.getEndpoint();
        String bucketName = oss.getBucketName();
        String folder = oss.getFolder();
        String url = "";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folder + filename, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader("x-oss-forbid-overwrite", "true");//禁止覆盖同名文件
            putObjectRequest.setMetadata(metadata);
            ossClient.putObject(putObjectRequest);
            url = "https://wantz-pic.oss-cn-shenzhen.aliyuncs.com/tsh/" + filename;
//                System.out.println(url);
//                System.out.println("https://wantz-pic.oss-cn-shenzhen.aliyuncs.com/tsh/"+filename);
            return url;

        }catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
            url = oe.getErrorCode();
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
