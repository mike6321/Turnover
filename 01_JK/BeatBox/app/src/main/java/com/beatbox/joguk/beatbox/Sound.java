package com.beatbox.joguk.beatbox;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];

        String fileExt = null;
        String[] fileCpnts = filename.split(".");
        if (fileCpnts.length > 1) {
            fileExt = fileCpnts[fileCpnts.length - 1];
        }

        if (fileExt != null) {
            mName = filename.replaceAll(fileExt, "");
        } else {
            mName = filename;
        }
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
