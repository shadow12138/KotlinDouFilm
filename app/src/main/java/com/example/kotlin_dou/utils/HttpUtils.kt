package com.example.kotlin_dou.utils


class HttpUtils {

    companion object{
        fun getListFromHtml(
            html: String?,
            list_start: String,
            item_start: String,
            startTags: List<String>,
            endTags: List<String>,
            properties: List<String>
        ): List<Map<String, String>> {
            html?.run {
                var index = indexOf(list_start)
                val results = mutableListOf<Map<String, String>>()
                while (true) {
                    val result = mutableMapOf<String, String>()
                    index = indexOf(item_start, index)
                    if (index == -1)
                        break

                    for (i in startTags.indices) {
                        val startTag = startTags[i]
                        val endTag = endTags[i]
                        val property = properties[i]

                        val startIndex = indexOf(startTag, index)
                        if (startIndex == -1) {
                            index += item_start.length
                            break
                        }

                        val endIndex = indexOf(endTag, startIndex + startTag.length)
                        index = endIndex
                        if (endIndex == -1) {
                            break
                        }

                        val content = substring(startIndex + startTag.length, endIndex)
                        result[property] = content
                    }

                    if(result.size < properties.size)
                        break

                    results.add(result)
                }
                return results
            }
            return mutableListOf()
        }
    }

}