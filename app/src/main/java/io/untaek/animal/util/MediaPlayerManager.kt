package io.untaek.animal.util

import android.media.MediaPlayer
import android.net.Uri

class MediaPlayerManager {
    enum class State{
        IDLE, PLAY, PAUSE, STOP, RELEASE
    }

    companion object {
        private var mediaPlayer: MediaPlayer = MediaPlayer()

        fun getMediaPlayer() = mediaPlayer

        fun generate() {
            mediaPlayer = MediaPlayer()
        }

        fun release() {
            if(mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
            mediaPlayer.reset()
            mediaPlayer.release()
        }

        fun changeSource(uri: Uri) {
            mediaPlayer.reset()
        }
    }
}