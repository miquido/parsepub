package com.miquido.parsepub.model

enum class MediaType(val type: String, val fileExtensions: List<String>) {

    CSS("text/css", listOf(".css")),
    EPUB("application/epub+zip", listOf(".epub")),
    GIF("image/gif", listOf(".gif")),
    JAVASCRIPT("text/javascript", listOf(".js")),
    JPG("image/jpeg", listOf(".jpg", ".jpeg")),
    MP3("audio/mpeg", listOf(".mp3")),
    MP4("video/mp4", listOf(".mp4")),
    NCX("application/x-dtbncx+xml", listOf(".ncx")),
    OGG("audio/ogg", listOf(".ogg")),
    OPENTYPE("application/vnd.ms-opentype", listOf(".otf")),
    PLS("application/pls+xml", listOf(".pls")),
    PNG("image/png", listOf(".png")),
    SMIL("application/smil+xml", listOf(".smil")),
    SVG("image/svg+xml", listOf(".svg")),
    TTF("application/x-truetype-font", listOf(".ttf")),
    WOFF("application/font-woff", listOf(".woff")),
    XHTML("application/xhtml+xml", listOf(".xhtml", ".htm", ".html")),
    XPGT("application/adobe-page-template+xml", listOf(".xpgt")),
    NOT_DEFINED("not_defined", listOf(""));

    companion object {
        fun getMediaType(fileName: String): MediaType {
            var mediaType: MediaType? = values().find {
                it.fileExtensions.firstOrNull { extension -> fileName.endsWith(extension, ignoreCase = true) } != null
            }
            if (mediaType == null) mediaType = NOT_DEFINED
            return mediaType
        }
    }
}