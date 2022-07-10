/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.behavior;

import baritone.Baritone;
import baritone.api.event.events.TickEvent;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.OptionalInt;
import java.util.function.Predicate;

public final class InventoryBehavior extends Behavior {

    //TODO fake mob inventories and tools. This whole class is marked TODO.
    public InventoryBehavior(Baritone baritone) {
        super(baritone);
    }

    @Override
    public void onTick(TickEvent event) {
//        if (!Baritone.settings().allowInventory.value) {
//            return;
//        }
//        if (event.getType() == TickEvent.Type.OUT) {
//            return;
//        }
//        if (ctx.mob().containerMenu != ctx.mob().inventoryMenu) {
//            // we have a crafting table or a chest or something open
//            return;
//        }
//        if (firstValidThrowaway() >= 9) { // aka there are none on the hotbar, but there are some in main inventory
//            swapWithHotBar(firstValidThrowaway(), 8);
//        }
//        int pick = bestToolAgainst(Blocks.STONE, PickaxeItem.class);
//        if (pick >= 9) {
//            swapWithHotBar(pick, 0);
//        }
    }

    public void attemptToPutOnHotbar(int inMainInvy, Predicate<Integer> disallowedHotbar) {
        OptionalInt destination = getTempHotbarSlot(disallowedHotbar);
        if (destination.isPresent()) {
            swapWithHotBar(inMainInvy, destination.getAsInt());
        }
    }

    public OptionalInt getTempHotbarSlot(Predicate<Integer> disallowedHotbar) {
        return OptionalInt.empty();
//        // we're using 0 and 8 for pickaxe and throwaway
//        ArrayList<Integer> candidates = new ArrayList<>();
//        for (int i = 1; i < 8; i++) {
//            if (ctx.mob().getInventory().items.get(i).isEmpty() && !disallowedHotbar.test(i)) {
//                candidates.add(i);
//            }
//        }
//        if (candidates.isEmpty()) {
//            for (int i = 1; i < 8; i++) {
//                if (!disallowedHotbar.test(i)) {
//                    candidates.add(i);
//                }
//            }
//        }
//        if (candidates.isEmpty()) {
//            return OptionalInt.empty();
//        }
//        return OptionalInt.of(candidates.get(new Random().nextInt(candidates.size())));
    }

    private void swapWithHotBar(int inInventory, int inHotbar) {
//        ctx.playerController().windowClick(ctx.mob().inventoryMenu.containerId, inInventory < 9 ? inInventory + 36 : inInventory, inHotbar, ClickType.SWAP, ctx.mob());
    }

    private int firstValidThrowaway() {
//        NonNullList<ItemStack> invy = ctx.mob().getInventory().items;
//        for (int i = 0; i < invy.size(); i++) {
//            if (Baritone.settings().acceptableThrowawayItems.value.contains(invy.get(i).getItem())) {
//                return i;
//            }
//        }
//        return -1;


        return -1;
    }

    private int bestToolAgainst(Block against, Class<? extends DiggerItem> cla$$) {
//        NonNullList<ItemStack> invy = ctx.mob().getInventory().items;
//        int bestInd = -1;
//        double bestSpeed = -1;
//        for (int i = 0; i < invy.size(); i++) {
//            ItemStack stack = invy.get(i);
//            if (stack.isEmpty()) {
//                continue;
//            }
//            if (Baritone.settings().itemSaver.value && (stack.getDamageValue() + Baritone.settings().itemSaverThreshold.value) >= stack.getMaxDamage() && stack.getMaxDamage() > 1) {
//                continue;
//            }
//            if (cla$$.isInstance(stack.getItem())) {
//                double speed = ToolSet.calculateSpeedVsBlock(stack, against.defaultBlockState()); // takes into account enchants
//                if (speed > bestSpeed) {
//                    bestSpeed = speed;
//                    bestInd = i;
//                }
//            }
//        }
//        return bestInd;
        return -1;
    }

    public boolean hasGenericThrowaway() {
        for (Item item : Baritone.settings().acceptableThrowawayItems.value) {
            if (throwaway(false, stack -> item.equals(stack.getItem()))) {
                return true;
            }
        }
        return false;
    }

    public boolean selectThrowawayForLocation(boolean select, int x, int y, int z) {
        return false; //TODO mobs cannot place blocks in this state
//        BlockState maybe = baritone.getBuilderProcess().placeAt(x, y, z, baritone.bsi.get0(x, y, z));
//        if (maybe != null && throwaway(select, stack -> stack.getItem() instanceof BlockItem && maybe.equals(((BlockItem) stack.getItem()).getBlock().getStateForPlacement(new BlockPlaceContext(new UseOnContext(ctx.world(), ctx.mob(), InteractionHand.MAIN_HAND, stack, new BlockHitResult(new Vec3(ctx.mob().position().x, ctx.mob().position().y, ctx.mob().position().z), Direction.UP, ctx.playerFeet(), false)) {}))))) {
//            return true; // gotem
//        }
//        if (maybe != null && throwaway(select, stack -> stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock().equals(maybe.getBlock()))) {
//            return true;
//        }
//        for (Item item : Baritone.settings().acceptableThrowawayItems.value) {
//            if (throwaway(select, stack -> item.equals(stack.getItem()))) {
//                return true;
//            }
//        }
//        return false;
    }

    public boolean throwaway(boolean select, Predicate<? super ItemStack> desired) {
        return throwaway(select, desired, Baritone.settings().allowInventory.value);
    }

    public boolean throwaway(boolean select, Predicate<? super ItemStack> desired, boolean allowInventory) {
        return false;
        //TODO this
//        PathfinderMob p = ctx.mob();
//        NonNullList<ItemStack> inv = p.getInventory().items;
//        for (int i = 0; i < 9; i++) {
//            ItemStack item = inv.get(i);
//            // this usage of settings() is okay because it's only called once during pathing
//            // (while creating the CalculationContext at the very beginning)
//            // and then it's called during execution
//            // since this function is never called during cost calculation, we don't need to migrate
//            // acceptableThrowawayItems to the CalculationContext
//            if (desired.test(item)) {
//                if (select) {
//                    p.getInventory().selected = i;
//                }
//                return true;
//            }
//        }
//        if (desired.test(p.getInventory().offhand.get(0))) {
//            // main hand takes precedence over off hand
//            // that means that if we have block A selected in main hand and block B in off hand, right clicking places block B
//            // we've already checked above ^ and the main hand can't possible have an acceptablethrowawayitem
//            // so we need to select in the main hand something that doesn't right click
//            // so not a shovel, not a hoe, not a block, etc
//            for (int i = 0; i < 9; i++) {
//                ItemStack item = inv.get(i);
//                if (item.isEmpty() || item.getItem() instanceof PickaxeItem) {
//                    if (select) {
//                        p.getInventory().selected = i;
//                    }
//                    return true;
//                }
//            }
//        }
//
//        if (allowInventory) {
//            for (int i = 9; i < 36; i++) {
//                if (desired.test(inv.get(i))) {
//                    swapWithHotBar(i, 7);
//                    if (select) {
//                        p.getInventory().selected = 7;
//                    }
//                    return true;
//                }
//            }
//        }
//
//        return false;
    }
}