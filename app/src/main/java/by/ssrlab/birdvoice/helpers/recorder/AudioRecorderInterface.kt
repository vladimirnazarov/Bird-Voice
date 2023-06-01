package by.ssrlab.birdvoice.helpers.recorder

import java.io.File

interface AudioRecorderInterface {
    fun start(outputFile: File)
    fun stop()
}