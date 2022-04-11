package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.registry.MalumSoundRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;

public class VanishingEntryObject extends EntryObject
{
    public VanishingEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void exit() {
        final MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity playerEntity = client.player;
        playerEntity.playSound(MalumSoundRegistry.SUSPICIOUS_SOUND, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ProgressionBookScreen.objects.remove(this);
    }
}
