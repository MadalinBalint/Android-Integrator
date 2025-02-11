package com.mendelin.androidintegrator.exchangerate.data.api

import com.mendelin.androidintegrator.exchangerate.data.model.GetLatestValueDto
import com.mendelin.androidintegrator.shared.*
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import nl.adaptivity.xmlutil.serialization.XML

class DefaultExchangeRateApi(private val httpClient: HttpClient) : ExchangeRateApi {
    private val BASE_URL = "http://infovalutar.ro/curs.asmx?wsdl"

    override suspend fun getLatestValue(currency: String): AiResult<GetLatestValueDto, String> {
        val soapRequest = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                <soap12:Body>
                    <getlatestvalue xmlns="http://www.infovalutar.ro/">
                        <Moneda>$currency</Moneda>
                    </getlatestvalue>
                </soap12:Body>
            </soap12:Envelope>
            """.trimIndent()

        return ktorResponseXml(
            deserialize = { XML.decodeFromString(GetLatestValueDto.serializer(), it) }
        ) {
            httpClient.post(BASE_URL) {
                contentType(ContentType.Text.Xml)
                setBody(soapRequest)
            }
        }
    }
}
