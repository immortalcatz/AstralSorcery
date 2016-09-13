package hellfirepvp.astralsorcery.common.item;

import hellfirepvp.astralsorcery.common.entities.EntityGrindstone;
import hellfirepvp.astralsorcery.common.item.base.IGrindable;
import hellfirepvp.astralsorcery.common.item.base.IItemVariants;
import hellfirepvp.astralsorcery.common.item.base.IMetaItem;
import hellfirepvp.astralsorcery.common.registry.RegistryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ItemCraftingComponent
 * Created by HellFirePvP
 * Date: 17.08.2016 / 13:10
 */
public class ItemCraftingComponent extends Item implements IGrindable, IItemVariants {

    public ItemCraftingComponent() {
        setMaxStackSize(64);
        setHasSubtypes(true);
        setCreativeTab(RegistryItems.creativeTabAstralSorcery);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (MetaType type : MetaType.values()) {
            subItems.add(new ItemStack(itemIn, 1, type.getItemMeta()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        Item i = stack.getItem();
        if(i instanceof ItemCraftingComponent) {
            MetaType type = MetaType.fromMeta(stack.getItemDamage());
            return super.getUnlocalizedName(stack) + "." + type.getUnlocalizedName();
        }
        return super.getUnlocalizedName(stack);
    }

    @Override
    public boolean canGrind(EntityGrindstone grindstone, ItemStack stack) {
        MetaType type = MetaType.fromMeta(stack.getItemDamage());
        return type.isGrindable();
    }

    @Nonnull
    @Override
    public GrindResult grind(EntityGrindstone grindstone, ItemStack stack, Random rand) {
        MetaType type = MetaType.fromMeta(stack.getItemDamage());
        switch (type) {
            case STARMETAL_INGOT:
                if(rand.nextInt(20) == 0) {
                    return GrindResult.itemChange(new ItemStack(this, 1, MetaType.STARDUST.getItemMeta()));
                }
                break;
        }
        return GrindResult.failNoOp();
    }

    @Override
    public String[] getVariants() {
        String[] sub = new String[MetaType.values().length];
        MetaType[] values = MetaType.values();
        for (int i = 0; i < values.length; i++) {
            MetaType mt = values[i];
            sub[i] = mt.getUnlocalizedName();
        }
        return sub;
    }

    @Override
    public int[] getVariantMetadatas() {
        int[] sub = new int[MetaType.values().length];
        MetaType[] values = MetaType.values();
        for (int i = 0; i < values.length; i++) {
            MetaType mt = values[i];
            sub[i] = mt.getItemMeta();
        }
        return sub;
    }

    public static enum MetaType {

        AQUAMARINE,
        STARMETAL_INGOT,
        STARDUST;

        public String getUnlocalizedName() {
            return name().toLowerCase();
        }

        public int getItemMeta() {
            return ordinal();
        }

        public static MetaType fromMeta(int meta) {
            int ord = MathHelper.clamp_int(meta, 0, values().length -1);
            return values()[ord];
        }

        public boolean isGrindable() {
            return this == STARMETAL_INGOT;
        }
    }

}