package com.naive.phase.Block.BlockEVETest;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Block.PhaseBlockBase;
import com.naive.phase.Base.Block.PhaseTiledBase;
import com.naive.phase.Render.Mistuned.MistuneRenderManager;
import com.naive.phase.TileEntity.TileEVETest.TileEVETest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEVETest extends PhaseTiledBase {

    @Registry.BlockInst
    public static BlockEVETest blockInst;

    @Registry.ItemInst
    public static Item itemInst;

    public BlockEVETest() {
        super("test_eve", Material.ROCK);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        MistuneRenderManager.setShouldEffect(true);
        if (worldIn.isRemote) {
            if (!playerIn.isSneaking()) {
                MistuneRenderManager.setShouldDecolor(!MistuneRenderManager.isShouldDecolor());
            } else {
                if (!MistuneRenderManager.isShouldDecolor())
                    MistuneRenderManager.setRgbOffsetOpt(.1f + MistuneRenderManager.getRgbOffsetOpt());
                MistuneRenderManager.setHorzFuzzOpt(.1f + MistuneRenderManager.getHorzFuzzOpt());
                MistuneRenderManager.setVertMovementOpt(.1f + MistuneRenderManager.getVertMovementOpt());
                MistuneRenderManager.setVertJerkOpt(.1f + MistuneRenderManager.getVertJerkOpt());
                MistuneRenderManager.setBottomStaticOpt(.1f + MistuneRenderManager.getBottomStaticOpt());
                MistuneRenderManager.setScalinesOpt(.1f + MistuneRenderManager.getScalinesOpt());
            }
        }
        return true;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) {
            MistuneRenderManager.setHorzFuzzOpt(0f);
            MistuneRenderManager.setVertMovementOpt(0f);
            MistuneRenderManager.setVertJerkOpt(0f);
            MistuneRenderManager.setBottomStaticOpt(0f);
            MistuneRenderManager.setScalinesOpt(0f);
            MistuneRenderManager.setRgbOffsetOpt(0f);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEVETest();
    }
}
