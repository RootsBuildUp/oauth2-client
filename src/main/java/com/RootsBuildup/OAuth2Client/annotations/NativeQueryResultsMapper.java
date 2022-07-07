package com.RootsBuildup.OAuth2Client.annotations;

import com.RootsBuildup.OAuth2Client.model.SerializableObjectConverter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Rashedul Islam
 * @since 07-01-2021
 */
public class NativeQueryResultsMapper {

    private static Logger log = LoggerFactory.getLogger(NativeQueryResultsMapper.class);

    @SneakyThrows
    public static <T> List<T> map(ResultSet rs, Class<T> genericType) {
        List<T> ret = new ArrayList<>();
        List<Field> mappingFields = getNativeQueryResultColumnAnnotatedFields(genericType);
        try {
            /** ConvertUtils do not throw exceptions on null values which is achieved by calling*/
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

            while (rs.next()) {
                T t = genericType.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.println(rs.getMetaData().getColumnName(i));
                    System.out.println(rs.getObject(i));
//                     BeanUtils.setProperty(t, rs.getMetaData().getColumnName(i), rs.getObject(i) != null ?rs.getObject(i).toString():null);
                }
                ret.add(t);
            }

        } catch (InstantiationException ie) {
            log.debug("Cannot instantiate: ", ie);
            ret.clear();
        } catch (IllegalAccessException iae) {
            log.debug("Illegal access: ", iae);
            ret.clear();
        } catch (InvocationTargetException ite) {
            log.debug("Cannot invoke method: ", ite);
            ret.clear();
        } catch (NoSuchMethodException e) {
            log.debug("No Such Method Exception: ", e);
            e.printStackTrace();
        }
        return ret;
    }

    // Get ordered list of fields
    private static <T> List<Field> getNativeQueryResultColumnAnnotatedFields(Class<T> genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<Field> orderedFields = Arrays.asList(new Field[fields.length]);
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(NativeQueryResultColumn.class)) {
                NativeQueryResultColumn nqrc = fields[i].getAnnotation(NativeQueryResultColumn.class);
                orderedFields.set(nqrc.index(), fields[i]);
            }
        }
        return orderedFields;
    }
}
