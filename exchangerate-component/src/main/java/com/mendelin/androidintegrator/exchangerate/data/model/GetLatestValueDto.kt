package com.mendelin.androidintegrator.exchangerate.data.model

import kotlinx.serialization.*
import nl.adaptivity.xmlutil.serialization.*

@Serializable
@XmlSerialName("Envelope", "http://www.w3.org/2003/05/soap-envelope", "")
internal data class GetLatestValueDto(
    @SerialName("Body")
    @XmlElement(true)
    val body: SoapBodyDto
)

@Serializable
@XmlSerialName("Body", "", "")
internal data class SoapBodyDto(
    @SerialName("getlatestvalueResponse")
    @XmlElement(true)
    val response: GetLatestValueResponseDto
)

@Serializable
@XmlSerialName("getlatestvalueResponse", "http://www.infovalutar.ro/", "")
internal data class GetLatestValueResponseDto(
    @SerialName("getlatestvalueResult")
    @XmlElement(true) val value: Double
)
