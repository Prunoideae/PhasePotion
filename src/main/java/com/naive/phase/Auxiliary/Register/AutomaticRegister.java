package com.naive.phase.Auxiliary.Register;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.naive.phase.Phase;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Pairs;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;

@EventBusSubscriber
public class AutomaticRegister {

    private static ASMDataTable ASMData;

    // Used to freeze the data.
    public static void init(ASMDataTable data) {
        ASMData = data;
    }

    public static List<Pairs<Class<?>, Field>> scanFor(Class<?> annotation) {
        Set<ASMDataTable.ASMData> targets = ASMData.getAll(annotation.getName());

        ArrayList<Pairs<Class<?>, Field>> results = new ArrayList<>();

        for (ASMDataTable.ASMData entry : targets) {
            final String targetClass = entry.getClassName();
            final String targetName = entry.getObjectName();

            try {
                Class<?> clazz = Class.forName(targetClass);
                Field field = clazz.getDeclaredField(targetName);
                results.add(new Pairs<Class<?>, Field>(clazz, field));
            } catch (Exception e) {
                Phase.logger.error("Error occured when trying to analyze annotation table!");
            }

        }
        return results;
    }

    public static Set<ASMData> scanRaw(Class<?> annotation) {
        return ASMData.getAll(annotation.getName());
    }
}