package com.ecgobike.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.ecgobike.common.constant.Constants;
import com.google.common.base.Strings;

public class Utils {
    private static char[] numbers = ("0123456789").toCharArray();
    private static char[] lowerLetters = ("abcdefghijklmnopqrstuvwxyz").toCharArray();
    private static char[] upperLetters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    @SafeVarargs
    public static <T> boolean notNull(T... array) {
        return array != null && array.length > 0;
    }

    /**
     * 随机指定精度的浮点数
     *
     * @param accuracy
     * @return
     */
    public static float randomFloat(int accuracy, int range) {
        int data = 9 - Constants.random.nextInt(19);
        data = data == 0 ? 1 : data;
        return Float.parseFloat(String.format("%." + accuracy + "f", data * Math.pow(0.1, accuracy) * range));
    }

    /**
     * 随机指定精度的浮点数
     *
     * @param accuracy
     * @return
     */
    public static double randomDouble(int accuracy, int range) {
        int data = 9 - Constants.random.nextInt(19);
        data = data == 0 ? 1 : data;
        return Double.parseDouble(String.format("%." + accuracy + "f", data * Math.pow(0.1, accuracy) * range));
    }

    /**
     * 获取所有父类
     *
     * @param clazz
     * @param parents
     */
    public static void getAllParents(Class<?> clazz, LinkedList<Class<?>> parents) {
        if (clazz == null) {
            return;
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {//Object 为所有类父类

            parents.addLast(superClass);
            getAllParents(superClass, parents);
        }else {
            return;
        }
    }

    /**
     * 获取所有属性
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        LinkedList<Class<?>> parents = new LinkedList<Class<?>>();
        getAllParents(clazz, parents);

        Field[] myFields = clazz.getDeclaredFields();
        List<Field> fis = new ArrayList<Field>();
        for (Class<?> cl : parents) {
            if (cl != null) {
                Field[] pFields = cl.getDeclaredFields();
                for (Field field : pFields) {
                    fis.add(field);
                }
            }
        }

        Field[] fields = combineArray(Field.class, myFields, fis.toArray(new Field[fis.size()]));
        return fields;
    }

    /**
     * 生成32位uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 从系统变量中获取数据
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getValueFromEvn(String name, String defaultValue) {
        return Strings.isNullOrEmpty(System.getenv(name)) ? defaultValue : System.getenv(name);
    }

    /**
     * 加入元素到链表
     *
     * @param ts
     * @return
     */
    @SafeVarargs
    public static <T> List<T> push2List(T... ts) {
        List<T> list = new ArrayList<T>();
        if (ts != null && ts.length > 0) {
            for (T t : ts) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 反射执行标准set方法
     *
     * @param instance 需要修改的对象
     * @param data 需要修改的值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void reflectSet(Object instance, Map<String, Object> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] ms = instance.getClass().getMethods();
        HashMap<String, Method> setMethodsMap = new HashMap<String, Method>();
        HashMap<String, Class<?>[]> setParamsMap = new HashMap<String, Class<?>[]>();
        for (Method m : ms) {
            if (m.getName().startsWith("set")) {
                setMethodsMap.put(m.getName(), m);
                setParamsMap.put(m.getName(), m.getParameterTypes());
            }
        }

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String setKey = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method method = setMethodsMap.get(setKey);
            if (method != null) {
                method.invoke(instance, value);
            }
        }
    }


    /**
     * 反射执行标准get方法
     *
     * @param instance
     * @param key
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object reflectGet(Object instance, String key) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return reflectMethod(instance, key, null, "get");
    }

    /**
     * 反射执行标准通用方法
     *
     * @param instance 需要修改的对象
     * @param data 需要修改的值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object reflectMethod(Object instance, String key, Object value, String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] ms = instance.getClass().getMethods();
        String setKey = name + key.substring(0, 1).toUpperCase() + key.substring(1);
        for (Method m : ms) {
            if (setKey.equals(m.getName())) {
                if (value == null) {
                    return m.invoke(instance);
                }else {
                    return m.invoke(instance, value);
                }
            }
        }

        return null;
    }

    public static void printBytes(byte[] data) {
        System.out.print("[");
        String dString = "";
        for (byte b : data) {
            dString += ", " + b;
        }
        dString = dString.substring(2);
        System.out.print(dString);
        System.out.print("]");
        System.out.println();
    }

    /**
     * object to byte[]
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * byte[] to object
     *
     * @param data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    /**
     * 字符串是否为空或空串
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 指定值是否在两个数中间
     *
     * @param value
     * @param floor
     * @param ceiling
     * @param attach 是否包含
     * @return
     */
    public static boolean isClamped(long value, long floor, long ceiling, boolean attach) {
        if(attach) {
            if (value >= floor && value <= ceiling) {
                return true;
            }
            return false;
        }else {
            if (value > floor && value < ceiling) {
                return true;
            }
            return false;
        }
    }

    /**
     * 计算可序列化对象长度
     *
     * @param o
     * @return
     */
    public static int calcSize(java.io.Serializable o) {
        int ret = 0;

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(buf);
            os.writeObject(o);
            ret = buf.size();
        } catch (IOException e) {
            // No need handle this exception
            e.printStackTrace();
            ret = -1;
        } finally {
            try {
                os.close();
                buf.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 哈希散列规则
     *
     * @param sum
     * @param id
     * @return
     */
    public static int hashRule(int sum, String id) {
        int index = (int)(Math.abs(id.hashCode()) % sum);
        return index;
    }

    /**
     * 获取哈希散值
     *
     * @param sum
     * @param id
     * @return
     */
    public static int hashCode(String id) {
        return Math.abs(id.hashCode());
    }

    /**
     * 生成int型随机数
     *
     * @return
     */
    public static int generateIntId() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        String tempId = currentTime.substring(5, currentTime.length());
        if (tempId.startsWith("0")) {
            tempId = tempId.replaceFirst("0", String.valueOf(Constants.random.nextInt(9) + 1));
        }
        Integer id = Integer.parseInt(tempId);
        return id;
    }
    /**
     * 主键ID生成 时间+随机（1000~9999）
     */
    public static long generateLongId() {
        int ext = Constants.random.nextInt(9000) + 1000;
        String id = System.currentTimeMillis() + String.valueOf(ext);
        return Long.parseLong(id);
    }



    /**
     * 主键ID
     * 时间(后7位,如果首位是0被1-9之间随机数取代)+key(被指定建议不超过4位)+随机(4位1000~9999)
     */
    public static long generateLongId(int key) {
        int ext = Constants.random.nextInt(9000) + 1000;
        String currentTime = String.valueOf(System.currentTimeMillis());
        String tempId = currentTime.substring(6, currentTime.length());
        if (tempId.startsWith("0")) {
            tempId = tempId.replaceFirst("0", String.valueOf(Constants.random.nextInt(9) + 1));
        }
        String id = tempId + key + ext;
        return Long.parseLong(id);
    }

    /**
     * 文件夹下所有文件的MD5值
     *
     * @param file
     * @param listChild
     * @return
     */
    public static Map<String, String> getMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        // <filepath,md5>
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getMD5(f, listChild));
            } else {
                md5 = getMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

    /**
     * 文件MD5值
     *
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 字符串MD5值
     *
     * @param s
     * @return
     */
    public final static String getMD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串MD5值
     *
     * @param strTemp
     * @return
     */
    public final static String getMD5(byte[] strTemp) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 加载指定的porperties
     *
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
        }catch(Exception e){
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
        }
        return properties;
    }





    /**
     * 通用翻页函数
     **/
    public static <T> List<T> page(List<T> objs, int pageId, int pageSize) {
        if (objs == null) {
            objs = new LinkedList<T>();
        }
        int start = pageId * pageSize;
        int end = start + pageSize;
        if (start < 0)
            start = 0;
        if (start > objs.size())
            start = objs.size();
        if (end < start)
            end = start;
        if (end > objs.size())
            end = objs.size();

        List<T> list = new LinkedList<T>();
        list.addAll(objs.subList(start, end));
        return list;
    }

    /**
     * 产生随机字符串
     *
     * @param length
     * @param mode 二进制表示
     * @return
     */
    public static final String randomString(int length) {
        return randomString(length, (byte)0B111);
    }

    /**
     * 产生随机字符串
     *
     * @param length
     * @param mode 二进制表示
     * @return
     */
    public static final String randomString(int length, byte mode) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        char[] resources = new char[0];
        if ((mode & 1) == 1) {//包含数字
            resources = combineArray(resources, numbers);
        }

        if ((mode >> 1 & 1) == 1) {// 包含小写字母
            resources = combineArray(resources, lowerLetters);
        }

        if ((mode >> 2 & 1) == 1) {// 包含大写字母
            resources = combineArray(resources, upperLetters);
        }

        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = resources[Constants.random.nextInt(resources.length)];
        }
        return new String(randBuffer);
    }


    /**
     * 截取字符串
     *
     * @param src 源字符串
     * @param regex 指定字符标识
     * @param beginIndex 指定字符标识的起始位置
     * @return 截取后的字符串
     */
    public static String subString(String src, String regex, int beginIndex){
        return subString(src, regex, beginIndex, regex);
    }

    /**
     * 截取字符串
     *
     * @param src 源字符串
     * @param regex 指定字符标识
     * @param beginIndex 指定字符标识的起始位置
     * @param newRegex 替换原来的指定字符
     * @return 截取后的字符串
     */
    public static String subString(String src, String regex, int beginIndex, String newRegex) {
        if (src == null || src.length() == 0 || src.indexOf(regex) == -1 || src.split(regex).length - 1 < beginIndex) {
            return "";
        }
        String[] cell = src.split(regex);
        String value = "";
        for (int i = beginIndex >= 0 ? beginIndex : 0; i < cell.length; i++) {
            value += newRegex + cell[i];
        }
        if (!"".endsWith(newRegex)) {
            value = value.substring(1);
        }
        return value;
    }

    /**
     * 计算总的页数
     **/
    public static int totalPage(int total, int pageSize){
        int n = total/pageSize;
        if(total%pageSize >0 ){
            n += 1;
        }
        return n;
    }

    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
    public static byte[] unZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 解压zlib
     *
     * @param data
     * @return
     */
	/*public static byte[] unZlib(byte[] data) {
		byte[] b = null;
		if (data != null) {
			Inflater inflater = new Inflater();
			int numberOfBytesToDecompress = data.length;
			inflater.setInput(data, 0, numberOfBytesToDecompress);
			int compressionFactorMaxLikely = 3;
			int bufferSizeInBytes = numberOfBytesToDecompress * compressionFactorMaxLikely;
			byte[] bytesDecompressed = new byte[bufferSizeInBytes];
			try {
				int numberOfBytesAfterDecompression = inflater.inflate(bytesDecompressed);
				b = new byte[numberOfBytesAfterDecompression];
				System.arraycopy(bytesDecompressed, 0, b, 0,	numberOfBytesAfterDecompression);
			} catch (DataFormatException dfe) {
				dfe.printStackTrace();
			}
			inflater.end();
		}
		return b;
	}*/

    /***
     * 压缩Zip
     *
     * @param data
     * @return
     */
    public static byte[] zip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;

    }

    /**
     * 压缩zlib
     *
     * @param encdata
     * @return
     */
	/*public static byte[] zlib(byte[] data) {
		byte[] d = null;
		if (data != null) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			byte[] bytesCompressed = new byte[Short.MAX_VALUE];
			int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);
			d = new byte[numberOfBytesAfterCompression];
			System.arraycopy(bytesCompressed, 0, d, 0, numberOfBytesAfterCompression);
		}
		return d;
	}*/

    private static int cachesize = 4096;
    private static Inflater decompresser = new Inflater();
    private static Deflater compresser = new Deflater();
    /**
     * Zlib 压缩
     *
     * @param data
     * @return
     */
    public static byte[] zlib(byte[] data) {
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        byte output[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[cachesize];
            int got;
            while (!compresser.finished()) {
                got = compresser.deflate(buf);
                o.write(buf, 0, got);
            }
            output = o.toByteArray();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * Zlib 解压缩
     * @param data
     * @return
     */
    public static byte[] unZlib(byte[] data) {
        byte output[] = new byte[0];
        decompresser.reset();
        decompresser.setInput(data);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[cachesize];
            int got;
            while (!decompresser.finished()) {
                got = decompresser.inflate(buf);
                o.write(buf, 0, got);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }


    public static byte[] combineArray(byte[]... array) {
        int totalLength = 0;
        for (int i = 0; i < array.length; i++) {
            totalLength += array[i].length;
        }

        byte[] total = new byte[totalLength];
        for (int i = 0; i < array.length; i++) {
            if(i == 0) {
                System.arraycopy(array[i], 0, total, 0, array[i].length);
            }else {
                System.arraycopy(array[i], 0, total, array[i - 1].length, array[i].length);
            }
        }
        return total;
    }

    public static char[] combineArray(char[]... array) {
        int totalLength = 0;
        for (int i = 0; i < array.length; i++) {
            totalLength += array[i].length;
        }

        char[] total = new char[totalLength];
        for (int i = 0; i < array.length; i++) {
            if(i == 0) {
                System.arraycopy(array[i], 0, total, 0, array[i].length);
            }else {
                System.arraycopy(array[i], 0, total, array[i - 1].length, array[i].length);
            }
        }
        return total;
    }

    @SafeVarargs
    public static <T> T[] combineArray(Class<T> clazz, T[]... array) {
        int totalLength = 0;
        for (int i = 0; i < array.length; i++) {
            totalLength += array[i].length;
        }

        @SuppressWarnings("unchecked")
        T[] total = (T[]) Array.newInstance(clazz, totalLength);
        for (int i = 0; i < array.length; i++) {
            if(i == 0) {
                System.arraycopy(array[i], 0, total, 0, array[i].length);
            }else {
                System.arraycopy(array[i], 0, total, array[i - 1].length, array[i].length);
            }
        }
        return total;
    }

	/*public static void main(String[] args) {
		byte[] data = new byte[230];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) new Random().nextInt(127);
		}
		byte a = 0;
		byte b = 0;
		if(data.length > 100) {
			a = (byte)(data.length / 100);
		}
		b = (byte)(data.length % 100);
		byte[] head = {0, 0, a, b, 0, 0};
		byte[] last = GameUtils.combineArray(head, data);
		System.out.println("");
	}*/
}
