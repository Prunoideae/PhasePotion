package com.naive.phase.Auxiliary.Helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class VectorHelper {

    public static List<EnumFacing> getAdjacent(EnumFacing facing) {
        ArrayList<EnumFacing> result = new ArrayList<>();
        for (EnumFacing value : EnumFacing.VALUES) {
            if (value != facing && value != facing.getOpposite())
                result.add(value);
        }
        return result;
    }

    public static Vec3i mul(Vec3i vec, int num) {
        return new Vec3i(vec.getX() * num, vec.getY() * num, vec.getZ() * num);
    }

    public static Vec3i add(Vec3i vec1, Vec3i vec2) {
        return new Vec3i(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
    }

    public static List<BlockPos> getSurrounded(BlockPos pos, int radius, EnumFacing facing) {
        if (facing == null)
            facing = EnumFacing.UP;
        ArrayList<BlockPos> result = new ArrayList<>();

        boolean getX = facing.getFrontOffsetX() == 0;
        boolean getY = facing.getFrontOffsetY() == 0;
        boolean getZ = facing.getFrontOffsetZ() == 0;

        Vec3i beginOff = new Vec3i(getX ? -radius : 0, getY ? -radius : 0, getZ ? -radius : 0);
        Vec3i endOff = mul(beginOff, -1);

        for (BlockPos it : BlockPos.getAllInBox(pos.add(beginOff), pos.add(endOff)))
            if (!it.equals(pos))
                result.add(it);

        return result;
    }

    @SideOnly(Side.CLIENT)
    public static Vec3d rayTrace(double dist, float partialTicks, EntityPlayer player) {
        Vec3d vec3d = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        return vec3d.addVector(vec3d1.x * dist, vec3d1.y * dist, vec3d1.z * dist);
    }

    public static Vec3d cartesianToSpherical(double x, double y, double z) {
        double r = Math.sqrt(x * x + y * y + z * z);
        double a = Math.atan2(y, x) / Math.PI;
        double p = Math.acos(z / r) / Math.PI;
        return new Vec3d(r, a, p);
    }

    public static Vec3d cartesianToSpherical(Vec3d v) {
        return cartesianToSpherical(v.x, v.y, v.z);
    }

    public static Vec3d sphericalToCartesian(double r, double a, double p) {
        float x = (float) (r * Math.cos(a) * Math.sin(p));
        float y = (float) (r * Math.sin(a) * Math.sin(p));
        float z = (float) (r * Math.cos(p));
        return new Vec3d(x, y, z);
    }

    public static Vec3d sphericalToCartesian(Vec3d v) {
        return sphericalToCartesian(v.x, v.y * Math.PI, v.z * Math.PI);
    }

}