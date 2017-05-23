package com.netcracker.crm.dto.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
public class ModelMapper {

    private ModelMapper() {
    }

    public static <F, T> T map(Mapper<F, T> mapper, F from, Class<T> toClass) {
        try {
            T to = toClass.newInstance();
            mapper.configure(from, to);
            return to;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <F, T> List<T> mapList(Mapper<F, T> mapper, List<F> from, Class<T> toClass) {
        List<T> result = new ArrayList<>();
        try {
            for (F f : from) {
                T to = toClass.newInstance();
                mapper.configure(f, to);
                result.add(to);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
