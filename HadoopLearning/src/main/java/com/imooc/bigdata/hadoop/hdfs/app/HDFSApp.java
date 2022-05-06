package com.imooc.bigdata.hadoop.hdfs.app;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;


/**
 * Hello world!
 *
 */
public class HDFSApp
{
    public static final String HDFS_PATH = "hdfs://hadoop000:8020";
    Configuration configuration = null;
    FileSystem fileSystem = null;



    @Before
    public void setUp() throws Exception {
        /**
         * 构造一个访问指定 HDFS 系统的客户端对象
         * 第一个参数 ： HDFS 的 URI
         * 第二个参数 ： 客户端指定的配置参数
         * 第三个参数 ： 访问 HDFS 系统的身份，即 用户名
         */
        System.out.println("-------setUp------");
        configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "lijh");
    }

    @Test
    public void mkdir() throws Exception {
        /**
         * 在 HDFS 中创建文件夹的 API
         */
        boolean result = fileSystem.mkdirs(new Path("/hdfsapi/hello111/"));
        System.out.println(result);
    }

    @Test
    public void text() throws Exception {
        /**
         * 查看 HDFS 的文件内容的 API
         */
//        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/test/core-site.xml"));
        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/output/hellokk.txt"));
        IOUtils.copyBytes(in, System.out, 1024);


    }

    @Test
    public void create() throws Exception {
        /**
         * 创建文件并添加到 HDFS 中
         */

        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/b.txt"));
        out.writeUTF("hello world. \n");
        out.flush();
        out.close();
    }

    @Test
    public void rename() throws Exception {
        /**
         * 测试修改文件名
         */
        Path oldPath = new Path("/hdfsapi/test/b.txt");
        Path newPath = new Path("/hdfsapi/test/bb.txt");

        boolean result = fileSystem.rename(oldPath, newPath);
        System.out.println("Rename result : " + result);

    }



    @Test
    public void copyFromLocalFile() throws Exception {
        /**
         * 拷贝本地文件到 HDFS 文件系统
         * @throws Exception
         */

        Path src = new Path("C:\\Users\\lijh\\Desktop\\hellokk.txt");
        Path dst = new Path("/hdfsapi/test/");

        fileSystem.copyFromLocalFile(src, dst);
    }

    @Test
    public void copyFromLocalBigFile() throws Exception {
        /**
         * 拷贝本地文件到 HDFS 文件系统 -> 带进度条的
         * @throws Exception
         */

        Path src = new Path("C:\\Users\\lijh\\Desktop\\hellokk.txt");
        Path dst = new Path("/hdfsapi/test/");

        fileSystem.copyFromLocalFile(src, dst);
    }



    @Test
    public void copyToLocalFile() throws Exception {
        /**
         * 拷贝 HDFS 文件系统的文件到本地
         * @throws Exception
         */

        Path dst = new Path("C:\\Users\\lijh\\Desktop");
        Path src = new Path("/hdfsapi/b.txt");

        // 空指针错误
//        fileSystem.copyToLocalFile(src, dst);
        fileSystem.copyToLocalFile(false, src, dst, true);
    }


    @Test
    public void listFiles() throws Exception{
        /**
         * 列出 HDFS 文件系统中的文件信息
         */
        Path path = new Path("/");
        // fileStatuses 中的每个元素都是一个文件的 描述信息
        FileStatus[] fileStatuses = fileSystem.listStatus(path);

        for(FileStatus file : fileStatuses){
            String isDir = file.isDirectory() ? "文件夹" : "文件";
            String permission = file.getPermission().toString();
            short replication = file.getReplication();
            long length = file.getLen();

            System.out.println(isDir + "\t" + permission + "\t"
                + replication + "\t" + length
            );
        }

    }

    @Test
    public void listFilesRecursive() throws Exception{
        /**
         * 列出 HDFS 文件系统中的文件信息
         */
        Path path = new Path("/hdfsapi/test");
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(path, true);
        while(files.hasNext()){
            LocatedFileStatus file = files.next();

            String isDir = file.isDirectory() ? "文件夹" : "文件";
            String permission = file.getPermission().toString();
            short replication = file.getReplication();
            long length = file.getLen();
            Path name = file.getPath();

            System.out.println(isDir + "\t" + permission + "\t"
                    + replication + "\t" + length + "\t" + name
            );

        }
    }

    @Test
    public void getFileBlockLocations() throws Exception {
        Path path= new Path("/jdk-8u91-linux-x64.tar.gz");
        FileStatus fileStatus = fileSystem.getFileStatus(path);
        BlockLocation[] blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());

        for(BlockLocation block : blocks){

            for(String name : block.getNames()){
                System.out.println(name + "\t" + block.getOffset() + "\t" + block.getLength() + "\t" + block.getHosts());
            }
        }
    }

    @Test
    public void delete() throws Exception {
        /**
         * 删除 HDFS 系统中的文件
         */
        Path path= new Path("/hdfsapi/test/hello.txt");
        boolean result = fileSystem.delete(path, true);
        System.out.println("result : " + result);
    }

    @After
    public void tearDown(){
        configuration = null;
        fileSystem = null;
        System.out.println("------tearDown------");
    }

    public static void main(String[] args) throws Exception
    {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.180.130:8020"),configuration, "lijh");

        Path path = new Path("/hdfsapi/test");

        boolean result = fileSystem.mkdirs(path);

        System.out.println("result : " + result);

    }
}
