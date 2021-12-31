package com.albar.moderncryptography.util

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

class RsaKey() {
    private val generator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA").also {
        it.initialize(512)
    }

    private val pair: KeyPair = generator.generateKeyPair()

    val publicKey: PublicKey = pair.public

    val privateKey: PrivateKey = pair.private

    var byteForMoment: ByteArray = byteArrayOf(0x01, 0x02, 0x03)
}