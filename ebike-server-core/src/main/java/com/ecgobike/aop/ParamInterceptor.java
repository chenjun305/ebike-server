package com.ecgobike.aop;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecgobike.common.annotation.Range;
import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.StringLength;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.log.LogHelp;
import com.ecgobike.common.util.Utils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

/**
 * Created by ChenJun on 2018/3/10.
 */

public class ParamInterceptor extends BaseInterceptor {

    @SuppressWarnings("rawtypes")
    List<Class> classesSpe = Arrays.asList(new Class[]{HttpServletRequest.class, HttpServletResponse.class});


    @SuppressWarnings("rawtypes")
    List<Class> numbers = Arrays.asList(new Class[]{Integer.class, Byte.class, Double.class, Short.class, Float.class, Long.class});
    List<String> numbersName = Arrays.asList(new String[]{"int", "byte", "double", "short", "long", "float"});

    List<String> classesPriName = Arrays.asList(new String[]{"int", "byte", "double", "short", "long", "float", "char", "boolean", "String"});

    @SuppressWarnings("rawtypes")
    List<Class> classesPri = Arrays.asList(new Class[]{String.class, Integer.class, Byte.class, Double.class, Short.class, Float.class, Long.class, Character.class, Boolean.class});

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = ((HandlerMethod) handler);
            MethodParameter[] params = method.getMethodParameters();
            if (params != null) {
                for (MethodParameter param : params) {
                    Class<?> type = param.getParameterType();
                    if (!(classesSpe.contains(type) || classesPriName.contains(type.getSimpleName()) || classesPri.contains(type))) {//?????????????????????????????????
                        boolean check = checkParams(request, response, type);
                        if (!check) {
                            return check;
                        }
                    }else if (classesPriName.contains(type.getSimpleName()) || classesPri.contains(type)) {//????????????
                        return checkFields(request, response, type, param);
                    }
                }
            }
        }
        return true;
    }


    private static Map<Class<?>, List<String>> cacheNotNullReflect = new HashMap<Class<?>, List<String>>();
    private static Map<Class<?>, List<String>> cacheNumberRangeReflect = new HashMap<Class<?>, List<String>>();
    private static Map<Class<?>, List<String>> cacheStringLengthReflect = new HashMap<Class<?>, List<String>>();
    private static List<Class<?>> cacheNothingReflect = new ArrayList<Class<?>>();//??????????????????????????????


    private <T> boolean checkFields(HttpServletRequest request, HttpServletResponse response, Class<T> type, MethodParameter param) throws IOException {
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(param.getMethod());
        String fieldName = parameterNames[param.getParameterIndex()];
        String data = request.getParameter(fieldName);
        NotNull notNull = param.getParameterAnnotation(NotNull.class);
        if (notNull != null) {// ?????????????????????
            if (Strings.isNullOrEmpty(data)) {
                sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] is null!");
                return false;
            }
        }

        StringLength stringLength = param.getParameterAnnotation(StringLength.class);
        if (stringLength != null) {// ???????????????????????????????????????string????????????
            if (type == String.class) {//?????????String??????
                if (stringLength.canNull()) {//????????????
                    if (!Strings.isNullOrEmpty(data)) {//??????????????????????????????
                        if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                            sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] length length range (" + stringLength.Min() + "," + stringLength.Max() + ")!");
                            return false;
                        }
                    }else {//?????????????????????????????????min<=0
                        if (stringLength.Min() > 0) {
                            sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] need length (" + stringLength.Min() + ") at least!");
                            return false;
                        }
                    }
                }else {//????????????
                    if (Strings.isNullOrEmpty(data)) {
                        sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] is null!");
                        return false;
                    }else {
                        if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                            sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] length length range (" + stringLength.Min() + "," + stringLength.Max() + ")!");
                            return false;
                        }
                    }
                }
            }
        }

        Range range = param.getParameterAnnotation(Range.class);
        if (range != null) {
            if (data == null) {
                sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + "] is null!");
                return false;
            }else {
                boolean result = chargeNumber(data, range);
                if (!result) {
                    sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + fieldName + ":" + data + "] is illegal!");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ????????????????????????
     *
     */
    public <T> boolean checkParams(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
        try {
            if (!cacheNothingReflect.contains(clazz) && !cacheNotNullReflect.containsKey(clazz) && !cacheNumberRangeReflect.containsKey(clazz) && !cacheStringLengthReflect.containsKey(clazz)) {//???????????????????????????
                Field[] fields = getAllFields(clazz);
                if (fields.length > 0) {
                    List<String> notNullFieldNames = new ArrayList<String>();
                    List<String> notNullIllegalFieldNames = new ArrayList<String>();
                    List<String> numberRangeFieldNames = new ArrayList<String>();
                    List<String> numberRangeIllegalFieldNames = new ArrayList<String>();
                    List<String> stringLengthFieldNames = new ArrayList<String>();
                    List<String> stringLengthIllegalFieldNames = new ArrayList<String>();
                    for (Field field : fields) {
                        Class<?> type = field.getType();
                        String fieldName = field.getName();
                        String data = request.getParameter(fieldName);
                        NotNull notNull = field.getAnnotation(NotNull.class);
                        if (notNull != null) {// ?????????????????????
                            if (Strings.isNullOrEmpty(data)) {
                                notNullIllegalFieldNames.add(fieldName);
                            }
                            notNullFieldNames.add(fieldName);
                        }

                        StringLength stringLength = field.getAnnotation(StringLength.class);
                        if (stringLength != null) {// ???????????????????????????????????????string????????????
                            if (type == String.class) {//?????????String??????
                                if (stringLength.canNull()) {//????????????
                                    if (!Strings.isNullOrEmpty(data)) {//??????????????????????????????
                                        if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                                            stringLengthIllegalFieldNames.add(fieldName + ":length length range (" + stringLength.Min() + "," + stringLength.Max() + ")");
                                        }
                                    }else {//?????????????????????????????????min<=0
                                        if (stringLength.Min() > 0) {
                                            stringLengthIllegalFieldNames.add(fieldName + ":need length (" + stringLength.Min() + ") at least");
                                        }
                                    }
                                }else {//????????????
                                    if (Strings.isNullOrEmpty(data)) {//??????
                                        stringLengthIllegalFieldNames.add(fieldName + ":is null");
                                    }else {//?????????
                                        if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                                            stringLengthIllegalFieldNames.add(fieldName + ":length range (" + stringLength.Min() + "," + stringLength.Max() + ")");
                                        }
                                    }
                                }

                                stringLengthFieldNames.add(fieldName);
                            }
                        }

                        Range range = field.getAnnotation(Range.class);
                        if (range != null) {
                            if (data == null) {
                                numberRangeIllegalFieldNames.add(fieldName + " is null");
                            }else {
                                boolean result = chargeNumber(data, range);
                                if (!result) {
                                    numberRangeIllegalFieldNames.add(fieldName + ":" + data);
                                }
                            }
                            numberRangeFieldNames.add(fieldName);
                        }
                    }

                    if (notNullFieldNames.size() == 0 && numberRangeFieldNames.size() == 0 && stringLengthFieldNames.size() == 0) {
                        cacheNothingReflect.add(clazz);
                    }else {
                        // ??????????????????,??????cache
                        if (notNullFieldNames.size() > 0) {
                            cacheNotNullReflect.put(clazz, notNullFieldNames);
                            if (notNullIllegalFieldNames.size() > 0) {
                                sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(notNullIllegalFieldNames) + "] is null!");
                                return false;
                            }
                        }
                        if (numberRangeFieldNames.size() > 0) {
                            cacheNumberRangeReflect.put(clazz, numberRangeFieldNames);
                            if (numberRangeIllegalFieldNames.size() > 0) {
                                sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(numberRangeIllegalFieldNames) + "] is illegal!");
                                return false;
                            }
                        }
                        if (stringLengthFieldNames.size() > 0) {
                            cacheStringLengthReflect.put(clazz, stringLengthFieldNames);
                            if (stringLengthIllegalFieldNames.size() > 0) {
                                sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(stringLengthIllegalFieldNames) + "] is illegal!");
                                return false;
                            }
                        }
                    }
                }
            }else {
                if (cacheNothingReflect.contains(clazz)) {//????????????????????????
                    return true;
                }

                if (cacheNotNullReflect.containsKey(clazz)) {
                    List<String> fieldNames = cacheNotNullReflect.get(clazz);
                    List<String> notNullIllegalFieldNames = new ArrayList<String>();
                    for (String fieldName : fieldNames) {
                        String data = request.getParameter(fieldName);
                        if (Strings.isNullOrEmpty(data)) {
                            notNullIllegalFieldNames.add(fieldName);
                        }
                    }

                    if (notNullIllegalFieldNames.size() > 0) {
                        sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(notNullIllegalFieldNames) + "] is null!");
                        return false;
                    }
                }

                if (cacheNumberRangeReflect.containsKey(clazz)) {
                    List<String> fieldNames = cacheNumberRangeReflect.get(clazz);
                    List<String> numberRangeIllegalFieldNames = new ArrayList<String>();
                    for (String fieldName : fieldNames) {
                        String data = request.getParameter(fieldName);
                        Range range = getField(clazz, fieldName).getAnnotation(Range.class);
                        if (range != null) {
                            if (data == null) {
                                numberRangeIllegalFieldNames.add(fieldName + " is null");
                            }else {
                                boolean result = chargeNumber(data, range);
                                if (!result) {
                                    numberRangeIllegalFieldNames.add(fieldName + ":" + data);
                                }
                            }
                        }
                    }

                    if (numberRangeIllegalFieldNames.size() > 0) {
                        sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(numberRangeIllegalFieldNames) + "] is illegal!");
                        return false;
                    }
                }

                if (cacheStringLengthReflect.containsKey(clazz)) {
                    List<String> fieldNames = cacheStringLengthReflect.get(clazz);
                    List<String> stringLengthIllegalFieldNames = new ArrayList<String>();
                    for (String fieldName : fieldNames) {
                        String data = request.getParameter(fieldName);
                        StringLength stringLength = getField(clazz, fieldName).getAnnotation(StringLength.class);
                        if (stringLength.canNull()) {//????????????
                            if (!Strings.isNullOrEmpty(data)) {//??????????????????????????????
                                if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                                    stringLengthIllegalFieldNames.add(fieldName + ":length range (" + stringLength.Min() + "," + stringLength.Max() + ")");
                                }
                            }else {//?????????????????????????????????min<=0
                                if (stringLength.Min() > 0) {
                                    stringLengthIllegalFieldNames.add(fieldName + ":need length (" + stringLength.Min() + ") at least");
                                }
                            }
                        }else {//????????????
                            if (Strings.isNullOrEmpty(data)) {//??????
                                stringLengthIllegalFieldNames.add(fieldName + ":is null");
                            }else {//?????????
                                if (data.length() > stringLength.Max() || data.length() < stringLength.Min()) {//??????????????????max
                                    stringLengthIllegalFieldNames.add(fieldName + ":length range (" + stringLength.Min() + "," + stringLength.Max() + ")");
                                }
                            }
                        }
                    }

                    if (stringLengthIllegalFieldNames.size() > 0) {
                        sendErrorMsg(response, ErrorConstants.ERR_ILLEGAL_REQUEST, "params [" + buildParamsErrorMsg(stringLengthIllegalFieldNames) + "] is illegal!");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            LogHelp.doLogException(e);
            return false;
        }

        return true;
    }

    private Field getField(Class<?> clazz, String fieldName) {
        Field[] fields = getAllFields(clazz);
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }

        return null;
    }

    /**
     * ??????????????????
     *
     * @param clazz
     * @param parents
     */
    private static void getAllParents(Class<?> clazz, LinkedList<Class<?>> parents) {
        if (clazz == null) {
            return;
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {//Object ??????????????????

            parents.addLast(superClass);
            getAllParents(superClass, parents);
        }else {
            return;
        }
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param clazz
     * @return
     */
    private static Field[] getAllFields(Class<?> clazz) {
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

        Field[] fields = Utils.combineArray(Field.class, myFields, fis.toArray(new Field[fis.size()]));
        return fields;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private String buildParamsErrorMsg(List<String> illegalFieldNames) {
        String data = "";
        if (illegalFieldNames.size() > 0) {
            for (String fieldName : illegalFieldNames) {
                data += fieldName + ", ";
            }
            data = data.substring(0, data.length() - 2);
        }
        return data;
    }

    private boolean chargeNumber(String data, Range range) {
        byte boundary = range.boundary();
        double min = range.Min();
        double max = range.Max();
        if (Strings.isNullOrEmpty(data)) {
            return false;
        }

        try {
            if (data.indexOf(".") != -1) {//???double??????
                Double d = Double.parseDouble(data);
                if (0B11 == boundary) {//????????????

                    if (d >= min && d <= max) {
                        return true;
                    }
                    return false;
                }else if (0B10 == boundary) {//?????????????????????

                    if (d >= min && d < max) {
                        return true;
                    }
                    return false;
                }else if (0B01 == boundary) {//?????????????????????

                    if (d > min && d <= max) {
                        return true;
                    }
                    return false;
                }else if (0B00 == boundary) {//???????????????

                    if (d > min && d < max) {
                        return true;
                    }
                    return false;
                }
                return false;
            }else {
                Long d = Long.parseLong(data);
                if (0B11 == boundary) {//????????????

                    if (d >= min && d <= max) {
                        return true;
                    }
                    return false;
                }else if (0B10 == boundary) {//?????????????????????

                    if (d >= min && d < max) {
                        return true;
                    }
                    return false;
                }else if (0B01 == boundary) {//?????????????????????

                    if (d > min && d <= max) {
                        return true;
                    }
                    return false;
                }else if (0B00 == boundary) {//???????????????

                    if (d > min && d < max) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
        } catch (NumberFormatException e) {
            LogHelp.doLogException(e);
            return false;
        }
    }
}
