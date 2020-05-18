package com.naive.phase.Block.BlockTransmitter;

import com.naive.phase.Base.Block.PhaseTiledBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTransmitter extends PhaseTiledBase {
    protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.3125D, 0.9375D, 0.375D, 0.6875D, 1.0D, 0.625D);
    protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.0625D, 0.625D);
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.3125D, 0.375D, 0.9375D, 0.6875D, 0.625D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.0625D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.9375D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.0625D, 0.625D, 0.6875D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyInteger SENDER = PropertyInteger.create("sender", 0, 1);

    public BlockTransmitter(String registryName) {
        super(registryName, Material.IRON);
    }

    public BlockTransmitter(String registryName, MapColor mapColorIn) {
        super(registryName, Material.IRON, mapColorIn);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return canPlaceBlock(worldIn, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : EnumFacing.values()) {
            if (canPlaceBlock(worldIn, pos, enumfacing)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return canPlaceBlock(worldIn, pos, facing) ? this.getDefaultState().withProperty(FACING, facing).withProperty(SENDER, 0) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(SENDER, 0);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (this.checkForDrop(worldIn, pos, state) && !canPlaceBlock(worldIn, pos, state.getValue(FACING))) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing enumfacing = state.getValue(FACING);

        switch (enumfacing) {
            case EAST:
                return AABB_EAST;
            case WEST:
                return AABB_WEST;
            case SOUTH:
                return AABB_SOUTH;
            case NORTH:
            default:
                return AABB_NORTH;
            case UP:
                return AABB_UP;
            case DOWN:
                return AABB_DOWN;
        }
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (this.canPlaceBlockAt(worldIn, pos)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

    protected static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction) {
        BlockPos blockpos = pos.offset(direction.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        boolean flag = iblockstate.getBlockFaceShape(worldIn, blockpos, direction) == BlockFaceShape.SOLID;
        Block block = iblockstate.getBlock();

        if (direction == EnumFacing.UP) {
            return iblockstate.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
        } else {
            return !isExceptBlockForAttachWithPiston(block) && flag;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing;

        switch (meta & 7) {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(SENDER, meta >> 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i;

        switch (state.getValue(FACING)) {
            case EAST:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case NORTH:
                i = 4;
                break;
            case UP:
            default:
                i = 5;
                break;
            case DOWN:
                i = 0;
        }
        return i + (state.getValue(SENDER) << 3);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }


    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SENDER);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
