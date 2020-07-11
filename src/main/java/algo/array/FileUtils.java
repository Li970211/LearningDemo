package main.java.algo.array;//package main.java.array;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.nio.channels.FileChannel;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * @author LiJia
// * @create 2019-08-22 12:21
// */
//public class FileUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
//    /**
//     * 复制文件
//     * @param source  源路径
//     * @param dest   目标路径
//     * @throws IOException
//     */
//    public static void copyFile(File source, File dest) throws IOException {
//        FileChannel inputChannel = null;
//        FileChannel outputChannel = null;
//        try {
//            inputChannel = new FileInputStream(source).getChannel();
//            outputChannel = new FileOutputStream(dest).getChannel();
//            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
//        } finally {
//            if(inputChannel != null){
//                inputChannel.close();
//            }
//            if (outputChannel != null){
//                outputChannel.close();
//            }
//        }
//    }
//
//    /**
//     * 删除单个文件
//     * @param fileName
//     * @return
//     */
//    public static boolean deleteFile(String fileName) {
//        File file = new File(fileName);
//        if (file.exists() && file.isFile()) {
//            if (file.delete()) {
//                logger.info("Successful delete [{}] files " ,fileName);
//                return true;
//            } else {
//                logger.info("Failed delete [{}] files " ,fileName);
//                return false;
//            }
//        } else {
//            logger.info("Failed delete [{}] files " ,fileName);
//            return false;
//        }
//    }
//
//    /**
//     *  剪切文件
//     * @param sourceName 原文件路径
//     * @param dest  目标文件
//     */
//    public static void cutFile(String sourceName, File dest) throws IOException{
//        File source = new File(sourceName);
//        FileUtils.copyFile(source,dest);
//        FileUtils.deleteFile(sourceName);
//    }
//
//    /**
//     * 压缩文件
//     *
//     * @param sourceFilePath 源文件路径
//     * @param zipFilePath    压缩后文件存储路径
//     * @param zipFilename    压缩文件名
//     */
//    public static void compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) {
//        ZipOutputStream zos = null;
//        File sourceFile = new File(sourceFilePath);
//        File zipPath = new File(zipFilePath);
//        if (!zipPath.exists()) {
//            zipPath.mkdirs();
//        }
//        File zipFile = new File(zipPath + File.separator + zipFilename);
//        try  {
//            zos = new ZipOutputStream(new FileOutputStream(zipFile));
//            writeZip(sourceFile, "", zos);
//            //文件压缩完成后，删除被压缩文件
//            boolean flag = deleteDir(sourceFile);
//            logger.info("删除被压缩文件[{}]标志：{}", sourceFile,flag);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e.getMessage(), e.getCause());
//        }finally {
//            if(zos != null){
//                try {
//                    zos.close();
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }
//    }
//
//    /**
//     * 遍历所有文件，压缩
//     *
//     * @param file       源文件目录
//     * @param parentPath 压缩文件目录
//     * @param zos        文件流
//     */
//    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
//        BufferedInputStream bis = null;
//        if (file.isDirectory()) {
//            //目录
//            File[] files = file.listFiles();
//            if(files.length == 0){
//                //空目录
//                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName() + "/");
//                try {
//                    zos.putNextEntry(zipEntry);
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }finally {
//                    if(zos != null){
//                        try {
//                            zos.closeEntry();
//                        } catch (IOException e) {
//                            logger.error(e.getMessage(), e);
//                        }
//                    }
//                }
//            }else{
//                //包含文件的目录
//                parentPath += file.getName() + File.separator;
//                for (File f : files) {
//                    writeZip(f, parentPath, zos);
//                }
//            }
//
//        } else {
//            //文件
//            try {
//                bis = new BufferedInputStream(new FileInputStream(file));
//                //指定zip文件夹
//                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
//                zos.putNextEntry(zipEntry);
//                int len;
//                byte[] buffer = new byte[1024 * 10];
//                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
//                    zos.write(buffer, 0, len);
//                    zos.flush();
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                throw new RuntimeException(e.getMessage(), e.getCause());
//            }finally {
//                if(zos != null){
//                    try {
//                        zos.closeEntry();
//                    } catch (IOException e) {
//                        logger.error(e.getMessage(), e);
//                    }
//                }
//                if (bis != null){
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        logger.error(e.getMessage(), e);
//                    }
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 删除文件夹
//     *
//     * @param file
//     * @return
//     */
//    public static boolean deleteDir(File file){
//        if (file.isDirectory()) {
//            String[] children = file.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(file, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        //删除空文件夹
//        return file.delete();
//    }
//
//}
