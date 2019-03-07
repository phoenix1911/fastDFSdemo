package TestFastDfs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Tjl on 2019/3/7 15:17.
 */




public class TestFastDfs {
    //这将获取 到classes目录的全路径
    private String conf_filename = this.getClass().getResource("/").getPath()+"fdfs_client.conf";;

    private String local_filename = this.getClass().getResource("/").getPath()+"p.jpeg";

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpload() {

        try {
//这将获取 到classes目录的全路径
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            //一种POST请求提交方式
//          NameValuePair nvp = new NameValuePair("age", "18");
            NameValuePair nvp [] = new NameValuePair[]{
                    new NameValuePair("age", "18"),
                    new NameValuePair("sex", "male")
            };
//          String[] fileIds = storageClient.upload_file(local_filename, "jpeg", nvp);

            //经测试 NameValuePair对象为空时 上传文件就不会上传.jpeg-m文件
            String[] fileIds = storageClient.upload_file(local_filename, "jpeg",null);

            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从服务器下载
     */
    @Test
    public void testDownload() {
        try {

            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] b = storageClient.download_file("group1", "M00/00/00/wKgPhVyAzhaAQQwAAACi3GCRcGk69.jpeg");
            System.out.println(b.toString());
            IOUtils.write(b, new FileOutputStream("D:/小骑士.jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件信息
     */
    @Test
    public void testGetFileInfo(){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            FileInfo fi = storageClient.get_file_info("group1", "M00/00/00/wKgPhVyAzhaAQQwAAACi3GCRcGk69.jpeg");
            System.out.println("服务器地址"+fi.getSourceIpAddr());
            System.out.println("文件大小"+fi.getFileSize());
            System.out.println("文件创建时间"+fi.getCreateTimestamp());
            System.out.println(fi.getCrc32());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFileMate(){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);
            NameValuePair nvps [] = storageClient.get_metadata("group1", "M00/00/00/wKgPhVyAzhaAQQwAAACi3GCRcGk69.jpeg");
            for(NameValuePair nvp : nvps){
                System.out.println(nvp.getName() + ":" + nvp.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);
            int i = storageClient.delete_file("group1", "M00/00/00/wKgPhVyAzhaAQQwAAACi3GCRcGk69.jpeg");
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
