package com.github.aracwong.commons;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Slf4j
public class FileUtil {


    /**
     * 读取文件所有内容<br />
     *
     * @param fileName 文件名
     * @return String 文件内容
     */
    public static String readAllText(String fileName) {
        if (!exists(fileName, false)) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
        } catch (Exception ex) {
            return "";
        }
        return str.toString();
    }

    public static byte[] read(String fileName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] temp = new byte[1024];
            int size;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
        } catch (Exception ex) {
            return new byte[0];
        }
        return out.toByteArray();
    }

    /**
     * 写文件<br />
     *
     * @param fileName 文件名
     * @return String 文件内容
     */
    public static boolean writeAllText(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 写文件<br />
     *
     * @param fileName 文件名
     * @return byteList 文件内容
     */
    public static boolean writeAllByteList(String fileName, byte[] byteList) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName))) {
            writer.write(byteList);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 写文件追加<br />
     *
     * @param fileName 文件名
     * @return byteList 文件内容
     */
    public static boolean writeAppendAllByteList(String fileName, byte[] byteList) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName, true))) {
            writer.write(byteList);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 验证文件是否存在<br />
     *
     * @param fileName    文件名
     * @param isDirectory 是否是目录，默认值：false
     * @return Boolean
     */
    public static Boolean exists(String fileName, Boolean isDirectory) {
        if (isDirectory == null) {
            isDirectory = false;
        }
        if (isDirectory) {
            File file = new File(fileName);
            return file.exists() && file.isDirectory();
        }
        return new File(fileName).exists();
    }

    /**
     * 文件或目录存在删除<br />
     *
     * @param fileName    文件名
     * @param isDirectory 是否是目录，默认值：false
     * @return Boolean
     */
    public static Boolean existsDelete(String fileName, Boolean isDirectory) {
        if (isDirectory == null) {
            isDirectory = false;
        }
        File file = new File(fileName);
        if (isDirectory && file.exists() && file.isDirectory()) {
            file.delete();
        }
        if (file.exists()) {
            file.delete();
        }
        file = null;
        return true;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean existsDeleteTest(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = existsDeleteTest(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 目录不存在则创建<br />
     *
     * @param fileName 文件名
     * @return Boolean
     */
    public static Boolean dirNotExistsCreate(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        return true;
    }

    /**
     * 获得文件后缀名，包含点<br />
     *
     * @param fileName 文件名
     * @return String 文件后缀名
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.length() < 1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获得目录下所有文件<br />
     *
     * @param directoryPath          目录地址
     * @param isIncludeSubdirectorie 是否包含子目录
     */
    public static List<String> getFileList(String directoryPath, boolean isIncludeSubdirectorie) {
        List<String> list = new ArrayList<String>();
        getFileList(directoryPath, isIncludeSubdirectorie, list);
        return list;
    }

    /**
     * 获得目录下所有文件<br />
     *
     * @param directoryPath          目录地址
     * @param isIncludeSubdirectorie 是否包含子目录
     * @param fileList               文件列表，用于返回
     */
    private static void getFileList(String directoryPath, boolean isIncludeSubdirectorie, List<String> fileList) {
        File root = new File(directoryPath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory() && isIncludeSubdirectorie) {
                //递归调用
                getFileList(file.getAbsolutePath(), isIncludeSubdirectorie, fileList);
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath().toLowerCase());
                }
            } else {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath().toLowerCase());
                }
            }
        }
    }

    /**
     * 未找到目录则创建<br />
     *
     * @param fileName 文件名
     */
    public static void notExistsDirectoryCreate(String fileName) {
        if (!exists(fileName, true)) {
            new File(fileName).mkdirs();
        }
    }
}