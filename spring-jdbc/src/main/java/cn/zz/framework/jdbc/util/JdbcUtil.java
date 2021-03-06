package cn.zz.framework.jdbc.util;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */

import cn.zz.framework.core.entity.BeanEntity;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.StringUtil;
import cn.zz.framework.jdbc.annotation.Column;
import cn.zz.framework.jdbc.annotation.DBVague;
import cn.zz.framework.jdbc.annotation.Table;
import cn.zz.framework.jdbc.entity.JDBCEntity;
import cn.zz.framework.jdbc.entity.Pager;
import cn.zz.framework.jdbc.entity.Where;
import cn.zz.framework.jdbc.exception.BuildModeltException;
import cn.zz.framework.jdbc.exception.BuildSqlException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {


    /**
     * 是否反驼峰表名
     */
    private static final boolean IS_UNPARSE_TABLE=true;
    /**
     * 是否反驼峰字段名
     */
    private static final boolean IS_UNPARSE_FIELD=false;


    /**
     * 获取模型对应的数据库表名
     *
     * @param obj
     * @return
     */
    public static String getTableName(Object obj) {
        try {
            Class<?> clazz=obj.getClass();
            if(obj instanceof Class<?>){
                clazz=(Class<?>) obj;
            }
            String modelName=clazz.getSimpleName();
            return  getTableName(modelName);
        } catch (Exception e) {

        }
        return null;
    }
    public static String getTableName(String modelName) {
        try {
            if(IS_UNPARSE_TABLE){
                return unParsParaName(modelName);
            }
            return modelName;
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 根据字段名获取数据库列名
     * @param fieldName
     * @return
     */
    private static String getColumnName(String fieldName){
        if(!IS_UNPARSE_FIELD){
            return fieldName;
        }
        return unParsParaName(fieldName);
    }
    /**
     * 下划线命名转驼峰式
     *
     * @param paraName
     * @return
     */
    private static String parsParaName(String paraName) {
        if (paraName == null) {
            return null;
        }
        if (paraName.indexOf("_") > -1) {
            String[] paraNames = paraName.split("_");
            if (paraNames.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(paraNames[0]);
                for (int i = 1; i < paraNames.length; i++) {
                    sb.append(firstUpcase(paraNames[i]));
                }
                return sb.toString();
            }
        }
        return paraName;
    }

    /**
     * 驼峰式命名转下划线
     *
     * @param paraName
     * @return
     */
    public static String unParsParaName(String paraName) {
        char[] chrs = paraName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chrs.length; i++) {
            char chr = chrs[i];
            if (i != 0 && Character.isUpperCase(chr)) {
                sb.append("_");
            }
            sb.append(String.valueOf(chr).toLowerCase());
        }
        return sb.toString();
    }
    /**
     * 根据数据库列名获取对象字段名
     */
    public static String getFieldName(String columnName){
        if(IS_UNPARSE_FIELD){
            return parsParaName(columnName);
        }
        return columnName;
    }
    /**
     * 获取模型对应的数据库表名
     *
     * @param obj
     * @return
     */
    public static String getTableName(Class<?> clazz) {
        try {
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (!StringUtil.isNullOrEmpty(table)) {
                if (!StringUtil.isNullOrEmpty(table.value())) {
                    return table.value();
                }
            }
            return getTableName(clazz.getSimpleName());
        } catch (Exception e) {

        }
        return null;
    }
    /**
     * 获取模型对于数据库字段名
     *
     * @param field
     * @return
     */
    public static String getColumnName(BeanEntity field) {
        Annotation[] annots = field.getFieldAnnotations();
        if (StringUtil.isNullOrEmpty(annots)) {
            return getColumnName(field.getFieldName());
        }
        for (Annotation annot : annots) {
            if (annot instanceof Column) {
                String fieldName = ((Column) annot).value();
                if (!StringUtil.isNullOrEmpty(fieldName)) {
                    return fieldName;
                }
            }
        }
        return getColumnName(field.getFieldName());
    }


    /**
     * 对象转为map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objToSqlParaMap(Object obj) {
        try {
            BeanInfo sourceBean = Introspector.getBeanInfo(obj.getClass(),
                    java.lang.Object.class);
            PropertyDescriptor[] sourceProperty = sourceBean
                    .getPropertyDescriptors();
            if (sourceProperty == null) {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>(
                    sourceProperty.length * 2);
            for (PropertyDescriptor tmp : sourceProperty) {
                map.put(getColumnName(tmp.getName()), tmp.getReadMethod()
                        .invoke(obj));
            }
            return map;
        } catch (Exception e) {
            throw new BuildSqlException("编译sql报错>>"+obj, e);
        }
    }

    /**
     * 首个字符串大写
     *
     * @param s
     * @return
     */
    public static String firstUpcase(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0)))
                .append(s.substring(1)).toString();
    }




    /**
     * 解析分页条件
     *
     * @param pager
     * @return
     */
    public static String parsPagerSQL(Pager pager) {
        // 封装分页条件
        if (StringUtil.isNullOrEmpty(pager.getCurrentPage())) {
            pager.setCurrentPage(1);
        }
        if (StringUtil.isNullOrEmpty(pager.getPageSize())) {
            pager.setPageSize(10);
        }
        Integer startRows = (pager.getCurrentPage() - 1) * pager.getPageSize();
        return MessageFormat.format(" limit {0},{1} ",
                String.valueOf(startRows), String.valueOf(pager.getPageSize()));
    }

    /**
     * 解析对象条件、where条件、分页条件
     *
     * @param obj   对象条件
     * @param where where条件
     * @param pager 分页条件
     * @return
     */
    public static JDBCEntity parseSQL(Object obj, Where where, Pager pager, String orderField, Boolean isDesc) {
        if (obj == null) {
            return null;
        }
        // 获取表名
        String tableName = getTableName(obj);
        StringBuilder sb = new StringBuilder(MessageFormat.format("select * from {0} where 1=1 ", tableName));
        List<Object> params = new ArrayList<Object>();
        // 封装对象内置条件,默认以等于
        if (!(obj instanceof java.lang.Class)) {
            List<BeanEntity> prpres = PropertUtil.getBeanFields(obj);
            if (prpres == null || prpres.isEmpty()) {
                return null;
            }
            for (BeanEntity entity : prpres) {
                if (entity.getFieldValue() == null) {
                    continue;
                }
                if (!String.class.isAssignableFrom(entity.getFieldType())) {
                    sb.append(MessageFormat.format(" and {0}=? ", JdbcUtil.getColumnName(entity)));
                    params.add(entity.getFieldValue());
                    continue;
                }
                DBVague vague = entity.getAnnotation(DBVague.class);
                if (vague == null || StringUtil.isNullOrEmpty(vague.value())) {
                    sb.append(MessageFormat.format(" and {0}=? ", JdbcUtil.getColumnName(entity)));
                    params.add(entity.getFieldValue());
                    continue;
                }
                sb.append(MessageFormat.format(" and {0} like ? ", JdbcUtil.getColumnName(entity)));
                String example = vague.value();
                params.add(example.replace("#{0}", entity.getFieldValue().toString()));
            }
        }
        // 封装where条件
        if (!StringUtil.isNullOrEmpty(where) && !StringUtil.isNullOrEmpty(where.getWheres())) {
            List<Where.WhereFace> wheres = where.getWheres();
            for (Where.WhereFace childWhere : wheres) {
                if (childWhere == null) {
                    continue;
                }
                if (Where.OrWhere.class.isAssignableFrom(childWhere.getClass())) {
                    Where.OrWhere orWheres = (Where.OrWhere) childWhere;
                    if (StringUtil.isNullOrEmpty(orWheres.getWheres())) {
                        continue;
                    }
                    sb.append(" and (1=2");
                    for (Where.ThisWhere orWhere : orWheres.getWheres()) {
                        sb.append(MessageFormat.format(" or {0} {1} ", orWhere.getFieldName(), orWhere.getSymbol()));
                        if (StringUtil.isNullOrEmpty(orWhere.getFieldValues())) {
                            continue;
                        }
                        String inParaSql = StringUtil.getInPara(orWhere.getFieldValues().size());
                        sb.append(MessageFormat.format(" ({0})  ", inParaSql));
                        for (Object value : orWhere.getFieldValues()) {
                            params.add(value);
                        }
                    }
                    sb.append(" )");
                    continue;
                }
                if(Where.ThisWhere.class.isAssignableFrom(childWhere.getClass())) {
                    Where.ThisWhere thisWhere=(Where.ThisWhere) childWhere;
                    sb.append(MessageFormat.format(" and {0} {1} ", thisWhere.getFieldName(), thisWhere.getSymbol()));
                    if (StringUtil.isNullOrEmpty(thisWhere.getFieldValues())) {
                        continue;
                    }
                    String inParaSql = StringUtil.getInPara(thisWhere.getFieldValues().size());
                    sb.append(MessageFormat.format(" ({0})  ", inParaSql));
                    for (Object value : thisWhere.getFieldValues()) {
                        params.add(value);
                    }
                }

            }
        }
        // 封装排序条件
        if (!StringUtil.isNullOrEmpty(orderField)) {
            sb.append(MessageFormat.format(" order by {0}", orderField));
            if (isDesc != null && isDesc) {
                sb.append(" desc ");
            }
        }
        // 封装分页条件
        if (!StringUtil.isNullOrEmpty(pager)) {
            sb.append(parsPagerSQL(pager));
        }
        return new JDBCEntity(sb.toString(), params.toArray());
    }

    /**
     * map转为对象
     *
     * @param cla
     * @param sourceMap
     * @return
     */
    public static <T> T buildBean(Class<?> cla, Map<String, Object> sourceMap) {
        if (StringUtil.findNull(cla, sourceMap) > -1) {
            return null;
        }
        try {
            List<BeanEntity> entitys = PropertUtil.getBeanFields(cla);
            Object obj = cla.newInstance();
            for (BeanEntity entity : entitys) {
                Object value = sourceMap.get(getFieldName(entity.getFieldName()));
                PropertUtil.setProperties(obj, entity.getFieldName(), value);
            }
            return (T) obj;
        } catch (Exception e) {
            throw new BuildModeltException("解析为Model异常>>class:"+cla.getName()+",data:"+sourceMap, e);
        }
    }

    /**
     * map转为对象
     *
     * @param cla
     * @param sourceMaps
     * @return
     */
    public static <T> List<T> buildBeans(Class<?> cla, List<Map<String, Object>> sourceMaps) {
        if (StringUtil.findNull(cla, sourceMaps) > -1) {
            return null;
        }
        List<T> lines=new ArrayList<T>();
        for(Map<String, Object> line:sourceMaps){
            if(StringUtil.isNullOrEmpty(line)) {
                continue;
            }
            lines.add((T) buildBean(cla, line));
        }
        if(StringUtil.isNullOrEmpty(lines)){
            return null;
        }
        return  lines;
    }


}
