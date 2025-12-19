package pro.fazeclan.river.stupid_express.modifier.allergic.cca;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import pro.fazeclan.river.stupid_express.StupidExpress;

import java.util.UUID;

public class AllergicComponent implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<AllergicComponent> KEY =
            ComponentRegistry.getOrCreate(StupidExpress.id("allergic"), AllergicComponent.class);

    private final Player player;
    public int armor = 0;

    @Getter
    @Setter
    public String allergyType;

    @Getter
    @Setter
    private UUID allergic;

    @Getter
    @Setter
    private int glowTicks;

    public AllergicComponent(Player player) {
        this.player = player;
    }

    public void serverTick() {
        if (this.glowTicks > 0) {
            --this.glowTicks;
        }
        this.sync();
    }

    public void giveArmor() {
        armor = 1;
        sync();
    }

    public void reset() {
        this.allergic = null;
        this.allergyType = null;
        this.glowTicks = 0;
        this.armor = 0;
        sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public boolean isAllergic() {
        return this.allergic != null && !this.allergic.equals(UUID.fromString("e1e89fbb-3beb-492a-b1be-46a4ce19c9d1"));
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.allergic = tag.contains("allergic") ? tag.getUUID("allergic") : null;
        this.armor = tag.contains("armor") ? tag.getInt("armor") : 0;
        this.glowTicks = tag.contains("glow_ticks") ? tag.getInt("glow_ticks") : 0;
        this.allergyType = tag.contains("allergy_type") ? tag.getString("allergy_type") : "none";
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putUUID("allergic", this.allergic != null ? this.allergic : UUID.fromString("e1e89fbb-3beb-492a-b1be-46a4ce19c9d1"));
        tag.putInt("armor", this.armor);
        tag.putInt("glow_ticks", this.glowTicks);
        tag.putString("allergy_type", this.allergyType != null ? this.allergyType : "none");
    }
}
