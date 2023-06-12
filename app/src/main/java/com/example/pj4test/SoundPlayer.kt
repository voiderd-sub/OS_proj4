package com.example.pj4test

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

object SoundPlayer {
    var DING_DONG = R.raw.sound_dingdong

    private var soundPool: SoundPool? = null
    private var soundPoolMap: HashMap<Int, Int>? = null

    // sound media initialize
    fun initSounds(context: Context?) {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()
        soundPoolMap = HashMap(1)
        soundPoolMap!![DING_DONG] =
            soundPool!!.load(context, DING_DONG, 1)
    }

    fun play(raw_id: Int) {
        if (soundPoolMap!!.containsKey(raw_id)) {
            soundPool!!.play(soundPoolMap!![raw_id]!!, 1f, 1f, 1, 0, 1f)
        }
    }
}