package de.mpg.mpdl.mpgcity

import de.fyt.mvvm.globalsetting.IGlobalHttpInterceptor
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GlobalHttpInterceptor : IGlobalHttpInterceptor {

    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {

        return request
    }

    override fun onHttpResultResponse(chain: Interceptor.Chain, response: Response): Response {
        return response
    }
}