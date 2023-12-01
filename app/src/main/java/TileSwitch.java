package moe.shamiko;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

@TargetApi(Build.VERSION_CODES.N)
public class TileSwitch extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        int oldState = getQsTile().getState();
        boolean isWhitelist = oldState == Tile.STATE_INACTIVE;
        Shamiko.toggleWhitelist(this, isWhitelist);
        updateTileState(isWhitelist ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
    }

    private void updateTileState(int state) {
        Tile tile = getQsTile();
        tile.setState(state);
        if (state == Tile.STATE_ACTIVE) {
            tile.setSubtitle("Whitelist");
        } else {
            tile.setSubtitle("Blacklist");
        }
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        boolean whitelistModeOn = Shamiko.isWhitelistModeOn();
        updateTileState(whitelistModeOn ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
    }
}
