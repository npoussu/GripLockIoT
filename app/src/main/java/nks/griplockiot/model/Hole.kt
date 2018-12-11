package nks.griplockiot.model

import java.io.Serializable

/**
 * Hole: POKO for holding data about a hole, implements Serializable
 */
data class Hole(var hole: Int, var par: Int, var length: Int) : Serializable
