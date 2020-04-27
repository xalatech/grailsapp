package sivadm

class RemoteFunctionTagLib {
    def remoteFunction =  { attrs, body ->
        def domeneKlasse = attrs['domeneKlasse']
        def action = attrs['action']
        def params = attrs['params']
        def onComplete = attrs['onComplete']

        def postAction

        if(action) {
            postAction = "/sivadmin/" + domeneKlasse + "/" + action
        }

        def script = ""

        script += "<script>"
        script += "\$(\"#remoteFunctionForm\").submit(function() {"
        script += onComplete
        script += "});"
        script += "</script>"

        def paramStr = ""

        if(params) {
            String[] split = params.split("=")
            paramStr += "<input name=\"" + split[0] + "\" id=\"" + split[0] + "\" type=\"hidden\" value=\"" + split[1] + "\" />\n"
        }

        def html = ""

        html += "<form id=\"remoteFunctionForm\" method=\"post\" action=\"" + postAction + "\">\n"
        html += paramStr
        html += "</form>\n"

        out << script
        out << html
    }
}